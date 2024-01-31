package com.debeziumtest;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/5 11:45
 */
public class DebeziumCDC {
    public static void main(String[] args) {

        final Properties props = new Properties();

        /* begin connector properties */
        props.setProperty("database.hostname", "127.0.0.1"); // ip
        props.setProperty("database.port", "3306");               // 端口
        props.setProperty("database.user", "root");               // 用户名
        props.setProperty("database.password", "root");         // 密码
        props.setProperty("database.server.id", "123456789"); // value可以任意修改,别重复
        props.setProperty("database.server.name", "differ");  // 可以任意修改,别重复
        props.setProperty("database.serverTimezone", "UTC"); // 时区
        // 1. 使用database.whitelist，只设置数据库（会通知全库的CDC信息）
        // 2. 使用table.whitelist，设置库名和表名（会通知单个库的单个表的CDC信息）
        props.setProperty("database.whitelist", "differ");    // 库名
//      props.setProperty("table.whitelist", "db_inventory_cdc.tb_products_cdc"); // 库.表名

        props.setProperty("name", "instala-core");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("offset.storage",
                "org.apache.kafka.connect.storage.FileOffsetBackingStore");
//        props.setProperty("offset.storage.file.filename", "C:/Users/liuc8/Desktop/offsets.dat");
        props.setProperty("offset.storage.file.filename", "/tmp/offsets.dat");
        props.setProperty("offset.flush.interval.ms", "60000");
        props.setProperty("database.history", "io.debezium.relational.history.FileDatabaseHistory");
//        props.setProperty("database.history.file.filename", "C:/Users/liuc8/Desktop/dbhistory.dat");
        props.setProperty("database.history.file.filename", "/tmp/dbhistory.dat");

//        props.setProperty("offset.storage", FileOffsetBackingStore.class.getCanonicalName());
//        props.setProperty("offset.flush.interval.ms", String.valueOf(10000L));
//        props.setProperty("key.converter.schemas.enable", "false");
//        props.setProperty("value.converter.schemas.enable", "true");
//        props.setProperty("include.schema.changes", "true");
//        props.setProperty("tombstones.on.delete", "false");
//        props.setProperty("database.history", FileDatabaseHistory.class.getCanonicalName());
//        props.setProperty("database.history.store.only.monitored.tables.ddl", "true");
//        props.setProperty("database.history.instance.name", UUID.randomUUID().toString());
//        props.setProperty("database.history.skip.unparseable.ddl", "true");

        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying(record -> {
                    // record中会有操作的类型（增、删、改）和具体的数据
                    // key是主键
                    System.out.println("record.key() = " + record.key());
                    System.out.println("record.value() = " + record.value());
                }).build();

        Executors.newSingleThreadExecutor().execute(engine);

        // Run the engine asynchronously ...
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);

    }
}
