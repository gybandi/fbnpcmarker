#!/bin/bash

# Files and directory to compress
DIR_TO_ZIP="assets"
FILES_TO_ZIP=("manifest.json")
OUTPUT_ZIP="build/fbnpcmarker-$1.zip"

# Create dir
mkdir -p build
# Create the zip archive
zip -r "$OUTPUT_ZIP" "$DIR_TO_ZIP" "${FILES_TO_ZIP[@]}"

echo "Created $OUTPUT_ZIP containing $DIR_TO_ZIP and ${FILES_TO_ZIP[*]}"