package com.myBlog.myblog;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyBlogApplication {

  @Bean
  ObservationRegistryCustomizer<ObservationRegistry> addTextHandler() {
    return (registry) ->
        registry.observationConfig().observationHandler(new ObservationTextPublisher());
  }

  public static void main(String[] args) {
    SpringApplication.run(MyBlogApplication.class, args);
  }
}
