package com.ll.springboot_20240810.domain.article.article.controller;

import com.ll.springboot_20240810.domain.article.article.entity.Article;
import com.ll.springboot_20240810.domain.article.service.ArticleService;
import com.ll.springboot_20240810.domain.member.member.entity.Member;
import com.ll.springboot_20240810.domain.member.service.MemberService;
import com.ll.springboot_20240810.global.rq.Rq;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor //생성자 자동 주입
@RequestMapping("/article")
@Validated
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;
    private final Rq rq;

/*
    @Autowired //생성자 주입, 만얀 생성자가 하나면 autowired는 생략가능하다.
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
        }
*/

    @GetMapping("/write")
    String showWrite() {
        return "/article/write";
    }


    @Data
    public static class WriteForm {
        @NotBlank String title;
        @NotBlank String body;
    }

    @PostMapping("/write")
    String write(@Valid WriteForm writeForm) {
        Article article = articleService.write(writeForm.title, writeForm.body);
        return rq.redirect("/article/list", "%d번 게시글을 생성되었습니다.".formatted(article.getId()));
    }

    @GetMapping("/detail/{id}")
    String showDetail(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();
        model.addAttribute("article", article);
        return "/article/detail";
    }

    @GetMapping("/delete/{id}")
    String delete(@PathVariable long id) {
        articleService.delete(id);
        return rq.redirect("/article/list", "%d번 게시글을 삭제되었습니다.".formatted(id));

    }


    @GetMapping("/modify/{id}")
    String showModify(Model model, @PathVariable long id) {
        Article article = articleService.findById(id).get();

        model.addAttribute("article", article);

        return "/article/modify";
    }

    @Data
    public static class ModifyForm {
        @NotBlank String title;
        @NotBlank String body;
    }

    @PostMapping("/modify/{id}")
    String write(@PathVariable long id, @Valid ModifyForm modifyForm) {
        articleService.modify(id, modifyForm.title, modifyForm.body);
        return rq.redirect("/article/list", "%d번 게시글을 수정되었습니다.".formatted(id));

    }

    @GetMapping("/list")
    String showList(Model model, HttpServletRequest req) {
        // 쿠키이름이 loginedMemberId 이것인 것의 값을 가져와서 long 타입으로 변환, 만약에 그런게 없다면, 0을 반환
        long loginedMemberId = Optional.ofNullable(req.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                .map(Cookie::getValue)
                .mapToLong(Long::parseLong)
                .findFirst()
                .orElse(0);

        if (loginedMemberId > 0) {
            Member loginedMember = memberService.findById(loginedMemberId).get();
            model.addAttribute("loginedMember", loginedMember);
        }

        List<Article> articles = articleService.findAll();

        model.addAttribute("articles", articles);

        return "article/list";

    }
}

