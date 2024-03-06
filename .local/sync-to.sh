#!/bin/sh
set -eux
LANGUAGE=java
PROJECT=$1
MODULE=$2
CONFIG_DIR="$HOME/${XDG_CONFIG_HOME+.config}/brix"
CONFIG_PROJECT_DIR="$CONFIG_DIR/$LANGUAGE/project"
CONFIG_MODULE_DIR="$CONFIG_DIR/$LANGUAGE/module"
TLD="com.xenoterracide"
PROJECT_DIR="$(pwd)/$PROJECT"

to_java_package() {
  echo "$1" | sed 's/[[:punct:]]/\./g'
}

to_java_package_path() {
  to_java_package "$1" | sed 's/\./\//g'
}

package_dir() {
  echo "java/$(to_java_package_path $TLD)/$(to_java_package_path "$PROJECT")/$(to_java_package_path "$MODULE")"
}

cp -a "$CONFIG_PROJECT_DIR/shared" "$PROJECT_DIR"
install --mode 600 --compare -D --target-directory "$PROJECT_DIR" \
  "$CONFIG_DIR/.editorconfig" \
  "$CONFIG_DIR/.gitattributes" \
  "$CONFIG_DIR/.prettierrc.yml" \
  "$CONFIG_DIR/.lintstagedrc.yml" \
  "$CONFIG_DIR/git-conventional-commits.yaml"

cd "$PROJECT_DIR"
git init
git add .
git commit -m "chore: initial commit"

sed -i "s/TLD/$TLD/g" "$PROJECT_DIR/build.gradle.kts"
sed -i "s/PROJECT/$PROJECT/g" "$PROJECT_DIR/package.json" "$PROJECT_DIR/settings.gradle.kts"

git add -u
git commit -m "chore: project $PROJECT initialized"

MODULE_DIR="$PROJECT_DIR/module/$MODULE"
install --mode 600 --compare -D --target-directory "$MODULE_DIR" "$CONFIG_MODULE_DIR/build.gradle.kts"
sed -i "s/TLD/$TLD/g" "$MODULE_DIR/build.gradle.kts"

install --mode 600 --compare -D --target-directory "$MODULE_DIR/src/main" "$CONFIG_MODULE_DIR/module-info.java"
sed -i "s/TLD.PROJECT.MODULE/$(to_java_package "$TLD.$PROJECT.$MODULE")/g" "$MODULE_DIR/src/main/$(package_dir)/module-info.java"

install --mode 600 --compare -D --target-directory "$MODULE_DIR/src/main/$(package_dir)" "$CONFIG_MODULE_DIR/Application.java"
sed -i "s/TLD.PROJECT.MODULE/$(to_java_package "$TLD.$PROJECT.$MODULE")/g" "$MODULE_DIR/src/main/$(package_dir)/Application.java"

git add .
git commit -m "chore: module $MODULE initialized"

npm install
./gradlew dependencies --write-locks

gh repo create "$PROJECT" --private --source . --remote origin
git push -u origin main

./gradlew spotlessApply --write-locks
git add .
git commit -m "chore: locks and dependencies initialized"

set +eux
