package com.task.restful.services;

import com.task.domain.blog.SearchBlogReqVo;
import com.task.domain.blog.result.SearchBlogGetVo;
import com.task.external.domain.blog.SearchBlogRequest;
import com.task.external.domain.blog.SearchBlogResponse;
import com.task.external.service.BlogSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogService {

    private final BlogSearchService blogSearchService;
    private final KeywordService keywordService;

    public BlogService(BlogSearchService blogSearchService, KeywordService keywordService) {
        this.blogSearchService = blogSearchService;
        this.keywordService = keywordService;
    }

    public SearchBlogGetVo getBlogs(SearchBlogReqVo reqVo) throws Exception {
        SearchBlogResponse searchBlogResponse = blogSearchService.blogSearch(SearchBlogRequest.builder()
                                                                                              .query(reqVo.getKeyword())
                                                                                              .sort(reqVo.getSort())
                                                                                              .page(reqVo.getPage())
                                                                                              .size(reqVo.getSize())
                                                                                              .build());

        if (reqVo.getPage() == 1) {
            try {
                keywordService.increaseSearchCount(reqVo.getKeyword());
            } catch(Exception e) {
                log.error("keywordService.increaseSearchCount(..) error", e);
            }
        }

        return SearchBlogGetVo.of(searchBlogResponse);
    }

}
