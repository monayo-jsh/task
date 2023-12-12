package com.task.external.blog;

import com.task.external.blog.kakao.KakaoApiCaller;
import com.task.external.blog.naver.NaverApiCaller;
import org.springframework.stereotype.Component;

@Component
public class SearchBlogApiCaller {

    private final SearchBlogApi searchBlogApi;

    public SearchBlogApiCaller(KakaoApiCaller kakaoApiCaller, NaverApiCaller naverApiCaller) {
        //first search blog
        this.searchBlogApi = kakaoApiCaller;

        //next search blog
        kakaoApiCaller.setNextBlogApiCaller(naverApiCaller);
    }

    public SearchBlogApi getSearchBlogApi() {
        return searchBlogApi;
    }
}
