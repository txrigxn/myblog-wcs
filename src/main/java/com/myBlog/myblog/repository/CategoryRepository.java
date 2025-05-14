package com.myBlog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myBlog.myblog.model.Category;
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
