package com.task.domain.blog;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SearchBlogReqVo {
    @NotEmpty
    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("sort")
    @Pattern(regexp = "accuracy|recency")
    private String sort;

    @Min(value = 1)
    @Max(value = 50)
    @JsonProperty("page")
    private Integer page = 1;

    @Min(value = 1)
    @Max(value = 50)
    @JsonProperty("size")
    private Integer size = 10;
}
