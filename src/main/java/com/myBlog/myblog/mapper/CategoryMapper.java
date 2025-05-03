package com.myBlog.myblog.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.myBlog.myblog.Dto.Article.ArticleDto;
import com.myBlog.myblog.Dto.Category.CategoryDto;
import com.myBlog.myblog.model.Category;

@Component
public class CategoryMapper {
  public CategoryDto convertToDto(Category category) {
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(category.getId());
    categoryDto.setName(category.getName());
    categoryDto.setUpdatedAt(category.getUpdatedAt());
    if (category.getArticles() != null) {
      categoryDto.setArticles(category.getArticles().stream().map(article -> {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setUpdatedAt(article.getUpdatedAt());
        articleDto.setCategoryName(article.getCategory().getName());
        return articleDto;
      }).collect(Collectors.toList()));
    }
    return categoryDto;
  }

}
