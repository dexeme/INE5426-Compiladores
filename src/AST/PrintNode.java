package AST;

public class PrintNode implements StatementNode {
    private final ExpressionNode expression;

    public PrintNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public ExpressionNode getExpression() { return expression; }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Print\n");
        sb.append(expression.toTree(indent + "  "));
        return sb.toString();
    }
}