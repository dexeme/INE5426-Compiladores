package AST;

/** A placeholder statement node for unimplemented syntax. */
public class DummyNode implements StatementNode {
    private final String name;
    public DummyNode(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }
    @Override
    public String toTree(String indent) {
        return indent + name;
    }
}