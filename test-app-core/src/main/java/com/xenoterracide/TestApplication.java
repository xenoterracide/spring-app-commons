// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Test application.
 */
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
class TestApplication {}
