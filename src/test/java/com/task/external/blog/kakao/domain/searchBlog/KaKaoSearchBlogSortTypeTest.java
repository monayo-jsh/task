package com.task.external.blog.kakao.domain.searchBlog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class KaKaoSearchBlogSortTypeTest {

    @Test
    @DisplayName("정의되지 않은 enum 반환 요청")
    void test1() {
        //when, then
        assertThrows(IllegalStateException.class, () -> KaKaoSearchBlogSortType.fromSortType("unKnown"));
    }

    @Test
    @DisplayName("정확도순 확인")
    void test2() {
        //given
        String type = "accuracy";

        //when
        KaKaoSearchBlogSortType sortType = KaKaoSearchBlogSortType.fromSortType(type);

        //then
        assertEquals(KaKaoSearchBlogSortType.accuracy, sortType);
    }

    @Test
    @DisplayName("최신순 확인")
    void test3() {
        //given
        String type = "recency";

        //when
        KaKaoSearchBlogSortType sortType = KaKaoSearchBlogSortType.fromSortType(type);

        //then
        assertEquals(KaKaoSearchBlogSortType.recency, sortType);
    }

}
