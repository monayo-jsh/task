package com.task.restful.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.task.domain.blog.SearchBlogReqVo;
import com.task.domain.blog.result.SearchBlogGetVo;
import com.task.external.domain.blog.SearchBlogResponse;
import com.task.external.service.BlogSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @Mock
    private BlogSearchService blogSearchService;

    @Mock
    private KeywordService keywordService;

    @InjectMocks
    private BlogService blogService;

    @Test
    @DisplayName("응답 객체는 반드시 제공")
    void test1() throws Exception {
        //given
        given(blogSearchService.blogSearch(any())).willReturn(SearchBlogResponse.empty());

        SearchBlogReqVo reqVo = new SearchBlogReqVo();
        reqVo.setKeyword("test");

        //when
        SearchBlogGetVo searchBlogGetVo = blogService.getBlogs(reqVo);

        //then
        assertNotNull(searchBlogGetVo);
        assertNotNull(searchBlogGetVo.getTotalCount());
        assertNotNull(searchBlogGetVo.getBlogs());
    }

    @Test
    @DisplayName("블로그 검색 연동 요청 확인")
    void test2() throws Exception {
        //given
        given(blogSearchService.blogSearch(any())).willReturn(SearchBlogResponse.empty());
        SearchBlogReqVo reqVo = new SearchBlogReqVo();
        reqVo.setKeyword("test");

        //when
        blogService.getBlogs(reqVo);

        //then
        verify(blogSearchService, atLeastOnce()).blogSearch(any());
        verify(keywordService, atLeastOnce()).increaseSearchCount(reqVo.getKeyword());
    }

    @Test
    @DisplayName("page 1 요청 시 키워드 검색수 증가 요청")
    void test3() throws Exception {
        //given
        given(blogSearchService.blogSearch(any())).willReturn(SearchBlogResponse.empty());

        SearchBlogReqVo reqVo = new SearchBlogReqVo();
        reqVo.setKeyword("test");
        reqVo.setPage(1);

        //when
        blogService.getBlogs(reqVo);

        //then
        verify(keywordService, atLeastOnce()).increaseSearchCount(reqVo.getKeyword());
    }

    @Test
    @DisplayName("page 1 초과 요청 시 검색수 증가 요청하지 않음")
    void test4() throws Exception {
        //given
        given(blogSearchService.blogSearch(any())).willReturn(SearchBlogResponse.empty());

        SearchBlogReqVo reqVo = new SearchBlogReqVo();
        reqVo.setKeyword("test");
        reqVo.setPage(2);

        //when
        blogService.getBlogs(reqVo);

        //then
        verify(keywordService, never()).increaseSearchCount(reqVo.getKeyword());
    }
}