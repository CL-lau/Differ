package com.ryan.debezium.listener;

import com.ryan.debezium.entity.ChangeData;
import com.ryan.debezium.service.MessageService;
import com.ryan.debezium.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.ryan.debezium.entity.Message;
import io.debezium.engine.format.Json;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class MysqlListener {

    @Resource
    private MessageService messageService;

    private final List<DebeziumEngine<ChangeEvent<String, String>>> engineList = new ArrayList<>();

    private MysqlListener(@Qualifier("mysqlProperties") List<Properties> list) {
        for (Properties props : list) {
            this.engineList.add(DebeziumEngine.create(Json.class)
                    .using(props)
                    .notifying(record -> {
                        receiveChangeEvent(record.value(), props.getProperty("debezium.name"));
                    }).build());
        }
    }

    private void receiveChangeEvent(String value, String name) {
        if (Objects.nonNull(value)) {
            Map<String, Object> payload = JSONUtils.getPayload(value);
            String handleType = JSONUtils.getHandleType(payload);
            if (!("NONE".equals(handleType) || "READ".equals(handleType))) {
                ChangeData changeData = JSONUtils.getChangeData(payload);
                Map<String, Object> data;
                if ("DELETE".equals(handleType)) {
                    data = changeData.getBefore();
                } else {
                    data = changeData.getAfter();
                }
                Message build = Message.builder()
                        .data(data)
                        .dbType("Mysql")
                        .database(String.valueOf(changeData.getSource().get("db")))
                        .table(String.valueOf(changeData.getSource().get("table")))
                        .handleType(handleType)
                        .build();
                log.info("【Debezium-" + name + "】" + build.toString());
                messageService.sendMessage(build);
            }
        }
    }

    @PostConstruct
    private void start() {
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            Executors.newSingleThreadExecutor().execute(engine);
        }
    }

    @PreDestroy
    private void stop() {
        for (DebeziumEngine<ChangeEvent<String, String>> engine : engineList) {
            if (engine != null) {
                try {
                    engine.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
