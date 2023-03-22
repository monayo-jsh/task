package com.task.external.naver.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverConfigTest {

    @Autowired
    private NaverConfig naverConfig;

    @Test
    @DisplayName("naver config 설정 확인")
    void test1() {
        //then
        assertNotNull(naverConfig.getClientId());
        assertNotNull(naverConfig.getClientSecret());
        assertNotNull(naverConfig.getConnectionTimeout());
        assertNotNull(naverConfig.getReadTimeout());
        assertNotNull(naverConfig.getHost());
        assertNotNull(naverConfig.getEndPoint());
        assertNotNull(naverConfig.getEndPoint().getSearchBlog());
    }

}