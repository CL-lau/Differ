package com.differ.compare.cache.impl;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 23:27
 */

import com.differ.compare.cache.ListCacheService;
import com.differ.compare.utils.FastjsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListCacheServiceImpl<T> implements ListCacheService<T> {

    private static final String CACHE_PREFIX = "cache:";

    @Autowired
    @Qualifier("redisTemplateStringSelf")
    private final RedisTemplate<String, String> redisTemplate;
    private final ListOperations<String, String> listOperations;

    @Autowired
    public ListCacheServiceImpl(@Qualifier("redisTemplateStringSelf") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public void save(String key, T value) {
        redisTemplate.opsForValue().set(CACHE_PREFIX + key, FastjsonUtils.toJson(value));
    }

    @Override
    public T get(String key, Class<T> clazz) {
        String value = this.redisTemplate.opsForValue().get(CACHE_PREFIX + key);

        return FastjsonUtils.fromJson(value, clazz);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(CACHE_PREFIX + key);
    }

    @Override
    public void saveAll(String key, List<T> values) {
        values.forEach(
                value -> {
                    this.redisTemplate.opsForList().rightPush(CACHE_PREFIX + key, FastjsonUtils.toJson(value));
                }
        );
//        redisTemplate.opsForList().rightPushAll(CACHE_PREFIX + key, (T) values.toArray(new Object[0]));
    }

    @Override
    public List<T> getAll(String key, Class<T> clazz) {
        List<String> values = this.listOperations.range(CACHE_PREFIX + key, 0, -1);
        List<T> result = new ArrayList<>();
        if (values != null){
            values.forEach(
                    value -> {
                        result.add(FastjsonUtils.fromJson(value, clazz));
                    }
            );
        }
        return result;
    }

    @Override
    public void appendToList(String key, T value) {
        this.listOperations.rightPush(CACHE_PREFIX + key, FastjsonUtils.toJson(value));
    }

    @Override
    public void appendAllToList(String key, List<T> values) {
        if (values != null){
            values.forEach(
                    value -> {
                        this.listOperations.rightPush(CACHE_PREFIX + key, FastjsonUtils.toJson(value));
                    }
            );
        }

//        listOperations.rightPushAll(CACHE_PREFIX + key, (T) values.toArray(new Object[0]));
    }

    @Override
    public List<T> getFromList(String key, Integer begin, Integer end, Class<T> clazz) {
        List<String> values = this.listOperations.range(CACHE_PREFIX + key, begin, end);
        List<T> result = new ArrayList<>();

        if (values != null){
            values.forEach(
                    value -> {
                        result.add(FastjsonUtils.fromJson(value, clazz));
                    }
            );
        }

        return result;
    }

    @Override
    public void deleteAll(String key) {

        this.redisTemplate.delete(CACHE_PREFIX + key);
    }

    @Override
    public void deleteFromList(String key, Integer begin, Integer end) {

        this.listOperations.trim(CACHE_PREFIX + key, begin, end);
    }

    @Override
    public void deleteFromList(String key, Integer size) {

        this.listOperations.trim(CACHE_PREFIX + key, size, -1);
    }
}

