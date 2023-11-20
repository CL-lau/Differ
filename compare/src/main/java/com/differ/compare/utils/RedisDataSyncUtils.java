package com.differ.compare.utils;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 14:47
 */

import com.differ.compare.entity.ChangeDto;
import com.differ.compare.entity.db.ColumnInfo;
import com.differ.compare.entity.db.DatabaseInfo;
import com.differ.compare.entity.db.TableInfo;
import com.differ.compare.repository.db.mapper.ChangeDtoRepository;
import com.differ.compare.repository.db.mapper.ColumnInfoRepository;
import com.differ.compare.repository.db.mapper.DatabaseInfoRepository;
import com.differ.compare.repository.db.mapper.TableInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class RedisDataSyncUtils {

//    @Autowired
//    @Qualifier("redisTemplateStringSelf")
    private RedisTemplate<String, String> redisTemplate;
    private ChangeDtoRepository changeDtoMapper;
    private ColumnInfoRepository columnInfoRepository;
    private DatabaseInfoRepository databaseInfoRepository;
    private TableInfoRepository tableInfoRepository;

    @Value("${redis.time.changeDto.expire}")
    private Integer expireChangeDto;

    @Value("${redis.time.changeDto.minExpire}")
    private Integer minExpireChangeDto;

    @Value("${redis.time.changeDto.maxExpire}")
    private Integer maxExpireChangeDto;

    @Value("${redis.time.databaseInfo.expire}")
    private Integer expireDatabaseInfoDto;

    @Value("${redis.time.databaseInfo.minExpire}")
    private Integer minExpireDatabaseInfoDto;

    @Value("${redis.time.databaseInfo.maxExpire}")
    private Integer maxExpireDatabaseInfoDto;

    @Autowired
    public void setInject(ChangeDtoRepository changeDtoMapper,
                          ColumnInfoRepository columnInfoRepository,
                          DatabaseInfoRepository databaseInfoRepository,
                          TableInfoRepository tableInfoRepository){
        this.changeDtoMapper = changeDtoMapper;
        this.databaseInfoRepository = databaseInfoRepository;
        this.tableInfoRepository = tableInfoRepository;
        this.columnInfoRepository = columnInfoRepository;
    }

    /**
     * @param key: redis 数据库的键
     * @param value: redis 数据库存储的值
     * @description: 永久存储键值到数据库
     */
    public void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @param key redis 键
     * @param changeDto redis 值
     * @description: 存储ChangeDto到redis以及数据库 并且根据yml中的时间设置来完成失效时间设置
     */
    public void saveChangeDto(String key, ChangeDto changeDto) {
        if (Objects.isNull(key)){
            if (!Objects.isNull(changeDto)){
                this.changeDtoMapper.insert(changeDto);
            }
        }else if (!Objects.isNull(changeDto)){
            String value = changeDto.toString();
            Optional<Boolean> flag = Optional.ofNullable(this.changeDtoMapper.insert(changeDto))
                    .filter(Objects::nonNull)
                    .filter(Boolean::booleanValue);

            if (flag.isPresent()) {
                if (Objects.isNull(expireChangeDto)){
                    int randomExpire = minExpireChangeDto + (int)(Math.random() * ((maxExpireChangeDto - minExpireChangeDto) + 1));
                    saveDataWithTTL(key, value, randomExpire);
                }else {
                    saveDataWithTTL(key, value, expireChangeDto);
                }
            }
        }else {
            return;
        }
    }

    /**
     *
     * @param key redis 存储的键
     * @param changeDtoList redis 存储的值
     * @description: 存储changeDtoList到redis以及数据库 并根据yml来设置失效时间
     */
    public void saveChangeDto(String key, List<ChangeDto> changeDtoList) {
        if (Objects.isNull(key)){
            if (changeDtoList.size()>0){
                changeDtoList.forEach(
                        changeDto -> {
                            this.changeDtoMapper.insert(changeDto);
                        }
                );
            }
        }else if (changeDtoList.size()>0){
            AtomicBoolean flag = new AtomicBoolean(true);
            changeDtoList.forEach(
                    changeDto -> {
                        flag.set(flag.get() && this.changeDtoMapper.insert(changeDto));
                    }
            );
            if (Boolean.TRUE.equals(flag.get())){
                String value = changeDtoList.toString();
                if (Objects.isNull(expireChangeDto)){
                    int randomExpire = minExpireChangeDto + (int)(Math.random() * ((maxExpireChangeDto - minExpireChangeDto) + 1));
                    saveDataWithTTL(key, value, randomExpire);
                }else {
                    saveDataWithTTL(key, value, expireChangeDto);
                }
            }
        }
    }

    public void saveColumnInfo(String key, ColumnInfo columnInfo) {
        String value = columnInfo.toString();
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveTableInfo(String key, TableInfo tableInfo) {
        String value = tableInfo.toString();


        redisTemplate.opsForValue().set(key, value);
    }

    public void saveColumnInfo(String key, DatabaseInfo databaseInfo) {
        String value = databaseInfo.toString();


        redisTemplate.opsForValue().set(key, value);
    }

    // 同步保存数据到Redis和数据库，并设置过期时间（秒）
    public void saveDataWithTTL(String key, String value, long ttl) {
        // 保存到 Redis，并设置过期时间
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }

    // 获取数据
    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 获取数据并更新其过期时间（秒）
    public String getDataWithTTL(String key, long ttl) {
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            // 更新 Redis 中数据的过期时间
            redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        }
        return value;
    }

    // 从 Redis 和数据库中删除数据
    public void deleteData(String key) {
        // 从数据库中删除数据
        // ...

        // 从 Redis 中删除数据
        redisTemplate.delete(key);
    }

    // 设置 Redis 集合数据
    public void addToSet(String key, String... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    // 获取 Redis 集合数据
    public Set<String> getSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // 添加元素到 Redis 列表
    public void addToList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    // 获取 Redis 列表范围
    public List<String> getListRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // 发布消息到 Redis 频道
    public void publishMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }

    // 计数器递增
    public Long incrementCounter(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
}
