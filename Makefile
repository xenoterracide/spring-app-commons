define createPr
	gh pr create --fill
endef

define gradleFullBuild
	./gradlew buildHealth build
endef


.PHONY: build
build:
	./gradlew build

up:
	./gradlew dependencies --write-locks --quiet 2>&1 | cat > /dev/null

merge :
	$(createPr) && $(gradleFullBuild) && gh run watch --exit-status && gh pr merge --squash --delete-branch --auto

ci-build:
	./gradlew build buildHealth --build-cache

ci-full:
	$(gradleFullBuild)
