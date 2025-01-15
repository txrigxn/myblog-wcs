package com.myBlog.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.ArticleDTO;
import com.myBlog.myblog.DTO.CategoryDTO;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryRepository
   categoryRepository;


  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {

    List<Category> categories = categoryRepository.findAll();

    if(categories.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    List<CategoryDTO> categoryDTOs = categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(categoryDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@RequestParam Long id) {
      Category category = categoryRepository.findById(id).orElse(null);

      if(category == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(convertToDTO(category));
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody Category category) {
      category.setCreatedAt(LocalDateTime.now());
      category.setUpdatedAt(LocalDateTime.now());

      Category savedCategory = categoryRepository.save(category);
      
      return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedCategory));
  }

  @PostMapping("/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
      Category category = categoryRepository.findById(id).orElse(null);

      if(category == null) {
        return ResponseEntity.notFound().build();
      }
      category.setName(categoryDetails.getName());
      category.setUpdatedAt(LocalDateTime.now());

      Category updatedCategory = categoryRepository.save(category);
      
      return ResponseEntity.ok(convertToDTO(updatedCategory));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    Category category = categoryRepository.findById(id).orElse(null);
    if (category == null) {
      return ResponseEntity.notFound().build();
    }
    categoryRepository.delete(category);

    return ResponseEntity.noContent().build();
  }
  
  private CategoryDTO convertToDTO(Category category) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setId(category.getId());
    categoryDTO.setName(category.getName());
    categoryDTO.setUpdatedAt(category.getUpdatedAt());
    if (category.getArticles() != null) {
      categoryDTO.setArticles(category.getArticles().stream().map(article -> {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        articleDTO.setCategoryName(article.getCategory().getName());
        return articleDTO;
      }).collect(Collectors.toList()));
    }
        return categoryDTO;
  }
}
