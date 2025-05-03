package com.myBlog.myblog.service.ArticleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myBlog.myblog.Dto.Article.ArticleDto;
import com.myBlog.myblog.exception.ResourceNotFoundException;
import com.myBlog.myblog.mapper.ArticleMapper;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.repository.ArticleRepository;

@Service
public class ArticleReadService {
  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;

  public ArticleReadService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
    this.articleRepository = articleRepository;
    this.articleMapper = articleMapper;
  }

  public List<ArticleDto> getAllArticles() {
    List<Article> articles = articleRepository.findAll();
    return articles.stream().map(articleMapper::convertToDto).collect(Collectors.toList());
  }

  public ArticleDto getArticleById(Long id) {
    Article article = articleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé."));
    if (article == null) {
      return null;
    }
    return articleMapper.convertToDto(article);
  }

  public List<ArticleDto> getArticlesByTitle(String title) {
    List<Article> articles = articleRepository.findByTitle(title);
    return articles.stream().map(articleMapper::convertToDto).collect(Collectors.toList());
  }

  public List<ArticleDto> getArticlesByContent(String content) {
    List<Article> articles = articleRepository.findByContentContaining(content);
    return articles.stream().map(articleMapper::convertToDto).collect(Collectors.toList());
  }

  public List<ArticleDto> getArticlesCreatedAfter(LocalDateTime date) {
    List<Article> articles = articleRepository.findByCreatedAtAfter(date);
    return articles.stream().map(articleMapper::convertToDto).collect(Collectors.toList());
  }

  public List<ArticleDto> getFiveLastArticles() {
    List<Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
    return articles.stream().map(articleMapper::convertToDto).collect(Collectors.toList());
  }
}
