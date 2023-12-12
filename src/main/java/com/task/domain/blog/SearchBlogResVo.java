package com.task.domain.blog;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.external.blog.kakao.domain.searchBlog.DocumentDTO;
import com.task.external.blog.naver.domain.searchBlog.ItemDTO;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchBlogResVo {
    @JsonProperty("blog_name")
    private String blogName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("summary_contents")
    private String summaryContents;
    @JsonProperty("blog_url")
    private String blogUrl;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    private SearchBlogResVo() {}

    @Builder
    private SearchBlogResVo(String blogName, String title, String summaryContents, String blogUrl,
                           String thumbnail, LocalDateTime createdDate) {
        this.blogName = blogName;
        this.title = title;
        this.summaryContents = summaryContents;
        this.blogUrl = blogUrl;
        this.thumbnail = thumbnail;
        this.createdDate = createdDate;
    }

    public static SearchBlogResVo of(DocumentDTO documentDTO) {
        return SearchBlogResVo.builder()
                              .blogName(documentDTO.getBlogName())
                              .title(documentDTO.getTitle())
                              .summaryContents(documentDTO.getContents())
                              .blogUrl(documentDTO.getUrl())
                              .thumbnail(documentDTO.getThumbnail())
                              .createdDate(documentDTO.getDatetime())
                              .build();
    }

    public static SearchBlogResVo of(ItemDTO itemDTO) {
        return SearchBlogResVo.builder()
                              .blogName(itemDTO.getBloggerName())
                              .title(itemDTO.getTitle())
                              .summaryContents(itemDTO.getDescription())
                              .blogUrl(itemDTO.getLink())
                              .createdDate(LocalDateTime.of(itemDTO.getPostdate(), LocalTime.of(0, 0)))
                              .build();
    }
}
