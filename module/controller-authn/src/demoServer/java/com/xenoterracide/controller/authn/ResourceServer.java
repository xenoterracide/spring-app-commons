// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.authn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/application-${spring.application.name}.properties",
  ignoreResourceNotFound = true
)
@SpringBootApplication
public class ResourceServer {

  ResourceServer() {}

  public static void main(String[] args) {
    SpringApplication.run(ResourceServer.class, args);
  }

  @Bean
  SecurityFilterChain demoFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).build();
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.debug(true);
  }

  @Bean
  HttpExchangeRepository httpExchangeRepository() {
    return new InMemoryHttpExchangeRepository();
  }

  @Bean
  WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
      }
    };
  }
}
