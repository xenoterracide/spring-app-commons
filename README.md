<!--
© Copyright 2024 Caleb Cushing.
SPDX-License-Identifier: CC-BY-4.0
-->

# README

## Contributing

### Languages

[asdf](https://asdf-vm.com) is suggested, you can use whatever you'd like to get

- Java 11+
- NodeJs

add a way to export these to your `PATH` in your `~/.profile`

### Build Tools

- [Gradle](https://docs.gradle.org/current/userguide/command_line_interface.html)
- [NPM](https://docs.npmjs.com/about-npm)

#### Fetching Dependencies

In order to get snapshots of dependencies, you must have a GitHub token in your `~/.gradle/gradle.properties` file. This file should look like:

```properties
ghUsername = <your username>
ghPassword = <your token>
```

You should generate your PAT as [Github Documents here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-to-github-packages).

> a personal access token (classic) with at least read:packages scope to install packages associated with other private repositories (which GITHUB_TOKEN can't access).

Then run.

Run `npm ci && ./gradlew dependencies` to install dependencies.

### Committing

Use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

## License

- Java:
  - For everyone - [GNU Affero General Public License v3.0](https://choosealicense.com/licenses/agpl-3.0/)
  - Alternatively for Individuals, Contributers, Sponsors, and Open Source projects - [Apache 2.0](https://choosealicense.com/licenses/apache-2.0/)
    - Donation may be used to become a contributor. Contact for details.
- Gradle Kotlin and Config Files: [MIT](https://choosealicense.com/licenses/mit/)
- Documentation including Javadoc: [CC BY 4.0](https://choosealicense.com/licenses/cc-by-4.0/)

© Copyright 2024 Caleb Cushing.
