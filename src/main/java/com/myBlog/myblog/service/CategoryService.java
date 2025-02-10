package com.myBlog.myblog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myBlog.myblog.DTO.CategoryDTO;
import com.myBlog.myblog.mapper.CategoryMapper;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.repository.CategoryRepository;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }
  
  public List<CategoryDTO> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();

    return categories.stream().map(categoryMapper::convertToDTO).collect(Collectors.toList());
  }

  public CategoryDTO getCategoryById(Long id) {
    Category category = categoryRepository.findById(id).orElse(null);

    return category != null ? categoryMapper.convertToDTO(category) : null;
  }

  public CategoryDTO createCategory(Category category) {
    category.setCreatedAt(LocalDateTime.now());
    category.setUpdatedAt(LocalDateTime.now());

    Category savedCategory = categoryRepository.save(category);

    return categoryMapper.convertToDTO(savedCategory);
  }

  public CategoryDTO updateCategory(Long id, Category categoryDetails) {
    Category category = categoryRepository.findById(id).orElse(null);

    if (category == null) return null;

    category.setName(categoryDetails.getName());
    category.setUpdatedAt(categoryDetails.getUpdatedAt());

    Category updatedCategory = categoryRepository.save(category);

    return categoryMapper.convertToDTO(updatedCategory);
  }

  public boolean deleteCategory(Long id) {
    Category category = categoryRepository.findById(id).orElse(null);
    if (category == null) return false;
    categoryRepository.delete(category);
    return true;
  }
}
