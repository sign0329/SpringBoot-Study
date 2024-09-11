package com.ll.springboot_20240810.domain.article.article.entity;

import com.ll.springboot_20240810.domain.member.member.entity.Member;
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
    private Member author;
    private String title;
    private String body;

    public Article(Member author, String title, String body){
        this.author = author;
        this.title = title;
        this.body = body;
    }
}