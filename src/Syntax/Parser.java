package Syntax;

import AL.Token;
import AL.TokenEnum;
import AST.*;
import Constants.Messages;

import java.util.*;

public class Parser {
    private final List<Token> tokens;
    private int index = 0;
    private final List<SyntaxException> errors = new ArrayList<>();

    public Parser(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
    }

    public ProgramNode parse() {
        List<FunctionNode> funcs = new ArrayList<>();
        if (match(TokenEnum.DEF)) {
            String name = consume(TokenEnum.IDENT, Messages.ERROR_UNEXPECTED_TOKEN).value();
            consume(TokenEnum.OPEN_PAREN, Messages.ERROR_UNEXPECTED_TOKEN);
            // todo: permitir argumentos
            consume(TokenEnum.CLOSE_PAREN, Messages.ERROR_UNEXPECTED_TOKEN);

            match(TokenEnum.OPEN_CURLY_BRACE); // optional function block start

            List<StatementNode> body = new ArrayList<>();
            while (!isAtEnd() && !check(TokenEnum.CLOSE_CURLY_BRACE)) {
                body.add(parseStatement());
            }

            match(TokenEnum.CLOSE_CURLY_BRACE); // optional function block end
            funcs.add(new FunctionNode(name, body));
        }
        return new ProgramNode(funcs);
    }

    public List<SyntaxException> getErrors() { return errors; }

    private StatementNode parseStatement() {
        if (check(TokenEnum.IDENT) && checkNext(TokenEnum.EQUAL)) {
            String name = advance().value();
            consume(TokenEnum.EQUAL, Messages.ERROR_UNEXPECTED_TOKEN);
            ExpressionNode expr = parseExpression(collectUntilLineChange());
            return new AssignmentNode(name, expr);
        }
        if (match(TokenEnum.PRINT)) {
            consume(TokenEnum.OPEN_PAREN, Messages.ERROR_UNEXPECTED_TOKEN);
            List<Token> exprTokens = collectUntil(TokenEnum.CLOSE_PAREN);
            consume(TokenEnum.CLOSE_PAREN, Messages.ERROR_UNEXPECTED_TOKEN);
            ExpressionNode expr = parseExpression(exprTokens);
            return new PrintNode(expr);
        }
        if (match(TokenEnum.IF)) {
            List<Token> conditionTokens = collectUntilLineChange();
            ExpressionNode cond = parseExpression(conditionTokens);
            if (isAtEnd()) {
                errors.add(new SyntaxException(Messages.ERROR_UNEXPECTED_EOF, previous()));
                return new PrintNode(new VarNode(""));
            }
            StatementNode thenStmt = parseStatement();
            return new IfNode(cond, List.of(thenStmt), null);
        }
        Token t = advance();
        SyntaxException e = new SyntaxException(Messages.ERROR_UNEXPECTED_TOKEN, t);
        errors.add(e);
        return new PrintNode(new VarNode(""));
    }

    private ExpressionNode parseExpression(List<Token> exprTokens) {
        if (exprTokens.isEmpty()) {
            return new VarNode("");
        }
        if (exprTokens.size() == 1) {
            Token tok = exprTokens.get(0);
            if (tok.type() == TokenEnum.INT_CONSTANT) {
                return new IntLiteralNode(tok.value());
            }
            return new VarNode(tok.value());
        }
        if (exprTokens.size() == 3) {
            ExpressionNode left = parseExpression(List.of(exprTokens.get(0)));
            String op = exprTokens.get(1).value();
            ExpressionNode right = parseExpression(List.of(exprTokens.get(2)));
            return new BinaryOpNode(left, op, right);
        }
        return new VarNode(exprTokens.getFirst().value());
    }

    private List<Token> collectUntilLineChange() {
        int currentLine = peek().line();
        List<Token> list = new ArrayList<>();
        while (!isAtEnd() && peek().line() == currentLine) {
            list.add(advance());
        }
        return list;
    }

    private List<Token> collectUntil(TokenEnum type) {
        List<Token> list = new ArrayList<>();
        while (!isAtEnd() && peek().type() != type) {
            list.add(advance());
        }
        return list;
    }

    private boolean match(TokenEnum type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    private boolean check(TokenEnum type) {
        if (isAtEnd()) return false;
        return peek().type() == type;
    }

    private boolean checkNext(TokenEnum type) {
        if (index + 1 >= tokens.size()) return false;
        return tokens.get(index + 1).type() == type;
    }

    private Token consume(TokenEnum type, String message) {
        if (check(type)) return advance();
        SyntaxException e = new SyntaxException(message, peek());
        errors.add(e);
        throw e;
    }

    private Token advance() {
        if (!isAtEnd()) index++;
        return tokens.get(index - 1);
    }

    private boolean isAtEnd() {
        return index >= tokens.size();
    }

    private Token peek() { return tokens.get(index); }
    private Token previous() { return tokens.get(index - 1); }
}
