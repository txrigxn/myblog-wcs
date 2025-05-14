package com.myBlog.myblog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.myBlog.myblog.Dto.Category.CategoryDto;
import com.myBlog.myblog.mapper.CategoryMapper;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.repository.CategoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  private CategoryMapper categoryMapper;
  @InjectMocks
  private CategoryService categoryService;

  @Test
  void testGetAllCategories() {
    // Arrange
    Category category1 = new Category();
    category1.setName("Category 1");
    category1.setCreatedAt(LocalDateTime.now());

    Category category2 = new Category();
    category2.setName("Category 2");
    category2.setCreatedAt(LocalDateTime.now());

    CategoryDto categoryDto1 = new CategoryDto();
    categoryDto1.setName(category1.getName());

    CategoryDto categoryDto2 = new CategoryDto();
    categoryDto2.setName(category2.getName());

    when(categoryRepository.findAll())
        .thenReturn(List.of(category1, category2));
    when(categoryMapper.convertToDto(category1))
        .thenReturn(categoryDto1);
    when(categoryMapper.convertToDto(category2))
        .thenReturn(categoryDto2);

    // ACT
    List<CategoryDto> categories = categoryService
        .getAllCategories();

    // ASSERT
    assertThat(categories).hasSize(2);
    assertThat(categories.get(0).getName()).isEqualTo("Category 1");
    assertThat(categories.get(1).getName()).isEqualTo("Category 2");
  }

  @Test
  void testGetCategoryByIdCategoryExists() {
    Category category = new Category();
    category.setName(" Category 1");
    category.setCreatedAt(LocalDateTime.now());

    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setName("Category 1");

    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    when(categoryMapper.convertToDto(category)).thenReturn(categoryDto);

    CategoryDto result = categoryService.getCategoryById(1L);

    assertThat(result.getName()).isEqualTo("Category 1");
  }

  @Test
  void testGetCategoryByIdCategoryNotFound() {
    when(categoryRepository.findById(99L))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> categoryService.getCategoryById(99L))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("La categorie avec l'id 99 n'a pas été trouvé.");
  }

}
