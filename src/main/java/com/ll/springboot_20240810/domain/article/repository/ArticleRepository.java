package com.ll.springboot_20240810.domain.article.repository;

import com.ll.springboot_20240810.domain.article.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    private final List<Article> articles;

    public Article save(Article article){
        if(article.getId() == null){
            article.setId(articles.size() +1L);
        }

        articles.add(article);

        return article;
    }
    public Article findLastArticle() {
        return articles.getLast();
    }

    public List<Article> findAll() {
        return articles;
    }

    public Optional<Article> findById(long id) {
        return articles.stream()
                .filter(article -> article.getId() == id).
                findFirst();
    }

    public void delete(long id) {
        articles.removeIf(article -> article.getId() == id);
    }
}
