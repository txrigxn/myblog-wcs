package com.myBlog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myBlog.myblog.model.Article;
import java.util.List;
import java.time.LocalDateTime;



public interface ArticleRepository extends JpaRepository<Article, Long> {
  List<Article> findByTitle(String title);

  List<Article> findByContentContaining(String content);
  List<Article> findByCreatedAtAfter(LocalDateTime createdAt);
  List<Article> findTop5ByOrderByCreatedAtDesc();

}