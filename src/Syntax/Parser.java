package Syntax;

import AL.Token;

import java.util.List;
import java.util.Stack;

public class Parser {

    private final List<Token> tokenList;
    private int currentTokenIndex = 0;

    public Parser(List<Token> tokens) {
        tokenList = tokens;
        tokenList.add(Token.endToken());
    }

    public void parse() {
        Stack<Symbol> stack = new Stack<>();
        stack.push(Symbol.endSymbol());
        stack.push(Symbol.startSymbol());

        Token currentToken = getNextToken();
        Symbol currentSymbol = stack.peek();

        while (!currentSymbol.equals(Symbol.endSymbol())) {
            if (currentSymbol.equalsToken(currentToken)) {
                stack.pop();
                currentToken = getNextToken();
            } else if (currentSymbol.isTerminal()) {
                throw new RuntimeException("Unexpected token: " + currentToken);
            } else {
                List<Symbol> production = ParsingTable.getProduction(currentSymbol, currentToken);
                if (production == null) {
                    throw new RuntimeException("No production found for " + currentSymbol + " with token " + currentToken);
                }
                stack.pop();
                for (int i = production.size() - 1; i >= 0; i--) {
                    stack.push(production.get(i));
                }
            }
            currentSymbol = stack.peek();
        }
    }

    private Token getNextToken() {
        if (currentTokenIndex < tokenList.size()) {
            return tokenList.get(currentTokenIndex++);
        } else {
            throw new RuntimeException("No more tokens available");
        }
    }

}
