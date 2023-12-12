package com.task.external.blog.kakao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.task.external.blog.kakao.config.KaKaoConfig;
import com.task.external.blog.kakao.domain.searchBlog.KaKaoSearchBlogRequest;
import com.task.external.blog.kakao.domain.searchBlog.KaKaoSearchBlogResponse;
import com.task.external.blog.kakao.domain.searchBlog.MetaDTO;
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
class KakaoApiCallerTest {

    @Mock
    private KaKaoConfig kaKaoConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private KakaoApiCaller kakaoApiCaller;

    @Test
    @DisplayName("요청 실패 - 키워드 누락")
    void test1() {
        //given
        KaKaoSearchBlogRequest kaKaoSearchBlogRequest = makeKaKaoSearchBlogRequest("");

        //when, then
        assertThrows(IllegalArgumentException.class, () -> kakaoApiCaller.getSearchBlog(kaKaoSearchBlogRequest));
    }

    @Test
    @DisplayName("요청 성공 - 응답 객체 확인")
    void test2() {
        //given
        makeKakaoConfigMock();
        whenCallerSuccess();

        KaKaoSearchBlogRequest kaKaoSearchBlogRequest = makeKaKaoSearchBlogRequest("test");

        //when
        KaKaoSearchBlogResponse kaKaoSearchBlogResponse = kakaoApiCaller.getSearchBlog(kaKaoSearchBlogRequest);

        //then
        assertNotNull(kaKaoSearchBlogResponse);
        assertTrue(kaKaoSearchBlogResponse.isSuccess());
    }

    @Test
    @DisplayName("요청 실패 - 응답 객체 확인")
    void test3() {
        //given
        makeKakaoConfigMock();
        whenCallerFail();

        KaKaoSearchBlogRequest kaKaoSearchBlogRequest = makeKaKaoSearchBlogRequest("test");

        //when
        KaKaoSearchBlogResponse kaKaoSearchBlogResponse = kakaoApiCaller.getSearchBlog(kaKaoSearchBlogRequest);

        //then
        assertNotNull(kaKaoSearchBlogResponse);
        assertFalse(kaKaoSearchBlogResponse.isSuccess());
    }

    private void whenCallerSuccess() {
        HttpHeaders httpHeaders = makeRequestHeader();

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(httpHeaders);

        when(restTemplate.exchange(kaKaoConfig.getSearchBlogEndpoint()+"?query=test", HttpMethod.GET, request, KaKaoSearchBlogResponse.class))
            .thenReturn(new ResponseEntity<>(new KaKaoSearchBlogResponse(true, new MetaDTO(), Collections.emptyList()), HttpStatus.OK));
    }

    private void whenCallerFail() {
        HttpHeaders httpHeaders = makeRequestHeader();

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(httpHeaders);

        when(restTemplate.exchange(kaKaoConfig.getSearchBlogEndpoint()+"?query=test", HttpMethod.GET, request, KaKaoSearchBlogResponse.class))
            .thenReturn(new ResponseEntity<>(KaKaoSearchBlogResponse.fail(), HttpStatus.BAD_REQUEST));
    }

    private HttpHeaders makeRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("KakaoAK %s", kaKaoConfig.getApiKey()));
        return headers;
    }

    private void makeKakaoConfigMock() {
        given(kaKaoConfig.getApiKey()).willReturn("apiKey");
        given(kaKaoConfig.getSearchBlogEndpoint()).willReturn("http://localhost/kakao");
    }

    private KaKaoSearchBlogRequest makeKaKaoSearchBlogRequest(String query) {
        return KaKaoSearchBlogRequest.builder().query(query).build();
    }
}
