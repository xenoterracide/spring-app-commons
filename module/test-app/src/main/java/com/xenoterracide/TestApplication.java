// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test application.
 */
@ActiveProfiles("test")
@SpringBootApplication
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/application-${spring.application.name}.properties",
  ignoreResourceNotFound = true
)
class TestApplication {}
