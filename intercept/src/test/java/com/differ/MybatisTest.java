package com.differ;

import com.differ.entity.enumer.BodyType;
import com.differ.entity.enumer.RequestType;
import com.differ.entity.enumer.ServiceType;
import com.differ.entity.request.HttpRequest;
import com.differ.entity.service.http.HttpServiceEntity;
import com.differ.service.impl.HttpServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/30 14:04
 */

@SpringBootTest
public class MybatisTest {

    @Resource
    private HttpServiceImpl httpServiceImpl;

    @Test
    public void testGetUser() {
        HttpServiceEntity httpServiceEntity = new HttpServiceEntity();
        httpServiceEntity.setServiceType(ServiceType.MASTER);
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setHost("127.0.0.1");
        httpRequest.setPort("8080");
        httpRequest.setBaseUri("www.baidu.com");
        httpRequest.setRequestUri(null);
        httpRequest.setRequestType(RequestType.POST);
        Map<String, String> header = new HashMap<>();
        header.put("content", "map");
        header.put("type", "json");
        httpRequest.setHeadersMap(header);
        httpServiceEntity.setHttpRequest(httpRequest);
        Map<String, String> params = new HashMap<>();
        params.put("content", "map");
        params.put("type", "json");
        httpRequest.setParams(params);
        httpRequest.setBodyType(BodyType.JSON);
        httpServiceEntity.setHttpRequest(httpRequest);
//        boolean flag = httpServiceImpl.addHttpServiceEntity(httpServiceEntity);
//        HttpServiceEntity entity = httpServiceImpl.selectHttpServiceEntity(1);
//        System.out.println(entity.toString());
        System.out.println(httpServiceEntity.toString());
    }
}
