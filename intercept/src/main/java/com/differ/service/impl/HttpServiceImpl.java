package com.differ.service.impl;

import com.differ.dispatcher.GlobalDispatcherMap;
import com.differ.dispatcher.HttpDispatcher;
import com.differ.dto.HttpResult;
import com.differ.entity.User;
import com.differ.entity.enumer.BodyType;
import com.differ.entity.enumer.RequestType;
import com.differ.entity.service.http.HttpServiceEntity;
import com.differ.entity.service.http.HttpSetEntity;
import com.differ.interfacer.Dispatcher;
import com.differ.repository.mapper.HttpResultMapper;
import com.differ.repository.mapper.HttpServiceEntityMapper;
import com.differ.service.HttpService;
import com.differ.utils.GsonUtils;
import com.differ.utils.SnowflakeIdUtil;
import jakarta.annotation.Nullable;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description:
 * @author: lau
 * @time: 2023/10/31 10:30
 */
@Service
public class HttpServiceImpl implements HttpService {
    /**
     * 全局Dispatcher MAP 用于检索对应的dispatcher
     */
    private GlobalDispatcherMap globalDispatcherMap;
    /**
     * http请求结果持久化mapper
     */
    private HttpResultMapper httpResultMapper;
    /**
     * http请求服务的持久化mapper
     */
    private HttpServiceEntityMapper httpServiceEntityMapper;

    @Autowired
    public void setMyDependency(GlobalDispatcherMap globalDispatcherMap, HttpResultMapper httpResultMapper, HttpServiceEntityMapper httpServiceEntityMapper){
        this.globalDispatcherMap = globalDispatcherMap;
        this.httpResultMapper = httpResultMapper;
        this.httpServiceEntityMapper = httpServiceEntityMapper;
    }

    public boolean setHttpService(String serviceString){
        HttpSetEntity httpSetEntity = GsonUtils.fromJson(serviceString, HttpSetEntity.class);
        List<Long> ids = new ArrayList<>();
        User user = httpSetEntity.getUser();
        List<HttpServiceEntity> httpServiceEntities = httpSetEntity.transToHttpServiceEntities();
        StringBuffer sb = new StringBuffer();
        httpServiceEntities.stream()
                .map(entity -> entity.getHttpRequest().getBaseUri())
                .forEach(sb::append);
        HttpDispatcher httpDispatcher = new HttpDispatcher(httpServiceEntities);
        AtomicBoolean flag = new AtomicBoolean(this.globalDispatcherMap.addDispatcher(sb.toString(), httpDispatcher) && this.globalDispatcherMap.addDispatcher(user.getName(), httpDispatcher));
        if (flag.get()) httpServiceEntities.forEach(entity -> {
            flag.set(flag.get() & this.httpServiceEntityMapper.insertHttpServiceEntity(entity));
            ids.add(entity.getId());
        });
        return flag.get();
    }

    public boolean dispatchPostRequest(String requestBody, String requestPath,
                                       @Nullable String dispatcherId) throws IOException {
        long resultFlag = SnowflakeIdUtil.nextId();
        Optional<Integer> dispatcherIdOptional = Optional.empty();
        List<CompletableFuture<Response>> completableFutures = new ArrayList<>();
        Map<String, CompletableFuture<Response>> completableFutureMap = new HashMap<>();
        if (Objects.equals(2, this.globalDispatcherMap.getDispatcherCount())){
            Optional<Dispatcher> optionalHttpDispatcher = this.globalDispatcherMap.getDispatcherMap().entrySet().stream()
                    .filter(entry -> entry.getValue() instanceof HttpDispatcher)
                    .map(Map.Entry::getValue)
                    .findFirst();

            if(optionalHttpDispatcher.isPresent()) {
                HttpDispatcher httpDispatcher = (HttpDispatcher) optionalHttpDispatcher.get();
                dispatcherIdOptional = Optional.ofNullable(httpDispatcher.getId());
                completableFutures = httpDispatcher.dispatchRequestWithResponse(requestBody, requestPath);
                completableFutureMap = httpDispatcher.dispatchRequestWithMapResponse(requestBody, requestPath);
            }
        } else {
            Optional<Dispatcher> optionalHttpDispatcher = this.globalDispatcherMap.getDispatcherMap().entrySet().stream()
                    .filter(entry -> entry.getValue() instanceof HttpDispatcher)
                    .filter(entry -> entry.getKey().equalsIgnoreCase(dispatcherId))
                    .map(Map.Entry::getValue)
                    .findFirst();
            if (optionalHttpDispatcher.isPresent()){
                HttpDispatcher httpDispatcher = (HttpDispatcher) optionalHttpDispatcher.get();
                dispatcherIdOptional = Optional.ofNullable(httpDispatcher.getId());
                completableFutures = httpDispatcher.dispatchRequestWithResponse(requestBody, requestPath);
                completableFutureMap = httpDispatcher.dispatchRequestWithMapResponse(requestBody, requestPath);
            }
        }

        Optional<Integer> finalDispatcherIdOptional = dispatcherIdOptional;
        completableFutures.forEach(response -> {
            HttpResult httpResult = new HttpResult(String.valueOf(response.join().code()), response.join().message(), response.join().request().url().toString(), Objects.requireNonNull(response.join().body()).toString());
            httpResult.setRequestUri(requestPath);
            httpResult.setBodyType(BodyType.JSON);
            httpResult.setRequestType(RequestType.POST);
            httpResult.setRequestBody(requestBody);
            finalDispatcherIdOptional.ifPresent(httpResult::setDispatcherId);
            httpResult.setHttpServiceEntityId(-1);
            httpResult.setResultFlag(resultFlag);
            this.httpResultMapper.insertHttpResult(httpResult);
        });
        completableFutureMap.forEach((String, response) -> {
            HttpResult httpResult = new HttpResult(String.valueOf(response.join().code()), response.join().message(), response.join().request().url().toString(), Objects.requireNonNull(response.join().body()).toString());
            httpResult.setRequestUri(requestPath);
            httpResult.setBodyType(BodyType.JSON);
            httpResult.setRequestType(RequestType.POST);
            httpResult.setRequestBody(requestBody);
            finalDispatcherIdOptional.ifPresent(httpResult::setDispatcherId);
            httpResult.setHttpServiceEntityId(-1);
            httpResult.setResultFlag(resultFlag);
            this.httpResultMapper.insertHttpResult(httpResult);
        });
        return true;
    }

    public boolean dispatchGetRequest(Map<String, String> params, String requestPath,
                                      @Nullable String dispatcherId) throws IOException {
        long resultFlag = SnowflakeIdUtil.nextId();
        Optional<Integer> dispatcherIdOptional = Optional.empty();
        List<CompletableFuture<Response>> completableFutures = new ArrayList<>();
        Map<String, CompletableFuture<Response>> completableFutureMap = new ConcurrentHashMap<>();
        if (Objects.equals(2, this.globalDispatcherMap.getDispatcherCount())){
            Optional<Dispatcher> optionalHttpDispatcher = this.globalDispatcherMap.getDispatcherMap().entrySet().stream()
                    .filter(entry -> entry.getValue() instanceof HttpDispatcher)
                    .map(Map.Entry::getValue)
                    .findFirst();

            if(optionalHttpDispatcher.isPresent()) {
                HttpDispatcher httpDispatcher = (HttpDispatcher) optionalHttpDispatcher.get();
                dispatcherIdOptional = Optional.ofNullable(httpDispatcher.getId());
                completableFutures = httpDispatcher.dispatchRequestWithResponse(params, requestPath);
                completableFutureMap = httpDispatcher.dispatchRequestWithMapResponse(params, requestPath);
            }
        }else {
            Optional<Dispatcher> optionalHttpDispatcher = this.globalDispatcherMap.getDispatcherMap().entrySet().stream()
                    .filter(entry -> entry.getValue() instanceof HttpDispatcher)
                    .filter(entry -> entry.getKey().equalsIgnoreCase(dispatcherId))
                    .map(Map.Entry::getValue)
                    .findFirst();
            if (optionalHttpDispatcher.isPresent()){
                HttpDispatcher httpDispatcher = (HttpDispatcher) optionalHttpDispatcher.get();
                dispatcherIdOptional = Optional.ofNullable(httpDispatcher.getId());
                completableFutures = httpDispatcher.dispatchRequestWithResponse(params, requestPath);
                completableFutureMap = httpDispatcher.dispatchRequestWithMapResponse(params, requestPath);
            }
        }

        Optional<Integer> finalDispatcherIdOptional = dispatcherIdOptional;
        completableFutures.forEach(response -> {
            HttpResult httpResult = new HttpResult();
            httpResult.setStatusCode(String.valueOf(response.join().code()));
            httpResult.setStatusMessage(response.join().message());
            httpResult.setUrl(response.join().request().url().toString());
            httpResult.setResponseBody(Objects.requireNonNull(response.join().body()).toString());
            httpResult.setRequestUri(requestPath);
            httpResult.setBodyType(BodyType.JSON);
            httpResult.setRequestType(RequestType.POST);
            httpResult.setParams(params);
            finalDispatcherIdOptional.ifPresent(httpResult::setDispatcherId);
            httpResult.setHttpServiceEntityId(-1);
            httpResult.setResultFlag(resultFlag);
            this.httpResultMapper.insertHttpResult(httpResult);
        });
        completableFutureMap.forEach((requestId, response) -> {
            HttpResult httpResult = new HttpResult();
            httpResult.setStatusCode(String.valueOf(response.join().code()));
            httpResult.setStatusMessage(response.join().message());
            httpResult.setUrl(response.join().request().url().toString());
            httpResult.setResponseBody(Objects.requireNonNull(response.join().body()).toString());
            httpResult.setRequestUri(requestPath);
            httpResult.setBodyType(BodyType.JSON);
            httpResult.setRequestType(RequestType.POST);
            httpResult.setParams(params);
            finalDispatcherIdOptional.ifPresent(httpResult::setDispatcherId);
            httpResult.setHttpServiceEntityId(Long.parseLong(requestId));
            httpResult.setResultFlag(resultFlag);
            this.httpResultMapper.insertHttpResult(httpResult);
        });
        return true;
    }

    @Override
    public long addInsertHttpResultWithId(HttpResult httpResult) {
        return this.httpResultMapper.insertHttpResult(httpResult)? httpResult.getId(): -1;
    }

    @Override
    public HttpResult addInsertHttpResultWithReturn(HttpResult httpResult) {
        return this.httpResultMapper.insertHttpResult(httpResult)? httpResult: new HttpResult();
    }

    @Override
    public boolean addInsertHttpResult(HttpResult httpResult) {
        return this.httpResultMapper.insertHttpResult(httpResult);
    }

    @Override
    public boolean addInsertHttpServiceEntity(HttpServiceEntity httpServiceEntity) {
        return this.httpServiceEntityMapper.insertHttpServiceEntity(httpServiceEntity);
    }

    @Override
    public long addInsertHttpServiceEntityWithId(HttpServiceEntity httpServiceEntity) {
        return this.httpServiceEntityMapper.insertHttpServiceEntity(httpServiceEntity)? httpServiceEntity.getId() : -1;
    }

    @Override
    public HttpServiceEntity addInsertHttpServiceEntityWithReturn(HttpServiceEntity httpServiceEntity) {
        return this.httpServiceEntityMapper.insertHttpServiceEntity(httpServiceEntity)? httpServiceEntity: new HttpServiceEntity();
    }

    @Override
    public boolean updateHttpServiceEntity(HttpServiceEntity httpServiceEntity) {
        return this.httpServiceEntityMapper.updateHttpServiceEntity(httpServiceEntity);
    }
}
