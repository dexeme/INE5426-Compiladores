package AST;

public class VarNode implements ExpressionNode {
    private final String name;
    public VarNode(String name) { this.name = name; }
    @Override
    public String toTree(String indent) {
        return indent + "Var " + name;
    }
}