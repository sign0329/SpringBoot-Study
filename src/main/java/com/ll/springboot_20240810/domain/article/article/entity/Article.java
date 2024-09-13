package com.ll.springboot_20240810.domain.article.article.entity;

import com.ll.springboot_20240810.domain.member.member.entity.Member;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Article {
    @EqualsAndHashCode.Include
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