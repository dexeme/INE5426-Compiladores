package AST;

public class ReadNode implements StatementNode {
    private final VarNode variable;

    public ReadNode(VarNode variable) {
        this.variable = variable;
    }

    public VarNode getVariable() {
        return variable;
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        return indent + "ReadNode\n" + variable.toTree(indent + "  ");
    }
}