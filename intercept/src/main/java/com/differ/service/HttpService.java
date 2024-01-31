package com.differ.service;

import com.differ.dto.HttpResult;
import com.differ.entity.service.http.HttpServiceEntity;
import jakarta.annotation.Nullable;

import java.io.IOException;
import java.util.Map;

public interface HttpService {

    public boolean setHttpService(String serviceString);

    boolean dispatchPostRequest(String requestBody, String requestPath, @Nullable String dispatcherId) throws IOException;

    boolean dispatchGetRequest(Map<String, String> params, String requestPath, @Nullable String dispatcherId) throws IOException;

    long addInsertHttpResultWithId(HttpResult httpResult);

    HttpResult addInsertHttpResultWithReturn(HttpResult httpResult);

    boolean addInsertHttpResult(HttpResult httpResult);

    boolean addInsertHttpServiceEntity(HttpServiceEntity httpServiceEntity);

    long addInsertHttpServiceEntityWithId(HttpServiceEntity httpServiceEntity);

    HttpServiceEntity addInsertHttpServiceEntityWithReturn(HttpServiceEntity httpServiceEntity);

    boolean updateHttpServiceEntity(HttpServiceEntity httpServiceEntity);
}
