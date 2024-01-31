package com.differ.compare.config;

import com.differ.compare.entity.debezium.DatabaseData;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/5 15:42
 */
//@Configuration
//@ConfigurationProperties(prefix = "debezium")
public class MysqlConfig {

    private List<DatabaseData> database;

    public void setDatabase(List<DatabaseData> database) {
        this.database = database;
    }

//    @Bean
    public List<String> tableList() {
        List<String> list = new ArrayList<>();
        for (DatabaseData dbd : database) {
            for (String str : dbd.getTable()) {
                list.add(str);
            }
        }
        return list;
    }

//    @Bean
    public List<Properties> mysqlProperties() {
        List<Properties> list = new ArrayList<>();

        String osName = System.getProperty("os.name");
        String path = "/";
        if (osName.startsWith("Windows")) {
            path = "C:/";
        } else if (osName.startsWith("Linux")) {
            path = "/opt/";
        }

        for (DatabaseData dbd : database) {
            //保存路径重新赋值
            dbd.setOffsetPath(path + dbd.getOffsetPath());
            dbd.setHistoryPath(path + dbd.getHistoryPath());

            Properties props = new Properties();
            props.setProperty("name", dbd.getName());
            props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
            props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
            props.setProperty("offset.storage.file.filename", dbd.getOffsetPath());
            props.setProperty("offset.flush.interval.ms", "600000");
            props.setProperty("database.hostname", dbd.getHost());
            props.setProperty("topic.prefix", "test");
            props.setProperty("database.port", dbd.getPort());
            props.setProperty("database.dbname", "differ");
            props.setProperty("database.user", dbd.getUsername());
            props.setProperty("database.password", dbd.getPassword());
            props.setProperty("database.server.id", dbd.getServerId());
            props.setProperty("database.serverTimezone", "UTC"); // 时区
            props.setProperty("database.server.name", "my_mysql_connector" + dbd.getName());
            props.setProperty("database.history",
                    "io.debezium.relational.history.FileDatabaseHistory");
            props.setProperty("database.history.file.filename", dbd.getHistoryPath());

            props.setProperty("topic.prefix", "test");
            String tableList = dbd.getTable().stream().map(item -> item.indexOf("&") > -1 ? item.substring(0, item.indexOf("&")) : item).collect(Collectors.joining(","));
            System.out.println("tableList" + " " + tableList);
            props.setProperty("table.include.list", tableList);
            list.add(props);
        }
        return list;
    }
}
