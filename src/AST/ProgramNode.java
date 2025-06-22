package AST;

import java.util.List;

public class ProgramNode implements ASTNode {
    private final List<FunctionNode> functions;

    public ProgramNode(List<FunctionNode> functions) {
        this.functions = functions;
    }

    public List<FunctionNode> getFunctions() {
        return functions;
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Program\n");
        for (FunctionNode fn : functions) {
            sb.append(fn.toTree(indent + "  ")).append("\n");
        }
        if (!functions.isEmpty()) sb.setLength(sb.length() - 1); // remove last newline
        return sb.toString();
    }
}