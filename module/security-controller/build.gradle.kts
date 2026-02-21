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
  api(sbd3.spring.context)
  compileOnly(sbd3.hibernate.validator)
  demoServerApi(platform(libs.spring.bom))
  demoServerApi(sbd3.spring.boot.autoconfigure)
  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(sbd3.spring.boot)
  demoServerImplementation(sbd3.spring.context)
  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(project)
  demoServerRuntimeOnly(sbd3.h2)
  demoServerRuntimeOnly(sbd3.spring.boot.devtools)
  demoServerRuntimeOnly(sbd3.spring.boot.starter.actuator)
  implementation(projects.securityModel)
  implementation(sbd3.jakarta.validation.api)
  implementation(sbd3.spring.graphql)
  runtimeOnly(sbd3.spring.boot.starter.graphql)
  runtimeOnly(sbd3.spring.boot.starter.validation)
  runtimeOnly(sbd3.spring.boot.starter.web)
}
testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sbd3.spring.beans)
        implementation(sbd3.spring.boot.test)
        implementation(sbd3.spring.boot.test.autoconfigure)
        implementation(sbd3.spring.graphql.test)
        implementation(sbd3.spring.test)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sbd3.h2)
        runtimeOnly(sbd3.mockito.core)
        runtimeOnly(sbd3.spring.boot.starter.test)
        runtimeOnly(sbd3.spring.boot.starter.webflux)
      }
    }
  }
}

dependencies {
  runtimeOnly(sbd3.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
