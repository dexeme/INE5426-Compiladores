package AST;


import java.util.List;

public class BlockNode implements StatementNode {

    private final List<StatementNode> nodes;

    public BlockNode(List<StatementNode> nodes) {
        this.nodes = nodes;
    }

    public List<StatementNode> getNodes() {
        return nodes;
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }


    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Block\n");
        for (StatementNode st : nodes) {
            sb.append(st.toTree(indent + "  ")).append("\n");
        }
        if (!nodes.isEmpty()) sb.setLength(sb.length() - 1); // Remove last newline
        return sb.toString();
    }
}
