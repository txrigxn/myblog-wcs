package com.myBlog.myblog.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.myBlog.myblog.DTO.ImageDTO;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.Image;

@Component
public class ImageMapper {
  
  public ImageDTO convertToDTO(Image image) {
    ImageDTO imageDTO = new ImageDTO();
    imageDTO.setId(image.getId());
    imageDTO.setUrl(image.getUrl());
    if (image.getArticles() != null) {
      imageDTO.setArticleIds(image.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
    }
    return imageDTO;
  }
  
}
