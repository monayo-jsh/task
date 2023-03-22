package com.task.domain.keyword.result;

import com.task.domain.keyword.KeywordResVo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordPopularGetVo {
    private List<KeywordResVo> keywords;
}
