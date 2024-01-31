package com.ryan.debezium.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Ryan
 * @date 2022年05月28日
 * @note
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeData {
    private Map<String, Object> after;
    private Map<String, Object> source;
    private Map<String, Object> before;
}
