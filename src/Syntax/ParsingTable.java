package Syntax;

import AL.Token;
import AL.TokenEnum;

import java.util.List;

public class ParsingTable {
    public static List<Symbol> getProduction(Symbol nonTerminal, Token token) {
        TokenEnum t = token.type();
        if (nonTerminal == Symbol.E) {
            if (t == TokenEnum.INT_CONSTANT) {
                return List.of(Symbol.T, Symbol.E_PRIME);
            }
        } else if (nonTerminal == Symbol.E_PRIME) {
            if (t == TokenEnum.PLUS) {
                return List.of(Symbol.PLUS, Symbol.T, Symbol.E_PRIME);
            }
            if (t == TokenEnum.END) {
                return List.of(Symbol.EPSILON);
            }
        } else if (nonTerminal == Symbol.T) {
            if (t == TokenEnum.INT_CONSTANT) {
                return List.of(Symbol.INT);
            }
        }
        return null;
    }
}
