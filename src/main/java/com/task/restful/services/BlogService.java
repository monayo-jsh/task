package com.task.restful.services;

import com.task.domain.blog.SearchBlogReqVo;
import com.task.domain.blog.result.SearchBlogGetVo;
import com.task.external.blog.SearchBlogApi;
import com.task.external.blog.SearchBlogApiCaller;
import com.task.external.blog.domain.SearchBlogRequest;
import com.task.external.blog.domain.SearchBlogResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogService {

    private final SearchBlogApi searchBlogApi;
    private final KeywordService keywordService;

    public BlogService(SearchBlogApiCaller searchBlogApiCaller, KeywordService keywordService) {
        this.searchBlogApi = searchBlogApiCaller.getSearchBlogApi();
        this.keywordService = keywordService;
    }

    public SearchBlogGetVo getBlogs(SearchBlogReqVo reqVo) {
        SearchBlogResponse searchBlogResponse = searchBlogApi.search(SearchBlogRequest.builder()
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
