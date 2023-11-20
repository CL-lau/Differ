package com.differ.compare.entity.enumer;

import java.util.HashMap;
import java.util.Map;

public enum ServiceType {
    MASTER(1),
    NOVEL(2),
    SLAVE(3);

    Map<Integer, String> linear = new HashMap<>(){{
        put(1, "Master");
        put(2, "Novel");
        put(3, "Slave");
    }};;

    private int code;
    ServiceType(int code) {
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }

    public String getType() {
        return this.linear.get(this.code);
    }
}
