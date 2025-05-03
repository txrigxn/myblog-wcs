package com.myBlog.myblog.Dto.Article;

import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.Author;

public class ArticleAuthorDto {
  private Long id;
  private Article article;
  private Author author;
  private String contribution;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {

    this.id = id;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getContribution() {

    return contribution;
  }

  public void setContribution(String contribution) {

    this.contribution = contribution;
  }
  
}
