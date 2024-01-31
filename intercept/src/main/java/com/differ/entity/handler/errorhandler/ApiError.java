package com.differ.entity.handler.errorhandler;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/1 20:07
 */
@Getter
@Setter
@Data
@ToString
public class ApiError {
    private String code;
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters and setters
}
