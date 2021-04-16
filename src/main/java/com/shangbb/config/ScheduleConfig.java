package com.shangbb.config;

import com.shangbb.entity.Register;
import com.shangbb.manager.IdGenerateManager;
import com.shangbb.manager.QueueManager;
import com.shangbb.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author benben.shang
 * @Date 2021/4/9 16:22
 */
@Slf4j
@Component
public class ScheduleConfig {
    @Resource
    private RegisterService registerService;

    @Resource
    private IdGenerateManager idGenerateManager;
    @Resource
    private QueueManager queueManager;


    @Scheduled(fixedDelay = 30000)
    public void scheduleBuildId() {
        List<Register> list = registerService.list();
        if (CollectionUtils.isEmpty(list)) {
            log.info("registered is empty,done");
            return;
        }
        list.forEach(idGenerateManager::handlerIdQueue);
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 10000)
    public void scheduleConsumeQueue() {
        queueManager.scheduleConsumeQueue();
    }

}
