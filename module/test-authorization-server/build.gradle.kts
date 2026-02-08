// © 2024 Copyright Caleb Cushing
// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  api(platform(libs.spring.bom))
  api(sb.spring.boot.autoconfigure)

  implementation(platform(libs.spring.bom))
  implementation(sb.spring.boot)
  implementation(sb.spring.context)
  implementation(sb.spring.security.oauth2.authorization.server)
  implementation(sb.spring.security.oauth2.core)
  implementation(sb.spring.core)
  implementation(sb.spring.security.config)
  implementation(sb.spring.security.web)
  implementation(sb.spring.web)

  compileOnly(libs.java.tools)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(sb.spring.boot.starter.oauth2.authorization.server)
  runtimeOnly(sb.spring.boot.starter.security)
  runtimeOnly(sb.spring.boot.starter.web)
  runtimeOnly(sb.spring.boot.devtools)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.test.impl)
  testImplementation(libs.httpcomponents.client5)
  testImplementation(sb.spring.test)
  testImplementation(sb.spring.boot.test)
  testImplementation(sb.spring.beans)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.bundles.test.runtime)
  testRuntimeOnly(projects.testAppCore)
}
