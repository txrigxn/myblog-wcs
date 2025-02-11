package com.myBlog.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.ImageDTO;
import com.myBlog.myblog.model.Image;
import com.myBlog.myblog.service.ImageService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/images")
public class ImageController {
  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

    @GetMapping

    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<ImageDTO> images = imageService.getAllImages();
        return images.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(images);
    }


    @GetMapping("/{id}")

    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        ImageDTO image = imageService.getImageById(id);

        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }


    @PostMapping

    public ResponseEntity<ImageDTO> create(@RequestBody Image image) {
        ImageDTO savedImage = imageService.createImage(image);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }


    @PutMapping("/{id}")

    public ResponseEntity<ImageDTO> update(@PathVariable Long id, @RequestBody Image imageDetails) {

        ImageDTO image = imageService.updateImage(id, imageDetails);
        
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        return imageService.deleteImage(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
