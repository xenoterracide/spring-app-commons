# SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
#
# SPDX-License-Identifier: MIT

HEAD := $(shell git rev-parse --verify HEAD)
GRADLE_DIR := $(wildcard ./.gradle/)
BUILD_DIRS := $(wildcard ./build/ */build/ ./module/*/build/)
CONFIGURATION_CACHE := $(wildcard $(GRADLE_DIR)configuration-cache/)

check_defined = $(strip $(foreach 1, $1,$(call __check_defined,$1,$(strip $(value 2)))))
__check_defined = $(if $(value $1),, $(error Undefined $1$(if $2, ($2))))

define gh_head_run_url
	gh run list --workflow $(1) --commit $(HEAD) --json url --jq '.[0].["url"]'
endef

define gh_head_run_id
	gh run list --workflow $(1) --commit $(HEAD) --json databaseId --jq '.[0].["databaseId"]'
endef

.PHONY: up
up:
# success if no output
	./gradlew dependencies --write-locks --console=plain | grep -e FAILED || exit 0

.PHONY: build
build:
	./gradlew build --console=plain

.PHONY: merge
merge: merge-head push create-pr build watch-full merge-squash

.PHONY: clean
clean:
	./gradlew clean

.PHONY: cleaner
cleaner: clean-build clean-gradle

clean-cc: $(CONFIGURATION_CACHE)
	- rm -rf $(CONFIGURATION_CACHE)

ci-build:
	./gradlew build --build-cache --scan

ci-full:
	./gradlew build --no-build-cache --no-configuration-cache --scan

ci-update-java: clean-lockfiles up-wrapper up up-all-deps

clean-build:
	- rm -rf $(BUILD_DIRS)

clean-gradle:
	- rm -rf $(GRADLE_DIR)

clean-lockfiles:
	find . -name '*gradle.lockfile' -delete

up-wrapper:
	./gradlew wrapper --write-locks && ./gradlew wrapper

up-all-deps:
	./gradlew build --write-locks --scan --console=plain | grep -e FAILED -e https

create-pr:
	@tmp_dir=$$(mktemp -d); \
	./scripts/pr-message.sh --title-file "$$tmp_dir/title.txt" --body-file "$$tmp_dir/body.txt" \
	  --skill-file ".github/skills/commit-or-pr-message/SKILL.md" || exit 0; \
	title=$$(cat "$$tmp_dir/title.txt"); \
	gh pr create --title "$$title" --body-file "$$tmp_dir/body.txt" || exit 0; \
	rm -rf "$$tmp_dir"

push:
	git push

merge-head:
	git fetch --all --prune --prune-tags --tags --force
	git merge origin/HEAD

merge-squash:
	gh pr merge --squash --delete-branch --auto

run-url:
	$(call check_defined, workflow)
	@$(call gh_head_run_url, $(workflow))

watch:
	$(call check_defined, workflow)
	@gh run watch $$($(call gh_head_run_id, $(workflow))) --exit-status

watch-full:
	@gh run watch $$($(call gh_head_run_id, "full")) --exit-status
