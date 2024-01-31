package com.debeziumtest.entity;

import lombok.Data;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/4 22:34
 */
@Data
public class ChangeListenerModel {
    /**
     * 当前DB
     */
    private String db;
    /**
     * 当前表
     */
    private String table;
    /**
     * 操作类型 1add 2update 3 delete
     */
    private Integer eventType;
    /**
     * 操作时间
     */
    private Long changeTime;
    /**
     * 现数据
     */
    private String data;
    /**
     * 之前数据
     */
    private String beforeData;
}
