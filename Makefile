HEAD := $(shell git rev-parse --verify HEAD)

check_defined = $(strip $(foreach 1, $1,$(call __check_defined,$1,$(strip $(value 2)))))
__check_defined = $(if $(value $1),, $(error Undefined $1$(if $2, ($2))))

define gh_head_run_url
	gh run list --workflow $(1) --commit $(HEAD) --json url --jq '.[0].["url"]'
endef

define gh_head_run_id
	gh run list --workflow $(1) --commit $(HEAD) --json databaseId --jq '.[0].["databaseId"]'
endef

define update_extended_dependencies
	./gradlew dependencies build buildHealth --write-locks --scan
endef

define delete_lockfiles
	find . -name '*gradle.lockfile' -delete
endef

define update_wrapper
	./gradlew wrapper --write-locks
	./gradlew wrapper # ensure the wrapper is up-to-date
endef


.PHONY: build
build:
	./gradlew build

up:
	./gradlew dependencies --write-locks --quiet 2>&1 > /dev/null

merge: create-pr build watch-full merge-squash

clean:
	./gradlew clean

ci-build:
	./gradlew build buildHealth --build-cache

ci-full:
	./gradlew buildHealth build --no-build-cache --no-configuration-cache

ci-update-java:
	$(call delete_lockfiles) && \
	$(call update_wrapper) && \
	$(call update_extended_dependencies)

create-pr:
	gh pr create || exit 0

merge-squash:
	gh pr merge --squash --delete-branch --auto

run-url:
	$(call check_defined, workflow)
	@$(call gh_head_run_url, $(workflow))

watch:
	$(call check_defined, workflow)
	@gh run watch $$($(call gh_head_run_id, $(workflow))) --exit-status

watch-full:
	@gh run watch $$($(call gh_head_run_id, "Full")) --exit-status
