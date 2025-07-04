package AS;

import Lexical.Token;
import Symbols.NonterminalEnum;

/**
 * Exception thrown when the syntax analysis finds an error.
 */
public class SyntaxException extends RuntimeException {
    private final int line;
    private final int column;

    public SyntaxException(String message, Token token) {
        super(String.format("%s '%s' at line %d, column %d", message, token.value(), token.line(), token.column()));
        this.line = token.line();
        this.column = token.column();
    }

    public SyntaxException(NonterminalEnum nonterminal, Token token) {
        super(String.format("Syntax error at line %d, column %d: LL(1) entry [%s, %s] is empty", token.line(), token.column(), nonterminal, token.type()));
        this.line = token.line();
        this.column = token.column();
    }

    public int getLine() { return line; }
    public int getColumn() { return column; }
}