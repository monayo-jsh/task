package com.task.restful.services;

import com.task.database.entity.Keyword;
import com.task.database.repository.KeywordRepository;
import com.task.domain.keyword.KeywordResVo;
import com.task.domain.keyword.result.KeywordPopularGetVo;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public KeywordPopularGetVo getPopularKeywords() {
        PageRequest popularKeywordRequest = makePopularKeywordRequest();
        Page<Keyword> keywords = keywordRepository.findAll(popularKeywordRequest);

        return new KeywordPopularGetVo(keywords.stream()
                                               .map(KeywordResVo::of)
                                               .limit(10)
                                               .collect(Collectors.toList()));
    }

    private PageRequest makePopularKeywordRequest() {
        int page = 0;
        int size = 10;
        Sort sort = Sort.by("searchCount")
                        .descending()
                        .and(Sort.by("word")
                                 .ascending());

        return PageRequest.of(page, size, sort);
    }

    @Async("asyncIncreaseKeywordExecutor")
    @Transactional
    public void increaseSearchCount(String word) {
        Optional<Keyword> keyword = keywordRepository.findById(word);
        if (keyword.isEmpty()) {
            keywordRepository.save(Keyword.builder().word(word).searchCount(1).build());
            return;
        }

        keywordRepository.increaseSearchCountByKeyword(word);
    }
}
