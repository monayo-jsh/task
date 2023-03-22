package com.task.external.naver.domain.searchBlog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class NaverSerchBlogSortTypeTest {

    @Test
    @DisplayName("정의되지 않은 enum 반환 요청")
    void test1() {
        //when, then
        assertThrows(IllegalStateException.class, () -> NaverSerchBlogSortType.fromSortType("unKnown"));
    }

    @Test
    @DisplayName("정확도순 확인")
    void test2() {
        //given
        String type = "accuracy";

        //when
        NaverSerchBlogSortType sortType = NaverSerchBlogSortType.fromSortType(type);

        //then
        assertEquals(NaverSerchBlogSortType.sim, sortType);
    }

    @Test
    @DisplayName("최신순 확인")
    void test3() {
        //given
        String type = "recency";

        //when
        NaverSerchBlogSortType sortType = NaverSerchBlogSortType.fromSortType(type);

        //then
        assertEquals(NaverSerchBlogSortType.date, sortType);
    }

}