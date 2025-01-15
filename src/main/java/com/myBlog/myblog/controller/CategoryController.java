package com.myBlog.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

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
  public ResponseEntity<List<Category>> getAllCategories() {

    List<Category> categories = categoryRepository.findAll();

    if(categories.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(categories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@RequestParam Long id) {
      Category category = categoryRepository.findById(id).orElse(null);

      if(category == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(category);
  }

  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
      category.setCreatedAt(LocalDateTime.now());
      category.setUpdatedAt(LocalDateTime.now());

      Category savedCategory = categoryRepository.save(category);
      
      return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
  }

  @PostMapping("/{id}")
  public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
      Category category = categoryRepository.findById(id).orElse(null);

      if(category == null) {
        return ResponseEntity.notFound().build();
      }
      category.setName(categoryDetails.getName());
      category.setUpdatedAt(LocalDateTime.now());

      Category updatedCategory = categoryRepository.save(category);
      
      return ResponseEntity.ok(updatedCategory);
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
}
