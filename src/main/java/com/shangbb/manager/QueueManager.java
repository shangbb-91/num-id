package com.shangbb.manager;

import com.shangbb.utils.NumberUtil;

import java.util.List;

/**
 * @Author benben.shang
 * @Date 2021/4/9 11:55
 */
public abstract class QueueManager {

    /**
     * 获取id
     *
     * @param registerId 注册id
     * @return
     */
    public Long getByRegisterId(String registerId) {
        if (queueNull(registerId)) {
            throw new RuntimeException("registerId is illegal");
        }
        if (queueEmpty(registerId)) {
            throw new RuntimeException("no id can used");
        }
        Long id = poll(registerId);
        putToConsumeQueue(registerId, id);
        return id;
    }

    /**
     * 队列是否存在
     *
     * @return
     */
    public abstract boolean queueNull(String registerId);

    /**
     * 初始化队列
     *
     * @return
     */
    public abstract void queueInit(String registerId);

    /**
     * 队列是否为空
     *
     * @return
     */
    public abstract boolean queueEmpty(String registerId);

    /**
     * 从队列中拿一个
     *
     * @return
     */
    public abstract Long poll(String registerId);

    /**
     * 获取队列长度
     *
     * @param registerId 注册id
     * @return
     */
    public abstract Long queueSize(String registerId);

    /**
     * 生成一个id
     *
     * @param min
     * @param max
     * @return
     */
    public abstract long generateId(long min, long max);

    /**
     * 将id放入队列
     *
     * @param registerId 注册id
     * @param ids        短 id
     */
    public abstract void putAll(String registerId, List<Long> ids);

    /**
     * 将使用过的id放入队列
     *
     * @param registerId 注册id
     * @param id         id
     */
    public abstract void putToConsumeQueue(String registerId, Long id);

    /**
     * 处理已使用id的队列
     */
    public abstract void scheduleConsumeQueue();


    public long getMin(int minLength) {
        StringBuilder str = new StringBuilder("1");
        for (int i = 0; i < minLength - 1; i++) {
            str.append("0");
        }
        return new Long(str.toString());
    }

    public long getMax(int maxLength) {
        //按照10进制取最大值，如8位最大值是1000000000-1；
        StringBuilder max10Str = new StringBuilder();
        for (int m = 0; m < maxLength; m++) {
            max10Str.append("9");
        }
        String s = NumberUtil.decimal2Scale(new Long(max10Str.toString()), 2);
        int i = s.indexOf("0");
        StringBuilder str = new StringBuilder();
        for (; i < s.length(); i++) {
            str.append("1");
        }
        return NumberUtil.binary2Decimal(str.toString());
    }

}
