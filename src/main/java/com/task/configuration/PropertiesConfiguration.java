package com.task.configuration;


import com.task.external.blog.kakao.config.KaKaoConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {
    KaKaoConfig.class,
    com.task.external.blog.naver.config.NaverConfig.class
})
public class PropertiesConfiguration {}
