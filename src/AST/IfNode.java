package AST;

import java.util.List;

public class IfNode implements StatementNode {
    private final ExpressionNode condition;
    private final List<StatementNode> thenBranch;
    private final List<StatementNode> elseBranch;

    public IfNode(ExpressionNode condition, List<StatementNode> thenBranch, List<StatementNode> elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public ExpressionNode getCondition() { return condition; }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("If\n");
        sb.append(condition.toTree(indent + "  ")).append("\n");
        for (StatementNode st : thenBranch) {
            sb.append(st.toTree(indent + "  ")).append("\n");
        }
        if (elseBranch != null && !elseBranch.isEmpty()) {
            sb.append(indent).append("  Else\n");
            for (StatementNode st : elseBranch) {
                sb.append(st.toTree(indent + "    ")).append("\n");
            }
        }
        if (sb.charAt(sb.length()-1)=='\n') sb.setLength(sb.length()-1);
        return sb.toString();
    }
}