package com.myBlog.myblog.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myBlog.myblog.DTO.ArticleCreateDTO;
import com.myBlog.myblog.DTO.ArticleDTO;
import com.myBlog.myblog.DTO.AuthorDTO;
import com.myBlog.myblog.exception.ResourceNotFoundException;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.ArticleAuthor;
import com.myBlog.myblog.model.Author;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.model.Image;
import com.myBlog.myblog.repository.AuthorRepository;
import com.myBlog.myblog.repository.CategoryRepository;
import com.myBlog.myblog.repository.ImageRepository;

@Component
public class ArticleMapper {
    public Article convertToEntity(ArticleCreateDTO dto) {
      Article article = new Article();
      article.setTitle(dto.getTitle());
      article.setContent(dto.getContent());

      Category category = new Category();
      category.setId(dto.getCategoryId());
      article.setCategory(category);

      List<Image> images = dto.getImages().stream()
        .map(imageDTO -> {
        Image image = new Image();
        image.setUrl(imageDTO.getUrl());
        return image;
        })
        .collect(Collectors.toList());
        article.setImages(images);

      List<ArticleAuthor> articleAuthors = dto.getAuthors().stream()
        .map(authorDTO -> {
          ArticleAuthor articleAuthor = new ArticleAuthor();

          Author author = new Author();
          author.setId(authorDTO.getAuthorId());
          articleAuthor.setAuthor(author);
          articleAuthor.setContribution(authorDTO.getContribution());
          articleAuthor.setArticle(article);
          return articleAuthor;
        }).collect(Collectors.toList());
        article.setArticleAuthors(articleAuthors);

      return article;
    }

    public ArticleDTO convertToDTO(Article article) {
      ArticleDTO articleDTO = new ArticleDTO();
      articleDTO.setId(article.getId());
      articleDTO.setTitle(article.getTitle());
      articleDTO.setContent(article.getContent());
      articleDTO.setUpdatedAt(article.getUpdatedAt());
      if(article.getCategory() != null) {
        articleDTO.setCategoryName(article.getCategory().getName());
      }
      if (article.getImages() != null) {
        articleDTO.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
      }
      if (article.getArticleAuthors() != null) {
        articleDTO.setAuthors(article.getArticleAuthors().stream().filter(articleAuthor -> articleAuthor.getAuthor() != null).map(articleAuthor -> {
          AuthorDTO authorDTO = new AuthorDTO();
          authorDTO.setId(articleAuthor.getAuthor().getId());
          authorDTO.setFirstname(articleAuthor.getAuthor().getFirstname());
          authorDTO.setLastname(articleAuthor.getAuthor().getLastname());
          return authorDTO;
        }).collect(Collectors.toList()));
      }
      return articleDTO;
    } 
  
}
