// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

import com.github.spotbugs.snom.SpotBugsTask

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
  implementation(platform(libs.spring.bom))
  implementation(libs.spring.security.config)
  implementation(libs.spring.security.web)
  implementation(libs.spring.context)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.starter.oauth2.resource.server)

  testFixturesImplementation(platform(libs.spring.bom))
  testFixturesImplementation(libs.log4j.api)
  testFixturesImplementation(libs.spring.security.core)
  testFixturesImplementation(libs.spring.web)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.spring.test)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.starter.web)
  testRuntimeOnly(libs.starter.webflux)
  testRuntimeOnly(projects.testApp)

  demoServerApi(platform(libs.spring.bom))
  demoServerApi(libs.spring.context)
  demoServerApi(libs.spring.boot.autoconfigure)

  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(libs.spring.security.config)
  demoServerImplementation(libs.spring.webmvc)
  demoServerImplementation(libs.spring.boot.actuator)
  demoServerImplementation(libs.spring.boot.core)

  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(testFixtures(project))
  demoServerRuntimeOnly(libs.spring.boot.devtools)
  demoServerRuntimeOnly(libs.starter.actuator)
  demoServerRuntimeOnly(libs.starter.log4j2)
  demoServerRuntimeOnly(libs.starter.web)
  demoServerRuntimeOnly(libs.starter.security)
  demoServerRuntimeOnly(libs.starter.oauth2.resource.server)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}

tasks.withType<Test>().configureEach {
  enabled = false
}

tasks.withType<JacocoCoverageVerification>().configureEach {
  enabled = false
}

tasks.withType<SpotBugsTask>().configureEach {
  enabled = false
}

tasks.withType<Javadoc>().configureEach {
  enabled = false
}
