package com.lsqingfeng.action.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RedisTemplateConfig {


    private final RedisConnectionFactory factory;

    private static final Long expireTime = 60L;

    @Autowired
    private Environment environment;

    public RedisTemplateConfig(RedisConnectionFactory factory) {
        this.factory = factory;
    }

//    @Bean
//    public RedisClusterConfiguration redisClusterConfiguration() {
//        Map<String, Object> source = new HashMap<String, Object>();
//        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
//        source.put("spring.redis.cluster.nodes", environment.getProperty("spring.redis.cluster.nodes"));
//        source.put("spring.redis.cluster.timeout", environment.getProperty("spring.redis.cluster.timeout"));
//        source.put("spring.redis.cluster.max-redirects", environment.getProperty("spring.redis.cluster.max-redirects"));
////        clusterConfiguration.clusterNode("host", port);
//        return clusterConfiguration;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //设置缓存过期时间
        if (expireTime > 0) {
            log.info("Redis 缓存过期时间 : {}", expireTime);
            //设置缓存有效期 秒
            redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireTime));
        } else {
            log.info("Redis 未设置缓存过期时间");
        }
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
