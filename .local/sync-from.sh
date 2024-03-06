#!/bin/sh
SOURCE=$1
rsync --archive --delete --verbose --human-readable \
  --exclude "sync-from.sh" \
  --exclude "sync-to.sh" \
  --exclude ".git/" \
  --exclude "*lockfile" \
  --exclude "node_modules/" \
  --exclude "build/" \
  --exclude ".gradle/" \
  --exclude ".idea/" \
  --exclude "./src/" \
  "$SOURCE/" .
