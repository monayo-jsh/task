package com.task.external.blog.kakao.domain.searchBlog;

import com.task.external.blog.domain.SearchBlogRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KaKaoSearchBlogRequest {
    private String query;
    private KaKaoSearchBlogSortType sort;
    //1~50, default: 1
    private Integer page;
    //1~50, default: 10
    private Integer size;

    @Builder
    private KaKaoSearchBlogRequest(String query, KaKaoSearchBlogSortType sort, Integer page, Integer size) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }

    public static KaKaoSearchBlogRequest of(SearchBlogRequest searchBlogRequest) {
        return KaKaoSearchBlogRequest.builder()
                                     .query(searchBlogRequest.getQuery())
                                     .sort(searchBlogRequest.getKakaoSortType())
                                     .page(searchBlogRequest.getPage())
                                     .size(searchBlogRequest.getSize())
                                     .build();
    }
}
