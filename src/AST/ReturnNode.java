package AST;

public class ReturnNode implements StatementNode {
    private final ExpressionNode expression;

    public ReturnNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Return");
        if (expression != null) {
            sb.append("\n").append(expression.toTree(indent + "  "));
        }
        return sb.toString();
    }
}