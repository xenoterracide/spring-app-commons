<!--
SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing

SPDX-License-Identifier: CC-BY-NC-4.0
-->

- skills can be found in `.ai/skills`
- architecture is hexagonal, CQRS, event sourcing, domain driven design.
- github jobs have a 10 minute timeout, gradle tasks shouldn't take more than 5 minutes
- `./gradlew check` must pass if `*.kts`, `*.java`, or `checkstyle/*.xml` has changed
- `yarn ug` updates gradle dependencies
- `gh` can be used if authenticated and available.
- gradle buildHealth task does not use JPMS, and can be incorrect if JPMS is having with its suggestions.
- include links to upstream bugs in comments when relevant
- packages under group `com.xenoterracide` can be changed by us.
