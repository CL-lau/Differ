package com.differ.compare.utils;

import com.differ.compare.entity.enumer.StatusCode;
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

    public static String bulidResponse(int code, String message, String content){
        ApiResponseUtil apiResponseUtil;
        if (code == StatusCode.SUCCESS.getCode()){
            apiResponseUtil =  new ApiResponseUtil(StatusCode.SUCCESS, message, content);
        }else {
            apiResponseUtil =  new ApiResponseUtil(StatusCode.FAILURE, message, content);
        }
        return FastjsonUtils.toJson(apiResponseUtil);
    }

    public static String bulidResponse(StatusCode statusCode, String message, String content){
        ApiResponseUtil apiResponseUtil = new ApiResponseUtil(StatusCode.SUCCESS, message, content);
        return FastjsonUtils.toJson(apiResponseUtil);
    }
}
