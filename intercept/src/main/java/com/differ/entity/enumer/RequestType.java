package com.differ.entity.enumer;

public enum RequestType {
    GET(1),
    POST(2),
    PUT(3),
    DELETE(4),
    UPDATE(5);
    private int code;
    RequestType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}