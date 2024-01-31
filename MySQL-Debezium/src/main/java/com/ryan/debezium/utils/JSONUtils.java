package com.ryan.debezium.utils;

import com.alibaba.fastjson.JSON;
import com.ryan.debezium.entity.ChangeData;

import java.util.Map;
import java.util.Objects;

/**
 * @author Ryan
 * @date 2022年05月28日
 * @note
 */
public class JSONUtils {

    public static Map<String, Object> getPayload(String value) {
        Map<String, Object> map = JSON.parseObject(value, Map.class);
        Map<String, Object> payload = JSON.parseObject(JSON.toJSONString(map.get("payload")), Map.class);
        return payload;
    }

    public static String getHandleType(Map<String, Object> payload) {
        String op = JSON.parseObject(JSON.toJSONString(payload.get("op")), String.class);
        if (Objects.nonNull(op)) {
            switch (op) {
                case "r":
                    return "READ";
                case "c":
                    return "CREATE";
                case "u":
                    return "UPDATE";
                case "d":
                    return "DELETE";
                default:
                    return "NONE";
            }
        } else {
            return "NONE";
        }

    }

    public static ChangeData getChangeData(Map<String, Object> payload) {
        return ChangeData.builder()
                .after(JSON.parseObject(JSON.toJSONString(payload.get("after")), Map.class))
                .source(JSON.parseObject(JSON.toJSONString(payload.get("source")), Map.class))
                .before(JSON.parseObject(JSON.toJSONString(payload.get("before")), Map.class))
                .build();
    }


}
