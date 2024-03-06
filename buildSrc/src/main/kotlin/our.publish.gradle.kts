// SPDX-License-Identifier: MIT
// © Copyright 2024 Caleb Cushing. All rights reserved.

plugins {
  `maven-publish`
  id("com.xenoterracide.gradle.semver")
}

version = semver.maven

val username = "xenoterracide"
val githubUrl = "https://github.com"
val repoShort = "$username/java-commons"

publishing {
  publications {
    create<MavenPublication>("maven") {
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
            name = "Apache-2.0"
            url = "https://choosealicense.com/licenses/apache-2.0/"
            distribution = "repo"
            comments = "Java"
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
      name = "GH"
      url = uri("https://maven.pkg.github.com/$repoShort")
      credentials(PasswordCredentials::class)
    }
  }
}
