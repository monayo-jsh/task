package com.task.external.service;

import com.task.external.domain.blog.SearchBlogRequest;
import com.task.external.domain.blog.SearchBlogResponse;

public interface BlogSearchService {
    SearchBlogResponse blogSearch(SearchBlogRequest searchBlogRequest) throws Exception;
}
