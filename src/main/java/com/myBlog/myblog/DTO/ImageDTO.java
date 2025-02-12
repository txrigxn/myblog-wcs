package com.myBlog.myblog.DTO;

import java.util.List;

import org.hibernate.validator.constraints.URL;

public class ImageDTO {
  private Long id;
  @URL(message = "L'URL de l'image doit etre valide.")
  private String url;
  private List<Long> articleIds;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<Long> getArticleIds() {
    return articleIds;
  }

  public void setArticleIds(List<Long> articleIds) {
    this.articleIds = articleIds;
  } 
  
}
