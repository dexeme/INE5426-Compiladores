package AST;

public class IntLiteralNode implements ExpressionNode {
    private final String value;

    public IntLiteralNode(String value) { this.value = value; }

    @Override
    public String toTree(String indent) {
        return indent + "IntLiteral " + value;
    }
}