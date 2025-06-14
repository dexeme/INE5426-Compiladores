package AL;

/** Different categories of lexical errors. */
public enum LexicalErrorType {
    /** Sequence of characters cannot start any valid token. */
    INVALID_START(ErrorMessages.INVALID_START),
    /** Token began correctly but ended in an invalid state. */
    INVALID_TOKEN(ErrorMessages.INVALID_TOKEN),
    /** Single symbol that does not belong to any token. */
    INVALID_SYMBOL(ErrorMessages.INVALID_SYMBOL),
    /** Sequence recognised but not mapped to a token type. */
    UNRECOGNISED_TOKEN(ErrorMessages.UNRECOGNISED_TOKEN);

    private final String message;

    LexicalErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}