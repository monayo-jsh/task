package com.task.external.naver.domain.searchBlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NaverSearchBlogResponse {

    private boolean success = true;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("items")
    private List<ItemDTO> items;

    private NaverSearchBlogResponse(boolean isSuccess) {
        this.success = isSuccess;
        this.total = 0;
        this.items = new ArrayList<>();
    }

    public static NaverSearchBlogResponse fail() {
        return new NaverSearchBlogResponse(false);
    }
}
