package com.task.external.kakao.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KaKaoRestTemplateConfig {

    private final KaKaoConfig kaKaoConfig;

    public KaKaoRestTemplateConfig(KaKaoConfig kaKaoConfig) {
        this.kaKaoConfig = kaKaoConfig;
    }

    @Bean("kakaoRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(kaKaoConfig.getConnectionTimeout()))
                                        .setReadTimeout(Duration.ofSeconds(kaKaoConfig.getReadTimeout()))
                                        .additionalMessageConverters(new MappingJackson2HttpMessageConverter())
                                        .build();
    }
}
