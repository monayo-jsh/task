package com.task.external.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.task.external.domain.blog.SearchBlogRequest;
import com.task.external.domain.blog.SearchBlogResponse;
import com.task.external.kakao.KakaoApiCaller;
import com.task.external.kakao.domain.searchBlog.DocumentDTO;
import com.task.external.kakao.domain.searchBlog.KaKaoSearchBlogResponse;
import com.task.external.kakao.domain.searchBlog.MetaDTO;
import com.task.external.naver.NaverApiCaller;
import com.task.external.naver.domain.searchBlog.NaverSearchBlogResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlogSearchServiceImplTest {

    @Mock
    private KakaoApiCaller kakaoApiCaller;

    @Mock
    private NaverApiCaller naverApiCaller;

    @InjectMocks
    private BlogSearchServiceImpl blogSearchService;

    @Test
    @DisplayName("블로그 검색 요청 - 카카오 응답")
    void test1() {
        //given
        makeGivenMockKakaoApiCaller();

        SearchBlogRequest searchBlogRequest = SearchBlogRequest.builder()
                                                               .query("query")
                                                               .build();

        //when
        SearchBlogResponse searchBlogResponse = blogSearchService.blogSearch(searchBlogRequest);

        //then
        assertNotNull(searchBlogResponse);
        assertNotNull(searchBlogResponse.getTotalCount());
        assertNotNull(searchBlogResponse.getBlogs());

        verify(kakaoApiCaller, atLeastOnce()).getSearchBlog(any());
        verify(naverApiCaller, never()).getSearchBlog(any());
    }

    private void makeGivenMockKakaoApiCaller() {
        MetaDTO meta = new MetaDTO(0, 0, true);
        List<DocumentDTO> blogs = new ArrayList<>();
        KaKaoSearchBlogResponse kaKaoSearchBlogResponse = new KaKaoSearchBlogResponse(true, meta, blogs);

        given(kakaoApiCaller.getSearchBlog(any())).willReturn(kaKaoSearchBlogResponse);
    }


    @Test
    @DisplayName("블로그 검색 요청 - 카카오 실패 후 네이버 응답")
    void test2() {
        //given
        makeGivenMockNaverApiCaller();

        SearchBlogRequest searchBlogRequest = SearchBlogRequest.builder()
                                                               .query("query")
                                                               .build();

        //when
        SearchBlogResponse searchBlogResponse = blogSearchService.blogSearch(searchBlogRequest);

        //then
        assertNotNull(searchBlogResponse);
        assertNotNull(searchBlogResponse.getTotalCount());
        assertNotNull(searchBlogResponse.getBlogs());

        verify(kakaoApiCaller, atLeastOnce()).getSearchBlog(any());
        verify(naverApiCaller, atLeastOnce()).getSearchBlog(any());
    }

    private void makeGivenMockNaverApiCaller() {
        given(kakaoApiCaller.getSearchBlog(any())).willReturn(KaKaoSearchBlogResponse.fail());


        NaverSearchBlogResponse naverSearchBlogResponse = new NaverSearchBlogResponse(true, 0, new ArrayList<>());
        given(naverApiCaller.getSearchBlog(any())).willReturn(naverSearchBlogResponse);
    }

    @Test
    @DisplayName("블로그 검색 요청 실패 응답")
    void test3() {
        //given
        makeGivenMockFail();

        SearchBlogRequest searchBlogRequest = SearchBlogRequest.builder()
                                                               .query("query")
                                                               .build();

        //when
        SearchBlogResponse searchBlogResponse = blogSearchService.blogSearch(searchBlogRequest);

        //then
        assertNotNull(searchBlogResponse);
        assertNotNull(searchBlogResponse.getTotalCount());
        assertNotNull(searchBlogResponse.getBlogs());

        verify(kakaoApiCaller, atLeastOnce()).getSearchBlog(any());
        verify(naverApiCaller, atLeastOnce()).getSearchBlog(any());
    }

    private void makeGivenMockFail() {
        given(kakaoApiCaller.getSearchBlog(any())).willReturn(KaKaoSearchBlogResponse.fail());
        given(naverApiCaller.getSearchBlog(any())).willReturn(NaverSearchBlogResponse.fail());
    }
}