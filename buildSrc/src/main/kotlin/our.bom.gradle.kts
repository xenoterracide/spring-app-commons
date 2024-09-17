// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-library`
}

dependencyLocking {
  lockAllConfigurations()
}

val libs = the<LibrariesForLibs>()

configurations.configureEach {
  exclude(group = "org.slf4j", module = "slf4j-nop")
  exclude(group = "junit", module = "junit")

  resolutionStrategy {
    componentSelection {
      all {
        val nonRelease = Regex("^[\\d.]+-(RC|M|ea|beta|alpha).*$")
        val module = Regex("^spotbugs.*")
        val group = Regex("^com.xenoterracide$")
        if (!candidate.group.matches(group) && !name.matches(module) && !candidate.module.matches(module)) {
          if (candidate.version.matches(nonRelease)) reject("no pre-release")
          if (candidate.version.endsWith("-SNAPSHOT")) reject("no snapshots")
        } else if (candidate.version.matches(nonRelease)) {
          logger.info("allowing: {}", candidate)
        }

        if (candidate.module == "hibernate-jpamodelgen" && candidate.version.startsWith("6.5")) {
          reject("https://hibernate.atlassian.net/browse/HHH-18203")
        }

        if (candidate.module == "nullaway") {
          val reason = "https://github.com/uber/NullAway/issues/533"
          if (candidate.version.matches(Regex("^0\\.9\\.[34]$"))) reject(reason)
        }
      }
    }
  }
}

configurations.matching { it.name == "runtimeClasspath" || it.name == "testRuntimeClasspath" }.configureEach {
  exclude(group = "com.google.code.findbugs", module = "jsr305")
  exclude(group = "com.google.errorprone", module = "error_prone_annotations")
  exclude(group = "org.checkerframework", module = "checker-qual")
  exclude(group = "ch.qos.logback", module = "logback-classic")
}

dependencies {
  compileOnly(libs.jspecify)
  compileOnly(libs.jmolecules.architecture.layered)

  // spring should be using this, but it's not, and some jakarta versions are missing
  implementation(platform(libs.jakarta.bom))
  implementation(platform(libs.spring.bom))
  implementation(platform(libs.jmolecules.bom))

  runtimeOnly(libs.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
