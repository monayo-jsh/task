package com.task.domain.blog;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

public enum SearchBlogSortType {
    accuracy, recency;

    @JsonCreator
    public static SearchBlogSortType fromCode(String code) {
        return Arrays.stream(SearchBlogSortType.values())
                     .filter(subscriptionType -> subscriptionType.name().equals(code))
                     .findFirst()
                     .orElse(SearchBlogSortType.accuracy);
    }
}
