package com.task.external.naver.domain.searchBlog;

import com.task.external.domain.blog.SearchBlogRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NaverSearchBlogRequest {
    private String query;
    private NaverSerchBlogSortType sort;
    //1~50, default: 1
    private Integer start;
    //1~50, default: 10
    private Integer display;

    @Builder
    public NaverSearchBlogRequest(String query, NaverSerchBlogSortType sort, Integer start, Integer display) {
        this.query = query;
        this.sort = sort;
        this.start = start;
        this.display = display;
    }

    public static NaverSearchBlogRequest of(SearchBlogRequest searchBlogRequest) {
        return NaverSearchBlogRequest.builder()
                                     .query(searchBlogRequest.getQuery())
                                     .sort(searchBlogRequest.getNaverSortType())
                                     .start(searchBlogRequest.getPage())
                                     .display(searchBlogRequest.getSize())
                                     .build();
    }
}
