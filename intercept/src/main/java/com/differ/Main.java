package com.differ;

import com.differ.dispatcher.HttpDispatcher;
import com.differ.entity.enumer.RequestType;
import com.differ.entity.request.HttpRequest;
import com.differ.entity.service.http.HttpServiceEntity;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@MapperScan(basePackages = "com.differ")
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        /**
        System.out.println("Hello world!");
        OkHttpClientWrapper.getAsync("https://www.baidu.com/", new HashMap<>(), new HashMap<>(), new httpCallback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("get finish");
            }
        });
         **/

        SpringApplication.run(Main.class, args);

        List<HttpServiceEntity> httpServiceEntities = new ArrayList<>();
        HttpRequest httpRequest = new HttpRequest(null,null, "https://www.baidu.com", "", RequestType.GET, null, null, null);
        HttpServiceEntity entity = new HttpServiceEntity();
        entity.setHttpRequest(httpRequest);
        httpServiceEntities.add(entity);
        httpServiceEntities.add(entity);
        HttpDispatcher dispatcher = new HttpDispatcher(httpServiceEntities);
//        CompletableFuture<String> responseFuture = dispatcher.dispatchRequest(null, null, null);
        Map<String, String> params = new HashMap<>();
        params.put("user", "user");
        String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        List<CompletableFuture<Response>> responseFuture = dispatcher.dispatchRequestWithResponse(params, "");
        for (CompletableFuture<Response> response : responseFuture) {
            ResponseBody responseBody = response.join().body();
//            if (responseBody != null) {
//                System.out.println(responseBody.string());
//                responseBody.close();
//            }
        }
        dispatcher.shutdown();

//        String a = "{  \n" +
//                "  \"user\": {  \n" +
//                "    \"username\": \"exampleUser\",  \n" +
//                "    \"password\": \"examplePassword\"  \n" +
//                "  },  \n" +
//                "  \"httpServiceEntities\": [  \n" +
//                "    {  \n" +
//                "      \"httpRequest\": {  \n" +
//                "        \"host\": \"example.com\",  \n" +
//                "        \"baseUri\": \"/api\",  \n" +
//                "        \"requestUri\": \"/endpoint\",  \n" +
//                "        \"requestType\": \"GET\",  \n" +
//                "        \"headers\": {  \n" +
//                "          \"Content-Type\": \"application/json\"  \n" +
//                "        },  \n" +
//                "        \"headersMap\": {  \n" +
//                "          \"key1\": \"value1\",  \n" +
//                "          \"key2\": \"value2\"  \n" +
//                "        },  \n" +
//                "        \"params\": {  \n" +
//                "          \"param1\": \"value1\",  \n" +
//                "          \"param2\": \"value2\"  \n" +
//                "        },  \n" +
//                "        \"bodyType\": \"JSON\",  \n" +
//                "        \"json\": \"{\\\"key\\\":\\\"value\\\"}\",  \n" +
//                "        \"requestBody\": \"{\\\"field1\\\":\\\"value1\\\",\\\"field2\\\":\\\"value2\\\"}\"  \n" +
//                "      },  \n" +
//                "      \"serviceType\": \"MASTER\"  \n" +
//                "    }  \n" +
//                "  ]  \n" +
//                "}";
//        HttpSetEntity entity = GsonUtils.fromJson(a, HttpSetEntity.class);
//        System.out.println(entity.toString());

    }
}