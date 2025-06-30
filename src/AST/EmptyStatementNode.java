package AST;

import Semantics.ASTVisitor;

public class EmptyStatementNode implements StatementNode{

    public EmptyStatementNode() {
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    //TODO
    @Override
    public String toTree(String indent) {
        return indent + "EmptyStatement";
    }
}
