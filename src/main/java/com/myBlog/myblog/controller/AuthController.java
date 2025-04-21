package com.myBlog.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myBlog.myblog.DTO.UserLoginDTO;
import com.myBlog.myblog.DTO.UserRegistrationDTO;
import com.myBlog.myblog.model.User;
import com.myBlog.myblog.security.AuthenticationService;
import com.myBlog.myblog.service.UserService;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
  private final UserService userService;
  private final AuthenticationService authenticationService;

  public AuthController(UserService userService, AuthenticationService authenticationService) {
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  @PostMapping("register")
  public ResponseEntity<User> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    User registeredUser = userService.registerUser(
        userRegistrationDTO.getEmail(),
        userRegistrationDTO.getPassword(),
        Set.of("ROLE_USER"));
    return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
  }

  @PostMapping("login")
  public ResponseEntity<String> authenticate(@RequestBody UserLoginDTO userLoginDTO) {
    String token = authenticationService.authenticate(userLoginDTO.getEmail(), userLoginDTO.getPassword());
    return ResponseEntity.ok(token);
  }

}
