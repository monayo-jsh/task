package com.task.external.blog.naver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.task.external.blog.naver.config.NaverConfig;
import com.task.external.blog.naver.domain.searchBlog.NaverSearchBlogRequest;
import com.task.external.blog.naver.domain.searchBlog.NaverSearchBlogResponse;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class NaverApiCallerTest {

    @Mock
    private NaverConfig naverConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NaverApiCaller naverApiCaller;

    @Test
    @DisplayName("요청 실패 - 키워드 누락")
    void test1() {
        //given
        NaverSearchBlogRequest naverSearchBlogRequest = makeNaverSearchBlogRequest("");

        //when, then
        assertThrows(IllegalArgumentException.class, () -> naverApiCaller.getSearchBlog(naverSearchBlogRequest));
    }

    @Test
    @DisplayName("요청 성공 - 응답 객체 확인")
    void test2() {
        //given
        makeKakaoConfigMock();
        whenCallerSuccess();

        NaverSearchBlogRequest naverSearchBlogRequest = makeNaverSearchBlogRequest("test");

        //when
        NaverSearchBlogResponse naverSearchBlogResponse = naverApiCaller.getSearchBlog(naverSearchBlogRequest);

        //then
        assertNotNull(naverSearchBlogResponse);
        assertTrue(naverSearchBlogResponse.isSuccess());
    }

    @Test
    @DisplayName("요청 실패 - 응답 객체 확인")
    void test3() {
        //given
        makeKakaoConfigMock();
        whenCallerFail();

        NaverSearchBlogRequest naverSearchBlogRequest = makeNaverSearchBlogRequest("test");

        //when
        NaverSearchBlogResponse naverSearchBlogResponse = naverApiCaller.getSearchBlog(naverSearchBlogRequest);

        //then
        assertNotNull(naverSearchBlogResponse);
        assertFalse(naverSearchBlogResponse.isSuccess());
    }

    private void whenCallerSuccess() {
        HttpHeaders httpHeaders = makeRequestHeader();

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(httpHeaders);

        when(restTemplate.exchange(naverConfig.getSearchBlogEndpoint()+"?query=test", HttpMethod.GET, request, NaverSearchBlogResponse.class))
            .thenReturn(new ResponseEntity<>(new NaverSearchBlogResponse(true, 0, Collections.emptyList()), HttpStatus.OK));
    }

    private void whenCallerFail() {
        HttpHeaders httpHeaders = makeRequestHeader();

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(httpHeaders);

        when(restTemplate.exchange(naverConfig.getSearchBlogEndpoint()+"?query=test", HttpMethod.GET, request, NaverSearchBlogResponse.class))
            .thenReturn(new ResponseEntity<>(NaverSearchBlogResponse.fail(), HttpStatus.BAD_REQUEST));
    }

    private HttpHeaders makeRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverConfig.getClientId());
        headers.set("X-Naver-Client-Secret", naverConfig.getClientSecret());
        return headers;
    }

    private void makeKakaoConfigMock() {
        given(naverConfig.getClientId()).willReturn("clientId");
        given(naverConfig.getClientSecret()).willReturn("clientSecret");
        given(naverConfig.getSearchBlogEndpoint()).willReturn("http://localhost/naver");
    }

    private NaverSearchBlogRequest makeNaverSearchBlogRequest(String query) {
        return NaverSearchBlogRequest.builder().query(query).build();
    }

}
