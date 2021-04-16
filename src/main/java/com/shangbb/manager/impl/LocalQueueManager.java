package com.shangbb.manager.impl;

import com.shangbb.manager.IdGenerateManager;
import com.shangbb.manager.QueueManager;
import com.shangbb.service.GenerateLogService;
import com.shangbb.strengthen.exception.data.DataNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author benben.shang
 * @Date 2021/4/9 11:58
 */
@Slf4j
public class LocalQueueManager extends QueueManager {

    private final Map<String, LinkedBlockingQueue<Long>> idQueueMap = new ConcurrentHashMap<>();
    private final Map<String, LinkedBlockingQueue<Long>> consumeQueueMap = new ConcurrentHashMap<>();
    private final AtomicLong num = new AtomicLong(0);
    @Resource
    private IdGenerateManager idGenerateManager;

    @Resource
    @Lazy
    private GenerateLogService generateLogService;

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
        LinkedBlockingQueue<Long> queue = idQueueMap.get(registerId);
        try {
            for (Long id : ids) {
                queue.put(id);
            }
        } catch (InterruptedException e) {
            log.error("put error", e);
        }
    }

    @Override
    public void putToConsumeQueue(String registerId, Long id) {
        LinkedBlockingQueue<Long> consumeQueue = consumeQueueMap.get(registerId);
        if (Objects.isNull(consumeQueue)) {
            consumeQueue = new LinkedBlockingQueue<>();
            consumeQueueMap.put(registerId, consumeQueue);
        }
        try {
            consumeQueue.put(id);
        } catch (InterruptedException e) {
            log.error("put message to consumeQueue error!", e);
        }
    }

    @Override
    public void scheduleConsumeQueue() {
        consumeQueueMap.forEach((registerId, ids) -> {
            if (CollectionUtils.isEmpty(ids)) {
                return;
            }
            CopyOnWriteArrayList<Long> list = new CopyOnWriteArrayList<>(ids);
            ids.removeAll(list);
            generateLogService.setUsedByIds(registerId, list);
        });
    }

    @Override
    public boolean queueNull(String registerId) {
        LinkedBlockingQueue<Long> queue = idQueueMap.get(registerId);
        return Objects.isNull(queue);
    }

    @Override
    public void queueInit(String registerId) {
        LinkedBlockingQueue<Long> queue = idQueueMap.get(registerId);
        if (Objects.isNull(queue)) {
            queue = new LinkedBlockingQueue<>();
            idQueueMap.put(registerId, queue);
        }
    }

    @Override
    public boolean queueEmpty(String registerId) {
        LinkedBlockingQueue<Long> queue = idQueueMap.get(registerId);
        if (Objects.isNull(queue)) {
            throw new DataNotExistException("queue is null!");
        }
        return queue.isEmpty();
    }

    @Override
    public Long poll(String registerId) {
        LinkedBlockingQueue<Long> queue = idQueueMap.get(registerId);
        if (Objects.isNull(queue)) {
            throw new DataNotExistException("queue is null!");
        }
        if (queue.size() < 20) {
            CompletableFuture.runAsync(() -> idGenerateManager.supply(registerId));
        }
        return queue.poll();
    }

    @Override
    public Long queueSize(String registerId) {
        LinkedBlockingQueue<Long> queue = idQueueMap.get(registerId);
        if (Objects.isNull(queue)) {
            throw new DataNotExistException("queue is null!");
        }
        return (long) queue.size();
    }
}
