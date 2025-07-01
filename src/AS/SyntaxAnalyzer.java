package AS;

import Lexical.Token;
import Lexical.TokenEnum;
import AST.*;
import Syntax.SyntaxException;
import java.util.*;

public class SyntaxAnalyzer {
    private final List<Token> tokens;
    private int index;
    private Token lookahead;

    public SyntaxAnalyzer(List<Token> tokens) {
        this.tokens = new ArrayList<>(tokens);
        this.tokens.add(new Token(TokenEnum.END, "", -1, -1));
        this.index = 0;
        this.lookahead = tokens.getFirst();
    }

    public ProgramNode parse() {
        return parseProgram();
    }

    private void consume() {
        index++;
        if (index >= tokens.size()) {
            throw new SyntaxException("Unexpected end of input", lookahead);
        }
        lookahead = tokens.get(index);
    }

    private void match(TokenEnum expected) {
        if (lookahead.type() == expected) {
            consume();
        } else {
            throw new SyntaxException(String.format(
                "Expected %s but found %s at line %d, column %d",
                expected, lookahead.type(), lookahead.line(), lookahead.column()
            ), lookahead);
        }
    }

    private ProgramNode parseProgram() {
        List<ASTNode> nodes = new ArrayList<>();
        switch (lookahead.type()) {
            case DEF -> nodes.addAll(parseFuncList());
            case IDENT, OPEN_CURLY_BRACE, STRING, FLOAT, INT, SEMICOLON, BREAK, PRINT, READ, RETURN, IF, FOR -> {
                StatementNode stmt = parseStatement();
                if (stmt != null) {
                    nodes.add(stmt);
                }
            }
            case END -> match(TokenEnum.END);
            default -> throw new SyntaxException("Unexpected token: " + lookahead.type(), lookahead);
        }
        return new ProgramNode(nodes);
    }

    private List<FunctionNode> parseFuncList() {
        List<FunctionNode> functions = new ArrayList<>();
        while (lookahead.type() == TokenEnum.DEF) {
            match(TokenEnum.DEF);
            if (lookahead.type() != TokenEnum.IDENT) {
                throw new SyntaxException("Expected function name after 'def'", lookahead);
            }
            Token functionIdentifier = lookahead;
            match(TokenEnum.IDENT);
            match(TokenEnum.OPEN_PAREN);
            List<Parameter> params = parseParamList();
            match(TokenEnum.CLOSE_PAREN);
            match(TokenEnum.OPEN_CURLY_BRACE);
            List<StatementNode> statements = parseStatementList();
            match(TokenEnum.CLOSE_CURLY_BRACE);
            functions.add(new FunctionNode(functionIdentifier, params, statements));
        }
        return functions;
    }

    private List<Parameter> parseParamList() {
        List<Parameter> params = new ArrayList<>();
        while (lookahead.type() != TokenEnum.CLOSE_PAREN) {
            if (lookahead.type() == TokenEnum.INT || lookahead.type() == TokenEnum.FLOAT || lookahead.type() == TokenEnum.STRING) {
                TokenEnum type = lookahead.type();
                match(type);
                if (lookahead.type() != TokenEnum.IDENT) {
                    throw new SyntaxException("Expected parameter name after type", lookahead);
                }
                String paramName = lookahead.value();
                match(TokenEnum.IDENT);
                params.add(new Parameter(type.name(), paramName));
                if (lookahead.type() == TokenEnum.COMMA) {
                    match(TokenEnum.COMMA);
                }
            } else {
                throw new SyntaxException("Unexpected token in parameter list: " + lookahead.type(), lookahead);
            }
        }
        return params;
    }

    private StatementNode parseStatement() {
        switch (lookahead.type()) {
            case INT, FLOAT, STRING -> {
                return parseVarDecl();
            }
            case IDENT -> {
                StatementNode atribStat = parseAtribStat();
                match(TokenEnum.SEMICOLON);
                return atribStat;
            }
            case PRINT -> {
                StatementNode printStat = parsePrintStat();
                match(TokenEnum.SEMICOLON);
                return printStat;
            }
            case READ -> {
                StatementNode readStat = parseReadStat();
                match(TokenEnum.SEMICOLON);
                return readStat;
            }
            case RETURN -> {
                StatementNode returnStat = parseReturnStat();
                match(TokenEnum.SEMICOLON);
                return returnStat;
            }
            case IF -> {
                return parseIfStat();
            }
            case FOR -> {
                return parseForStat();
            }
            case OPEN_CURLY_BRACE -> {
                match(TokenEnum.OPEN_CURLY_BRACE);
                List<StatementNode> statements = parseStatementList();
                match(TokenEnum.CLOSE_CURLY_BRACE);
                return new BlockNode(statements);
            }
            case BREAK -> {
                match(TokenEnum.BREAK);
                match(TokenEnum.SEMICOLON);
                return new BreakNode();
            }
            case SEMICOLON -> {
                match(TokenEnum.SEMICOLON);
                return new EmptyStatementNode();
            }
            default -> throw new SyntaxException("Unexpected token in statement: " + lookahead.type(), lookahead);
        }
    }

    private StatementNode parseVarDecl() {
        TokenEnum type = lookahead.type();
        if (type != TokenEnum.INT && type != TokenEnum.FLOAT && type != TokenEnum.STRING) {
            throw new SyntaxException("Expected variable declaration type", lookahead);
        }
        match(type);
        if (lookahead.type() != TokenEnum.IDENT) {
            throw new SyntaxException("Expected variable name after type", lookahead);
        }
        Token variableIdentifier = lookahead;
        match(TokenEnum.IDENT);
        List<Integer> dimensions = parseVarDim();
        match(TokenEnum.SEMICOLON);
        return new VarDeclNode(type.name(), variableIdentifier, dimensions);
    }

    private List<Integer> parseVarDim() {
        List<Integer> dimensions = new ArrayList<>();
        while (lookahead.type() == TokenEnum.OPEN_BRACKET) {
            match(TokenEnum.OPEN_BRACKET);
            if (lookahead.type() != TokenEnum.INT_CONSTANT) {
                throw new SyntaxException("Expected integer constant for array dimension", lookahead);
            }
            dimensions.add(Integer.parseInt(lookahead.value()));
            match(TokenEnum.INT_CONSTANT);
            match(TokenEnum.CLOSE_BRACKET);
        }
        if (lookahead.type() != TokenEnum.SEMICOLON) {
            throw new SyntaxException("Expected semicolon after variable declaration", lookahead);
        }
        return dimensions;
    }

    private StatementNode parseAtribStat() {
        VarNode lvalue = parseLValue();
        match(TokenEnum.EQUAL);
        AssignableNode atribStat1 = parseAtribStat1();
        return new AssignmentNode(lvalue, atribStat1);
    }

    private FunctionCallNode parseFuncCall() {
        if (lookahead.type() != TokenEnum.IDENT) {
            throw new SyntaxException("Expected function name in function call", lookahead);
        }
        String funcName = lookahead.value();
        consume();
        match(TokenEnum.OPEN_PAREN);
        List<String> params = parseParamListCall();
        match(TokenEnum.CLOSE_PAREN);
        return new FunctionCallNode(funcName, params);
    }

    private List<String> parseParamListCall() {
        List<String> params = new ArrayList<>();
        while (lookahead.type() != TokenEnum.CLOSE_PAREN) {
            if (lookahead.type() == TokenEnum.IDENT) {
                params.add(lookahead.value());
                match(TokenEnum.IDENT);
                if (lookahead.type() == TokenEnum.COMMA) {
                    match(TokenEnum.COMMA);
                }
            } else {
                throw new SyntaxException("Expected identifier in parameter list", lookahead);
            }
        }
        return params;
    }

    private PrintNode parsePrintStat() {
        match(TokenEnum.PRINT);
        ExpressionNode expression = parseExpression();
        return new PrintNode(expression);
    }

    private ReadNode parseReadStat() {
        match(TokenEnum.READ);
        VarNode lvalue = parseLValue();
        return new ReadNode(lvalue);
    }

    private ReturnNode parseReturnStat() {
        match(TokenEnum.RETURN);
        return new ReturnNode();
    }

    private IfNode parseIfStat() {
        match(TokenEnum.IF);
        match(TokenEnum.OPEN_PAREN);
        ExpressionNode condition = parseExpression();
        match(TokenEnum.CLOSE_PAREN);
        match(TokenEnum.OPEN_CURLY_BRACE);
        List<StatementNode> thenStatements = parseStatementList();
        match(TokenEnum.CLOSE_CURLY_BRACE);
        switch (lookahead.type()) {
            case ELSE -> {
                match(TokenEnum.ELSE);
                match(TokenEnum.OPEN_CURLY_BRACE);
                List<StatementNode> elseStatements = parseStatementList();
                match(TokenEnum.CLOSE_CURLY_BRACE);
                return new IfNode(condition, thenStatements, elseStatements);
            }
            case IDENT, OPEN_CURLY_BRACE, CLOSE_CURLY_BRACE, STRING, FLOAT, INT, SEMICOLON, BREAK, PRINT, READ, RETURN,
                 IF, FOR, END -> {
                return new IfNode(condition, thenStatements, null);
            }
            default -> {
                throw new SyntaxException("Unexpected token after 'if' statement: " + lookahead.type(), lookahead);
            }
        }
    }

    private StatementNode parseForStat() {
        match(TokenEnum.FOR);
        match(TokenEnum.OPEN_PAREN);
        StatementNode init = parseAtribStat();
        match(TokenEnum.SEMICOLON);
        ExpressionNode condition = parseExpression();
        match(TokenEnum.SEMICOLON);
        StatementNode update = parseAtribStat();
        match(TokenEnum.CLOSE_PAREN);
        StatementNode body = parseStatement();
        return new ForNode(init, condition, update, body);
    }

    private List<StatementNode> parseStatementList() {
        List<StatementNode> statements = new ArrayList<>();
        while (lookahead.type() != TokenEnum.CLOSE_CURLY_BRACE) {
            StatementNode stmt = parseStatement();
            statements.add(stmt);
        }
        return statements;
    }

    private AllocExpressionNode parseAllocExpression() {
        match(TokenEnum.NEW);
        TokenEnum type = lookahead.type();
        if (type != TokenEnum.INT && type != TokenEnum.FLOAT && type != TokenEnum.STRING) {
            throw new SyntaxException("Expected type after 'new'", lookahead);
        }
        match(type);
        match(TokenEnum.OPEN_BRACKET);
        ExpressionNode dimension = parseNumExpression();
        match(TokenEnum.CLOSE_BRACKET);
        List<ExpressionNode> dimensions = new ArrayList<>();
        dimensions.add(dimension);
        while (lookahead.type() == TokenEnum.OPEN_BRACKET) {
            match(TokenEnum.OPEN_BRACKET);
            ExpressionNode dim = parseNumExpression();
            dimensions.add(dim);
            match(TokenEnum.CLOSE_BRACKET);
        }
        if (lookahead.type() != TokenEnum.SEMICOLON && lookahead.type() != TokenEnum.CLOSE_PAREN) {
            throw new SyntaxException("Expected semicolon or close parenthesis after allocation", lookahead);
        }
        return new AllocExpressionNode(type.name(), dimensions);
    }

    private ExpressionNode parseExpression() {
        ExpressionNode left = parseNumExpression();
        switch (lookahead.type()) {
            case LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL, EQUALS, NOT_EQUALS -> {
                TokenEnum operator = lookahead.type();
                consume();
                ExpressionNode right = parseNumExpression();
                return new BinaryOpNode(left, operator.name(), right);
            }
            case CLOSE_PAREN, SEMICOLON -> {
                return left;
            }
            default -> throw new SyntaxException("Unexpected token in expression: " + lookahead.type(), lookahead);
        }
    }

    private ExpressionNode parseNumExpression() {
        ExpressionNode left = parseTerm();
        while (lookahead.type() == TokenEnum.PLUS || lookahead.type() == TokenEnum.MINUS) {
            TokenEnum operator = lookahead.type();
            consume();
            ExpressionNode right = parseTerm();
            left = new BinaryOpNode(left, operator.name(), right);
        }
        return left;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode left = parseUnaryExpr();
        while (lookahead.type() == TokenEnum.MULTIPLY || lookahead.type() == TokenEnum.DIVIDE || lookahead.type() == TokenEnum.MODULO) {
            TokenEnum operator = lookahead.type();
            consume();
            ExpressionNode right = parseUnaryExpr();
            left = new BinaryOpNode(left, operator.name(), right);
        }
        switch (lookahead.type()) {
            case CLOSE_PAREN, SEMICOLON, CLOSE_BRACKET, PLUS, MINUS, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL ,
                 GREATER_THAN_OR_EQUAL, EQUALS, NOT_EQUALS -> {
                return left;
            }
            default -> throw new SyntaxException("Unexpected token in term: " + lookahead.type(), lookahead);
        }
    }

    private ExpressionNode parseUnaryExpr() {
        switch (lookahead.type()) {
            case IDENT, OPEN_PAREN, INT_CONSTANT, FLOAT_CONSTANT, STRING_CONSTANT, NULL -> {
                return parseFactor();
            }
            case PLUS, MINUS -> {
                TokenEnum operator = lookahead.type();
                consume();
                ExpressionNode factor = parseFactor();
                return new UnaryOpNode(factor, operator.name());
            }
            default -> {
                throw new SyntaxException("Unexpected token in unary expression: " + lookahead.type(), lookahead);
            }
        }
    }

    private ExpressionNode parseFactor() {
        switch (lookahead.type()) {
            case INT_CONSTANT -> {
                String value = lookahead.value();
                consume();
                return new IntLiteralNode(value);
            }
            case FLOAT_CONSTANT -> {
                String value = lookahead.value();
                consume();
                return new FloatLiteralNode(value);
            }
            case STRING_CONSTANT -> {
                String value = lookahead.value();
                consume();
                return new StringLiteralNode(value);
            }
            case NULL -> {
                consume();
                return new NullNode();
            }
            case IDENT -> {
                return parseLValue();
            }
            case OPEN_PAREN -> {
                consume();
                ExpressionNode expr = parseNumExpression();
                match(TokenEnum.CLOSE_PAREN);
                return expr;
            }
            default -> {
                throw new SyntaxException("Unexpected token in factor: " + lookahead.type(), lookahead);
            }
        }
    }

    private VarNode parseLValue() {
        if (lookahead.type() != TokenEnum.IDENT) {
            throw new SyntaxException("Expected identifier for LValue", lookahead);
        }
        Token variableIdentifier = lookahead;
        consume();
        List<ExpressionNode> dimensions = new ArrayList<>();
        while (lookahead.type() == TokenEnum.OPEN_BRACKET) {
            consume();
            ExpressionNode index = parseNumExpression();
            dimensions.add(index);
            match(TokenEnum.CLOSE_BRACKET);
        }
        switch (lookahead.type()) {
            case CLOSE_PAREN, SEMICOLON, CLOSE_BRACKET, EQUAL, PLUS, MINUS, MULTIPLY, DIVIDE, MODULO,
                 LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL, EQUALS, NOT_EQUALS -> {
                return new VarNode(variableIdentifier, dimensions);

            }
            default -> throw new SyntaxException("Unexpected token after LValue: " + lookahead.type(), lookahead);
        }
    }

    private AssignableNode parseAtribStat1() {
        switch (lookahead.type()) {
            case NEW -> {
                return parseAllocExpression();
            } case IDENT -> {
                Token nextToken = tokens.get(index + 1);
                switch (nextToken.type()) {
                    case OPEN_PAREN -> {
                        return parseFuncCall();
                    }
                    case CLOSE_PAREN, SEMICOLON, OPEN_BRACKET, PLUS, MINUS, MULTIPLY, DIVIDE, MODULO,
                         LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL, EQUALS, NOT_EQUALS -> {
                        return parseExpression();
                    }
                    default -> throw new SyntaxException("Unexpected token after identifier in Atribution: " + nextToken.type(), nextToken);
                }
            }
            case OPEN_PAREN, INT_CONSTANT, PLUS, MINUS, FLOAT_CONSTANT, STRING_CONSTANT, NULL -> {
                return parseExpression();
            }
            default -> throw new SyntaxException("Unexpected token in assignment: " + lookahead.type(), lookahead);
        }
    }

}