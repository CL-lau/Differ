package com.differ.entity.service.http;

import com.differ.entity.enumer.ServiceType;
import com.differ.entity.request.HttpRequest;
import com.differ.interfacer.HttpServiceInterface;
import com.differ.utils.GsonUtils;
import com.differ.utils.OkHttpClientWrapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Data
@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpServiceEntity implements HttpServiceInterface {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;
    public ServiceType serviceType;
    public HttpRequest httpRequest;

    @Override
    public CompletableFuture<String> processRequest(Request request, String id) throws IOException {
        String result = OkHttpClientWrapper.sendRequest(request);
        result = processResult(id, result);
        return CompletableFuture.completedFuture(result);
    }
    @Override
    public CompletableFuture<String> processRequest(String id) {
        return CompletableFuture.completedFuture("None Request Type Find.");
    }

    @Override
    public CompletableFuture<String> processRequest(RequestBody requestBody, String id) throws IOException {
        String result = OkHttpClientWrapper.post(this.httpRequest.getURL(), this.httpRequest.getHeadersMap(), requestBody);
        result = processResult(id, result);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<String> processRequest(String requestBody, String id) throws IOException {
        String result = OkHttpClientWrapper.postJson(this.httpRequest.getURL(), this.httpRequest.getHeadersMap(), requestBody);
        result = processResult(id, result);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<String> processRequest(Map<String, String> params, String id) throws IOException {
        String result = OkHttpClientWrapper.get(this.httpRequest.getURL(), this.httpRequest.getHeadersMap(), params);
        result = processResult(id, result);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<Response> processRequestWithResponse(String jsonRequest, String id) throws IOException {
        Response result = OkHttpClientWrapper.postJsonWithResponse(this.httpRequest.getURL(), this.httpRequest.getHeadersMap(), jsonRequest);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<Response> processRequestWithResponse(Map<String, String> params, String id) throws IOException {
        Response result = OkHttpClientWrapper.getWithResponse(this.httpRequest.getURL(), this.httpRequest.getHeadersMap(), params);
        return CompletableFuture.completedFuture(result);
    }

    public String processResult(String id, String res){
        Map<String, String> ans = new HashMap<>();
        ans.put("id", id);
        ans.put("result", res);
        String result = GsonUtils.mapToJson2(ans);
        return result;
    }

    public String processResult(String id, Response res){
        Map<String, String> ans = new HashMap<>();
        ans.put("id", id);
        ans.put("result", res.toString());
        return GsonUtils.mapToJson2(ans);
    }
}
