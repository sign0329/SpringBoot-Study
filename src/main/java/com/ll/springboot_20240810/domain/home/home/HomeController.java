package com.ll.springboot_20240810.domain.home.home;

import com.ll.springboot_20240810.global.rq.Rq;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final Rq rq;

    @GetMapping("/")
    public String goToArticleList(String msg) {
        return rq.redirect("/article/list", msg);
    }

    @GetMapping("/home/session")
    @ResponseBody
    public Map<String, Object> showSession(HttpSession session) {
        return Collections.list(session.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> session.getAttribute(key)
                        )
                );
    }

    @GetMapping("/home/test1")
    @ResponseBody
    public String showTest1() {
        return Thread.currentThread().getName();
    }
    @GetMapping("/home/test2")
    @ResponseBody
    public String showTest2(String name) {
        return name + "님 안녕하세요하하!!!";
    }
}
