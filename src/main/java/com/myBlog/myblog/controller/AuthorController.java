package com.myBlog.myblog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.AuthorDTO;
import com.myBlog.myblog.model.Author;
import com.myBlog.myblog.repository.AuthorRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/authors")
public class AuthorController {
  private final AuthorRepository authorRepository;

  public AuthorController(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  private AuthorDTO convertToDTO(Author author) {
    AuthorDTO authorDTO = new AuthorDTO();
    authorDTO.setId(author.getId());
    authorDTO.setFirstname(author.getFirstname());
    authorDTO.setLastname(author.getLastname());
    if (author.getArticleAuthors() != null) {
      authorDTO.setArticleIds(author.getArticleAuthors().stream()
      .filter(articleAuthor -> articleAuthor.getArticle() != null)
      .map(articleAuthor -> {
        return articleAuthor.getArticle().getId();
      }).toList()); 
    }
    return authorDTO;
  }

  @GetMapping
  public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
    List<Author> authors = authorRepository.findAll();
    if (authors.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    List<AuthorDTO> authorDTOs = authors.stream()
    .map(this::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(authorDTOs);
  
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
      Author author = authorRepository.findById(id).orElse(null);

      if (author == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(convertToDTO(author));
  }

    @PostMapping
    public ResponseEntity<AuthorDTO> create(@RequestBody Author authorBody) {
        Author savedAuthor = authorRepository.save(authorBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedAuthor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @RequestBody Author authorBody) {
      Author author = authorRepository.findById(id).orElse(null);
      if (author == null) {
        return ResponseEntity.notFound().build();
      }
      author.setFirstname(authorBody.getFirstname());
      author.setLastname(authorBody.getLastname());
      Author savedAuthor = authorRepository.save(author);
      return ResponseEntity.ok(convertToDTO(savedAuthor));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
      Author author = authorRepository.findById(id).orElse(null);
      if (author == null) {
        return ResponseEntity.notFound().build();
      }
      authorRepository.delete(author);
      return ResponseEntity.noContent().build();
    }

}
