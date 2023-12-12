package com.task.external.blog;

import com.task.external.blog.domain.SearchBlogRequest;
import com.task.external.blog.domain.SearchBlogResponse;
import org.springframework.util.ObjectUtils;

public abstract class SearchBlogApi {

    private SearchBlogApi nextBlogApiCaller;

    public void setNextBlogApiCaller(SearchBlogApi nextBlogApiCaller) {
        this.nextBlogApiCaller = nextBlogApiCaller;
    }

    public SearchBlogResponse search(SearchBlogRequest request) {
        SearchBlogResponse resolve = resolve(request);
        if (resolve.isSuccess()) {
            return resolve;
        }

        if (!ObjectUtils.isEmpty(nextBlogApiCaller)) {
            return nextBlogApiCaller.search(request);
        }

        return SearchBlogResponse.empty();
    }

    protected abstract SearchBlogResponse resolve(SearchBlogRequest request);

}
