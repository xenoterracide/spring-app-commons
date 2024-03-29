// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

plugins {
  `maven-publish`
  id("com.xenoterracide.gradle.semver")
}

val repo = "spring-app-commons"
val username = "xenoterracide"
val githubUrl = "https://github.com"
val repoShort = "$username/$repo"

version = semver.maven

publishing {
  publications {
    register<MavenPublication>("maven") {
      from(components["java"])
    }
    withType<MavenPublication>().configureEach {
      versionMapping {
        allVariants {
          fromResolutionResult()
        }
      }
      pom {
        artifactId = project.name
        groupId = rootProject.group.toString()
        version = project.version.toString()
        description = project.description
        inceptionYear = "2024"
        url = "$githubUrl/$repoShort"
        licenses {
          license {
            name = "AGPL-3.0-or-later"
            url = "https://choosealicense.com/licenses/agpl-3.0/"
            distribution = "repo"
            comments = "Java - for everyone"
          }
          license {
            name = "Apache-2.0"
            url = "https://choosealicense.com/licenses/apache-2.0/"
            distribution = "repo"
            comments = "Java - for Individuals, Contributers, Sponsors, and Open Source projects. Contact for Sponsorship"
          }
          license {
            name = "MIT"
            url = "https://choosealicense.com/licenses/mit/"
            distribution = "repo"
            comments = "Gradle Build Files and Configuration Files"
          }
          license {
            name = "CC-BY-4.0"
            url = "https://choosealicense.com/licenses/cc-by-4.0/"
            distribution = "repo"
            comments = "Documentation including Javadoc"
          }
        }
        developers {
          developer {
            id = username
            name = "Caleb Cushing"
            email = "xenoterracide@gmail.com"
          }
        }
        scm {
          connection = "$githubUrl/$repoShort.git"
          developerConnection = "scm:git:$githubUrl/$repoShort.git"
          url = "$githubUrl/$repoShort"
        }
      }
    }
  }

  repositories {
    maven {
      name = "gh"
      url = uri("https://maven.pkg.github.com/$repoShort")
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
  }
}
