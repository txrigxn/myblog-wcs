package com.myBlog.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myBlog.myblog.Dto.Image.ImageDto;
import com.myBlog.myblog.exception.ResourceNotFoundException;
import com.myBlog.myblog.mapper.ImageMapper;
import com.myBlog.myblog.model.Image;
import com.myBlog.myblog.repository.ImageRepository;

@Service
public class ImageService {
  private final ImageRepository imageRepository;
  private final ImageMapper imageMapper;

  public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
    this.imageRepository = imageRepository;
    this.imageMapper = imageMapper;
  }

  public List<ImageDto> getAllImages() {
    List<Image> images = imageRepository.findAll();
    List<ImageDto> imageDtos = images
        .stream()
        .map(imageMapper::convertToDto).collect(Collectors.toList());

    return imageDtos;
  }

  public ImageDto getImageById(Long id) {
    Image image = imageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé."));

    return image != null ? imageMapper.convertToDto(image) : null;
  }

  public ImageDto createImage(Image image) {
    Image savedImage = imageRepository.save(image);

    return imageMapper.convertToDto(savedImage);
  }

  public ImageDto updateImage(Long id, Image imageDetails) {
    Image image = imageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé."));

    if (image == null) {
      return null;
    }
    image.setUrl(imageDetails.getUrl());

    Image updatedImage = imageRepository.save(image);

    return imageMapper.convertToDto(updatedImage);

  }

  public boolean deleteImage(Long id) {
    Image image = imageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé."));

    if (image == null) {
      return false;
    }

    imageRepository.delete(image);
    return true;
  }

}
