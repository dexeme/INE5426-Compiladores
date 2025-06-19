package AST;

public class VarDeclNode implements StatementNode {
    private final String type;
    private final String name;

    public VarDeclNode(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toTree(String indent) {
        return indent + "VarDecl " + type + " " + name;
    }
}