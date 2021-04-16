package com.shangbb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author benben.shang
 * @Date 2021/2/5 14:53
 */
@ConfigurationProperties(prefix = "app")
@Configuration
@Data
public class BaseAppConfig {

    private String redisKeyPrefix;
}
