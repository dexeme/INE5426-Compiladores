package AST;

public class AssignmentNode implements StatementNode {
    private final VarNode left;
    private final AssignableNode right;

    public AssignmentNode(VarNode left, AssignableNode right) {
        this.left = left;
        this.right = right;
    }

    public VarNode getLeft() { return left; }

    public AssignableNode getRight() { return right; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Assignment\n");
        sb.append(left.toTree(indent + "  ")).append("\n");
        sb.append(right.toTree(indent + "  "));
        return sb.toString();
    }
}