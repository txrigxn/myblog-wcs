package com.myBlog.myblog.service;

import com.myBlog.myblog.model.User;
import com.myBlog.myblog.repository.UserRepository;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User registerUser(String email, String password, Set<String> roles) {
    if (userRepository.existsByEmail(email)) {
      throw new RuntimeException("Cet email est déjà utilisé");
    }
    User user = new User();
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    user.setRoles(roles);
    return userRepository.save(user);
  }
}
