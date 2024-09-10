package com.ll.springboot_20240810.domain.article.article.controller;

import com.ll.springboot_20240810.domain.article.article.entity.Article;
import com.ll.springboot_20240810.domain.article.service.ArticleService;
import com.ll.springboot_20240810.domain.member.service.MemberService;
import com.ll.springboot_20240810.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor //생성자 자동 주입
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/list")
    String showList(Model model) {
        List<Article> articles = articleService.findAll();

        model.addAttribute("articles", articles);

        return "article/list";
    }

    @GetMapping("/detail/{id}")
    String showDetail(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();

        model.addAttribute("article", article);

        return "/article/detail";
    }

    @GetMapping("/write")
    String showWrite() {
        return "/article/write";
    }

    @Data
    public static class WriteForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @PostMapping("/write")
    String write(@Valid WriteForm writeForm) {
        Article article = articleService.write(writeForm.title, writeForm.body);

        return rq.redirect("/article/list", "%d번 게시물 생성되었습니다.".formatted(article.getId()));
    }

    @GetMapping("/modify/{id}")
    String showModify(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();

        model.addAttribute("article", article);

        return "/article/modify";
    }

    @Data
    public static class ModifyForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @PostMapping("/modify/{id}")
    String modify(@PathVariable long id, @Valid ModifyForm modifyForm) {
        articleService.modify(id, modifyForm.title, modifyForm.body);

        return rq.redirect("/article/list", "%d번 게시물 수정되었습니다.".formatted(id));
    }

    @GetMapping("/delete/{id}")
    String delete(@PathVariable long id) {
        articleService.delete(id);

        return rq.redirect("/article/list", "%d번 게시물 삭제되었습니다.".formatted(id));
    }
}
