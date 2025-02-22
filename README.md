<!--
Copyright 2024 Caleb Cushing.
SPDX-FileCopyrightText: Copyright © 2025 Caleb Cushing

SPDX-License-Identifier: CC-BY-4.0
SPDX-License-Identifier: CC-BY-NC-4.0
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

In order to get snapshots of dependencies, you must have a GitHub token in your `~/.gradle/gradle.properties` file. This
file should look like:

```properties
ghUsername = <your username>
ghPassword = <your token>
```

You should generate your PAT
as [Github Documents here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-to-github-packages).

> a personal access token (classic) with at least read:packages scope to install packages associated with other private
> repositories (which GITHUB_TOKEN can't access).

Then run.

Run `npm ci && ./gradlew dependencies` to install dependencies.

### Committing

Use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

## Licenses

- Java: SPDX-Identifier( [AGPL-3.0-or-later](https://choosealicense.com/licenses/agpl-3.0/)
  WITH [Universal-FOSS-exception-1.0](https://spdx.org/licenses/Universal-FOSS-exception-1.0.html)
  AND [CC BY 4.0](https://choosealicense.com/licenses/cc-by-4.0/) )
  OR [CC-BY-NC-4.0](https://creativecommons.org/licenses/by-nc/4.0/)\
  For clarity the intent here is to maintain this software for _any_ open source and non-commercial use. Attribution is
  required. If you would like to use this for commercial purposes please contact me.
- Gradle Kotlin and Config Files: [MIT](https://choosealicense.com/licenses/mit/)
- Documentation including Javadoc: [CC-BY-NC-4.0](https://creativecommons.org/licenses/by-nc/4.0/)

Copyright © 2025 Caleb Cushing.
