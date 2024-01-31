package com.differ.entity.enumer;

public enum BodyType {
    JSON("application/json"),
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    MULTIPART_FORM_DATA("multipart/form-data"),
    PLAIN_TEXT("text/plain"),
    XML("text/xml"),
    OCTET_STREAM("application/octet-stream");
    private final String code;

    BodyType(String code) {
        this.code = code;
    }

    public String getValue() {
        return code;
    }
}