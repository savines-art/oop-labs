#!/bin/bash

PROGRAMM="lab.task.HeapSort"

SRC_DIR=src/main/java
OUT_DIR=build/classes
DOC_DIR=build/javadoc

mkdir -p "$OUT_DIR" "$DOC_DIR"

find "$SRC_DIR" -name "*.java" | xargs javac -d "$OUT_DIR"

javadoc -d "$DOC_DIR" -sourcepath "$SRC_DIR" -subpackages lab.task

java -cp "$OUT_DIR" "$PROGRAMM"

