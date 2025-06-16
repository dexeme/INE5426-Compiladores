package Syntax;

import AL.Token;
import AL.TokenEnum;

public class Symbol {
    private final String name;
    private final boolean terminal;
    private final TokenEnum tokenType;

    private Symbol(String name, boolean terminal, TokenEnum tokenType) {
        this.name = name;
        this.terminal = terminal;
        this.tokenType = tokenType;
    }

    public static Symbol terminal(TokenEnum type) {
        return new Symbol(type.name(), true, type);
    }

    public static Symbol nonTerminal(String name) {
        return new Symbol(name, false, null);
    }

    public boolean equalsToken(Token token) {
        return terminal && token.type() == tokenType;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public String getName() {
        return name;
    }

    public TokenEnum getTokenType() {
        return tokenType;
    }

    private static final Symbol START = nonTerminal("E");
    private static final Symbol END = terminal(TokenEnum.END);
    public static final Symbol E = START;
    public static final Symbol E_PRIME = nonTerminal("E'");
    public static final Symbol T = nonTerminal("T");
    public static final Symbol PLUS = terminal(TokenEnum.PLUS);
    public static final Symbol INT = terminal(TokenEnum.INT_CONSTANT);
    public static final Symbol EPSILON = new Symbol("ε", true, null);

    public static Symbol startSymbol() {
        return START;
    }

    public static Symbol endSymbol() {
        return END;
    }

    @Override
    public String toString() {
        return name;
    }
}
