package AST;

import Semantics.ASTVisitor;

public class UnaryOpNode implements ExpressionNode {
    private final ExpressionNode expressionNode;
    private final String operator;

    public UnaryOpNode(ExpressionNode expressionNode, String operator) {
        this.expressionNode = expressionNode;
        this.operator = operator;
    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        return indent + "UnaryOpNode " + operator + "\n" + expressionNode.toTree(indent + "  ");
    }
}
