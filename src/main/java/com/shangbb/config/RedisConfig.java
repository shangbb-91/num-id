package com.shangbb.config;

import com.shangbb.strengthen.GlobalStringRedisSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Objects;

/**
 * <p>
 * redis配置
 * </p>
 */
@Configuration
public class RedisConfig {

    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory, BaseAppConfig baseAppConfig) {
        if (Objects.isNull(baseAppConfig.getRedisKeyPrefix()) || "".equals(baseAppConfig.getRedisKeyPrefix().trim())) {
            throw new RuntimeException("please set redis prefix! [example app.redis-key-prefix=xxx]");
        }
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        //key采用String序列化方式
        GlobalStringRedisSerializer globalStringRedisSerializer = new GlobalStringRedisSerializer(baseAppConfig);
        redisTemplate.setKeySerializer(globalStringRedisSerializer);
        redisTemplate.setHashKeySerializer(globalStringRedisSerializer);

        //value采用fast-json序列化方式。
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
