package AST;

import Semantics.ASTVisitor;

public interface ASTNode {
    <T> T accept(ASTVisitor<T> visitor);
    String toTree(String indent);
    default String toTree() { return toTree("" ); }
}