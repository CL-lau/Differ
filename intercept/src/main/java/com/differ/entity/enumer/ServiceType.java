package com.differ.entity.enumer;

public enum ServiceType{
    MASTER(1),
    NOVEL(2),
    SLAVE(3);

    private int code;
    ServiceType(int code) {
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
}
