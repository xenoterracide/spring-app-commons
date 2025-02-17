// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

plugins {
  `maven-publish`
  id("com.xenoterracide.gradle.convention.publish")
}

publicationLegal {
  this.inceptionYear.set(2024)
  this.spdxLicenseIdentifiers.addAll("AGPL-3.0-or-later", "Universal-FOSS-exception-1.0", "CC-BY-NC-4.0")
}

publishing {
  publications {
    register<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}
