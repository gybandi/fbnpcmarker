#!/bin/bash

if [ -z "$1" ] 
then
  echo "Usage: ./package.sh <version>"
  exit 0
fi

SRC_DIR="src"
OUTPUT_ZIP="build/fbnpcmarker-$1.zip"

# Create build dir
mkdir -p build

# Zip contents of src (not the folder itself)
cd "$SRC_DIR" || exit 1
zip -r "../$OUTPUT_ZIP" ./*
cd - >/dev/null

echo "Created $OUTPUT_ZIP containing contents of $SRC_DIR"