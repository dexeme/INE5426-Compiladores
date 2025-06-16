package AST;

public interface ASTNode {
    String toTree(String indent);
    default String toTree() { return toTree("" ); }
}