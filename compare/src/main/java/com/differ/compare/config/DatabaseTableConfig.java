package com.differ.compare.config;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/4 15:58
 */
import java.util.List;

public class DatabaseTableConfig {
    private String databaseName;
    private List<String> tables;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}