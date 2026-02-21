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
  api(sbd4.spring.context)
  compileOnly(sbd4.hibernate.validator)
  demoServerApi(platform(libs.spring.bom))
  demoServerApi(sbd4.spring.boot.autoconfigure)
  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(sbd4.spring.boot)
  demoServerImplementation(sbd4.spring.context)
  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(project)
  demoServerRuntimeOnly(sbd4.h2)
  demoServerRuntimeOnly(sbd4.spring.boot.devtools)
  demoServerRuntimeOnly(sbd4.spring.boot.starter.actuator)
  implementation(projects.securityModel)
  implementation(sbd4.jakarta.validation.api)
  implementation(sbd4.spring.graphql)
  runtimeOnly(sbd4.spring.boot.starter.graphql)
  runtimeOnly(sbd4.spring.boot.starter.validation)
  runtimeOnly(sbd4.spring.boot.starter.web)
}
testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sbd4.spring.beans)
        implementation(sbd4.spring.boot.test)
        implementation(sbd4.spring.boot.test.autoconfigure)
        implementation(sbd4.spring.graphql.test)
        implementation(sbd4.spring.test)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sbd4.h2)
        runtimeOnly(sbd4.mockito.core)
        runtimeOnly(sbd4.spring.boot.starter.test)
        runtimeOnly(sbd4.spring.boot.starter.webflux)
      }
    }
  }
}

dependencies {
  runtimeOnly(sbd4.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
