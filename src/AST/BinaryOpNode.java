package AST;

import Lexical.Token;

public class BinaryOpNode implements ExpressionNode {
    private final ExpressionNode left;
    private final Token operator;
    private final ExpressionNode right;

    public BinaryOpNode(ExpressionNode left, Token operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ExpressionNode getLeft() { return left; }
    public Token getOperator() { return operator; }
    public ExpressionNode getRight() { return right; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("BinaryOp ").append(operator.value()).append("\n");
        sb.append(left.toTree(indent + "  ")).append("\n");
        sb.append(right.toTree(indent + "  "));
        return sb.toString();
    }
}