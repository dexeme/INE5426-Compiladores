package AST;

public class NullNode implements ExpressionNode {

    public NullNode() {}

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        return indent + "NullNode";
    }
}
