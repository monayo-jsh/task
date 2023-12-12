package com.task.external.blog.naver.config;

import com.task.external.blog.kakao.config.KaKaoConfig;
import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NaverRestTemplateConfig {

    private final KaKaoConfig naverConfig;

    public NaverRestTemplateConfig(KaKaoConfig naverConfig) {
        this.naverConfig = naverConfig;
    }

    @Bean("naverRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(naverConfig.getConnectionTimeout()))
                                        .setReadTimeout(Duration.ofSeconds(naverConfig.getReadTimeout()))
                                        .additionalMessageConverters(new MappingJackson2HttpMessageConverter())
                                        .build();
    }
}
