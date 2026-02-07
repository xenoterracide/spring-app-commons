// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

val demoServer by sourceSets.creating

java {
  registerFeature("demoServer") {
    usingSourceSet(demoServer)
  }
}

val demoServerImplementation by configurations.existing
val demoServerRuntimeOnly by configurations.existing
val demoServerApi by configurations.existing

dependencies {
  api(sb.spring.context)

  compileOnly(libs.hibernate.validator)

  implementation(libs.jakarta.validation)
  implementation(sb.spring.graphql)
  implementation(projects.securityModel)

  runtimeOnly(sb.spring.boot.starter.graphql)
  runtimeOnly(sb.spring.boot.starter.validation)
  runtimeOnly(sb.spring.boot.starter.web)

  testImplementation(sb.spring.beans)
  testImplementation(sb.spring.boot.test.autoconfigure)
  testImplementation(sb.spring.boot.test)
  testImplementation(sb.spring.graphql.test)
  // testImplementation(sb.spring.orm)
  testImplementation(sb.spring.test)

  testRuntimeOnly(projects.testAppCore)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.mockito)
  testRuntimeOnly(sb.spring.boot.starter.webflux)
  testRuntimeOnly(sb.spring.boot.starter.test)

  demoServerApi(platform(libs.spring.bom))
  demoServerApi(sb.spring.boot.autoconfigure)

  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(sb.spring.boot)
  demoServerImplementation(sb.spring.context)

  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(sb.spring.boot.devtools)
  demoServerRuntimeOnly(sb.spring.boot.starter.actuator)
  demoServerRuntimeOnly(libs.h2)
  demoServerRuntimeOnly(project)
}

dependencies {
  runtimeOnly(sb.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
