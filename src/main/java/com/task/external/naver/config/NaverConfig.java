package com.task.external.naver.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "external.naver")
public class NaverConfig {
    private final String clientId;
    private final String clientSecret;
    private final String host;
    private final EndPoint endPoint;
    private final Long connectionTimeout;
    private final Long readTimeout;

    @Getter
    @RequiredArgsConstructor
    public static class EndPoint {
        private final String searchBlog;
    }

    public String getSearchBlogEndpoint() {
        return getHost() + getEndPoint().getSearchBlog();
    }
}
