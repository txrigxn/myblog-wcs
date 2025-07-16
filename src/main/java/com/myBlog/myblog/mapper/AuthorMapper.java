package com.myBlog.myblog.mapper;

import com.myBlog.myblog.Dto.Author.AuthorDto;
import com.myBlog.myblog.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

  public AuthorDto convertToDto(Author author) {
    AuthorDto authorDto = new AuthorDto();
    authorDto.setId(author.getId());
    authorDto.setFirstname(author.getFirstname());
    authorDto.setLastname(author.getLastname());
    if (author.getArticleAuthors() != null) {
      authorDto.setArticleIds(
          author.getArticleAuthors().stream()
              .filter(articleAuthor -> articleAuthor.getArticle() != null)
              .map(
                  articleAuthor -> {
                    return articleAuthor.getArticle().getId();
                  })
              .toList());
    }
    return authorDto;
  }
}
