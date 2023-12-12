package com.task.external.blog.kakao.domain.searchBlog;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KaKaoSearchBlogResponse {

    private boolean success = true;

    @JsonProperty("meta")
    private MetaDTO meta;

    @JsonProperty("documents")
    private List<DocumentDTO> documents;

    private KaKaoSearchBlogResponse(boolean isSuccess) {
        this.success = isSuccess;
        this.meta = new MetaDTO();
        this.documents = new ArrayList<>();
    }

    public static KaKaoSearchBlogResponse fail() {
        return new KaKaoSearchBlogResponse(false);
    }
}
