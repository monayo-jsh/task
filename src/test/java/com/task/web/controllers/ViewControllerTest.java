package com.task.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ViewController.class)
class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/blog 페이지 로드 확인")
    void test1() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/blog"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("blog"));
    }

    @Test
    @DisplayName("/keyword 페이지 로드 확인")
    void test2() throws Exception {
        //given

        //when
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/keyword"));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andExpect(MockMvcResultMatchers.view().name("keyword"));
    }

}