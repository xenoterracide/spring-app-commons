// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The main class for the demo resource server.
 */
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/application-${spring.application.name}.properties",
  ignoreResourceNotFound = true
)
@SpringBootApplication(proxyBeanMethods = false)
public class ResourceServer {

  ResourceServer() {}

  /**
   * The main method for the resource server.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(ResourceServer.class, args);
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
