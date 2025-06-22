package AST;

import Lexical.Token;
import Lexical.TokenEnum;

import java.util.List;

public class ExpressionTreeBuilder {
    private final List<Token> tokens;
    private int pos = 0;

    private ExpressionTreeBuilder(List<Token> tokens) {
        this.tokens = tokens;
    }

    public static ExpressionNode build(List<Token> tokens) {
        return new ExpressionTreeBuilder(tokens).parseRelational();
    }

    private ExpressionNode parseRelational() {
        ExpressionNode left = parseAdditive();
        while (pos < tokens.size() && isRelOp(peek().type())) {
            String op = advance().value();
            ExpressionNode right = parseAdditive();
            left = new BinaryOpNode(left, op, right);
        }
        return left;
    }

    private ExpressionNode parseAdditive() {
        ExpressionNode left = parseMultiplicative();
        while (pos < tokens.size() &&
                (check(TokenEnum.PLUS) || check(TokenEnum.MINUS))) {
            String op = advance().value();
            ExpressionNode right = parseMultiplicative();
            left = new BinaryOpNode(left, op, right);
        }
        return left;
    }

    private ExpressionNode parseMultiplicative() {
        ExpressionNode left = parseUnary();
        while (pos < tokens.size() && (check(TokenEnum.MULTIPLY) ||
                check(TokenEnum.DIVIDE) || check(TokenEnum.MODULO))) {
            String op = advance().value();
            ExpressionNode right = parseUnary();
            left = new BinaryOpNode(left, op, right);
        }
        return left;
    }

    private ExpressionNode parseUnary() {
        if (match(TokenEnum.MINUS)) {
            ExpressionNode operand = parseUnary();
            return new BinaryOpNode(new IntLiteralNode("0"), "-", operand);
        }
        if (match(TokenEnum.PLUS)) {
            return parseUnary();
        }
        return parsePrimary();
    }

    private ExpressionNode parsePrimary() {
        if (match(TokenEnum.OPEN_PAREN)) {
            ExpressionNode inside = parseRelational();
            consume(TokenEnum.CLOSE_PAREN);
            return inside;
        }
        if (match(TokenEnum.INT_CONSTANT)) {
            return new IntLiteralNode(previous().value());
        }
        if (match(TokenEnum.FLOAT_CONSTANT)) {
            return new FloatLiteralNode(previous().value());
        }
        if (match(TokenEnum.STRING_CONSTANT)) {
            return new StringLiteralNode(previous().value());
        }
        if (match(TokenEnum.IDENT)) {
            return new VarNode(previous().value());
        }
        return new VarNode("");
    }

    private boolean match(TokenEnum t) {
        if (check(t)) { advance(); return true; }
        return false;
    }

    private boolean check(TokenEnum t) {
        return pos < tokens.size() && tokens.get(pos).type() == t;
    }

    private Token advance() { return tokens.get(pos++); }

    private Token consume(TokenEnum t) {
        if (check(t)) return advance();
        return new Token(t, "", 0, 0);
    }

    private Token peek() { return tokens.get(pos); }
    private Token previous() { return tokens.get(pos - 1); }

    private boolean isRelOp(TokenEnum t) {
        return t == TokenEnum.LESS_THAN || t == TokenEnum.GREATER_THAN ||
                t == TokenEnum.LESS_THAN_OR_EQUAL ||
                t == TokenEnum.GREATER_THAN_OR_EQUAL ||
                t == TokenEnum.EQUALS || t == TokenEnum.NOT_EQUALS;
    }
}