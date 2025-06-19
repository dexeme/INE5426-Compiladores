package AST;

public class StringLiteralNode implements ExpressionNode {
    private final String value;
    public StringLiteralNode(String value) { this.value = value; }
    @Override
    public String toTree(String indent) {
        return indent + "StringLiteral " + value;
    }
}