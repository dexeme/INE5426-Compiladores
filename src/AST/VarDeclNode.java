package AST;

import java.util.List;

public class VarDeclNode implements StatementNode {
    private final String type;
    private final String name;
    private final List<Integer> dimensions;

    public VarDeclNode(String type, String name, List<Integer> dimensions) {
        this.type = type;
        this.name = name;
        this.dimensions = dimensions;
    }

    public String getType() { return type; }
    public String getName() { return name; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent + "VarDeclNode\n");
        sb.append(indent).append("  Type: ").append(type).append("\n");
        sb.append(indent).append("  Name: ").append(name);
        if (dimensions != null && !dimensions.isEmpty()) {
            sb.append("\n").append(indent).append("  Dimensions: ");
            for (int i = 0; i < dimensions.size(); i++) {
                sb.append(dimensions.get(i));
                if (i < dimensions.size() - 1) sb.append(", ");
            }
        }
        return sb.toString();
    }
}