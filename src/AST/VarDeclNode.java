package AST;

public class VarDeclNode implements StatementNode {
    private final String type;
    private final String name;

    public VarDeclNode(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() { return type; }
    public String getName() { return name; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        return indent + "VarDecl " + type + " " + name;
    }
}