package com.myBlog.myblog.controller;

import com.myBlog.myblog.Dto.Category.CategoryDto;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.service.CategoryService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    List<CategoryDto> categories = categoryService.getAllCategories();

    if (categories.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return categories.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(categories);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> getCategoryById(@RequestParam Long id) {
    CategoryDto category = categoryService.getCategoryById(id);
    return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<CategoryDto> create(@RequestBody Category category) {
    CategoryDto savedCategory = categoryService.createCategory(category);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);

  }

  @PreAuthorize("id == authentication.principal.id or hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<CategoryDto> update(@PathVariable Long id,
      @RequestBody Category categoryDetails) {
    CategoryDto category = categoryService.updateCategory(id, categoryDetails);

    return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
  }

  @PreAuthorize("id == authentication.principal.id or hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return categoryService.deleteCategory(id) ? ResponseEntity.noContent().build()
        : ResponseEntity.notFound().build();
  }

}
