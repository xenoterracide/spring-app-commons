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
  demoServerApi(platform(libs.spring.bom))
  demoServerApi(sbd4.spring.boot.autoconfigure)
  demoServerApi(sbd4.spring.context)
  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(sbd4.spring.boot)
  demoServerImplementation(sbd4.spring.boot.actuator)
  demoServerImplementation(sbd4.spring.security.config)
  demoServerImplementation(sbd4.spring.webmvc)
  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(sbd4.spring.boot.devtools)
  demoServerRuntimeOnly(sbd4.spring.boot.starter.actuator)
  demoServerRuntimeOnly(sbd4.spring.boot.starter.log4j2)
  demoServerRuntimeOnly(sbd4.spring.boot.starter.oauth2.resource.server)
  demoServerRuntimeOnly(sbd4.spring.boot.starter.security)
  demoServerRuntimeOnly(sbd4.spring.boot.starter.web)
  demoServerRuntimeOnly(testFixtures(project))
  implementation(sbd4.spring.context)
  implementation(sbd4.spring.security.config)
  implementation(sbd4.spring.security.web)
  runtimeOnly(sbd4.spring.boot.starter.oauth2.resource.server)
  runtimeOnly(sbd4.spring.boot.starter.security)
  runtimeOnly(sbd4.spring.boot.starter.web)
  testFixturesImplementation(platform(libs.spring.bom))
  testFixturesImplementation(sbd4.log4j.api)
  testFixturesImplementation(sbd4.spring.security.core)
  testFixturesImplementation(sbd4.spring.web)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sbd4.spring.boot.test)
        runtimeOnly(sbd4.spring.boot.starter.web)
        runtimeOnly(sbd4.spring.boot.starter.webflux)
        runtimeOnly(projects.testAppCore)
      }
    }
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation.bundle(sbd4.bundles.test.impl)
        runtimeOnly.bundle(sbd4.bundles.test.runtime)
      }
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
