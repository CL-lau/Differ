package com.differ.compare.cache.impl;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 23:19
 */

import com.differ.compare.cache.ChangeDtoCache;
import com.differ.compare.entity.ChangeDto;
import com.differ.compare.utils.FastjsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ChangeDtoCacheImpl implements ChangeDtoCache {

    private static final String CACHE_PREFIX = "changeDto:";

    @Autowired
    @Qualifier("redisTemplateStringSelf")
    private final RedisTemplate<String, String> redisTemplate;
    private final ListOperations<String, String> listOperations;

    @Autowired
    public ChangeDtoCacheImpl(@Qualifier("redisTemplateStringSelf") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    @Override
    public void saveChange(String key, ChangeDto changeDto) {
        String changeDtoValue = FastjsonUtils.toJson(changeDto);
        this.redisTemplate.opsForValue().set(CACHE_PREFIX + key, changeDtoValue);
    }

    @Override
    public ChangeDto getChange(String key) {
        String value = this.redisTemplate.opsForValue().get(CACHE_PREFIX + key);
        return FastjsonUtils.fromJson(value, ChangeDto.class);
    }

    @Override
    public void deleteChange(String key) {
        this.redisTemplate.delete(CACHE_PREFIX + key);
    }

    @Override
    public void saveChanges(String key, List<ChangeDto> changeDtoList) {
        changeDtoList.forEach(
                changeDto -> {
                    this.redisTemplate.opsForList().rightPush(CACHE_PREFIX + key, FastjsonUtils.toJson(changeDto));
                }
        );
//        redisTemplate.opsForList().rightPushAll(CACHE_PREFIX + key, changeDtoList.toArray(new ChangeDto[0]));
    }

    @Override
    public List<ChangeDto> getChanges(String key) {
        List<ChangeDto> result = new ArrayList<>();
        Optional<List<String>> changes = Optional.ofNullable(
                this.listOperations.range(CACHE_PREFIX + key, 0, -1)).filter(Objects::nonNull);
        changes.ifPresent(strings -> strings.forEach(change -> {
            result.add(FastjsonUtils.fromJson(change, ChangeDto.class));
        }));
        return result;
    }

    @Override
    public void saveChangeToList(String key, ChangeDto changeDto) {
        if (changeDto!=null){
            this.listOperations.rightPush(CACHE_PREFIX + key, FastjsonUtils.toJson(changeDto));
        }
    }

    @Override
    public void saveChangesToList(String key, List<ChangeDto> changeDtoList) {
        if (changeDtoList != null && changeDtoList.size()>1){
            changeDtoList.forEach(
                    changeDto -> {
                        this.listOperations.rightPush(CACHE_PREFIX + key, FastjsonUtils.toJson(changeDto));
                    }
            );
//            this.listOperations.rightPushAll(CACHE_PREFIX + key, changeDtoList.toArray(new ChangeDto[0]));
        }
    }

    @Override
    public List<ChangeDto> getChangesFromList(String key, Integer begin, Integer end) {
        List<ChangeDto> result = new ArrayList<>();
        List<String> values = listOperations.range(CACHE_PREFIX + key, begin, end);
        values.forEach(
                s -> {
                    result.add(FastjsonUtils.fromJson(s, ChangeDto.class));
                }
        );
        return result;
    }

    @Override
    public void deleteChanges(String key) {
        redisTemplate.delete(CACHE_PREFIX + key);
    }

    @Override
    public void deleteChangesFromList(String key, Integer begin, Integer end) {
        listOperations.trim(CACHE_PREFIX + key, begin, end);
    }

    @Override
    public void deleteChangesFromList(String key, Integer size) {
        listOperations.trim(CACHE_PREFIX + key, size, -1);
    }
}
