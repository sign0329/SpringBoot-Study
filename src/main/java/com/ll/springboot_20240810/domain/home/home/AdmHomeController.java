package com.ll.springboot_20240810.domain.home.home;

import com.ll.springboot_20240810.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm")
public class AdmHomeController {
    private final Rq rq;

    @GetMapping("")
    public String showMain(){
        return "/home/adm/main";
    }

    @GetMapping("/home/about")
    public String showAbout(){
        return "/home/adm/about";
    }
}