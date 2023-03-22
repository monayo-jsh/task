package com.task.restful.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.restful.services.BlogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(BlogController.class)
class BlogControllerTest {

    @MockBean
    private BlogService blogService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("정상 요청 확인 - keyword")
    void test1() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "test");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @Test
    @DisplayName("정상 요청 확인 - keyword & sort")
    void test2() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "test");
        params.add("sort", "accuracy");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @Test
    @DisplayName("정상 요청 확인 - keyword & sort & size")
    void test3() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "test");
        params.add("sort", "recency");
        params.add("size", "1");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @Test
    @DisplayName("정상 요청 확인 - keyword & sort & size & page")
    void test4() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "test");
        params.add("sort", "accuracy");
        params.add("size", "1");
        params.add("page", "10");

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @ParameterizedTest
    @DisplayName("파라미터 검증 - 키워드 필수")
    @NullSource @EmptySource
    void test5(String keyword) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", keyword);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isBadRequest())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @ParameterizedTest
    @DisplayName("파라미터 검증 - sort 유효성 검증")
    @ValueSource(strings = {"accuracy", "recency"})
    void test6(String sort) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "keyword");
        params.add("sort", sort);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @ParameterizedTest
    @DisplayName("파라미터 검증 - sort 유효성 검증")
    @EmptySource
    @ValueSource(strings = {" ", "sort"})
    void test7(String sort) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "keyword");
        params.add("sort", sort);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isBadRequest())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @ParameterizedTest
    @DisplayName("파라미터 검증 - page 유효성 검증")
    @NullSource @EmptySource
    @ValueSource(strings = {"1", "25", "50"})
    void test8(String page) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "keyword");
        params.add("page", page);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @ParameterizedTest
    @DisplayName("파라미터 검증 - page 유효성 검증")
    @ValueSource(strings = {"-1", "51"})
    void test9(String page) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "keyword");
        params.add("page", page);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isBadRequest())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @ParameterizedTest
    @DisplayName("파라미터 검증 - size 유효성 검증")
    @NullSource @EmptySource
    @ValueSource(strings = {"1", "25", "50"})
    void test10(String size) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "keyword");
        params.add("size", size);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }

    @ParameterizedTest
    @DisplayName("파라미터 검증 - size 유효성 검증")
    @ValueSource(strings = {"-1", "51"})
    void test11(String size) throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("keyword", "keyword");
        params.add("size", size);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/blog/search")
                                                                            .params(params));

        //then
        resultActions.andExpect(status().isBadRequest())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }
}