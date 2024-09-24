// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.modulith.Modulithic;

/**
 * Main Application class.
 */

@Modulithic
@SpringBootApplication
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/application.properties",
  ignoreResourceNotFound = true
)
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/application-local.properties",
  ignoreResourceNotFound = true
)
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/${spring.application.name}.properties",
  ignoreResourceNotFound = true
)
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/${spring.application.name}-local.properties",
  ignoreResourceNotFound = true
)
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class Application {

  Application() {
    // empty
  }

  /**
   * Main method.
   *
   * @param args
   *   command line arguments
   */
  @SuppressWarnings("checkstyle:UncommentedMain")
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
