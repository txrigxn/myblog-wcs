package com.myBlog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myBlog.myblog.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}