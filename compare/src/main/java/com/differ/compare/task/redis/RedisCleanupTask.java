package com.differ.compare.task.redis;

import com.differ.compare.entity.ChangeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/8 20:01
 */
@Component
public class RedisCleanupTask {

    @Autowired
    @Qualifier("redisTemplateObject")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("redisTemplateStringSelf")
    private RedisTemplate<String, String> redisTemplateStringSelf;

    @Autowired
    @Qualifier("redisTemplateChangeDto")
    private RedisTemplate<String, ChangeDto> redisTemplateChangeDto;

    @Scheduled(fixedRate = 3600000)
    public void cleanUpExpiredRedisKeysObject() {
        redisTemplate.keys("*").forEach(key -> {
            Long ttl = redisTemplate.getExpire(key);
            if (ttl != null && ttl <= 0) {
                redisTemplate.delete(key);
            }
        });
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanUpExpiredRedisKeysString() {
        redisTemplateStringSelf.keys("*").forEach(key -> {
            Long ttl = redisTemplate.getExpire(key);
            if (ttl != null && ttl <= 0) {
                redisTemplate.delete(key);
            }
        });
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanUpExpiredRedisKeysChanges() {
        redisTemplateChangeDto.keys("*").forEach(key -> {
            Long ttl = redisTemplate.getExpire(key);
            if (ttl != null && ttl <= 0) {
                redisTemplate.delete(key);
            }
        });
    }
}
