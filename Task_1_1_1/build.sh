#!/bin/bash

PROGRAMM="lab.task.HeapSort"

BUILD_DIR=build
SRC_DIR=src/main/java
OUT_DIR="$BUILD_DIR"/classes
DOC_DIR="$BUILD_DIR"/javadoc

mkdir -p "$OUT_DIR" "$DOC_DIR"

javac -d "$OUT_DIR" "$SRC_DIR"/lab/task/HeapSort.java

javadoc -d "$DOC_DIR" -sourcepath "$SRC_DIR" lab.task

jar cfe "$BUILD_DIR"/HeapSort.jar "$PROGRAMM" -C "$OUT_DIR" .

java -jar "$BUILD_DIR"/HeapSort.jar

