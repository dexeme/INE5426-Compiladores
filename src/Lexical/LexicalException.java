package Lexical;

/**
 * Exception thrown when the lexical analysis finds an error.
 */
public class LexicalException extends RuntimeException {
    private final int line;
    private final int column;
    private final LexicalErrorType type;

    public LexicalException(LexicalErrorType type, String lexeme, int line, int column) {
        super(String.format("%s '%s' at line %d, column %d", type.getMessage(), lexeme, line, column));
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public LexicalErrorType getType() {
        return type;
    }
}