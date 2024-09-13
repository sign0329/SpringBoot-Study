package com.ll.springboot_20240810.domain.article.service;

import com.ll.springboot_20240810.domain.article.article.entity.Article;
import com.ll.springboot_20240810.domain.article.repository.ArticleRepository;
import com.ll.springboot_20240810.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    //생성자 주입
    public Article write(Member author, String title, String body) {
        Article article = new Article(author, title, body);
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

    public void delete(Article article) {
        articleRepository.delete(article);
    }

    public void modify(long id, String title, String body) {
        Article article = findById(id).get();
        article.setTitle(title);
        article.setBody(body);
    }

    public boolean canModify(Member actor, Article article) {
        return article.getAuthor().equals(actor);
    }

    public boolean canDelete(Member actor, Article article) {
        if (actor == null) return false;

        if (actor.isAdmin()) return true;

        return article.getAuthor().equals(actor);

    }
}
