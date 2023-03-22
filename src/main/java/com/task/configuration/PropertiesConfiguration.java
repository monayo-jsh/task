package com.task.configuration;


import com.task.external.kakao.config.KaKaoConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {
    KaKaoConfig.class,
    com.task.external.naver.config.NaverConfig.class
})
public class PropertiesConfiguration {}
