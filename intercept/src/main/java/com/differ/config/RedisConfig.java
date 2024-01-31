package com.differ.config;

//import org.redisson.Redisson;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/1 19:56
 */
public class RedisConfig {

////    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName("127.0.0.1");
//        factory.setPort(6379);
//        factory.setPassword("your-redis-password");
//        factory.afterPropertiesSet();
//        return factory;
//    }
//
////    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://your-redis-host:6379")
//                .setPassword("your-redis-password"); // 如果有密码
//        return Redisson.create(config);
//    }
//
////    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
//        template.afterPropertiesSet();
//        return template;
//    }
}
