package com.myBlog.myblog.controller;

import com.myBlog.myblog.Dto.User.UserLoginDto;
import com.myBlog.myblog.Dto.User.UserRegistrationDto;
import com.myBlog.myblog.model.User;
import com.myBlog.myblog.security.AuthenticationService;
import com.myBlog.myblog.service.UserService;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<User> register(@RequestBody UserRegistrationDto userRegistrationDto) {
    User registeredUser = userService.registerUser(userRegistrationDto.getEmail(),
        userRegistrationDto.getPassword(), Set.of("ROLE_USER"));
    return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
  }

  @PostMapping("login")
  public ResponseEntity<String> authenticate(@RequestBody UserLoginDto userLoginDto) {
    String token =
        authenticationService.authenticate(userLoginDto.getEmail(), userLoginDto.getPassword());
    return ResponseEntity.ok(token);
  }

}
