package com.differ.compare.config;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/4 15:58
 */
import java.util.List;

public class BinlogConfig {
    private List<DatabaseTableConfig> databases;

    public List<DatabaseTableConfig> getDatabases() {
        return databases;
    }

    public void setDatabases(List<DatabaseTableConfig> databases) {
        this.databases = databases;
    }
}
