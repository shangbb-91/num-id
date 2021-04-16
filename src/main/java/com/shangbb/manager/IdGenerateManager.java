package com.shangbb.manager;

import com.shangbb.entity.GenerateLog;
import com.shangbb.entity.Register;
import com.shangbb.service.GenerateLogService;
import com.shangbb.service.RegisterService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @Author benben.shang
 * @Date 2021/4/11 10:15
 */
@Component
public class IdGenerateManager {
    @Resource
    private GenerateLogService generateLogService;
    @Resource
    private QueueManager queueManager;
    @Resource
    private RegisterService registerService;

    private final Map<String, Object> supplyFlag = new ConcurrentHashMap<>();

    public synchronized void supply(String registerId) {
        if (supplyFlag.containsKey(registerId)) {
            return;
        }
        try {
            supplyFlag.put(registerId, 0);
            Register register = registerService.getById(registerId);
            handlerQueue(register, 20, 0);
        } finally {
            supplyFlag.remove(registerId);
        }
    }

    public void handlerIdQueue(Register register) {
        Integer cacheLength = register.getCacheLength();
        if (Objects.isNull(cacheLength) || cacheLength == 0) {
            return;
        }
        Long queueSize;
        if (queueManager.queueNull(register.getId().toString())) {
            queueManager.queueInit(register.getId().toString());
            queueSize = 0L;
        } else {
            queueSize = queueManager.queueSize(register.getId().toString());
        }
        //如果队列里的长度大于缓存的长
        if (queueSize >= cacheLength) {
            return;
        }
        //查询未使用的
        if (queueSize == 0) {
            List<GenerateLog> unusedList = generateLogService.loadUnused(register.getSeq());
            if (!CollectionUtils.isEmpty(unusedList)) {
                List<Long> collect = unusedList.stream().map(GenerateLog::getId).collect(Collectors.toList());
                queueSize += collect.size();
                queueManager.putAll(register.getId().toString(), collect);
            }
        }
        handlerQueue(register, cacheLength, queueSize);
    }

    private void handlerQueue(Register register, Integer cacheLength, long queueSize) {
        List<Long> ids = new CopyOnWriteArrayList<>();
        for (long i = queueSize; i < cacheLength; i++) {
            long generateId = queueManager.generateId(register.getMinVal(), register.getMaxVal());
            if (ids.contains(generateId)) {
                i--;
                continue;
            }
            ids.add(generateId);
        }
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        handlerRepeat(ids, register);
        generateLogService.saveAll(ids, register.getSeq());
        queueManager.putAll(register.getId().toString(), ids);
    }

    private void handlerRepeat(List<Long> ids, Register register) {
        //查询已经使用过的
        List<GenerateLog> logs = generateLogService.listByIds(ids, register.getSeq(), 1);
        if (CollectionUtils.isEmpty(logs)) {
            return;
        }
        //去除已经使用过的
        removeRepeat(ids, logs);
        //并重新生成该长度的id
        List<Long> newIds = new LinkedList<>();
        for (int i = 0; i < logs.size(); i++) {
            long generateId = queueManager.generateId(register.getMinVal(), register.getMaxVal());
            if (newIds.contains(generateId)) {
                i--;
                continue;
            }
            newIds.add(generateId);
        }
        //再次查重
        logs = generateLogService.listByIds(ids, register.getSeq(), 1);
        if (CollectionUtils.isEmpty(logs)) {
            return;
        }
        //去除已经使用过的,不再重试，需要监控预警重复概率，概率太高需要回收
        removeRepeat(newIds, logs);
        ids.addAll(newIds);
    }

    private void removeRepeat(List<Long> ids, List<GenerateLog> logs) {
        List<Long> existIds = logs.stream().map(GenerateLog::getId).collect(Collectors.toList());
        ids.removeAll(existIds);
    }

}
