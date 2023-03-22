package com.task.external.kakao.domain.searchBlog;

import org.springframework.util.ObjectUtils;

public enum KaKaoSearchBlogSortType {
    accuracy, recency;

    public static KaKaoSearchBlogSortType fromSortType(String sortType) {
        if(ObjectUtils.isEmpty(sortType)) return KaKaoSearchBlogSortType.accuracy;

        switch(sortType) {
            case "accuracy": return KaKaoSearchBlogSortType.accuracy;
            case "recency": return KaKaoSearchBlogSortType.recency;

            default:
                throw new IllegalStateException("Unexpected value: " + sortType);
        }
    }
}
