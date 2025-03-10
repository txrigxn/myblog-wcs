package com.myBlog.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.UserRegistrationDTO;
import com.myBlog.myblog.model.User;
import com.myBlog.myblog.service.UserService;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("register")
  public ResponseEntity<User> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
      User registeredUser = userService.registerUser(
        userRegistrationDTO.getEmail(), 
        userRegistrationDTO.getPassword(), 
        Set.of("ROLE_USER")
      );
      return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
  }
  
  
}
