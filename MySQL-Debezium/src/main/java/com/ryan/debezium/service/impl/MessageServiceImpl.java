package com.ryan.debezium.service.impl;

import com.ryan.debezium.entity.Message;
import com.ryan.debezium.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {


    @Override
    public void sendMessage(Message message) {
        System.out.println(message);
//        String exchange = "FANOUT.ORACLE.CHANGE." + message.getTable();
//        log.info("【RabbitMQ】Send message to '" + exchange);
//        rabbitTemplate.convertAndSend(exchange, null, JSON.toJSONString(message));
    }
}
