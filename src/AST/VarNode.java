package AST;

import java.util.List;

public class VarNode implements ExpressionNode {
    private final String name;
    private final List<ExpressionNode> dimensions;

    public VarNode(String name, List<ExpressionNode> dimensions) {
        this.name = name;
        this.dimensions = dimensions;
    }

    public String getName() { return name; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent + "VarNode " + name);
        if (!dimensions.isEmpty()) {
            sb.append("\n");
            for (ExpressionNode dim : dimensions) {
                sb.append(dim.toTree(indent + "  "));
            }
        }
        return sb.toString();
    }
}