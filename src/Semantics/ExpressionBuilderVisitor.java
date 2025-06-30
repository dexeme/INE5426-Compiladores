package Semantics;

import Lexical.Token;
import Lexical.TokenEnum;
import AST.*;
import Constants.Messages;
import Symbols.SymbolEntry;
import Symbols.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/** Visitor that performs semantic checks while traversing the AST. */
public class ExpressionBuilderVisitor extends GenericVisitor<Type> {
    protected final SymbolTable symbols = new SymbolTable();
    protected final List<SemanticException> errors = new ArrayList<>();
    private int loopDepth = 0;

    public List<SemanticException> getErrors() {
        return new ArrayList<>(errors);
    }

    public void analyze(ProgramNode program) {
        errors.clear();
        symbols.clear();
        program.accept(this);
    }

    private Type fromString(String t) {
        return switch (t) {
            case "int" -> Type.INT;
            case "float" -> Type.FLOAT;
            case "string" -> Type.STRING;
            case "void" -> Type.VOID;
            default -> Type.UNKNOWN;
        };
    }

    // ----- Visitor methods -----
    @Override
    public Type visit(ProgramNode node) {
        for (ASTNode fn : node.getFunctions()) fn.accept(this);
        return Type.VOID;
    }

    @Override
    public Type visit(FunctionNode node) {
        symbols.enterScope();
        for (Parameter p : node.getParameters()) {
            Token tok = new Token(TokenEnum.IDENT, p.name(), 0, 0);
            symbols.add(tok, fromString(p.type()));
        }
        for (StatementNode st : node.getBody()) st.accept(this);
        symbols.exitScope();
        return Type.VOID;
    }

    @Override
    public Type visit(VarDeclNode node) {
        if (symbols.existsInCurrentScope(node.getName())) {
            Token tok = new Token(TokenEnum.IDENT, node.getName(), 0, 0);
            errors.add(new SemanticException(Messages.ERROR_DUPLICATE_DECL, tok));
        } else {
            Token tok = new Token(TokenEnum.IDENT, node.getName(), 0, 0);
            symbols.add(tok, fromString(node.getType()));
        }
        return Type.VOID;
    }

    @Override
    public Type visit(AssignmentNode node) {
        SymbolEntry entry = symbols.lookup(node.getLeft().getName());
        Token tok = new Token(TokenEnum.IDENT, node.getLeft().getName(), 0, 0);
        if (entry == null) {
            errors.add(new SemanticException(Messages.ERROR_UNDECLARED_VAR, tok));
            node.getRight().accept(this); // still visit expression
            return Type.ERROR;
        }
        Type exprT = node.getRight().accept(this);
        if (entry.type() != null && exprT != Type.ERROR && entry.type() != exprT) {
            errors.add(new SemanticException(Messages.ERROR_TYPE_MISMATCH, tok));
        }
        return entry.type();
    }

    @Override
    public Type visit(BinaryOpNode node) {
        Type left = node.getLeft().accept(this);
        Type right = node.getRight().accept(this);
        if (left == Type.ERROR || right == Type.ERROR) return Type.ERROR;
        if (left != right) {
            Token tok = new Token(TokenEnum.IDENT, node.getOperator(), 0, 0);
            errors.add(new SemanticException(Messages.ERROR_TYPE_MISMATCH, tok));
            return Type.ERROR;
        }
        return left;
    }

    @Override
    public Type visit(VarNode node) {
        SymbolEntry entry = symbols.lookup(node.getName());
        Token tok = new Token(TokenEnum.IDENT, node.getName(), 0, 0);
        if (entry == null) {
            errors.add(new SemanticException(Messages.ERROR_UNDECLARED_VAR, tok));
            return Type.ERROR;
        }
        return entry.type() == null ? Type.UNKNOWN : entry.type();
    }

    @Override
    public Type visit(IntLiteralNode node) {
        return Type.INT;
    }

    @Override
    public Type visit(StringLiteralNode node) {
        return Type.STRING;
    }

    @Override
    public Type visit(FloatLiteralNode node) {
        return Type.FLOAT;
    }

    @Override
    public Type visit(PrintNode node) {
        node.getExpression().accept(this);
        return Type.VOID;
    }

    @Override
    public Type visit(ReadNode node) {
        SymbolEntry entry = symbols.lookup(node.getVariable().getName());
        if (entry == null) {
            Token tok = new Token(TokenEnum.IDENT, node.getVariable().getName(), 0, 0);
            errors.add(new SemanticException(Messages.ERROR_UNDECLARED_VAR, tok));
        }
        return Type.VOID;
    }

    @Override
    public Type visit(ReturnNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(IfNode node) {
        node.getCondition().accept(this);
        symbols.enterScope();
        for (StatementNode st : node.getThenBranch()) st.accept(this);
        symbols.exitScope();
        if (node.getElseBranch() != null) {
            symbols.enterScope();
            for (StatementNode st : node.getElseBranch()) st.accept(this);
            symbols.exitScope();
        }
        return Type.VOID;
    }

    @Override
    public Type visit(ForNode node) {
        symbols.enterScope();
        loopDepth++;
        if (node.getInit() != null) node.getInit().accept(this);
        if (node.getCondition() != null) node.getCondition().accept(this);
        if (node.getIncrement() != null) node.getIncrement().accept(this);
        if (node.getBody() != null) { node.getBody().accept(this); }
        loopDepth--;
        symbols.exitScope();
        return Type.VOID;
    }

    @Override
    public Type visit(BreakNode node) {
        if (loopDepth == 0) {
            Token tok = new Token(TokenEnum.BREAK, "break", 0, 0);
            errors.add(new SemanticException(Messages.ERROR_BREAK_OUTSIDE, tok));
        }
        return Type.VOID;
    }

    @Override
    public Type visit(DummyNode node) {
        return Type.VOID;
    }
}