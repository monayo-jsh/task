package com.task.external.naver.domain.searchBlog;

import org.springframework.util.ObjectUtils;

public enum NaverSerchBlogSortType {
    sim, date;

    public static NaverSerchBlogSortType fromSortType(String sortType) {
        if(ObjectUtils.isEmpty(sortType)) return NaverSerchBlogSortType.sim;

        switch(sortType) {
            case "accuracy": return NaverSerchBlogSortType.sim;
            case "recency": return NaverSerchBlogSortType.date;

            default:
                throw new IllegalStateException("Unexpected value: " + sortType);
        }
    }
}
