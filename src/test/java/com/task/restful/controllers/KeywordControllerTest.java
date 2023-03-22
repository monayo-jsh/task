package com.task.restful.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.task.restful.services.KeywordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(KeywordController.class)
class KeywordControllerTest {

    @MockBean
    private KeywordService keywordService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test1() throws Exception {
        //given

        //when
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/keyword/popular"));

        //then
        resultActions.andExpect(status().isOk())
                     .andExpect(jsonPath("$.code").exists())
                     .andExpect(jsonPath("$.message").exists())
                     .andExpect(jsonPath("$.result").hasJsonPath());
    }
}