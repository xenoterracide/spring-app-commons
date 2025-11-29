// Copyright 2023 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

rootProject.name = "buildSrc"

pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

  repositories {
    gradlePluginPortal() // this should only be necessary in buildSrc/settings.gradle.kts
    mavenLocal()
  }
  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
    create("sb") {
      from("com.xenoterracide.gradle:gradle-version-catalog-spring:0.0.1-alpha.0.56+git.56.72fc362")
    }
  }
}
