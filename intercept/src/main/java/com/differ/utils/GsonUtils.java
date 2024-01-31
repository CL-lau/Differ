package com.differ.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    private static final Gson gson = new GsonBuilder().create();

    // 对象转JSON字符串
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    // JSON字符串转对象
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    // JSON字符串转JSON数组
    public static JsonArray toJsonArray(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        if (!jsonElement.isJsonArray()) {
            throw new JsonParseException("JSON string is not a JSON array.");
        }
        return jsonElement.getAsJsonArray();
    }

    // JSON数组转对象列表
    public static <T> List<T> fromJsonArray(String json, Class<T> classOfT) {
        JsonArray jsonArray = toJsonArray(json);
        Type listType = List.class;
        return gson.fromJson(jsonArray, listType);
    }

    // JSON数组转对象数组
    public static <T> List<T> toObjectList(String json, Class<T> clazz){
        Type type = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(json, type);
    }
    // Map转为JSON字符串
    public static String mapToJson(Map<String, Object> map) {
        return gson.toJson(map);
    }

    // Map转为JSON字符串
    public static String mapToJson2(Map<String, String> map) {
        return gson.toJson(map);
    }

    // List 转为 JSON字符串
    public static String mapToJson(List<String> keys, List<String> values) {
        JsonObject jsonObject = new JsonObject();
        assert keys.size() == values.size();
        for (int i = 0; i < keys.size(); i++) {
            jsonObject.addProperty(keys.get(i), values.get(i));
        }
        String jsonString = gson.toJson(jsonObject);
        return jsonString;
    }
}
