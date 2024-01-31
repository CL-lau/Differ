package com.debeziumtest.entity;

import cn.hutool.core.lang.Assert;
import io.debezium.engine.DebeziumEngine;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.SmartLifecycle;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/4 21:05
 */
@Data
public class DebeziumServerBootstrap implements InitializingBean, SmartLifecycle {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private DebeziumEngine<?> debeziumEngine;
    @Override
    public void start() {
        executor.execute(debeziumEngine);
    }
    @SneakyThrows
    @Override
    public void stop() {
        debeziumEngine.close();
    }
    @Override
    public boolean isRunning() {
        return false;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(debeziumEngine, "debeziumEngine must not be null");
    }
}