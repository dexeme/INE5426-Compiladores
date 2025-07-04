# Makefile para INE5426-Compiladores

SRC_DIR=src
BIN_DIR=bin
MAIN_CLASS=Main
JAVA_FILES=$(shell find $(SRC_DIR) -name '*.java')
JAVAC_FLAGS=-g:none -O -Xlint:all -encoding UTF-8

.PHONY: all run clean

all: $(BIN_DIR)/$(MAIN_CLASS).class

$(BIN_DIR)/$(MAIN_CLASS).class: $(JAVA_FILES)
	@mkdir -p $(BIN_DIR)
	javac $(JAVAC_FLAGS) -d $(BIN_DIR) $(JAVA_FILES)

run: all
	java -cp $(BIN_DIR) $(MAIN_CLASS) $(FILE)

clean:
	rm -rf $(BIN_DIR) 