package AST;

public class ReturnNode implements StatementNode {

    public ReturnNode() {
    }


    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        return indent + "ReturnNode";
    }
}