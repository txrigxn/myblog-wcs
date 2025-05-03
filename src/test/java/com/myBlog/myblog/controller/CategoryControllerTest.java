package com.myBlog.myblog.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.myBlog.myblog.Dto.Category.CategoryDto;
import com.myBlog.myblog.security.JwtAuthenticationFilter;
import com.myBlog.myblog.security.JwtService;
import com.myBlog.myblog.service.CategoryService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
@ActiveProfiles("test")
class CategoryControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private JwtService jwtService;
  @MockitoBean
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @MockitoBean
  private CategoryService categoryService;

  @Test
  void testGetAllCategories() throws Exception {

    CategoryDto category1 = new CategoryDto();
    category1.setName("Category 1");

    CategoryDto category2 = new CategoryDto();
    category2.setName("Category 2");

    when(categoryService.getAllCategories())
        .thenReturn(List.of(category1, category2));

    mockMvc
        .perform(get("/categories"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].name").value("Category 1"))
        .andExpect(jsonPath("$[1].name").value("Category 2"));
  }

  @Test
  void testGetCategoryByIdCategoryExists() throws Exception {

    CategoryDto category = new CategoryDto();
    category.setName("Category 1");

    when(categoryService.getCategoryById(1L)).thenReturn(category);

    mockMvc.perform(get("/categories/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Category 1"));
  }

  @Test
  void testGetCategoryByIdCategoryNotFound() throws Exception {

    when(categoryService.getCategoryById(99L))
        .thenThrow(new RuntimeException("La categorie avec l'id 99 n'a pas été trouvé."));

    mockMvc.perform(get("/categories/99"))
        .andExpect(status().isNotFound());
  }
}
