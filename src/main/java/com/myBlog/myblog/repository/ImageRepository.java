package com.myBlog.myblog.repository;

import com.myBlog.myblog.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {}
