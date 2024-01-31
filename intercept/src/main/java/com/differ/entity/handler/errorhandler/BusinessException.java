package com.differ.entity.handler.errorhandler;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/1 20:08
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

