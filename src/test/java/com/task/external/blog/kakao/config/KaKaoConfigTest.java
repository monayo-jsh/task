package com.task.external.blog.kakao.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KaKaoConfigTest {

    @Autowired
    private KaKaoConfig kaKaoConfig;

    @Test
    @DisplayName("kakao config 설정 확인")
    void test1() {
        //then
        assertNotNull(kaKaoConfig.getApiKey());
        assertNotNull(kaKaoConfig.getHost());
        assertNotNull(kaKaoConfig.getConnectionTimeout());
        assertNotNull(kaKaoConfig.getReadTimeout());
        assertNotNull(kaKaoConfig.getEndPoint());
        assertNotNull(kaKaoConfig.getEndPoint().getSearchBlog());
    }
}
