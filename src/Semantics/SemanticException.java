package Semantics;

import Lexical.Token;

/** Exception thrown during semantic analysis. */
public class SemanticException extends RuntimeException {
    private final int line;
    private final int column;

    public SemanticException(String message, Token token) {
        super(String.format("%s '%s' at line %d, column %d", message, token.value(), token.line(), token.column()));
        this.line = token.line();
        this.column = token.column();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}