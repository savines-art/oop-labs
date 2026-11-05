#!/bin/bash

PROGRAMM="sys.pro.Blackjack"

BUILD_DIR=build
SRC_DIR=src/main/java
OUT_DIR="$BUILD_DIR"/classes
DOC_DIR="$BUILD_DIR"/javadoc

mkdir -p "$OUT_DIR" "$DOC_DIR"

javac -d "$OUT_DIR" "$SRC_DIR"/sys/pro/*.java

javadoc -d "$DOC_DIR" -sourcepath "$SRC_DIR" sys.pro

jar cfe "$BUILD_DIR"/Blackjack.jar "$PROGRAMM" -C "$OUT_DIR" .

java -jar "$BUILD_DIR"/Blackjack.jar