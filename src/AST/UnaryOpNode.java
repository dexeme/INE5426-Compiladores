package AST;

import Lexical.Token;

public class UnaryOpNode implements ExpressionNode {
    private final ExpressionNode expressionNode;
    private final Token operator;

    public UnaryOpNode(ExpressionNode expressionNode, Token operator) {
        this.expressionNode = expressionNode;
        this.operator = operator;
    }

    public ExpressionNode getExpressionNode() {
        return expressionNode;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        return indent + "UnaryOpNode " + operator.value() + "\n" + expressionNode.toTree(indent + "  ");
    }
}
