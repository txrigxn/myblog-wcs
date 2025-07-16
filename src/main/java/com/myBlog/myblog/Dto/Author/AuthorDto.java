package com.myBlog.myblog.Dto.Author;

import java.util.List;

public class AuthorDto {
  private Long id;
  private String firstname;
  private String lastname;
  private List<Long> articleIds;

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

  public List<Long> getArticleIds() {
    return articleIds;
  }

  public void setArticleIds(List<Long> articleIds) {
    this.articleIds = articleIds;
  }
}
