package com.ll.springboot_20240810.domain.home.home;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @GetMapping("/")
    public String goToHome(){return "/";}

    @GetMapping("/home/session")
    @ResponseBody
    public Map<String, Object> showSession(HttpSession session){
        return Collections.list(session.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> session.getAttribute(key)
                        )
                );
    }
}
