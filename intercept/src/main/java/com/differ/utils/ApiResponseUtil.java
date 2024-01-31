package com.differ.utils;

import com.differ.entity.enumer.StatusCode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Data
@Slf4j
public class ApiResponseUtil {

    private StatusCode code;
    private String message;
    private String content;

    public ApiResponseUtil(StatusCode code, String message, String content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public static ApiResponseUtil bulidResponse(int code, String message, String content){
        if (code == StatusCode.SUCCESS.getCode()){
            return new ApiResponseUtil(StatusCode.SUCCESS, message, content);
        }else {
            return new ApiResponseUtil(StatusCode.FAILURE, message, content);
        }
    }
}
