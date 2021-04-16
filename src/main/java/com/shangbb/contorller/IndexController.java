package com.shangbb.contorller;

import com.shangbb.config.ScheduleConfig;
import com.shangbb.manager.QueueManager;
import com.shangbb.strengthen.vo.IdEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author benben.shang
 * @Date 2021/4/9 11:37
 */
@RestController
@RequestMapping("")
public class IndexController {

    @Resource
    private QueueManager queueManager;

    @Resource
    private ScheduleConfig scheduleConfig;

    @GetMapping("getOne")
    public IdEntity getOne(@RequestParam String registerId) {
        Long id = queueManager.getByRegisterId(registerId);
        return new IdEntity(id.toString());
    }

    @GetMapping("schedule")
    public void schedule() {
        scheduleConfig.scheduleBuildId();
    }
}
