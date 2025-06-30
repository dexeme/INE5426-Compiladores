package AST;

import java.util.List;

public class ForNode implements StatementNode {
    private final StatementNode init;
    private final ExpressionNode condition;
    private final StatementNode increment;
    private final StatementNode body;

    public ForNode(StatementNode init, ExpressionNode condition, StatementNode increment, StatementNode body) {
        this.init = init;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }

    public StatementNode getInit() { return init; }
    public ExpressionNode getCondition() { return condition; }
    public StatementNode getIncrement() { return increment; }
    public StatementNode getBody() { return body; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("ForNode\n");
        sb.append(indent).append("  Init:\n").append(init.toTree(indent + "    "));
        sb.append(indent).append("  Condition:\n").append(condition.toTree(indent + "    "));
        sb.append(indent).append("  Increment:\n").append(increment.toTree(indent + "    "));
        sb.append(indent).append("  Body:\n").append(body.toTree(indent + "    "));
        return sb.toString();
    }
}