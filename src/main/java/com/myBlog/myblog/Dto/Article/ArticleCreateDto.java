package com.myBlog.myblog.Dto.Article;

import com.myBlog.myblog.Dto.Author.AuthorContributionDto;
import com.myBlog.myblog.Dto.Image.ImageDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ArticleCreateDto {

  @NotBlank(message = "Le titre ne doit pas être vide")
  @Size(min = 2, max = 50, message = "Le titre doit contenir entre 2 et 50 caractères")
  private String title;

  @NotBlank(message = "Le contenu ne doit pas être vide")
  @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
  private String content;

  @NotNull(message = "L'ID de la catégorie ne doit pas être nul")
  @Positive(message = "L'ID de la catégorie doit être un nombre positif")
  private Long categoryId;

  @NotEmpty(message = "La liste des images ne doit pas être vide")
  private List<@Valid ImageDto> images;

  @NotEmpty(message = "La liste des auteurs ne doit pas être vide")
  private List<AuthorContributionDto> authors;

  // Getters et setters

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public List<@Valid ImageDto> getImages() {
    return images;
  }

  public void setImages(List<@Valid ImageDto> images) {
    this.images = images;
  }

  public List<AuthorContributionDto> getAuthors() {
    return authors;
  }

  public void setAuthors(List<AuthorContributionDto> authors) {
    this.authors = authors;
  }
}
