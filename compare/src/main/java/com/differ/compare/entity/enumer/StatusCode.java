package com.differ.compare.entity.enumer;

public enum StatusCode {
    SUCCESS(200),
    FAILURE(500);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}