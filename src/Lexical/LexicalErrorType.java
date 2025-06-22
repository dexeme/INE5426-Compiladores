package Lexical;

import Constants.Messages;

/** Different categories of lexical errors. */
public enum LexicalErrorType {
    /** Sequence of characters cannot start any valid token. */
    INVALID_START(Messages.ERROR_INVALID_START),
    /** Token began correctly but ended in an invalid state. */
    INVALID_TOKEN(Messages.ERROR_INVALID_TOKEN),
    /** Single symbol that does not belong to any token. */
    INVALID_SYMBOL(Messages.ERROR_INVALID_SYMBOL),
    /** Sequence recognised but not mapped to a token type. */
    UNRECOGNISED_TOKEN(Messages.ERROR_UNRECOGNISED_TOKEN);

    private final String message;

    LexicalErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}