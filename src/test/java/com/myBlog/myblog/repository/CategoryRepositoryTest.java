package com.myBlog.myblog.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.myBlog.myblog.model.Category;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testFindAllCategories() {
    Category category1 = new Category();
    category1.setName("Category 1");
    category1.setCreatedAt(LocalDateTime.now());

    Category category2 = new Category();
    category2.setName("Category 2");
    category2.setCreatedAt(LocalDateTime.now());

    categoryRepository.saveAll(List.of(category1, category2));

    List<Category> categories = categoryRepository.findAll();

    assertThat(categories).hasSize(2);
    assertThat(categories.get(0).getName()).isEqualTo("Category 1");
    assertThat(categories.get(1).getName()).isEqualTo("Category 2");
  }
}
