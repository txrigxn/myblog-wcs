package com.myBlog.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.ArticleDTO;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.service.ArticleService.ArticleReadService;
import com.myBlog.myblog.service.ArticleService.ArticleWriteService;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/articles")
public class ArticleController {
  
  private final ArticleReadService articleReadService;
  private final ArticleWriteService articleWriteService;
  
  public ArticleController(ArticleReadService articleReadService, ArticleWriteService articleWriteService) {
    this.articleWriteService = articleWriteService;
    this.articleReadService = articleReadService;
  }

  @GetMapping
  public ResponseEntity<List<ArticleDTO>> getAllArticles() {
    List<ArticleDTO> articles = articleReadService.getAllArticles();
    if(articles.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(articles);
    
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
       ArticleDTO article = articleReadService.getArticleById(id);
        if (article == null) {
          return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
  }

  @PostMapping
  public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
      ArticleDTO savedArticle = articleWriteService.createArticle(article);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
      ArticleDTO updatedArticle = articleWriteService.updateArticle(id, articleDetails);
      if (updatedArticle == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(updatedArticle);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    if (articleWriteService.deleteArticle(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/search-title")
  public ResponseEntity<List<ArticleDTO>> getArticlesByTitle(@RequestParam String title) {
    List<ArticleDTO> articleDTOs = articleReadService.getArticlesByTitle(title);
    return articleDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
  }

  @GetMapping("/search-content")
  public ResponseEntity<List<ArticleDTO>> getArticlesByContent(@RequestParam String content) {
    List<ArticleDTO> articleDTOs = articleReadService.getArticlesByContent(content);
    return articleDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
  }

  @GetMapping("/created-after")
    public ResponseEntity<List<ArticleDTO>> getArticlesCreateAfter(@RequestParam LocalDateTime date) {
      List<ArticleDTO> articleDTOs = articleReadService.getArticlesCreatedAfter(date);
      return articleDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
    }

  @GetMapping("/latest")
    public ResponseEntity<List<ArticleDTO>> getFiveLastArticles() {
      List<ArticleDTO> articleDTOs = articleReadService.getFiveLastArticles();
      return articleDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articleDTOs);
    }
}
