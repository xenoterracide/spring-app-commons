// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

plugins {
  our.javalibrary
}

dependencies {
  api(platform(libs.spring.bom))
  api(libs.spring.context)

  implementation(platform(libs.spring.bom))
  implementation(projects.modelSecurity)
  implementation(libs.spring.graphql)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.graphql)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}

tasks.jacocoTestCoverageVerification {
  enabled = false
}
