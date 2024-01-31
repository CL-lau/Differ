package com.differ.utils;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/1 20:00
 */
//@Component
public class RedisUtil {

//    private final RedisTemplate<String, Object> redisTemplate;
//    private final RedissonClient redissonClient;
//
//    public RedisUtil(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
//        this.redisTemplate = redisTemplate;
//        this.redissonClient = redissonClient;
//    }
//
//    public void set(String key, Object value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    public void setWithExpiry(String key, Object value, long seconds) {
//        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
//    }
//
//    public Object get(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    public void delete(String key) {
//        redisTemplate.delete(key);
//    }
//
//    public boolean hasKey(String key) {
//        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
//    }
//
//    public Long increment(String key, long delta) {
//        return redisTemplate.opsForValue().increment(key, delta);
//    }
//
//    public boolean expire(String key, long seconds) {
//        return Boolean.TRUE.equals(redisTemplate.expire(key, seconds, TimeUnit.SECONDS));
//    }
//
//    public boolean acquireLock(String lockName, long timeout, long leaseTime) {
//        RLock lock = redissonClient.getLock(lockName);
//        try {
//            return lock.tryLock(timeout, leaseTime, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            return false;
//        }
//    }
//
//    public void releaseLock(String lockName) {
//        RLock lock = redissonClient.getLock(lockName);
//        if (lock.isHeldByCurrentThread()) {
//            lock.unlock();
//        }
//    }
//
//    public void waitForLock(String lockName, long timeout) {
//        RLock lock = redissonClient.getLock(lockName);
//        lock.lock(timeout, TimeUnit.MILLISECONDS);
//    }
}
