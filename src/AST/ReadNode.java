package AST;

public class ReadNode implements StatementNode {
    private final String name;

    public ReadNode(String name) { this.name = name; }

    @Override
    public String toTree(String indent) {
        return indent + "Read " + name;
    }
}