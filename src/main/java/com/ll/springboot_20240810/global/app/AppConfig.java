package com.ll.springboot_20240810.global.app;


import com.ll.springboot_20240810.domain.article.article.entity.Article;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    List<Article> articles(){
        return new java.util.LinkedList<>();
    }

}
