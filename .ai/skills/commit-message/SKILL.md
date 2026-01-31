---
name: commit-message
description: Generates a concise and descriptive commit message based on the provided code changes.
license: CC0-1.0
metadata:
  author: Caleb Cushing
---

<!--
SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing

SPDX-License-Identifier: CC-BY-NC-4.0
-->

You MUST follow this exact template:

<type>(<scope>): <summary>

<body>

Rules:

- Output plain text only. No markdown fences.
- First line MUST be a valid Conventional Commit subject.
- Keep the FIRST line <= 72 characters.
- Use a specific scope when possible.
- Body:
  - Provide 2-6 bullet points.
  - Explain WHAT changed and WHY.
  - Wrap body lines to <= 72 characters.
  - Do not repeat the subject.
