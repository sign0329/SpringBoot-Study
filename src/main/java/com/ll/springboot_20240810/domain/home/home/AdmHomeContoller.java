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
        if (!rq.isAdmin()){
            throw new RuntimeException("관리자만 접근할수 있습니다.");
        }
        return "/home/adm/main";
    }

    @GetMapping("/adm/home/about")
    public String showAbout(){
        if (!rq.isAdmin()){
            throw new RuntimeException("관리자만 접근할수 있습니다.");
        }

        return "/home/adm/about";
    }
}
