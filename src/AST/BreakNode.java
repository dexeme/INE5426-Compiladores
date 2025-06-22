package AST;

public class BreakNode implements StatementNode {
    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override
    public String toTree(String indent) {
        return indent + "Break";
    }
}