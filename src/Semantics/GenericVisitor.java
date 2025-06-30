package Semantics;

import AST.*;

/** Default visitor that performs a depth-first traversal of the AST. */
public class GenericVisitor<T> implements ASTVisitor<T> {
    protected T defaultValue;

    public GenericVisitor() {
        this.defaultValue = null;
    }

    public GenericVisitor(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T visit(ProgramNode node) {
        for (ASTNode fn : node.getFunctions()) fn.accept(this);
        return defaultValue;
    }

    @Override
    public T visit(FunctionNode node) {
        for (StatementNode st : node.getBody()) st.accept(this);
        return defaultValue;
    }

    @Override
    public T visit(VarDeclNode node) {
        return defaultValue;
    }

    @Override
    public T visit(AssignmentNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return defaultValue;
    }

    @Override
    public T visit(BinaryOpNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        return defaultValue;
    }

    @Override
    public T visit(VarNode node) {
        return defaultValue;
    }

    @Override
    public T visit(IntLiteralNode node) {
        return defaultValue;
    }

    @Override
    public T visit(StringLiteralNode node) {
        return defaultValue;
    }

    @Override
    public T visit(FloatLiteralNode node) {
        return defaultValue;
    }

    @Override
    public T visit(PrintNode node) {
        node.getExpression().accept(this);
        return defaultValue;
    }

    @Override
    public T visit(ReadNode node) {
        return defaultValue;
    }

    @Override
    public T visit(ReturnNode node) {
        return defaultValue;
    }

    @Override
    public T visit(IfNode node) {
        node.getCondition().accept(this);
        for (StatementNode st : node.getThenBranch()) st.accept(this);
        if (node.getElseBranch() != null) {
            for (StatementNode st : node.getElseBranch()) st.accept(this);
        }
        return defaultValue;
    }

    @Override
    public T visit(ForNode node) {
        if (node.getInit() != null) node.getInit().accept(this);
        if (node.getCondition() != null) node.getCondition().accept(this);
        if (node.getIncrement() != null) node.getIncrement().accept(this);
        if (node.getBody() != null) node.getBody().accept(this);
        return defaultValue;
    }

    @Override
    public T visit(BreakNode node) {
        return defaultValue;
    }

    @Override
    public T visit(DummyNode node) {
        return defaultValue;
    }

    @Override
    public T visit(AllocExpressionNode node) {
        for (ExpressionNode dim : node.getDimensions()) {
            dim.accept(this);
        }
        return defaultValue;
    }

    @Override
    public T visit(BlockNode node) {
        for (StatementNode st : node.getNodes()) {
            st.accept(this);
        }
        return defaultValue;
    }

    @Override
    public T visit(EmptyStatementNode node) {
        return defaultValue;
    }

    @Override
    public T visit(FunctionCallNode node) {
        return defaultValue;
    }

    @Override
    public T visit(NullNode node) {
        return defaultValue;
    }

    @Override
    public T visit(UnaryOpNode node) {
        node.getExpressionNode().accept(this);
        return defaultValue;
    }
}