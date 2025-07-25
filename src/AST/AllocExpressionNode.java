package AST;

import Semantics.ASTVisitor;

import java.util.List;

public class AllocExpressionNode implements AssignableNode {

    private final String type;
    private final List<ExpressionNode> dimensions;

    public AllocExpressionNode(String type, List<ExpressionNode> dimensions) {
        this.type = type;
        this.dimensions = dimensions;
    }

    public String getType() {
        return type;
    }

    public List<ExpressionNode> getDimensions() {
        return dimensions;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("AllocExpression ").append(type);
        if (!dimensions.isEmpty()) {
            sb.append("\n");
            sb.append(indent).append("  ").append("Dimensions:");
            for (ExpressionNode dim : dimensions) {
                sb.append("\n");
                sb.append(dim.toTree(indent + "    "));
            }
        }
        return sb.toString();
    }

}
