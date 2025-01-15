package com.myBlog.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.ArticleDTO;
import com.myBlog.myblog.model.Article;
import com.myBlog.myblog.model.Category;
import com.myBlog.myblog.model.Image;
import com.myBlog.myblog.repository.ArticleRepository;
import com.myBlog.myblog.repository.CategoryRepository;
import com.myBlog.myblog.repository.ImageRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/articles")
public class ArticleController {
  
  private final ArticleRepository articleRepository;
  private final CategoryRepository categoryRepository;
  private final ImageRepository imageRepository;
  
  public ArticleController(ArticleRepository articleRepository, CategoryRepository categoryRepository, ImageRepository imageRepository) {
    this.articleRepository = articleRepository;
    this.categoryRepository = categoryRepository;
    this.imageRepository = imageRepository;
  }

  @GetMapping
  public ResponseEntity<List<ArticleDTO>> getAllArticles() {
    List<Article> articles = articleRepository.findAll();

    if(articles.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(articleDTOs);
    
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
      Article article = articleRepository.findById(id).orElse(null);
      if (article == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(convertToDTO(article));
  }

  @PostMapping
  public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
      article.setCreatedAt(LocalDateTime.now());
      article.setUpdatedAt(LocalDateTime.now());

      if (article.getCategory() != null) {
        Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
        if(category == null) {
          return ResponseEntity.badRequest().body(null);
        }
        article.setCategory(category);
      }


      if(article.getImages() != null && !article.getImages().isEmpty()) {
        List<Image> validImages = new ArrayList<>();
        for (Image image : article.getImages()) {
          if (image.getId() != null) {
            Image existinImage = imageRepository.findById(image.getId()).orElse(null);
            if (existinImage != null) {
              validImages.add(existinImage);
            } else {
              return ResponseEntity.badRequest().body(null);
            }
          } else {
            Image savedImage = imageRepository.save(image);
            validImages.add(savedImage);
          }
        }
        article.setImages(validImages);
      }

      Article savedArticle = articleRepository.save(article);
      
      return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedArticle));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
      Article article = articleRepository.findById(id).orElse(null);
      if(article == null) {
        return ResponseEntity.notFound().build();
      }

      article.setTitle(articleDetails.getTitle());
      article.setContent(articleDetails.getContent());
      article.setUpdatedAt(LocalDateTime.now());

      if (article.getCategory() != null) {
        Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElse(null);
        if(category == null) {
          return ResponseEntity.badRequest().body(null);
        }
        article.setCategory(category);
      }
      if (articleDetails.getImages() != null) {

        List<Image> validImages = new ArrayList<>();

        for (Image image : articleDetails.getImages()) {

          if (image.getId() != null) {

          Image existingImage = imageRepository.findById(image.getId()).orElse(null);

            if (existingImage != null) {

              validImages.add(existingImage);

            } else {

              return ResponseEntity.badRequest().build(); 

            }

          } else {

            Image savedImage = imageRepository.save(image);

            validImages.add(savedImage);

          }

        }
            article.setImages(validImages);

        } else {

            article.getImages().clear();
        }

      Article updatedArticle = articleRepository.save(article); 
      
      return ResponseEntity.ok(convertToDTO(updatedArticle));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    Article article = articleRepository.findById(id).orElse(null);

    if (article == null) {
      return ResponseEntity.notFound().build();
    }

    articleRepository.delete(article);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search-title")
  public ResponseEntity<List<ArticleDTO>> getArticlesByTitle(@RequestParam String title) {
    List<Article> articles = articleRepository.findByTitle(title);  
    if(articles.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(articleDTOs);
  }

  @GetMapping("/search-content")
  public ResponseEntity<List<ArticleDTO>> getArticlesByContent(@RequestParam String content) {
    List<Article> articles = articleRepository.findByContentContaining(content);
    if(articles.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(articleDTOs);
  }

  @GetMapping("/created-after")
  public ResponseEntity<List<ArticleDTO>> getArticlesCreateAfter(@RequestParam LocalDateTime date) {
    List<Article> articles = articleRepository.findByCreatedAtAfter(date);
    if(articles.isEmpty()){
      return ResponseEntity.noContent().build();
    }
    List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(articleDTOs);
  }

  @GetMapping("/latest")
  public ResponseEntity<List<ArticleDTO>> getFiveLastArticles() {
    List<Article> articles = articleRepository.findTop5ByOrderByCreatedAtDesc();
    if(articles.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToDTO).collect(Collectors.toList());
    return ResponseEntity.ok(articleDTOs);
  }

  private ArticleDTO convertToDTO(Article article) {

    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setId(article.getId());
    articleDTO.setTitle(article.getTitle());
    articleDTO.setContent(article.getContent());
    articleDTO.setUpdatedAt(article.getUpdatedAt());
    if(article.getCategory() != null) {
      articleDTO.setCategoryName(article.getCategory().getName());
    }
    if (article.getImages() != null) {
      articleDTO.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
    }
    return articleDTO;
    
  }
}
