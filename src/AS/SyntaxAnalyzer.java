package AS;

import Lexical.Token;
import Lexical.TokenEnum;
import AST.*;
import AST.ExpressionTreeBuilder;
import Syntax.SyntaxException;
import Symbols.NonterminalEnum;

import java.util.*;

public class SyntaxAnalyzer {
    private final List<Token> tokens;
    private int index = 0;
    private final List<SyntaxException> errors = new ArrayList<>();

    public SyntaxAnalyzer(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
    }

    /**
     * Parse the entire program and return the AST root.
     */
    public ProgramNode parse() {
        List<FunctionNode> funcs = new ArrayList<>();

        if (!check(TokenEnum.DEF)) {
            if (!isAtEnd()) {
                recordError(new SyntaxException(NonterminalEnum.PROGRAM, peek()));
                // consume remaining tokens to avoid infinite loop
                while (!isAtEnd()) advance();
            }
            return new ProgramNode(funcs);
        }

        while (match(TokenEnum.DEF)) {
            String name = consume(TokenEnum.IDENT, NonterminalEnum.FUNCDEF).value();
            consume(TokenEnum.OPEN_PAREN, NonterminalEnum.FUNCDEF);
            List<Parameter> params = new ArrayList<>();
            if (!check(TokenEnum.CLOSE_PAREN)) {
                do {
                    Token typeTok;
                    if (match(TokenEnum.INT) || match(TokenEnum.FLOAT) || match(TokenEnum.STRING)) {
                        typeTok = previous();
                    } else {
                        typeTok = consume(TokenEnum.INT, NonterminalEnum.PARAMLIST);
                    }
                    String nameTok = consume(TokenEnum.IDENT, NonterminalEnum.PARAMLIST).value();
                    params.add(new Parameter(typeTok.value(), nameTok));
                } while (match(TokenEnum.COMMA));
            }
            consume(TokenEnum.CLOSE_PAREN, NonterminalEnum.FUNCDEF);

            match(TokenEnum.OPEN_CURLY_BRACE); // optional function block start

            List<StatementNode> body = new ArrayList<>();
            while (!isAtEnd() && !check(TokenEnum.CLOSE_CURLY_BRACE)) {
                body.add(parseStatement());
            }

            match(TokenEnum.CLOSE_CURLY_BRACE); // optional function block end
            funcs.add(new FunctionNode(name, params, body));
        }

        if (!isAtEnd()) {
            recordError(new SyntaxException(NonterminalEnum.PROGRAM, peek()));
            while (!isAtEnd()) advance();
        }

        return new ProgramNode(funcs);
    }



    public List<SyntaxException> getErrors() {
        return new ArrayList<>(errors);
    }

    private void recordError(SyntaxException e) {
        errors.add(e);
    }

    private StatementNode parseStatement() {
        // variable declaration
        if (match(TokenEnum.STRING) || match(TokenEnum.FLOAT) || match(TokenEnum.INT)) {
            Token type = previous();
            Token nameTok = consume(TokenEnum.IDENT, NonterminalEnum.VARDECL);
            // ignore optional array dimensions
            while (match(TokenEnum.OPEN_BRACKET)) {
                consume(TokenEnum.INT_CONSTANT, NonterminalEnum.VARDIM);
                consume(TokenEnum.CLOSE_BRACKET, NonterminalEnum.VARDIM);
            }
            consume(TokenEnum.SEMICOLON, NonterminalEnum.VARDECL);
            return new VarDeclNode(type.value(), nameTok.value());
        }
        // assignment or expression starting with identifier
        if (check(TokenEnum.IDENT)) {
            Token ident = advance();
            if (match(TokenEnum.EQUAL)) {
                ExpressionNode expr = parseExpression(collectUntil(TokenEnum.SEMICOLON));
                match(TokenEnum.SEMICOLON);
                return new AssignmentNode(ident.value(), expr);
            } else {
                // consume until semicolon for function calls or expressions
                collectUntil(TokenEnum.SEMICOLON);
                match(TokenEnum.SEMICOLON);
                return new DummyNode("ExprStatement");
            }
        }
        if (match(TokenEnum.PRINT)) {
            List<Token> exprTokens = collectUntil(TokenEnum.SEMICOLON);
            consume(TokenEnum.SEMICOLON, NonterminalEnum.PRINTSTAT);
            ExpressionNode expr = parseExpression(exprTokens);
            return new PrintNode(expr);
        }
        if (match(TokenEnum.READ)) {
            Token id = consume(TokenEnum.IDENT, NonterminalEnum.READSTAT);
            consume(TokenEnum.SEMICOLON, NonterminalEnum.READSTAT);
            return new ReadNode(id.value());
        }
        if (match(TokenEnum.RETURN)) {
            List<Token> exprToks = collectUntil(TokenEnum.SEMICOLON);
            consume(TokenEnum.SEMICOLON, NonterminalEnum.RETURNSTAT);
            ExpressionNode expr = parseExpression(exprToks);
            return new ReturnNode(expr);
        }
        if (match(TokenEnum.BREAK)) {
            consume(TokenEnum.SEMICOLON, NonterminalEnum.STATEMENT);
            return new BreakNode();
        }
        if (match(TokenEnum.IF)) {
            consume(TokenEnum.OPEN_PAREN, NonterminalEnum.IFSTAT);
            List<Token> conditionTokens = collectUntil(TokenEnum.CLOSE_PAREN);
            consume(TokenEnum.CLOSE_PAREN, NonterminalEnum.IFSTAT);
            match(TokenEnum.OPEN_CURLY_BRACE);
            ExpressionNode cond = parseExpression(conditionTokens);
            List<StatementNode> thenStmts = new ArrayList<>();
            while (!check(TokenEnum.CLOSE_CURLY_BRACE) && !isAtEnd()) {
                thenStmts.add(parseStatement());
            }
            consume(TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.IFSTAT);
            List<StatementNode> elseStmts = null;
            if (match(TokenEnum.ELSE)) {
                match(TokenEnum.OPEN_CURLY_BRACE);
                elseStmts = new ArrayList<>();
                while (!check(TokenEnum.CLOSE_CURLY_BRACE) && !isAtEnd()) {
                    elseStmts.add(parseStatement());
                }
                consume(TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.IFSTAT);
            }
            return new IfNode(cond, thenStmts, elseStmts);
        }
        if (match(TokenEnum.FOR)) {
            consume(TokenEnum.OPEN_PAREN, NonterminalEnum.FORSTAT);
            List<Token> initToks = collectUntil(TokenEnum.SEMICOLON); consume(TokenEnum.SEMICOLON, NonterminalEnum.FORSTAT);
            List<Token> condToks = collectUntil(TokenEnum.SEMICOLON); consume(TokenEnum.SEMICOLON, NonterminalEnum.FORSTAT);
            List<Token> incrToks = collectUntil(TokenEnum.CLOSE_PAREN); consume(TokenEnum.CLOSE_PAREN, NonterminalEnum.FORSTAT);
            StatementNode init = tokensToStatement(initToks);
            ExpressionNode cond = parseExpression(condToks);
            StatementNode incr = tokensToStatement(incrToks);
            match(TokenEnum.OPEN_CURLY_BRACE);
            List<StatementNode> body = new ArrayList<>();
            while (!check(TokenEnum.CLOSE_CURLY_BRACE) && !isAtEnd()) {
                body.add(parseStatement());
            }
            consume(TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.FORSTAT);
            return new ForNode(init, cond, incr, body);
        }
        if (match(TokenEnum.OPEN_CURLY_BRACE)) {
            while (!check(TokenEnum.CLOSE_CURLY_BRACE) && !isAtEnd()) {
                parseStatement();
            }
            consume(TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.STATEMENT);
            return new DummyNode("Block");
        }
        if (match(TokenEnum.SEMICOLON)) {
            return new DummyNode("Empty");
        }
        Token t = advance();
        recordError(new SyntaxException(NonterminalEnum.STATEMENT, t));
        return new DummyNode("Error");
    }

    private ExpressionNode parseExpression(List<Token> exprTokens) {
        if (exprTokens.isEmpty()) {
            return new VarNode("");
        }
        return ExpressionTreeBuilder.build(exprTokens);
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

    private Token consume(TokenEnum type, NonterminalEnum ctx) {
        if (check(type)) return advance();
        SyntaxException e = new SyntaxException(ctx, peek());
        recordError(e);
        if (!isAtEnd()) advance();
        Token current = previous();
        return new Token(type, current.value(), current.line(), current.column());
    }

    // fallback method using a plain message
    private Token consume(TokenEnum type, String message) {
        if (check(type)) return advance();
        SyntaxException e = new SyntaxException(message, peek());
        recordError(e);
        if (!isAtEnd()) advance();
        Token current = previous();
        return new Token(type, current.value(), current.line(), current.column());
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

    private StatementNode tokensToStatement(List<Token> list) {
        if (list.isEmpty()) return new DummyNode("Empty");
        if (list.size() >= 3 && list.get(0).type() == TokenEnum.IDENT && list.get(1).type() == TokenEnum.EQUAL) {
            String name = list.get(0).value();
            ExpressionNode expr = parseExpression(list.subList(2, list.size()));
            return new AssignmentNode(name, expr);
        }
        return new DummyNode("Expr");
    }
}