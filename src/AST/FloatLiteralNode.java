package AST;

public class FloatLiteralNode implements ExpressionNode {
    private final String value;
    public FloatLiteralNode(String value) { this.value = value; }

    public String getValue() { return value; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        return indent + "FloatLiteral " + value;
    }
}