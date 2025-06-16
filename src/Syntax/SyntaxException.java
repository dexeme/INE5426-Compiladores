package Syntax;

import AL.Token;

/**
 * Exception thrown when the syntax analysis finds an error.
 */
public class SyntaxException extends RuntimeException {
    public SyntaxException(String message, Token token) {
        super(String.format("%s '%s' at line %d, column %d", message, token.value(), token.line(), token.column()));
    }
}