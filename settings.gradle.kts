// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

rootProject.name = "spring-app-commons"
rootDir.resolve("module").listFiles()?.forEach { file ->
  if (file.isDirectory && file?.list { _, name -> name == "build.gradle.kts" }
      ?.isNotEmpty() == true
  ) {
    val name = file.name
    include(":$name")
    project(":$name").projectDir = file("module/$name")
  } else {
    logger.error("Invalid module directory: {}", file)
  }
}

pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

plugins {
  id("com.gradle.enterprise") version ("3.16.2")
}

gradleEnterprise {
  buildScan {
    publishOnFailureIf(providers.environmentVariable("CI").isPresent)
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

  repositories {
    maven("https://maven.pkg.github.com/xenoterracide/java-commons") {
      name = "gh"
      mavenContent {
        includeModule("com.xenoterracide", "tools")
        snapshotsOnly()
      }
      providers.environmentVariable("CI").map { it.toBoolean() }.let {
        if (it.getOrElse(false)) {
          credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
          }
        } else {
          credentials(PasswordCredentials::class)
        }
      }
    }
    mavenCentral()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
