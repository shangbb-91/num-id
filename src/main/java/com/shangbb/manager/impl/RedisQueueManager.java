package com.shangbb.manager.impl;

import com.alibaba.fastjson.JSON;
import com.shangbb.config.BaseAppConfig;
import com.shangbb.manager.QueueManager;
import com.shangbb.service.GenerateLogService;
import com.shangbb.strengthen.GlobalStringRedisSerializer;
import com.shangbb.strengthen.exception.data.DataNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author benben.shang
 * @Date 2021/4/15 17:17
 */
@Slf4j
public class RedisQueueManager extends QueueManager {
    private final RedisTemplate<String, Long> redisTemplate;
    private final GenerateLogService generateLogService;

    private final String ID_QUEUE = "idQueue";
    private final String CONSUME_QUEUE = "consumeQueue";
    private final BaseAppConfig baseAppConfig;

    private final RedisAtomicLong num;

    public RedisQueueManager(@Nonnull RedisTemplate<String, Long> redisTemplate, GenerateLogService generateLogService, BaseAppConfig baseAppConfig) {
        this.redisTemplate = redisTemplate;
        this.generateLogService = generateLogService;
        this.baseAppConfig = baseAppConfig;
        num = new RedisAtomicLong("idNum", redisTemplate.getConnectionFactory());
    }

    @Override
    public boolean queueNull(String registerId) {
        Boolean hasKey = redisTemplate.hasKey(ID_QUEUE + registerId);
        return !Objects.equals(true, hasKey);
    }

    @Override
    public void queueInit(String registerId) {
        //redis list不需要初始化
    }

    @Override
    public boolean queueEmpty(String registerId) {
        Long size = redisTemplate.opsForList().size(ID_QUEUE + registerId);
        if (Objects.isNull(size)) {
            throw new DataNotExistException("queue is null!");
        }
        return Objects.equals(size, 0L);
    }

    @Override
    public Long poll(String registerId) {
        Object one = redisTemplate.opsForList().leftPop(ID_QUEUE + registerId);
        if (Objects.isNull(one)) {
            throw new RuntimeException("no id can used");
        }
        //jackson，数字小的话会被解析成int
        if (one instanceof Integer) {
            Integer id = (Integer) one;
            return id.longValue();
        }
        return (Long) one;
    }

    @Override
    public Long queueSize(String registerId) {
        Long size = redisTemplate.opsForList().size(ID_QUEUE + registerId);
        if (Objects.isNull(size)) {
            throw new DataNotExistException("queue is null!");
        }
        return size;
    }

    @Override
    public long generateId(long min, long max) {
        long id = Math.round(Math.random() * max + min);
        while (id < (min + 225)) {
            id = Math.round(Math.random() * max + min);
        }
        id = Math.abs(id);

        long inc = num.incrementAndGet();
        long inc1bytes = inc & 0xFF;
        long combined = ((id >> 8) << 8) + inc1bytes;
        if ((combined + "").length() != 8) {
            System.out.println("{id:" + id + ",combined:" + combined + ",inc:" + inc + "}");
        }
        return combined;
    }

    @Override
    public void putAll(String registerId, List<Long> ids) {
        redisTemplate.opsForList().rightPushAll(ID_QUEUE + registerId, ids);
    }

    @Override
    public void putToConsumeQueue(String registerId, Long id) {
        redisTemplate.opsForList().leftPushAll(CONSUME_QUEUE + registerId, id);
    }

    @Override
    public void scheduleConsumeQueue() {
        Set<String> keys = redisTemplate.keys(CONSUME_QUEUE + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        GlobalStringRedisSerializer serializer = new GlobalStringRedisSerializer(baseAppConfig);
        for (String key : keys) {
            Long size = redisTemplate.opsForList().size(key);
            if (Objects.equals(size, 0L)) {
                continue;
            }
            List<Object> objects = redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                byte[] keyByte = serializer.serialize(key);
                connection.lRange(keyByte, 0, 100);
                connection.lTrim(keyByte, 101, Integer.MIN_VALUE);
                return null;
            });
            System.out.println(JSON.toJSONString(objects));
            if (CollectionUtils.isEmpty(objects)) {
                continue;
            }
            List<Number> ids = (ArrayList<Number>) objects.get(0);
            generateLogService.setUsedByIds(key.replace(CONSUME_QUEUE, ""), ids);
        }
    }
}
