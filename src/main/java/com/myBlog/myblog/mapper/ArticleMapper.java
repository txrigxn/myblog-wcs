package com.myBlog.myblog.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.myBlog.myblog.Dto.Article.ArticleCreateDto;
import com.myBlog.myblog.Dto.Article.ArticleDto;
import com.myBlog.myblog.Dto.Author.AuthorDto;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.ArticleAuthor;
import com.myBlog.myblog.model.Author;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.model.Image;

@Component
public class ArticleMapper {

  public Article convertToEntity(ArticleCreateDto dto) {
    Article article = new Article();
    article.setTitle(dto.getTitle());
    article.setContent(dto.getContent());

    Category category = new Category();
    category.setId(dto.getCategoryId());
    article.setCategory(category);

    List<Image> images = dto.getImages().stream()
        .map(imageDto -> {
          Image image = new Image();
          image.setUrl(imageDto.getUrl());
          return image;
        })
        .collect(Collectors.toList());
    article.setImages(images);

    List<ArticleAuthor> articleAuthors = dto.getAuthors().stream()

        .map(authorContributionDto -> {

          ArticleAuthor articleAuthor = new ArticleAuthor();
          Author author = new Author();

          author.setId(authorContributionDto.getAuthorId());
          articleAuthor.setAuthor(author);
          articleAuthor.setContribution(authorContributionDto.getContribution());
          articleAuthor.setArticle(article);
          return articleAuthor;
        }).collect(Collectors.toList());
    article.setArticleAuthors(articleAuthors);

    return article;
  }

  public ArticleDto convertToDto(Article article) {
    ArticleDto articleDto = new ArticleDto();
    articleDto.setId(article.getId());
    articleDto.setTitle(article.getTitle());
    articleDto.setContent(article.getContent());
    articleDto.setUpdatedAt(article.getUpdatedAt());
    if (article.getCategory() != null) {
      articleDto.setCategoryName(article.getCategory().getName());
    }
    if (article.getImages() != null) {
      articleDto.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
    }
    if (article.getArticleAuthors() != null) {
      articleDto.setAuthors(article.getArticleAuthors().stream()
          .filter(articleAuthor -> articleAuthor.getAuthor() != null).map(articleAuthor -> {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(articleAuthor.getAuthor().getId());
            authorDto.setFirstname(articleAuthor.getAuthor().getFirstname());
            authorDto.setLastname(articleAuthor.getAuthor().getLastname());
            return authorDto;
          }).collect(Collectors.toList()));
    }
    return articleDto;
  }
}
