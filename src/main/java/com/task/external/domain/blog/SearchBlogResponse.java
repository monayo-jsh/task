package com.task.external.domain.blog;

import com.task.domain.blog.SearchBlogResVo;
import com.task.external.kakao.domain.searchBlog.KaKaoSearchBlogResponse;
import com.task.external.naver.domain.searchBlog.NaverSearchBlogResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchBlogResponse {

    private Integer totalCount;

    private List<SearchBlogResVo> blogs;

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
