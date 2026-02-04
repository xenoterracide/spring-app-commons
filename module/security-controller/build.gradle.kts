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
  compileOnly(libs.hibernate.validator)

  implementation(libs.jakarta.validation)
  implementation(projects.securityModel)
  implementation(libs.helidon.webserver.graphql)
  implementation(libs.graphql.java)
  implementation(libs.graphql.java.annotations)

  runtimeOnly(platform(libs.helidon.dependencies))

  testRuntimeOnly(projects.testAppCore)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.mockito)

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
}
