package AST;

public class AssignmentNode implements StatementNode {
    private final String name;
    private final ExpressionNode expression;

    public AssignmentNode(String name, ExpressionNode expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() { return name; }
    public ExpressionNode getExpression() { return expression; }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Assignment ").append(name).append("\n");
        sb.append(expression.toTree(indent + "  "));
        return sb.toString();
    }
}