package com.differ.compare.utils;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/8 14:37
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

public class FastjsonUtils {

    // 将Java对象转换为JSON字符串
    public static String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    // 将JSON字符串转换为Java对象
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    // 将Java列表转换为JSON字符串
    public static String toJsonList(List<?> list) {
        return JSON.toJSONString(list);
    }

    // 将JSON字符串转换为Java列表
    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    // 将JSON字符串转换为Java列表（使用TypeReference指定泛型类型）
    public static <T> List<T> fromJsonListWithType(String json, TypeReference<List<T>> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    public static <T>  String transMap2JsonV1(Map<String, List<T>> values){
        return JSON.toJSONString(values);
    }

    public static <T>  String transMap2JsonV2(Map<String, Map<String, List<Object>>> values){
        return JSON.toJSONString(values);
    }

    public static <T>  String transMap2JsonV3(Map<String, Map<String, List<List<Object>>>> values){
        return JSON.toJSONString(values);
    }

}
