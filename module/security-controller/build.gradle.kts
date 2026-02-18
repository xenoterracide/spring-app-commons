// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
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
  compileOnly(sb.hibernate.validator)
  demoServerApi(platform(libs.spring.bom))
  demoServerApi(sb.spring.boot.autoconfigure)
  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(sb.spring.boot)
  demoServerImplementation(sb.spring.context)
  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(project)
  demoServerRuntimeOnly(sb.h2)
  demoServerRuntimeOnly(sb.spring.boot.devtools)
  demoServerRuntimeOnly(sb.spring.boot.starter.actuator)
  implementation(projects.securityModel)
  implementation(sb.jakarta.validation.api)
  implementation(sb.spring.graphql)
  runtimeOnly(sb.spring.boot.starter.graphql)
  runtimeOnly(sb.spring.boot.starter.validation)
  runtimeOnly(sb.spring.boot.starter.web)
}
testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sb.spring.beans)
        implementation(sb.spring.boot.test)
        implementation(sb.spring.boot.test.autoconfigure)
        implementation(sb.spring.graphql.test)
        implementation(sb.spring.test)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sb.h2)
        runtimeOnly(sb.mockito.core)
        runtimeOnly(sb.spring.boot.starter.test)
        runtimeOnly(sb.spring.boot.starter.webflux)
      }
    }
  }
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
