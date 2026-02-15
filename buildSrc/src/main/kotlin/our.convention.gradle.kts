// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

import com.xenoterracide.gradle.convention.publish.GithubPublicRepositoryConfiguration
import org.gradle.accessors.dm.LibrariesForLibs


plugins {
  id("com.autonomousapps.dependency-analysis")
  id("com.xenoterracide.gradle.convention.checkstyle")
  id("com.xenoterracide.gradle.convention.coverage")
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

publishing {
  publications {
    register<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}
