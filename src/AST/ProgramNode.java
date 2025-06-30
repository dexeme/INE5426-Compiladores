package AST;

import java.util.List;

public class ProgramNode implements ASTNode {
    private final List<ASTNode> nodes;

    public ProgramNode(List<ASTNode> nodes) {
        this.nodes = nodes;
    }

    public List<ASTNode> getFunctions() {
        return nodes;
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent + "Program\n");
        for (ASTNode node : nodes) {
            sb.append(node.toTree(indent + "  ")).append("\n");
        }
        return sb.toString();
    }
}