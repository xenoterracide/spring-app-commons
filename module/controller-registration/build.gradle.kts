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
  api(platform(libs.spring.bom))
  api(libs.spring.context)
  api(libs.jakarta.validation)

  implementation(platform(libs.spring.bom))
  implementation(libs.spring.graphql)
  implementation(projects.modelSecurity)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.graphql)
  runtimeOnly(libs.starter.validation)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.spring.boot.test.autoconfigure)

  demoServerApi(platform(libs.spring.bom))
  demoServerApi(libs.spring.context)
  demoServerApi(libs.spring.boot.autoconfigure)

  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(libs.spring.boot.devtools)
  demoServerRuntimeOnly(libs.starter.actuator)
  demoServerRuntimeOnly(libs.starter.log4j2)
  demoServerRuntimeOnly(libs.starter.web)
  demoServerRuntimeOnly(libs.h2)
  demoServerRuntimeOnly(libs.starter.graphql)
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
tasks.jacocoTestCoverageVerification {
  enabled = false
}
