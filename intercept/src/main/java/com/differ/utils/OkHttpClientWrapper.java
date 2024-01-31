package com.differ.utils;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class OkHttpClientWrapper {

    private static final long CONNECT_TIMEOUT = 10;
    private static final long READ_TIMEOUT = 10;
    private static final long WRITE_TIMEOUT = 10;

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(new LoggingInterceptor())
            .build();

    public static String get(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (headers != null) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }

        if (params != null) {
            HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            for (String key : params.keySet()) {
                httpBuilder.addQueryParameter(key, params.get(key));
            }
            builder.url(httpBuilder.build());
        }

        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return Optional.ofNullable(response.body())
                    .map(ResponseBody::toString)
                    .orElse(null);
        }
    }

    public static Response getWithResponse(String url, Map<String, String> headers,
                                           Map<String, String> params) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (headers != null) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }

        if (params != null) {
            HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            for (String key : params.keySet()) {
                httpBuilder.addQueryParameter(key, params.get(key));
            }
            builder.url(httpBuilder.build());
        }

        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return Optional.ofNullable(response).orElse(null);
        }
    }

    public static String post(String url, Map<String, String> headers, RequestBody requestBody) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);

        if (headers != null) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }

        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return Optional.ofNullable(response.body())
                    .map(ResponseBody::toString)
                    .orElse(null);
        }
    }

    public static Response postWithResponse(String url, Map<String, String> headers,
                                            RequestBody requestBody) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);

        if (headers != null) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }

        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return Optional.ofNullable(response).orElse(null);
        }
    }

    public static String sendRequest(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return Optional.ofNullable(response.body())
                    .map(ResponseBody::toString)
                    .orElse(null);
        }
    }

    public static Response sendRequestWithResponse(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }


    public static String postJson(String url, Map<String, String> headers, String json) throws IOException {
        Request request = createRequest(url, headers, json);
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return Optional.ofNullable(response.body())
                    .map(ResponseBody::toString)
                    .orElse(null);
        }
    }

    public static Response postJsonWithResponse(String url, Map<String, String> headers, String json) throws IOException {
        Request request = createRequest(url, headers, json);
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }

    public static String put(String url, Map<String, String> headers, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON_MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String delete(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON_MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    public static void getAsync(String url, Map<String, String> headers, Map<String, String> params, final Callback callback) {
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (headers != null) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }

        if (params != null) {
            HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            for (String key : params.keySet()) {
                httpBuilder.addQueryParameter(key, params.get(key));
            }
            builder.url(httpBuilder.build());
        }

        Request request = builder.build();
        client.newCall(request).enqueue(callback);
    }

    public static void postJsonAsync(String url, Map<String, String> headers, String json, final Callback callback) {
        Request request = createRequest(url, headers, json);
        client.newCall(request).enqueue(callback);
    }

    private static Request createRequest(String url, Map<String, String> headers, String json) {
        RequestBody requestBody = RequestBody.create(json, JSON_MEDIA_TYPE);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);

        if (headers != null) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }

        return builder.build();
    }

    public static WebSocket listenWebSocket(String url, WebSocketListener listener) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newWebSocket(request, listener);
    }

    public static void sendWebSocketMessage(WebSocket webSocket, String message, Runnable onMessageReceived) throws Exception {
        webSocket.send(message);
        onMessageReceived.run();
    }

    private static class LoggingInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.nanoTime();
            System.out.println(String.format("Sending request %s on %s%n%s", request.method(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long endTime = System.nanoTime();
            System.out.println(String.format("Received response for %s in %.1fms%n%s", response.request().method(), (endTime - startTime) / 1e6d, response.headers()));
            return response;
        }
    }
}
