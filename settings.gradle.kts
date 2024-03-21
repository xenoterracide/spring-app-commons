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
    throw Exception("Invalid module directory: $file")
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
      mavenContent {
        includeModule("com.xenoterracide", "tools")
        snapshotsOnly()
      }
      credentials {
        val creds = providers.credentials(PasswordCredentials::class, "gh")
        username = providers.environmentVariable("GITHUB_ACTOR").orElse(creds.map { it.username!! }).get()
        password = providers.environmentVariable("GITHUB_TOKEN").orElse(creds.map { it.password!! }).get()
      }
    }
    mavenCentral()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
