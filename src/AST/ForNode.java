package AST;

import java.util.List;

public class ForNode implements StatementNode {
    private final StatementNode init;
    private final ExpressionNode condition;
    private final StatementNode increment;
    private final List<StatementNode> body;

    public ForNode(StatementNode init, ExpressionNode condition, StatementNode increment, List<StatementNode> body) {
        this.init = init;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("For\n");
        sb.append(init.toTree(indent + "  ")).append("\n");
        sb.append(condition.toTree(indent + "  ")).append("\n");
        sb.append(increment.toTree(indent + "  ")).append("\n");
        for (int i = 0; i < body.size(); i++) {
            sb.append(body.get(i).toTree(indent + "  "));
            if (i < body.size() - 1) sb.append("\n");
        }
        return sb.toString();
    }
}