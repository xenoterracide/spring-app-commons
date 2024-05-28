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
        version = semver.gitDescribed.version
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
            comments =
              "Java - for Individuals, Contributers, Sponsors, and Open Source projects. Contact for Sponsorship"
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
      credentials {
        // use properties because gradles credentials errors if missing
        providers.gradleProperty("ghUsername").let { username = it.orNull }
        providers.gradleProperty("ghPassword").let { password = it.orNull }
        // avoid congiguration cache missing on credentials
        if (username == null || password == null) {
          username = System.getenv("GITHUB_ACTOR")
          password = System.getenv("GITHUB_TOKEN")
        }
      }
    }
  }
}
