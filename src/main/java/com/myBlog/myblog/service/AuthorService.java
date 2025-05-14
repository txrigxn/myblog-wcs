package com.myBlog.myblog.service;

import com.myBlog.myblog.Dto.Author.AuthorDto;
import com.myBlog.myblog.exception.ResourceNotFoundException;
import com.myBlog.myblog.mapper.AuthorMapper;
import com.myBlog.myblog.model.Author;
import com.myBlog.myblog.repository.AuthorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorMapper = authorMapper;
    this.authorRepository = authorRepository;
  }

  public List<AuthorDto> getAllAuthors() {
    List<Author> authors = authorRepository.findAll();
    return authors.stream().map(authorMapper::convertToDto).collect(Collectors.toList());
  }

  public AuthorDto getAuthorById(Long id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "L'auteur avec l'id " + id + " n'a pas été trouvé."));

    return author != null ? authorMapper.convertToDto(author) : null;
  }

  public AuthorDto createAuthor(Author author) {
    Author savedAuthor = authorRepository.save(author);
    return authorMapper.convertToDto(savedAuthor);
  }

  public AuthorDto updateAuthor(Long id, Author authorDetails) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "L'auteur avec l'id " + id + " n'a pas été trouvé."));
    if (author == null) {
      return null;
    }

    author.setFirstname(authorDetails.getFirstname());
    author.setLastname(authorDetails.getLastname());
    author.setArticleAuthors(authorDetails.getArticleAuthors());

    Author updatedAuthor = authorRepository.save(author);
    return authorMapper.convertToDto(updatedAuthor);
  }

  public boolean deleteAuthor(Long id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "L'auteur avec l'id " + id + " n'a pas été trouvé."));
    if (author == null) {
      return false;
    }
    authorRepository.delete(author);
    return true;
  }
}
