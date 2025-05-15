package com.myBlog.myblog.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.myBlog.myblog.Dto.Image.ImageDto;
import com.myBlog.myblog.model.Image;
import com.myBlog.myblog.service.ImageService;

@RestController
@RequestMapping("/images")
public class ImageController {
  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping
  public ResponseEntity<List<ImageDto>> getAllImages() {
    List<ImageDto> images = imageService.getAllImages();
    return images.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(images);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ImageDto> getImageById(@PathVariable Long id) {
    ImageDto image = imageService.getImageById(id);

    return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<ImageDto> create(@RequestBody Image image) {
    ImageDto savedImage = imageService.createImage(image);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
  }

  @PutMapping("/{id}")
  @PreAuthorize("id == authentication.principal.id or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ImageDto> update(@PathVariable Long id, @RequestBody Image imageDetails) {

    ImageDto image = imageService.updateImage(id, imageDetails);

    return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
  }

  @PreAuthorize("id == authentication.principal.id or hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
    return imageService.deleteImage(id) ? ResponseEntity.noContent().build()
        : ResponseEntity.notFound().build();
  }
}
