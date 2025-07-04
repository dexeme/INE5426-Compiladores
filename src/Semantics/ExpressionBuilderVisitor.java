package Semantics;

import Lexical.Token;
import Lexical.TokenEnum;
import AST.*;
import Constants.Messages;
import Symbols.SymbolEntry;
import Symbols.ScopeTreeNode;

import java.util.ArrayList;
import java.util.List;

/** Visitor that performs semantic checks while traversing the AST. */
public class ExpressionBuilderVisitor extends GenericVisitor<Type> {
    protected ScopeTreeNode rootScope;
    protected ScopeTreeNode currentScope;
    protected final List<SemanticException> errors = new ArrayList<>();
    private int loopDepth = 0;

    public List<SemanticException> getErrors() {
        return new ArrayList<>(errors);
    }

    public void analyze(ProgramNode program) {
        errors.clear();
        rootScope = new ScopeTreeNode(null);
        currentScope = rootScope;
        program.accept(this);
    }

    public void printScopeTree() {
        printScopeTreeDFS(rootScope, 0);
    }

    private void printScopeTreeDFS(ScopeTreeNode node, int depth) {
        if (node.getTable().getHead() != null) {
            System.out.println("Scope level " + depth + ":");
            System.out.println(node.getTable());
        }
        for (ScopeTreeNode child : node.getChildren()) {
            printScopeTreeDFS(child, depth + 1);
        }
    }

    private Type fromString(String t) {
        return switch (t.toLowerCase()) {
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
        for (ASTNode fn : node.getFunctions()) {
            if (fn instanceof FunctionNode functionNode) {
                currentScope.getTable().add(functionNode.getFunctionIdentifier(), Type.VOID);
            }
            fn.accept(this);
        }
        return Type.VOID;
    }

    @Override
    public Type visit(FunctionNode node) {
        currentScope = currentScope.addChild();
        for (Parameter p : node.getParameters()) {
            Token tok = new Token(TokenEnum.IDENT, p.name(), 0, 0);
            currentScope.getTable().add(tok, fromString(p.type()));
        }
        for (StatementNode st : node.getBody()) st.accept(this);
        currentScope = currentScope.getParent();
        return Type.VOID;
    }

    @Override
    public Type visit(VarDeclNode node) {
        if (currentScope.getTable().existsInCurrentScope(node.getVariableIdentifier().value())) {
            errors.add(new SemanticException(Messages.ERROR_DUPLICATE_DECL, node.getVariableIdentifier()));
        } else {
            currentScope.getTable().add(node.getVariableIdentifier(), fromString(node.getType()));
        }
        return Type.VOID;
    }

    @Override
    public Type visit(AssignmentNode node) {
        SymbolEntry entry = lookupSymbol(node.getLeft().getVariableIdentifier().value());
        if (entry == null) {
            errors.add(new SemanticException(Messages.ERROR_UNDECLARED_VAR, node.getLeft().getVariableIdentifier()));
            node.getRight().accept(this); // still visit expression
            return Type.ERROR;
        }
        Type exprT = node.getRight().accept(this);
        if (entry.type() != null && exprT != Type.ERROR && entry.type() != exprT
                && !(node.getRight() instanceof FunctionCallNode)) {
            errors.add(new SemanticException(Messages.ERROR_TYPE_MISMATCH, node.getLeft().getVariableIdentifier()));
        }
        return entry.type();
    }

    private SymbolEntry lookupSymbol(String name) {
        ScopeTreeNode node = currentScope;
        while (node != null) {
            SymbolEntry entry = node.getTable().lookup(name);
            if (entry != null) return entry;
            node = node.getParent();
        }
        return null;
    }

    @Override
    public Type visit(BinaryOpNode node) {
        Type left = node.getLeft().accept(this);
        Type right = node.getRight().accept(this);
        if (left == Type.ERROR || right == Type.ERROR) return Type.ERROR;
        if (left != right) {
            errors.add(new SemanticException(Messages.ERROR_TYPE_MISMATCH, node.getOperator()));
            return Type.ERROR;
        }
        return left;
    }

    @Override
    public Type visit(VarNode node) {
        SymbolEntry entry = lookupSymbol(node.getVariableIdentifier().value());
        if (entry == null) {
            errors.add(new SemanticException(Messages.ERROR_UNDECLARED_VAR, node.getVariableIdentifier()));
            return Type.ERROR;
        }
        for(ExpressionNode dimExpr : node.getDimensions()) {
            dimExpr.accept(this);
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
    public Type visit(NullNode node) {
        return Type.VOID;
    }


    @Override
    public Type visit(ReadNode node) {
        SymbolEntry entry = lookupSymbol(node.getVariable().getVariableIdentifier().value());
        if (entry == null) {
            errors.add(new SemanticException(Messages.ERROR_UNDECLARED_VAR, node.getVariable().getVariableIdentifier()));
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
        currentScope = currentScope.addChild();
        for (StatementNode st : node.getThenBranch()) st.accept(this);
        currentScope = currentScope.getParent();
        if (node.getElseBranch() != null) {
            currentScope = currentScope.addChild();
            for (StatementNode st : node.getElseBranch()) st.accept(this);
            currentScope = currentScope.getParent();
        }
        return Type.VOID;
    }

    @Override
    public Type visit(ForNode node) {
        currentScope = currentScope.addChild();
        loopDepth++;
        if (node.getInit() != null) node.getInit().accept(this);
        if (node.getCondition() != null) node.getCondition().accept(this);
        if (node.getIncrement() != null) node.getIncrement().accept(this);
        if (node.getBody() != null) { node.getBody().accept(this); }
        loopDepth--;
        currentScope = currentScope.getParent();
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

    @Override
    public Type visit(BlockNode node) {
        currentScope = currentScope.addChild();
        for (StatementNode st : node.getNodes()) {
            st.accept(this);
        }
        currentScope = currentScope.getParent();
        return Type.VOID;
    }

    @Override
    public Type visit(FunctionCallNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(UnaryOpNode node) {
        return node.getExpressionNode().accept(this);
    }

    @Override
    public Type visit(AllocExpressionNode node) {
        for (ExpressionNode dim : node.getDimensions()) {
            dim.accept(this);
        }
        return fromString(node.getType());
    }
}