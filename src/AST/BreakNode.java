package AST;

public class BreakNode implements StatementNode {
    @Override
    public String toTree(String indent) {
        return indent + "Break";
    }
}