package com.differ.entity.request;

import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/20 16:38
 */
public class HttpSetRequest {
    private String host;
    private String baseUri;
    private String requestUri;
    private String requestType;
    private Map<String, String> headersMap;
    private Map<String, String> params;
    private String bodyType;
    private String json;
    public String getURL() {
        return this.baseUri + "/" + this.requestUri;
    }
}
