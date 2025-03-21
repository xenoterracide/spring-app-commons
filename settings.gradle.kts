// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

rootProject.name = "spring-app-commons"

pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

plugins {
  id("com.gradle.develocity") version "3.18.1"
}

develocity {
  val ci = providers.environmentVariable("CI")
  buildScan {
    publishing.onlyIf { ci.isPresent }
    termsOfUseUrl.set("https://gradle.com/terms-of-service")
    termsOfUseAgree.set("yes")
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
  rulesMode = RulesMode.FAIL_ON_PROJECT_RULES

  components {
    withModule<JakartaTransactionRule>("jakarta.transaction:jakarta.transaction-api")
  }

  repositories {
    maven("https://maven.pkg.github.com/xenoterracide/java-commons") {
      name = "gh"
      mavenContent {
        includeModule("com.xenoterracide", "tools")
      }
      credentials(PasswordCredentials::class)
    }
    mavenCentral()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

@CacheableRule
abstract class JakartaTransactionRule : ComponentMetadataRule {
  override fun execute(context: ComponentMetadataContext) {
    context.details.allVariants {
      withDependencies {
        add("jakarta.enterprise:jakarta.enterprise.cdi-api")
        add("jakarta.inject:jakarta.inject-api")
        add("jakarta.interceptor:jakarta.interceptor-api")
        add("jakarta.enterprise:jakarta.enterprise.lang-model")
      }
    }
  }
}

rootDir.resolve("module").listFiles()?.forEach { file ->
  if (file.isDirectory &&
    file
      ?.list { _, name -> name.startsWith("build.gradle") }
      ?.isNotEmpty() == true
  ) {
    val name = file.name
    include(":$name")
    project(":$name").projectDir = file("module/$name")
  }
}
