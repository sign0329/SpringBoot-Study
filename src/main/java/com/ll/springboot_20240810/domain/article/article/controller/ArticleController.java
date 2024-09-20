package com.ll.springboot_20240810.domain.article.article.controller;

import com.ll.springboot_20240810.domain.article.article.entity.Article;
import com.ll.springboot_20240810.domain.article.service.ArticleService;
import com.ll.springboot_20240810.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor //생성자 자동 주입
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final Rq rq;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    @SneakyThrows
    String showWrite() {
        if (!rq.isLogined()) throw new RuntimeException("로그인 후 이용해주세요");
        return "/article/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    String write(@Valid WriteForm writeForm) {
        if (!rq.isLogined()) throw new RuntimeException("로그인 후 이용해주세요");
        Article article = articleService.write(rq.getMember(), writeForm.title, writeForm.body);

        return rq.redirect("/article/list", "%d번 게시물 생성되었습니다.".formatted(article.getId()));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    String showList(Model model) {
        List<Article> articles = articleService.findAll();

        model.addAttribute("articles", articles);

        return "article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    String showDetail(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();

        model.addAttribute("article", article);

        return "/article/detail";
    }


    @Data
    public static class WriteForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @Data
    public static class ModifyForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    String showModify(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();

        if (!articleService.canModify(rq.getMember(), article)) throw new RuntimeException("수정권한이 없습니다");

        model.addAttribute("article", article);

        return "/article/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/modify/{id}")
    String modify(@PathVariable long id, @Valid ModifyForm modifyForm) {
        articleService.modify(id, modifyForm.title, modifyForm.body);

        return rq.redirect("/article/list", "%d번 게시물 수정되었습니다.".formatted(id));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    String delete(@PathVariable long id) {
        Article article = articleService.findById(id).get();
        
        if (!articleService.canDelete(rq.getMember(), article)) throw new RuntimeException("삭재권한이 없습니다");

        articleService.delete(article);

        return rq.redirect("/article/list", "%d번 게시물 삭제되었습니다.".formatted(id));
    }
}
