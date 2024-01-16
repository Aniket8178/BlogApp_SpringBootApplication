package com.blogapp.articles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.articles.dtos.CreateArticleRequest;
import com.blogapp.articles.dtos.UpdateArticleRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<ArticleEntity>> getAllArticles() {
        Iterable<ArticleEntity> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleEntity> getArticleBySlug(@PathVariable String slug) {
        ArticleEntity article = articleService.getArticleBySlug(slug);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/{authorId}")
    public ResponseEntity<ArticleEntity> createArticle(
            @PathVariable Long authorId,
            @RequestBody CreateArticleRequest createArticleRequest) {
        ArticleEntity newArticle = articleService.createArticle(authorId, createArticleRequest);
        return ResponseEntity.ok(newArticle);
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<ArticleEntity> updateArticle(
            @PathVariable Long articleId,
            @RequestBody UpdateArticleRequest updateArticleRequest) {
        ArticleEntity updatedArticle = articleService.updateArticle(articleId, updateArticleRequest);
        return ResponseEntity.ok(updatedArticle);
    }
}
