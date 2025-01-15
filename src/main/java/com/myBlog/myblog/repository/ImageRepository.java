package com.myBlog.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myBlog.myblog.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
  
}
