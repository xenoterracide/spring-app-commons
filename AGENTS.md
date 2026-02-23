<!--
SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing

SPDX-License-Identifier: CC-BY-NC-4.0
-->

- `yarn test` must pass if `*.kts`, `*.java`, or `checkstyle/*.xml` has changed
- `yarn ug` updates gradle dependencies
- gradle buildHealth task does not use JPMS, and can be incorrect if JPMS is having with its suggestions.
- include links to upstream bugs in comments when relevant
- architecture is hexagonal, CQRS
