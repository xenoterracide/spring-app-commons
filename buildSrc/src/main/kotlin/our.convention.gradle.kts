// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

import com.xenoterracide.gradle.convention.publish.GithubPublicRepositoryConfiguration
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.publish.tasks.GenerateModuleMetadata


plugins {
  id("com.autonomousapps.dependency-analysis")
  id("com.xenoterracide.gradle.convention.checkstyle")
  id("com.xenoterracide.gradle.convention.compile")
  id("com.xenoterracide.gradle.convention.coverage")
  id("com.xenoterracide.gradle.convention.javadoc")
  id("com.xenoterracide.gradle.convention.publish")
  id("com.xenoterracide.gradle.convention.spotbugs")
}

val libs = the<LibrariesForLibs>()

dependencies {
  spotbugs(libs.spotbugs)
}

repositoryHost(GithubPublicRepositoryConfiguration())
repositoryHost.namespace.set("xenoterracide")

publicationLegal {
  inceptionYear.set(2024)
  spdxLicenseIdentifiers.addAll("AGPL-3.0-or-later")
}

dependencies {
  errorprone(libs.bundles.ep)
  compileOnly(libs.bundles.compile)
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(25))
  }
}

tasks.javadoc {
  // because jpamodelgen puts non java sources in java source dirs https://hibernate.atlassian.net/browse/HHH-18676
  include("**/*.java")
}

// Workaround for Gradle issue: plainJavadocJar task is not automatically wired as a dependency
// of generateMetadataFileForMavenPublication when using the maven-publish plugin with java-library.
tasks.withType<GenerateModuleMetadata>().configureEach {
  dependsOn(tasks.named("plainJavadocJar"))
}
