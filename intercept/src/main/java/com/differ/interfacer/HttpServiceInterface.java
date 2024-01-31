package com.differ.interfacer;


import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface HttpServiceInterface {
    CompletableFuture<String> processRequest(Request request, String id) throws IOException;

    CompletableFuture<String> processRequest(String jsonRequest, String id) throws IOException;

    CompletableFuture<String> processRequest(String id);

    CompletableFuture<String> processRequest(RequestBody requestBody, String id) throws IOException;

    CompletableFuture<String> processRequest(Map<String, String> params, String id) throws IOException;

    CompletableFuture<Response> processRequestWithResponse(String jsonRequest, String id) throws IOException;

    CompletableFuture<Response> processRequestWithResponse(Map<String, String> params, String id) throws IOException;

}
