package com.myBlog.myblog.Dto.Author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AuthorContributionDto {

  @NotNull(message = "L'ID de l'auteur ne doit pas être nul")
  @Positive(message = "L'ID de l'auteur doit être un nombre positif")
  private Long authorId;

  @NotBlank(message = "La contribution de l'auteur ne doit pas être vide")
  private String contribution;

  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

  public String getContribution() {
    return contribution;
  }

  public void setContribution(String contribution) {
    this.contribution = contribution;
  }
}
