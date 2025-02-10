package com.myBlog.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myBlog.myblog.DTO.ImageDTO;
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

  public List<ImageDTO> getAllImages() {
    List<Image> images = imageRepository.findAll();
    
    List<ImageDTO> imageDTOs = images.stream().map(imageMapper::convertToDTO).collect(Collectors.toList());

    return imageDTOs;
  }

  public ImageDTO getImageById(Long id) {
    Image image = imageRepository.findById(id).orElse(null);

    return image != null ? imageMapper.convertToDTO(image) : null;
  }

  public ImageDTO createImage(Image image) {
    Image savedImage = imageRepository.save(image);

    return imageMapper.convertToDTO(savedImage);
  }

  public ImageDTO updateImage(Long id, Image imageDetails) {
    Image image = imageRepository.findById(id).orElse(null);

    if (image == null) return null;
    image.setUrl(imageDetails.getUrl());

    Image updatedImage = imageRepository.save(image);

    return imageMapper.convertToDTO(updatedImage);

  }

  public boolean deleteImage(Long id) {
    Image image = imageRepository.findById(id).orElse(null);

    if (image == null) return false;

    imageRepository.delete(image);
    return true;
  }

  
}
