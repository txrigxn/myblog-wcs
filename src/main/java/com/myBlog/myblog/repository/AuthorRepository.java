package com.myBlog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myBlog.myblog.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  
}
