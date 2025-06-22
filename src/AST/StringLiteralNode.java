package AST;

public class StringLiteralNode implements ExpressionNode {
    private final String value;
    public StringLiteralNode(String value) { this.value = value; }

    public String getValue() { return value; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override
    public String toTree(String indent) {
        return indent + "StringLiteral " + value;
    }
}