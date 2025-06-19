package AST;

/** A placeholder statement node for unimplemented syntax. */
public class DummyNode implements StatementNode {
    private final String name;
    public DummyNode(String name) { this.name = name; }
    @Override
    public String toTree(String indent) {
        return indent + name;
    }
}