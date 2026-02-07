// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
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
  implementation(sb.spring.security.config)
  implementation(sb.spring.security.web)
  implementation(sb.spring.context)

  runtimeOnly(sb.spring.boot.starter.security)
  runtimeOnly(sb.spring.boot.starter.web)
  runtimeOnly(sb.spring.boot.starter.oauth2.resource.server)

  testFixturesImplementation(platform(libs.spring.bom))
  testFixturesImplementation(sb.log4j.api)
  testFixturesImplementation(sb.spring.security.core)
  testFixturesImplementation(sb.spring.web)

  testImplementation(sb.spring.boot.test)

  testRuntimeOnly(sb.spring.boot.starter.web)
  testRuntimeOnly(sb.spring.boot.starter.webflux)
  testRuntimeOnly(projects.testAppCore)

  demoServerApi(platform(libs.spring.bom))
  demoServerApi(sb.spring.context)
  demoServerApi(sb.spring.boot.autoconfigure)

  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(sb.spring.security.config)
  demoServerImplementation(sb.spring.webmvc)
  demoServerImplementation(sb.spring.boot.actuator)
  demoServerImplementation(sb.spring.boot)

  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(testFixtures(project))
  demoServerRuntimeOnly(sb.spring.boot.devtools)
  demoServerRuntimeOnly(sb.spring.boot.starter.actuator)
  demoServerRuntimeOnly(sb.spring.boot.starter.log4j2)
  demoServerRuntimeOnly(sb.spring.boot.starter.web)
  demoServerRuntimeOnly(sb.spring.boot.starter.security)
  demoServerRuntimeOnly(sb.spring.boot.starter.oauth2.resource.server)

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

tasks.withType<JacocoReport>().configureEach {
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
