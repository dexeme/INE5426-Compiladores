package Constants;

public class Messages {
    public static final String ERROR_INVALID_TOKEN = "Invalid token";
    public static final String ERROR_INVALID_START = "Invalid token start";
    public static final String ERROR_INVALID_SYMBOL = "Invalid symbol";
    public static final String ERROR_UNRECOGNISED_TOKEN = "Token not recognised";
    public static final String ERROR_READING_FILE = "Error reading input file: ";
    public static final String TOTAL_LEXICAL_ERRORS = "Total lexical errors: ";

    public static final String TOTAL_SYNTAX_ERRORS = "Total syntax errors: ";

    public static final String SYMBOL_TABLE_HEADER = "Symbol Table:";
    public static final String AST_HEADER = "AST:";
    public static final String NO_SYNTAX_ERRORS = "No syntax errors found!";

    public static final String TOTAL_SEMANTIC_ERRORS = "Total semantic errors: ";
    public static final String ERROR_DUPLICATE_DECL = "Duplicate declaration";
    public static final String ERROR_UNDECLARED_VAR = "Undeclared variable";
    public static final String ERROR_TYPE_MISMATCH = "Type mismatch";
    public static final String ERROR_BREAK_OUTSIDE = "'break' used outside of loop";

    private Messages() {
    }
}