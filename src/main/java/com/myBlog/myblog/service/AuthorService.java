package com.myBlog.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myBlog.myblog.DTO.AuthorDTO;
import com.myBlog.myblog.mapper.AuthorMapper;
import com.myBlog.myblog.model.Author;
import com.myBlog.myblog.repository.AuthorRepository;

@Service
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorMapper = authorMapper;
    this.authorRepository = authorRepository;
  }

  public List<AuthorDTO> getAllAuthors() {
    List<Author> authors = authorRepository.findAll();
    return authors.stream().map(authorMapper::convertToDTO).collect(Collectors.toList());
  }

  public AuthorDTO getAuthorById(Long id) {
    Author author = authorRepository.findById(id).orElse(null);

    return author != null ? authorMapper.convertToDTO(author) : null;
  }

  public AuthorDTO createAuthor(Author author) {
    Author savedAuthor = authorRepository.save(author);
    return authorMapper.convertToDTO(savedAuthor);
  }

  public AuthorDTO updateAuthor(Long id, Author authorDetails) {
    Author author = authorRepository.findById(id).orElse(null);
    if (author == null) return null;

    author.setFirstname(authorDetails.getFirstname());
    author.setLastname(authorDetails.getLastname());
    author.setArticleAuthors(authorDetails.getArticleAuthors());

    Author updatedAuthor = authorRepository.save(author);
    return authorMapper.convertToDTO(updatedAuthor);
  }

  public boolean deleteAuthor(Long id) {
    Author author = authorRepository.findById(id).orElse(null);
    if (author == null) return false;
    authorRepository.delete(author);
    return true;
  }
}
