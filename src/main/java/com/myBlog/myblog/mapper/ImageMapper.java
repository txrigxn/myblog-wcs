package com.myBlog.myblog.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.myBlog.myblog.Dto.Image.ImageDto;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.Image;

@Component
public class ImageMapper {
  public ImageDto convertToDto(Image image) {
    ImageDto imageDto = new ImageDto();
    imageDto.setId(image.getId());
    imageDto.setUrl(image.getUrl());
    if (image.getArticles() != null) {
      imageDto
          .setArticleIds(image.getArticles()
              .stream()
              .map(Article::getId)
              .collect(Collectors.toList()));
    }
    return imageDto;
  }

}
