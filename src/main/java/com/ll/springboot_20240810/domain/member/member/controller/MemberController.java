package com.ll.springboot_20240810.domain.member.member.controller;

import com.ll.springboot_20240810.domain.member.member.entity.Member;
import com.ll.springboot_20240810.domain.member.service.MemberService;
import com.ll.springboot_20240810.global.rq.Rq;
import com.ll.springboot_20240810.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    String showJoin() {
        return "/member/join";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    String showLogin() {
        return "/member/login";
    }

    @Data
    public static class JoinForm {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    String join(@Valid JoinForm joinForm) {
        try {
            RsData<Member> joinRs = memberService.join(joinForm.username, joinForm.password);

            if (joinRs.isFail()) {
                return rq.historyBack(joinRs.getMsg());
            }
            return rq.redirect("/member/join", joinRs.getMsg());
        } catch (RuntimeException e) {
            return rq.historyBack(e.getMessage());
        }
    }
}