package com.differ.entity.service.http;

import com.differ.entity.User;
import com.differ.entity.enumer.BodyType;
import com.differ.entity.enumer.RequestType;
import com.differ.entity.enumer.ServiceType;
import com.differ.entity.request.HttpRequest;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/20 14:12
 */

@Getter
@Setter
@Data
@NoArgsConstructor
@ToString
public class HttpSetEntity {
    private User user;
    private List<HttpSetServiceEntity> httpSetServiceEntities = new ArrayList<>();

    @Data
    @Setter
    @Getter
    @ToString
    public class HttpSetServiceEntity{
        private HttpSetRequest httpSetRequest;
        private String serviceType;
    }

    @Data
    @Setter
    @Getter
    @ToString
    public class HttpSetRequest {
        private String host;
        private String baseUri;
        private String requestUri;
        private String requestType;
        private Map<String, String> headersMap;
        private Map<String, String> params;
        private String bodyType;
        private String json;
    }

    public List<HttpServiceEntity> transToHttpServiceEntities(){
        List<HttpServiceEntity> httpServiceEntities = new ArrayList<>();
        Map<String, ServiceType> serviceTypeMap = new ConcurrentHashMap<>();
        Map<String, RequestType> requestTypeMap = new ConcurrentHashMap<>();
        Map<String, BodyType> bodyTypeMap = new ConcurrentHashMap<>();

        serviceTypeMap.put("Master", ServiceType.MASTER);
        serviceTypeMap.put("Novel", ServiceType.NOVEL);
        serviceTypeMap.put("Slave", ServiceType.SLAVE);
        serviceTypeMap.put("master", ServiceType.MASTER);
        serviceTypeMap.put("novel", ServiceType.NOVEL);
        serviceTypeMap.put("slave", ServiceType.SLAVE);
        serviceTypeMap.put("MASTER", ServiceType.MASTER);
        serviceTypeMap.put("NOVEL", ServiceType.NOVEL);
        serviceTypeMap.put("SLAVE", ServiceType.SLAVE);

        requestTypeMap.put("Get", RequestType.GET);
        requestTypeMap.put("Post", RequestType.POST);
        requestTypeMap.put("Put", RequestType.PUT);
        requestTypeMap.put("Delete", RequestType.DELETE);
        requestTypeMap.put("Update", RequestType.UPDATE);
        requestTypeMap.put("get", RequestType.GET);
        requestTypeMap.put("post", RequestType.POST);
        requestTypeMap.put("put", RequestType.PUT);
        requestTypeMap.put("delete", RequestType.DELETE);
        requestTypeMap.put("update", RequestType.UPDATE);
        requestTypeMap.put("GET", RequestType.GET);
        requestTypeMap.put("POST", RequestType.POST);
        requestTypeMap.put("PUT", RequestType.PUT);
        requestTypeMap.put("DELETE", RequestType.DELETE);
        requestTypeMap.put("UPDATE", RequestType.UPDATE);

        bodyTypeMap.put(BodyType.JSON.getValue(), BodyType.JSON);
        bodyTypeMap.put(BodyType.FORM_URLENCODED.getValue(), BodyType.FORM_URLENCODED);
        bodyTypeMap.put(BodyType.MULTIPART_FORM_DATA.getValue(), BodyType.MULTIPART_FORM_DATA);
        bodyTypeMap.put(BodyType.PLAIN_TEXT.getValue(), BodyType.PLAIN_TEXT);
        bodyTypeMap.put(BodyType.XML.getValue(), BodyType.XML);
        bodyTypeMap.put(BodyType.OCTET_STREAM.getValue(), BodyType.OCTET_STREAM);

        for (HttpSetServiceEntity httpSetServiceEntity : this.httpSetServiceEntities) {
            HttpSetRequest httpSetRequest = httpSetServiceEntity.getHttpSetRequest();
            String serviceType = httpSetServiceEntity.getServiceType();

            HttpServiceEntity httpServiceEntity = new HttpServiceEntity();
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setHost(httpSetRequest.getHost());
            httpRequest.setBaseUri(httpSetRequest.getBaseUri());
            httpRequest.setRequestUri(httpSetRequest.getRequestUri());
            httpRequest.setRequestType(requestTypeMap.get(httpSetRequest.getRequestType()));
            httpRequest.setHeadersMap(httpSetRequest.getHeadersMap());
            httpRequest.setParams(httpSetRequest.getParams());
            httpRequest.setBodyType(bodyTypeMap.get(httpSetRequest.getBodyType()));

            httpServiceEntity.setHttpRequest(httpRequest);
            httpServiceEntity.setServiceType(serviceTypeMap.get(serviceType));
            httpServiceEntities.add(httpServiceEntity);
        }
        return httpServiceEntities;
    }
}

/***
 * {
 *   "user": {
 *     "name": "exampleUser",
 *     "password": "examplePassword"
 *   },
 *   "httpSetServiceEntities": [
 *     {
 *       "httpSetRequest": {
 *         "host": "example.com",
 *         "baseUri": "/api",
 *         "requestUri": "/endpoint",
 *         "requestType": "GET",
 *         "headersMap": {
 *           "Content-Type": "application/json"
 *         },
 *         "params": {
 *           "param1": "value1"
 *         },
 *         "bodyType": "json",
 *         "json": "{\"key\":\"value\"}"
 *       },
 *       "serviceType": "service1"
 *     }
 *   ]
 * }
 */
