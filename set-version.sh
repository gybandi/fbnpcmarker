#!/bin/bash
set -e

VERSION=$1
FILE="manifest.json"

if [ -z "$VERSION" ]; then
  echo "Usage: $0 <version>"
  exit 1
fi

tmp=$(mktemp)

jq --arg v "$VERSION" '.version = $v' "$FILE" > "$tmp" && mv "$tmp" "$FILE"

echo "Updated version to $VERSION in $FILE"