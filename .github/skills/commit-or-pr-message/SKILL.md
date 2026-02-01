---
name: commit-message
description: Generates a concise and descriptive commit or PR message based on the code changes.
license: CC0-1.0
metadata:
  author: Caleb Cushing
allowed-tools: bash(git:*) bash(make:*)
---

<!--
SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing

SPDX-License-Identifier: CC-BY-NC-4.0
-->

## Instructions

- Use for PR's or commit message's
- this is a git conventional commit format
  - review `git-conventional-commits.yaml` values in `convention.commitTypes` for `<type>`'s available
- the git subject line becomes the PR title
- You MUST follow the exact template

## Template

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
