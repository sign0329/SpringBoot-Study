package com.ll.springboot_20240810.domain.home.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeContoller {
    @GetMapping("/")
    public String goToHome(){return "/";}
}
