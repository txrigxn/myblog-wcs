package com.myBlog.myblog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myBlog.myblog.Dto.Category.CategoryDto;
import com.myBlog.myblog.exception.ResourceNotFoundException;
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

  public List<CategoryDto> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();

    return categories.stream().map(categoryMapper::convertToDto).collect(Collectors.toList());
  }

  public CategoryDto getCategoryById(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("La categorie avec l'id " + id + " n'a pas été trouvé."));

    return category != null ? categoryMapper.convertToDto(category) : null;
  }

  public CategoryDto createCategory(Category category) {
    category.setCreatedAt(LocalDateTime.now());
    category.setUpdatedAt(LocalDateTime.now());

    Category savedCategory = categoryRepository.save(category);

    return categoryMapper.convertToDto(savedCategory);
  }

  public CategoryDto updateCategory(Long id, Category categoryDetails) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("La categorie avec l'id " + id + " n'a pas été trouvé."));
    ;

    if (category == null)
      return null;

    category.setName(categoryDetails.getName());
    category.setUpdatedAt(categoryDetails.getUpdatedAt());

    Category updatedCategory = categoryRepository.save(category);

    return categoryMapper.convertToDto(updatedCategory);
  }

  public boolean deleteCategory(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("La categorie avec l'id " + id + " n'a pas été trouvé."));
    ;
    if (category == null) {
      return false;
    }
    categoryRepository.delete(category);
    return true;
  }
}
