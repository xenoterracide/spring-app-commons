// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.accessors.dm.LibrariesForSbd4

plugins {
  `java-library`
}

dependencyLocking {
  lockAllConfigurations()
}

val libs = the<LibrariesForLibs>()
var sbd4 = the<LibrariesForSbd4>()

configurations.configureEach {
  exclude(group = "org.slf4j", module = "slf4j-nop")
  exclude(group = "junit", module = "junit")
  exclude(group = "org.junit.jupiter", module = "junit-jupiter")

  resolutionStrategy {
    capabilitiesResolution {
      withCapability("jakarta.el", "jakarta.el-impl") {
        select("org.apache.tomcat.embed:tomcat-embed-el:0")
      }
    }
    componentSelection {
      all {
        val nonRelease = Regex("^[\\d.]+-(RC|M|ea|beta|alpha).*$")
        if (candidate.group != "com.xenoterracide") {
          if (candidate.version.matches(nonRelease)) reject("no pre-release")
          if (candidate.version.endsWith("-SNAPSHOT")) reject("no snapshots")
        } else if (candidate.version.matches(nonRelease)) {
          logger.info("allowing: {}", candidate)
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

configurations.configureEach {
  dependencies {
    constraints {
      this@configureEach(libs.jboss.logging)
    }
  }
}

dependencies {
  api(platform(libs.jakarta.bom))
  api(platform(libs.spring.bom))
  api(platform(libs.jmolecules.bom))
  api(platform(libs.spring.modulith.bom))

  compileOnly(platform(libs.jakarta.bom))
  compileOnly(platform(libs.spring.bom))
  compileOnly(platform(libs.jmolecules.bom))
  compileOnly(platform(libs.spring.modulith.bom))

  implementation(platform(libs.jakarta.bom))
  implementation(platform(libs.spring.bom))
  implementation(platform(libs.jmolecules.bom))
  implementation(platform(libs.spring.modulith.bom))

  runtimeOnly(platform(libs.jakarta.bom))
  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(platform(libs.jmolecules.bom))
  runtimeOnly(platform(libs.spring.modulith.bom))

  compileOnly(sbd4.jspecify)
  compileOnly(libs.jmolecules.architecture.layered)

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
