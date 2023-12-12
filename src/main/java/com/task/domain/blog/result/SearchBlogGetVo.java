package com.task.domain.blog.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.domain.blog.SearchBlogResVo;
import com.task.external.blog.domain.SearchBlogResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchBlogGetVo {

    @JsonProperty("total_count")
    private Integer totalCount;

    @JsonProperty("blogs")
    private List<SearchBlogResVo> blogs;

    @Builder
    private SearchBlogGetVo(Integer totalCount, List<SearchBlogResVo> blogs) {
        this.totalCount = totalCount;
        this.blogs = blogs;
    }

    public static SearchBlogGetVo of(SearchBlogResponse searchBlogResponse) {
        return SearchBlogGetVo.builder()
                              .totalCount(searchBlogResponse.getTotalCount())
                              .blogs(searchBlogResponse.getBlogs())
                              .build();
    }
}
