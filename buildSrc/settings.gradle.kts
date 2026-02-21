// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

rootProject.name = "buildSrc"

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
    create("sbd3") {
      from("com.xenoterracide.gradle.vc:version-catalog-spring-boot:3.5.0")
      bundle("spring-test", listOf("spring-test", "spring-boot-test", "spring-boot-test-autoconfigure"))
      bundle("test-impl", listOf("junit-jupiter-api", "assertj-core", "junit-jupiter-params"))
      bundle("test-runtime", listOf("junit-platform-engine", "junit-platform-launcher"))
    }
  }
}

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
  }
}
