package com.myBlog.myblog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.myBlog.myblog.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomUserDetailsService customUserDetailsService;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
      CustomUserDetailsService customUserDetailsService) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/authors/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/articles/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/images/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/api-docs/**").permitAll()
            .requestMatchers("/api-docs.html").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated())
        .userDetailsService(customUserDetailsService)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session
            .sessionCreationPolicy((SessionCreationPolicy.STATELESS)));
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
