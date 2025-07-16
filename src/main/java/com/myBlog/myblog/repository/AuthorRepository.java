package com.myBlog.myblog.repository;

import com.myBlog.myblog.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
