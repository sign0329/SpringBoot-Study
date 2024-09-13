package com.ll.springboot_20240810.domain.member.member.controller;

import com.ll.springboot_20240810.domain.member.member.entity.Member;
import com.ll.springboot_20240810.domain.member.service.MemberService;
import com.ll.springboot_20240810.global.rq.Rq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/join")
    String showJoin() {
        return "/member/join";
    }


    @Data
    public static class LoginForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @GetMapping("/login")
    String showLogin(){return "/member/login";}

    @PostMapping("/login")
    String login(@Valid LoginForm loginForm, HttpServletResponse response, HttpServletRequest req) {
        Member member = memberService.findByUsername(loginForm.username).get();

        if (!member.getPassword().equals(loginForm.password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        rq.setSessionAttr("loginedMemberId", member.getId());
        rq.setSessionAttr("authorities", member.getAuthorities());

        return rq.redirect("/article/list", "로그인이 완료되었습니다.");
    }


    @GetMapping("/logout")
    String logout() {
        rq.removeSessionAttr("loginedMemberId");
        return rq.redirect("/article/list", "로그아웃이 완료되었습니다.");
    }

    @Data
    public static class JoinForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PostMapping("/member/join")
    String join(@Valid JoinForm joinForm) {
        memberService.join(joinForm.username, joinForm.password);
        return rq.redirect("member/join", "회원가입이 완료되었습니다");
    }

}