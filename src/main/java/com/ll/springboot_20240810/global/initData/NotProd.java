package com.ll.springboot_20240810.global.initData;

import com.ll.springboot_20240810.domain.article.service.ArticleService;
import com.ll.springboot_20240810.domain.member.member.entity.Member;
import com.ll.springboot_20240810.domain.member.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotProd {

    @Bean
    public ApplicationRunner initNotProd(
            MemberService memberService,
            ArticleService articleService
    ) {
        return args -> {
            Member memberAdmin = memberService.join("admin", "1234").getData();
            Member memberUser1 = memberService.join("user", "1234").getData();
            Member memberUser2 = memberService.join("user1", "1234").getData();

            articleService.write(memberAdmin, "제목1", "내용1");
            articleService.write(memberUser1, "제목2", "내용2");
            articleService.write(memberUser1, "제목3", "내용3");
        };
    }
}
