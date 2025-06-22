package AST;

public class VarNode implements ExpressionNode {
    private final String name;
    public VarNode(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override
    public String toTree(String indent) {
        return indent + "Var " + name;
    }
}