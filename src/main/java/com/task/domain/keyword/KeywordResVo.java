package com.task.domain.keyword;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.database.entity.Keyword;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KeywordResVo {

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("searched_count")
    private Integer searchedCount;

    private KeywordResVo() {}

    @Builder
    private KeywordResVo(String keyword, Integer searchedCount) {
        this.keyword = keyword;
        this.searchedCount = searchedCount;
    }

    public static KeywordResVo of(Keyword keyword) {
        return KeywordResVo.builder()
                           .keyword(keyword.getWord())
                           .searchedCount(keyword.getSearchCount())
                           .build();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        KeywordResVo that = (KeywordResVo) o;
        return Objects.equals(getKeyword(), that.getKeyword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKeyword());
    }
}
