// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

rootProject.name = "spring-app-commons"

pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

plugins {
  id("com.gradle.develocity") version "4.3.2"
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
    withModule<JakartaElCapabilityRule>("jakarta.el:jakarta.el-api")
    withModule<JakartaElCapabilityRule>("org.apache.tomcat.embed:tomcat-embed-el")
    withModule<JakartaElCapabilityRule>("org.glassfish:jakarta.el")
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
abstract class JakartaElCapabilityRule : ComponentMetadataRule {
  override fun execute(context: ComponentMetadataContext) {
    context.details.allVariants {
      withCapabilities {
        addCapability("jakarta.el", "jakarta.el-impl", context.details.id.version)
      }
    }
  }
}

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

dependencyResolutionManagement {
  versionCatalogs {
    create("sbd4") {
      from("com.xenoterracide.gradle.vc:version-catalog-spring-boot:4.0.0")
      bundle("spring-test", listOf("spring-test", "spring-boot-test", "spring-boot-test-autoconfigure"))
      bundle("test-impl", listOf("junit-jupiter-api", "assertj-core", "junit-jupiter-params"))
      bundle("test-runtime", listOf("junit-jupiter-engine", "junit-platform-launcher"))
    }
  }
}
