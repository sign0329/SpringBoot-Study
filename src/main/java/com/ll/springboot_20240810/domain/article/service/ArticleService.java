package com.ll.springboot_20240810.domain.article.service;

import com.ll.springboot_20240810.domain.article.article.entity.Article;
import com.ll.springboot_20240810.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    //생성자 주입
    public Article write(String title, String body) {
        Article article = new Article(title, body);
        articleRepository.save(article);

        return article;
    }

    public Article findLastArticle() {
        return articleRepository.findLastArticle();
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);

    }

    public void delete(long id) {
        articleRepository.delete(id);
    }

    public void modify(long id, String title, String body) {
        Article article = findById(id).get();
        article.setTitle(title);
        article.setBody(body);
    }
}
