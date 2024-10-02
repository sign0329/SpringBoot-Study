package com.ll.springboot_20240810.domain.Home;

import com.ll.springboot_20240810.domain.home.home.AdmHomeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AdmHomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("관리자가 아니면 관리자 페이지에 접속할수 없다.")
    @WithUserDetails("user")
    void t1() throws Exception {
        //When
        ResultActions resultActions =mvc
                .perform(get("/adm"))
                .andDo(print());
        //Then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName(("관리자라면 접속할수 있다."))
    @WithUserDetails("admin")
    void t2() throws Exception{
        ResultActions resultActions = mvc
                .perform(get("/adm"))
                .andDo(print());

        //Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AdmHomeController.class))
                .andExpect(handler().methodName("showMain"))
                .andExpect(view().name("/home/adm/main"));
    }

    @Test
    @DisplayName(("/adm/home/about"))
    @WithUserDetails("admin")
    void t3() throws Exception{
        ResultActions resultActions = mvc
                .perform(get("/adm/home/about"))
                .andDo(print());

        //Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(AdmHomeController.class))
                .andExpect(handler().methodName("showAbout"))
                .andExpect(view().name("/home/adm/about"));
    }


}
