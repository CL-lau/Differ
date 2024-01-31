package com.differ.entity.request;

import com.differ.entity.enumer.BodyType;
import com.differ.entity.enumer.RequestType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Data
@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpRequest {
    private String host;
    private String port;
    private String baseUri;
    private String requestUri;
    private RequestType requestType;
    private Map<String, String> headersMap;
    private Map<String, String> params;
    private BodyType bodyType;

//    public Map<String, String> getHeadersMap() {
//        if (headersMapJson != null) {
//            try {
//                return new ObjectMapper().readValue(headersMapJson, new TypeReference<Map<String, String>>() {});
//            } catch (IOException e) {
//                return new HashMap<>();
//            }
//        }
//        return new HashMap<>();
//    }
//
//    public void setHeadersMap(Map<String, String> headersMap) {
//        try {
//            headersMapJson = new ObjectMapper().writeValueAsString(headersMap);
//        } catch (JsonProcessingException e) {
//            headersMapJson = "";
//        }
//    }
//
//    private Map<String, String> getParams() {
//        if (paramsJson != null) {
//            try {
//                return new ObjectMapper().readValue(paramsJson, new TypeReference<Map<String, String>>() {});
//            } catch (IOException e) {
//                return new HashMap<>();
//            }
//        }
//        return new HashMap<>();
//    }
//
//    public void setParams(Map<String, String> params) {
//        try {
//            headersMapJson = new ObjectMapper().writeValueAsString(params);
//        } catch (JsonProcessingException e) {
//            headersMapJson = "";
//        }
//    }

    public String getURL() {
        return Optional.ofNullable(this.host).orElseGet(() -> this.baseUri + "/" + this.requestUri) +
                (this.host != null&&this.port!=null ? ":" + this.port : "");
    }
}
