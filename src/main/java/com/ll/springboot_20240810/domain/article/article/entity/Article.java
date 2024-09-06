package com.ll.springboot_20240810.domain.article.article.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Article {
    private Long id;
    private String title;
    private String body;

    public Article(String title, String body){
        this.title = title;
        this.body = body;
    }
}