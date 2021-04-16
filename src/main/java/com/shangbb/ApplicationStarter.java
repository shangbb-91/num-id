package com.shangbb;

import com.shangbb.config.BaseAppConfig;
import com.shangbb.config.RedisConfig;
import com.shangbb.manager.QueueManager;
import com.shangbb.manager.impl.RedisQueueManager;
import com.shangbb.service.GenerateLogService;
import com.shangbb.strengthen.GlobalExceptionHandler;
import com.shangbb.strengthen.GlobalSuccessHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author benben.shang
 * @Date 2021/4/9 10:42
 */
@SpringBootApplication
@EnableScheduling
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

    @Bean
    public QueueManager queueManager(RedisTemplate<String, Long> redisTemplate, GenerateLogService generateLogService, BaseAppConfig baseAppConfig) {
        return new RedisQueueManager(redisTemplate, generateLogService, baseAppConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalSuccessHandler globalSuccessHandler() {
        return new GlobalSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory factory, BaseAppConfig baseAppConfig) {
        return new RedisConfig().redisTemplate(factory, baseAppConfig);
    }

}
