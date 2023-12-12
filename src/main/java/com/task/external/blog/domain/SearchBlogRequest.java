package com.task.external.blog.domain;

import com.task.external.blog.kakao.domain.searchBlog.KaKaoSearchBlogSortType;
import com.task.external.blog.naver.domain.searchBlog.NaverSerchBlogSortType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchBlogRequest {
    private String query;
    private String sort;
    //1~50, default: 1
    private Integer page;
    //1~50, default: 10
    private Integer size;

    @Builder
    public SearchBlogRequest(String query, String sort, Integer page, Integer size) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }

    public KaKaoSearchBlogSortType getKakaoSortType() {
        return KaKaoSearchBlogSortType.fromSortType(this.sort);
    }

    public NaverSerchBlogSortType getNaverSortType() {
        return NaverSerchBlogSortType.fromSortType(this.sort);
    }

}
