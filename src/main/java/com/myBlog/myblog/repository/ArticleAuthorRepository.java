package com.myBlog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myBlog.myblog.model.ArticleAuthor;


public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, Long> {}