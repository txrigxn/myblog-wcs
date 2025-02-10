package com.myBlog.myblog.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.myBlog.myblog.DTO.ArticleDTO;
import com.myBlog.myblog.DTO.CategoryDTO;
import com.myBlog.myblog.model.Category;

@Component
public class CategoryMapper {
  
  public CategoryDTO convertToDTO(Category category) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setId(category.getId());
    categoryDTO.setName(category.getName());
    categoryDTO.setUpdatedAt(category.getUpdatedAt());
    if (category.getArticles() != null) {
      categoryDTO.setArticles(category.getArticles().stream().map(article -> {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        articleDTO.setCategoryName(article.getCategory().getName());
        return articleDTO;
      }).collect(Collectors.toList()));
    }
        return categoryDTO;
  }
  
}
