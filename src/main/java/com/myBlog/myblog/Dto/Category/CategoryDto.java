package com.myBlog.myblog.Dto.Category;

import java.time.LocalDateTime;
import java.util.List;

import com.myBlog.myblog.Dto.Article.ArticleDto;

public class CategoryDto {
  private Long id;
  private String name;
  private LocalDateTime updatedAt;
  private List<ArticleDto> articles;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
  
  public List<ArticleDto> getArticles() {
    return articles;
  }

  public void setArticles(List<ArticleDto> articles) {
    this.articles = articles;
  }
  
}
