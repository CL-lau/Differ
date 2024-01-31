package com.differ.dto;

import com.differ.entity.enumer.BodyType;
import com.differ.entity.enumer.RequestType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/27 23:32
 */
@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String statusCode;
    private String statusMessage;
    private String url;
    private String responseBody;
    private String requestUri;
    private BodyType bodyType;
    private RequestType requestType;
    private Map<String, String> params;
    private String requestBody;
    private int dispatcherId;
    private long httpServiceEntityId;
    private long resultFlag;
    private long ctime;
    private long utime;

    public HttpResult(String statusCode, String statusMessage, String url, String responseBody){
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.url = url;
        this.responseBody = responseBody;
    }

    public static void main(String[] args) {
        HttpResult httpResult = new HttpResult();
        Map<String, String> tmp = new HashMap<>();
        tmp.put("test", "test");
        httpResult.setParams(tmp);
        System.out.println(httpResult.toString());
    }
}