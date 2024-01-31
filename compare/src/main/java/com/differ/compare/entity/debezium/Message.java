package com.differ.compare.entity.debezium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/5 16:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements Serializable {
    private Map<String, Object> data;
    private String dbType;
    private String handleType;
    private String database;
    private String table;
}
