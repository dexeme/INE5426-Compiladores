package AST;

public class BinaryOpNode implements ExpressionNode {
    private final ExpressionNode left;
    private final String operator;
    private final ExpressionNode right;

    public BinaryOpNode(ExpressionNode left, String operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("BinaryOp ").append(operator).append("\n");
        sb.append(left.toTree(indent + "  ")).append("\n");
        sb.append(right.toTree(indent + "  "));
        return sb.toString();
    }
}