package com.differ.compare.cache;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ListCacheService<T> {
    /**
     * @param key redis数据库键
     * @param value redis数据库值 泛型
     * @description: 把对象存储到redis
     */
    void save(String key, T value);

    /**
     * @param key redis数据库键
     * @param clazz 返回对象的具体类型
     * @return redis中存储的值
     * @description: 根据redis的key和类型来获取redis中的值
     */
    T get(String key, Class<T> clazz);

    /**
     * @param key redis中的键
     * @description: 删除redis中的数据
     */
    void delete(String key);

    /**
     * @param key redis中的键
     * @param values 存储对象列表
     * @description: 存储Java对象list到redis的list中
     */
    void saveAll(String key, List<T> values);

    /**
     * @param key redis键
     * @param clazz 存储对象的类型
     * @return 对象列表
     */
    List<T> getAll(String key, Class<T> clazz);

    /**
     * @param key redis键
     * @param value 存储的对象
     * @description: 存储对象到redis的list中
     */
    void appendToList(String key, T value);

    /**
     * @param key redis键
     * @param values 新增对象列表
     * @description: 存储list到已有的redis list中
     */
    void appendAllToList(String key, List<T> values);

    /**
     * @param key redis键
     * @param begin redis开始位置
     * @param end redis结束位置
     * @param clazz 存储对象类型
     * @return list列表
     * @description: 查询redis中对应的list
     */
    List<T> getFromList(String key, Integer begin, Integer end, Class<T> clazz);

    /**
     * @param key redis中的键
     * @description: 删除redis元素
     */
    void deleteAll(String key);

    /**
     * @param key redis中的键
     * @param begin list中的开始
     * @param end list的结束
     * @description: 删除元素
     */
    void deleteFromList(String key, Integer begin, Integer end);

    /**
     * @param key redis中的键
     * @param size 删除元素数量
     * @description: 存储一定数量的元素从redis的list中
     */
    void deleteFromList(String key, Integer size);
}

