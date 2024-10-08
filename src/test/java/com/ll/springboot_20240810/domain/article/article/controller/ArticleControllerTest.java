package com.ll.springboot_20240810.domain.article.article.controller;

import com.ll.springboot_20240810.domain.article.article.entity.Article;
import com.ll.springboot_20240810.domain.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional

public class ArticleControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ArticleService articleService;


    // GET /article/list
    @Test
    @DisplayName("게시물 목록 페이지를 보여준다")
    void t1() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/list"))
                .andDo(print());

        Article article = articleService.findLatest().get();
        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showList"))
                .andExpect(content().string(containsString("""
                        글 목록
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        %d번 : %s
                        """.formatted(article.getId(), article.getTitle()).stripIndent().trim())));

    }


    // GET /article/detail/{id}
    @Test
    @DisplayName("게시물 내용 페이지를 보여준다")
    void t2() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/detail/1"))
                .andDo(print());
        Article article = articleService.findById(1L).get();
        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showDetail"))
                .andExpect(content().string(containsString("""
                        게시글 내용
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <div class="badge badge-outline">1</div>
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString(article.getTitle())))
                .andExpect(content().string(containsString(article.getBody())));
    }

    // GET /article/write
    @Test
    @DisplayName("게시물 작성 페이지를 보여준다")
    @WithUserDetails("user")
    void t3() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/write"))
                .andDo(print());
        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showWrite"))
                .andExpect(content().string(containsString("""
                        게시글 작성
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="text" name="title"
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <textarea name="body"
                        """.stripIndent().trim())));
    }

    // POST /article/write
    @Test
    @DisplayName("게시물을 작성한다")
    @WithUserDetails("user")
    void t4() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/article/write")
                        .with(csrf())
                        .param("title", "제목 new")
                        .param("body", "내용 new"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("write"))
                .andExpect(redirectedUrlPattern("/?msg=**"));

        Article article = articleService.findLatest().get();

        assertThat(article.getTitle()).isEqualTo("제목 new");
        assertThat(article.getBody()).isEqualTo("내용 new");


    }
    @DisplayName("작성자가 아니라면 수정폼을 볼 수 없다.")
    @WithUserDetails("user")
    void t5() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/modify/1"))
                .andDo(print());
        // THEN
        resultActions
                .andExpect(status().is4xxClientError());
    }


    // GET /article/modify/{id}
    @Test
    @DisplayName("글 수정폼 페이지 보여줌.")
    @WithUserDetails("admin")
    void t6() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/article/modify/1"))
                .andDo(print());

        Article article = articleService.findById(1L).get();

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))

                .andExpect(handler().methodName("showModify"))
                .andExpect(content().string(containsString("""
                        글 수정
                        """.stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <input type="text" name="title" value="%s"
                        """.formatted(article.getTitle()).stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        <textarea name="body"
                        """.formatted(article.getBody()).stripIndent().trim())))
                .andExpect(content().string(containsString("""
                        >%s</textarea>
                        """.formatted(article.getBody()).stripIndent().trim())));
    }


    // POST /article/modify/{id}
    @Test
    @DisplayName("글 수정을 보여줌")
    @WithUserDetails("admin")
    void t7() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(put("/article/modify/1")
                        .with(csrf())
                        .param("title", "제목 new")
                        .param("body", "내용 new"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(redirectedUrlPattern("/=**"));

        Article article = articleService.findById(1L).get();

        assertThat(article.getTitle()).isEqualTo("제목 new");
        assertThat(article.getBody()).isEqualTo("내용 new");
    }
    @Test
    @DisplayName("게시물 삭제")
    @WithUserDetails("admin")
    void t8() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        delete("/article/delete/1")
                                .with(csrf())
                )
                .andDo(print());
        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(redirectedUrlPattern("/?msg=**"));
        Optional<Article> optionalArticle = articleService.findById(1L);
        assertThat(optionalArticle.isEmpty()).isEqualTo(true);
    }
}