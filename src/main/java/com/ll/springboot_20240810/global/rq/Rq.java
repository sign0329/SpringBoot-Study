package com.ll.springboot_20240810.global.rq;

import com.ll.springboot_20240810.domain.member.member.entity.Member;
import com.ll.springboot_20240810.domain.member.service.MemberService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequestScope
@Component
@Getter
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    private User user;
    private Member member;

    @PostConstruct
    public void init() {
        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        }
    }


    public String redirect(String path, String msg) {
        boolean containTtl = msg.contains(";ttl=");
        if (!containTtl) {msg = msg.split(";ttl=", 2)[0];}

        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        msg +="'ttl=" +  (new Date().getTime() + 1000 * 5);


        return "redirect:" + path + "?msg=" + msg;
    }

    private String getMemberUsername() {
        return user.getUsername();
    }

    public boolean isLogined() {
        return user != null;
    }

    public Member getMember() {
        if (!isLogined()) {
            return null;
        }
        if (member == null)
            member = memberService.findByUsername(getMemberUsername()).get();
        return member;
    }

    public void setSessionAttr(String attrName, Object value) {
        req.getSession().setAttribute(attrName, value);
    }

    public void removeSessionAttr(String name) {
        req.getSession().removeAttribute(name);
    }

    public <T> T getSessionAttr(String name) {
        return (T) req.getSession().getAttribute(name);
    }

    public boolean isAdmin() {
        if (!isLogined()) {
            return false;
        }

        return user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}