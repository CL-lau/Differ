package com.differ.compare.entity.debezium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/5 16:01
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
