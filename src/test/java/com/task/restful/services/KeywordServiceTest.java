package com.task.restful.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.task.database.entity.Keyword;
import com.task.database.repository.KeywordRepository;
import com.task.domain.keyword.KeywordResVo;
import com.task.domain.keyword.result.KeywordPopularGetVo;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class KeywordServiceTest {

    @Mock
    private KeywordRepository keywordRepository;

    @InjectMocks
    private KeywordService keywordService;

    @Test
    @DisplayName("응답 객체는 반드시 제공")
    void test1() {
        //given
        given(keywordRepository.findAll((Pageable) any())).willReturn(Page.empty());

        //when
        KeywordPopularGetVo keywordPopularGetVo = keywordService.getPopularKeywords();

        //then
        assertNotNull(keywordPopularGetVo);
    }

    @Test
    @DisplayName("최대 10개의 검색 키워드를 제공")
    void test2() {
        //given
        given(keywordRepository.findAll((Pageable) any())).willReturn(new PageImpl<>(makeKeywords(11)));

        //when
        KeywordPopularGetVo keywordPopularGetVo = keywordService.getPopularKeywords();

        //then
        assertEquals(10, keywordPopularGetVo.getKeywords().size());
    }


    @Test
    @DisplayName("많이 검색된 순서")
    void test3() {
        //given
        List<Keyword> keywords = makeKeywords(11);
        given(keywordRepository.findAll((Pageable) any())).willReturn(new PageImpl<>(keywords));

        List<KeywordResVo> popularKeywords = makePopularKeywords(keywords, 10);

        //when
        KeywordPopularGetVo keywordPopularGetVo = keywordService.getPopularKeywords();

        //then
        assertEquals(popularKeywords, keywordPopularGetVo.getKeywords());
    }

    @Test
    @DisplayName("검색어 별로 검색된 횟수도 함께 제공")
    void test4() {
        //given
        List<Keyword> keywords = makeKeywords(11);
        given(keywordRepository.findAll((Pageable) any())).willReturn(new PageImpl<>(keywords));

        //when
        KeywordPopularGetVo keywordPopularGetVo = keywordService.getPopularKeywords();

        //then
        for(KeywordResVo keywordResVo : keywordPopularGetVo.getKeywords()) {
            assertNotNull(keywordResVo.getSearchedCount());
        }
    }


    @Test
    @DisplayName("검색어 저장 확인")
    void test5() {

        Keyword keyword = makeKeyword("리뷰");
        given(keywordRepository.findById(keyword.getWord())).willReturn(Optional.empty());

        //when
        keywordService.increaseSearchCount(keyword.getWord());

        //then
        verify(keywordRepository, atLeastOnce()).findById(keyword.getWord());
        verify(keywordRepository, atLeastOnce()).save(any());
        verify(keywordRepository, never()).increaseSearchCountByKeyword(keyword.getWord());
    }

    @Test
    @DisplayName("키워드 조회수 증가 확인")
    void test6() {
        //given
        Keyword keyword = makeKeyword("리뷰");
        given(keywordRepository.findById(keyword.getWord())).willReturn(Optional.of(keyword));

        //when
        keywordService.increaseSearchCount(keyword.getWord());

        //then
        verify(keywordRepository, atLeastOnce()).findById(keyword.getWord());
        verify(keywordRepository, never()).save(any());
        verify(keywordRepository, atLeastOnce()).increaseSearchCountByKeyword(keyword.getWord());
    }

    private Keyword makeKeyword(String keyword) {
        return Keyword.builder()
                      .word(keyword)
                      .searchCount(1)
                      .build();
    }
    private List<KeywordResVo> makePopularKeywords(List<Keyword> keywords, int size) {
        return keywords.stream()
                       .sorted(Comparator.comparing(Keyword::getSearchCount).reversed())
                       .limit(size)
                       .map(KeywordResVo::of)
                       .collect(Collectors.toList());
    }

    private List<Keyword> makeKeywords(int size) {
        //expected query result
        return IntStream.range(0, size)
                        .mapToObj(i -> Keyword.builder()
                                              .word("word"+i)
                                              .searchCount(i)
                                              .build())
                        .sorted(Comparator.comparing(Keyword::getSearchCount).reversed())
                        .collect(Collectors.toList());
    }
}