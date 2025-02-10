package com.myBlog.myblog.DTO;

import java.util.List;

import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.ArticleAuthor;

public class AuthorDTO {
  private Long id;
  private String firstname;
  private String lastname;
  private List<ArticleAuthor> articleAuthors;


  public Long getId() {

    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }


  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public List<ArticleAuthor> getArticleAuthors() {
    return articleAuthors;
  }

  public void setArticleAuthors(List<ArticleAuthor> articleAuthors) {
    this.articleAuthors = articleAuthors;
  }
  
}
