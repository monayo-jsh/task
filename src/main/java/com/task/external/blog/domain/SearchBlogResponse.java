package com.task.external.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.domain.blog.SearchBlogResVo;
import com.task.external.blog.kakao.domain.searchBlog.KaKaoSearchBlogResponse;
import com.task.external.blog.naver.domain.searchBlog.NaverSearchBlogResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class SearchBlogResponse {

    @JsonIgnore
    private boolean success = true;

    private Integer totalCount;

    private List<SearchBlogResVo> blogs;

    private SearchBlogResponse(boolean success) {
        this.success = success;
    }

    public static SearchBlogResponse fail() {
        return new SearchBlogResponse(false);
    }

    @Builder
    private SearchBlogResponse(Integer totalCount, List<SearchBlogResVo> blogs) {
        this.totalCount = totalCount;
        this.blogs = blogs;
    }

    public static SearchBlogResponse empty() {
        return SearchBlogResponse.builder()
                                 .totalCount(0)
                                 .blogs(Collections.emptyList())
                                 .build();
    }

    public static SearchBlogResponse of(KaKaoSearchBlogResponse kaKaoSearchBlogResponse) {
        return SearchBlogResponse.builder()
                                 .totalCount(kaKaoSearchBlogResponse.getMeta().getPageableCount())
                                 .blogs(kaKaoSearchBlogResponse.getDocuments()
                                                               .stream()
                                                               .map(SearchBlogResVo::of)
                                                               .collect(Collectors.toList()))
                                 .build();
    }

    public static SearchBlogResponse of(NaverSearchBlogResponse naverSearchBlogResponse) {
        return SearchBlogResponse.builder()
                                 .totalCount(naverSearchBlogResponse.getTotal())
                                 .blogs(naverSearchBlogResponse.getItems()
                                                               .stream()
                                                               .map(SearchBlogResVo::of)
                                                               .collect(Collectors.toList()))
                                 .build();
    }
}
