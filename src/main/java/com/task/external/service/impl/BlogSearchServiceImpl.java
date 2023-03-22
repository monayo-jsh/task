package com.task.external.service.impl;

import com.task.external.domain.blog.SearchBlogRequest;
import com.task.external.domain.blog.SearchBlogResponse;
import com.task.external.kakao.KakaoApiCaller;
import com.task.external.kakao.domain.searchBlog.KaKaoSearchBlogRequest;
import com.task.external.kakao.domain.searchBlog.KaKaoSearchBlogResponse;
import com.task.external.naver.NaverApiCaller;
import com.task.external.naver.domain.searchBlog.NaverSearchBlogRequest;
import com.task.external.naver.domain.searchBlog.NaverSearchBlogResponse;
import com.task.external.service.BlogSearchService;
import org.springframework.stereotype.Component;

@Component
public class BlogSearchServiceImpl implements BlogSearchService {

    private final KakaoApiCaller kakaoApiCaller;
    private final NaverApiCaller naverApiCaller;

    public BlogSearchServiceImpl(KakaoApiCaller kakaoApiCaller, NaverApiCaller naverApiCaller) {
        this.kakaoApiCaller = kakaoApiCaller;
        this.naverApiCaller = naverApiCaller;
    }

    @Override
    public SearchBlogResponse blogSearch(SearchBlogRequest searchBlogRequest) {

        KaKaoSearchBlogResponse kaKaoSearchBlogResponse = kakaoApiCaller.getSearchBlog(KaKaoSearchBlogRequest.of(searchBlogRequest));

        if(kaKaoSearchBlogResponse.isSuccess()) {
            return SearchBlogResponse.of(kaKaoSearchBlogResponse);
        }

        NaverSearchBlogResponse naverSearchBlogResponse = naverApiCaller.getSearchBlog(NaverSearchBlogRequest.of(searchBlogRequest));
        if(naverSearchBlogResponse.isSuccess()) {
            return SearchBlogResponse.of(naverSearchBlogResponse);
        }

        return SearchBlogResponse.empty();
    }
}
