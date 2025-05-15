package com.myBlog.myblog.controller;

import com.myBlog.myblog.Dto.Article.ArticleCreateDto;
import com.myBlog.myblog.Dto.Article.ArticleDto;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.service.ArticleService.ArticleReadService;
import com.myBlog.myblog.service.ArticleService.ArticleWriteService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/articles")
public class ArticleController {

  private final ArticleReadService articleReadService;
  private final ArticleWriteService articleWriteService;

  public ArticleController(ArticleReadService articleReadService,
      ArticleWriteService articleWriteService) {
    this.articleWriteService = articleWriteService;
    this.articleReadService = articleReadService;
  }

  @GetMapping
  public ResponseEntity<List<ArticleDto>> getAllArticles() {
    List<ArticleDto> articles = articleReadService.getAllArticles();
    if (articles.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(articles);

  }

  @GetMapping("/{id}")
  public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
    ArticleDto article = articleReadService.getArticleById(id);
    if (article == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(article);
  }

  @PostMapping
  public ResponseEntity<ArticleDto> createArticle(
      @Valid @RequestBody ArticleCreateDto articleCreateDto) {
    ArticleDto savedArticle = articleWriteService.createArticle(articleCreateDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
  }

  @PutMapping("/{id}")
  @PreAuthorize("id == authentication.principal.id or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id,
      @RequestBody Article articleDetails) {
    ArticleDto updatedArticle = articleWriteService.updateArticle(id, articleDetails);
    if (updatedArticle == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updatedArticle);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("id == authentication.principal.id or hasRole('ROLE_ADMIN')")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    if (articleWriteService.deleteArticle(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/search-title")
  public ResponseEntity<List<ArticleDto>> getArticlesByTitle(@RequestParam String title) {
    List<ArticleDto> articleDtos = articleReadService.getArticlesByTitle(title);
    return articleDtos.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(articleDtos);
  }

  @GetMapping("/search-content")
  public ResponseEntity<List<ArticleDto>> getArticlesByContent(@RequestParam String content) {
    List<ArticleDto> articleDtos = articleReadService.getArticlesByContent(content);
    return articleDtos.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(articleDtos);
  }

  @GetMapping("/created-after")
  public ResponseEntity<List<ArticleDto>> getArticlesCreateAfter(@RequestParam LocalDateTime date) {
    List<ArticleDto> articleDtos = articleReadService.getArticlesCreatedAfter(date);
    return articleDtos.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(articleDtos);
  }

  @GetMapping("/latest")
  public ResponseEntity<List<ArticleDto>> getFiveLastArticles() {
    List<ArticleDto> articleDtos = articleReadService.getFiveLastArticles();
    return articleDtos.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(articleDtos);
  }
}
