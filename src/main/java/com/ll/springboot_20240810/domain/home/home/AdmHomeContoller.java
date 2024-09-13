package com.ll.springboot_20240810.domain.home.home;

import com.ll.springboot_20240810.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdmHomeContoller {
    private final Rq rq;
    @GetMapping("/adm")
    public String showMain(){
        return "/home/adm/main";
    }

    @GetMapping("/adm/home/about")
    public String showAbout(){
        return "/home/adm/about";
    }
}