package com.differ.dispatcher;

import com.differ.entity.enumer.RequestType;
import com.differ.entity.enumer.ServiceType;
import com.differ.entity.service.http.HttpServiceEntity;
import com.differ.interfacer.Dispatcher;
import jakarta.annotation.Nullable;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
@ToString
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
@Component
public class HttpDispatcher extends Dispatcher {

    /**
     * id
     */
    private int id;
    /**
     * 该dispatcher关联的HttpService请求。
     */
    private List<HttpServiceEntity> httpServiceEntities;
    /**
     * httpService请求数量
     */
    private int httpNum;
    /**
     * 线程执行
     */
    private final ExecutorService executorService;

    public HttpDispatcher() {
        this.httpServiceEntities = new ArrayList<>();
        this.httpNum = 0;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public HttpDispatcher(HttpServiceEntity httpServiceEntity_1, HttpServiceEntity httpServiceEntity_2){
        this.httpServiceEntities = new ArrayList<>();
        this.httpServiceEntities.add(httpServiceEntity_1);
        this.httpServiceEntities.add(httpServiceEntity_2);
        this.httpNum = this.httpServiceEntities.size();
        this.executorService = Executors.newFixedThreadPool(this.httpServiceEntities.size());
    }

    public HttpDispatcher(List<HttpServiceEntity> httpServiceEntities){
        this.httpServiceEntities = httpServiceEntities;
        this.httpNum = this.httpServiceEntities.size();
        this.executorService = Executors.newFixedThreadPool(this.httpServiceEntities.size());
    }

    public CompletableFuture<String> dispatchRequest(@Nullable Request request,
                                                     @Nullable Map<String, String> params,
                                                     @Nullable RequestBody requestBody) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                case GET -> entity.processRequest(params, id);
                case POST -> entity.processRequest(requestBody, id);
                case PUT -> null;
                case DELETE -> null;
                case UPDATE -> null;
            };
            completableFutures.add(result);
        }
        for (CompletableFuture<String> tmp: completableFutures){
            System.out.println(tmp.join());
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public CompletableFuture<String> dispatchRequest(Request request) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.POST);
            CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                case GET -> null;
                case POST -> entity.processRequest(request, id);
                case PUT -> null;
                case DELETE -> null;
                case UPDATE -> null;
            };
            completableFutures.add(result);
        }
        for (CompletableFuture<String> tmp: completableFutures){
            System.out.println(tmp.join());
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public CompletableFuture<String> dispatchRequest(RequestBody requestBody) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.POST);
            CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                case GET -> null;
                case POST -> entity.processRequest(requestBody, id);
                case PUT -> null;
                case DELETE -> null;
                case UPDATE -> null;
            };
            completableFutures.add(result);
        }
        for (CompletableFuture<String> tmp: completableFutures){
            System.out.println(tmp.join());
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public List<CompletableFuture<String>> dispatchRequest
            (String jsonRequest, String requestPath) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.POST);
            entity.httpRequest.setRequestUri(requestPath);
            CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                case GET, PUT, UPDATE, DELETE -> null;
                case POST -> entity.processRequest(jsonRequest, id);
            };
            completableFutures.add(result);
        }
        for (CompletableFuture<String> tmp: completableFutures){
            System.out.println(tmp.join());
        }

        return completableFutures;
    }

    public List<CompletableFuture<String>> dispatchRequest
            (Map<String, String> params, String requestPath) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.GET);
            entity.httpRequest.setRequestUri(requestPath);
            CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                case GET -> entity.processRequest(params, id);
                case POST, PUT, DELETE, UPDATE -> null;
            };
            completableFutures.add(result);
        }
        for (CompletableFuture<String> tmp: completableFutures){
            System.out.println(tmp.join());
        }

        return completableFutures;
    }

    public List<CompletableFuture<Response>> dispatchRequestWithResponse
            (String jsonRequest, String requestPath) throws IOException {
        List<CompletableFuture<Response>> completableFutures = new ArrayList<>();
        Map<String, CompletableFuture<Response>> completableFutureHashMap = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.POST);
            entity.httpRequest.setRequestUri(requestPath);
            CompletableFuture<Response> result = switch (entity.getHttpRequest().getRequestType()){
                case GET, PUT, UPDATE, DELETE -> null;
                case POST -> entity.processRequestWithResponse(jsonRequest, id);
            };
            completableFutures.add(result);
            completableFutureHashMap.put(String.valueOf(entity.getId()), result);
        }
        for (CompletableFuture<Response> tmp: completableFutures){
            System.out.println(tmp.join().toString());
        }

        return completableFutures;
    }

    public List<CompletableFuture<Response>> dispatchRequestWithResponse
            (Map<String, String> params, String requestPath) throws IOException {
        List<CompletableFuture<Response>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.GET);
            entity.httpRequest.setRequestUri(requestPath);
            CompletableFuture<Response> result = switch (entity.getHttpRequest().getRequestType()){
                case GET -> entity.processRequestWithResponse(params, id);
                case POST, PUT, DELETE, UPDATE -> null;
            };
            completableFutures.add(result);
        }
        for (CompletableFuture<Response> tmp: completableFutures){
            System.out.println(tmp.join().toString());
        }

        return completableFutures;
    }

    public Map<String, CompletableFuture<Response>> dispatchRequestWithMapResponse
            (String jsonRequest, String requestPath) throws IOException {
        Map<String, CompletableFuture<Response>> completableFutureHashMap = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.POST);
            entity.httpRequest.setRequestUri(requestPath);
            CompletableFuture<Response> result = switch (entity.getHttpRequest().getRequestType()){
                case GET, PUT, UPDATE, DELETE -> null;
                case POST -> entity.processRequestWithResponse(jsonRequest, id);
            };

            completableFutureHashMap.put(String.valueOf(entity.getId()), result);
        }
        completableFutureHashMap.forEach((key, value) -> {
            System.out.println("Key = " + key + ", Value = " + value.toString());
        });

        return completableFutureHashMap;
    }

    public Map<String, CompletableFuture<Response>> dispatchRequestWithMapResponse
            (Map<String, String> params, String requestPath) throws IOException {
        Map<String, CompletableFuture<Response>> completableFutureHashMap = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            entity.httpRequest.setRequestType(RequestType.GET);
            entity.httpRequest.setRequestUri(requestPath);
            CompletableFuture<Response> result = switch (entity.getHttpRequest().getRequestType()){
                case GET -> entity.processRequestWithResponse(params, id);
                case POST, PUT, DELETE, UPDATE -> null;
            };
            completableFutureHashMap.put(String.valueOf(entity.getId()), result);
        }
        completableFutureHashMap.forEach((key, value) ->{
            System.out.println("Key = " + key + ", Value = " + value.toString());
        });

        return completableFutureHashMap;
    }

    public CompletableFuture<String> dispatchRequestOnlyMaster(@Nullable Request request,
                                                     @Nullable Map<String, String> params,
                                                     @Nullable RequestBody requestBody) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            if (Objects.equals(entity.getServiceType(), ServiceType.MASTER)){
                CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                    case GET -> entity.processRequest(params, id);
                    case POST -> entity.processRequest(requestBody, id);
                    case PUT -> null;
                    case DELETE -> null;
                    case UPDATE -> null;
                };
                completableFutures.add(result);
                break;
            }
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public CompletableFuture<String> dispatchRequestOnlyMaster(Request request) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            if (Objects.equals(entity.getServiceType(), ServiceType.MASTER)){
                CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                    case GET -> null;
                    case POST -> entity.processRequest(request, id);
                    case PUT -> null;
                    case DELETE -> null;
                    case UPDATE -> null;
                };
                completableFutures.add(result);
                break;
            }
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public CompletableFuture<String> dispatchRequestOnlyMaster(RequestBody requestBody) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            if (Objects.equals(entity.getServiceType(), ServiceType.MASTER)) {
                CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()) {
                    case GET -> null;
                    case POST -> entity.processRequest(requestBody, id);
                    case PUT -> null;
                    case DELETE -> null;
                    case UPDATE -> null;
                };
                completableFutures.add(result);
                break;
            }
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public CompletableFuture<String> dispatchRequestOnlyMaster(String jsonRequest) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            if (Objects.equals(entity.getServiceType(), ServiceType.MASTER)){
                CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                    case GET -> null;
                    case POST -> entity.processRequest(jsonRequest, id);
                    case PUT -> null;
                    case DELETE -> null;
                    case UPDATE -> null;
                };
                completableFutures.add(result);
                break;
            }
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public CompletableFuture<String> dispatchRequestOnlyMaster(Map<String, String> params) throws IOException {
        List<CompletableFuture<String>> completableFutures = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(9000) + 1000);
        String id = timestamp + randomNumber;
        for (HttpServiceEntity entity: this.httpServiceEntities) {
            if (Objects.equals(entity.getServiceType(), ServiceType.MASTER)){
                CompletableFuture<String> result = switch (entity.getHttpRequest().getRequestType()){
                    case GET -> entity.processRequest(params, id);
                    case POST -> null;
                    case PUT -> null;
                    case DELETE -> null;
                    case UPDATE -> null;
                };
                completableFutures.add(result);
                break;
            }
        }

        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply((Void aVoid) -> {
                    return "Aggregated Response: " + completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.joining(", "));
                });
    }

    public boolean isAvailable(){
        return !executorService.isShutdown();
    }

    public boolean isRunning() {
        return !(executorService.isTerminated()||executorService.isShutdown());
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
