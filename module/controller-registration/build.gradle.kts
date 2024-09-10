// © Copyright 2024 Caleb Cushing
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
  api(libs.spring.context)

  implementation(libs.jakarta.validation)
  implementation(libs.spring.graphql.core)
  implementation(projects.modelSecurity)

  runtimeOnly(libs.starter.graphql)
  runtimeOnly(libs.starter.validation)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.starter.log4j2)

  testImplementation(libs.spring.beans)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.spring.boot.test.core)
  testImplementation(libs.spring.graphql.test)

  testRuntimeOnly(projects.testApp)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.mockito)
  testRuntimeOnly(libs.starter.webflux)
  testRuntimeOnly(libs.starter.test)

  demoServerApi(platform(libs.spring.bom))
  demoServerApi(libs.spring.boot.autoconfigure)

  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(libs.spring.boot.core)
  demoServerImplementation(libs.spring.context)

  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(libs.spring.boot.devtools)
  demoServerRuntimeOnly(libs.starter.actuator)
  demoServerRuntimeOnly(libs.h2)
  demoServerRuntimeOnly(project)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
