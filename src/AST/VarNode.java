package AST;

import Lexical.Token;

import java.util.List;

public class VarNode implements ExpressionNode {
    private final Token variableIdentifier;
    private final List<ExpressionNode> dimensions;

    public VarNode(Token variableIdentifier, List<ExpressionNode> dimensions) {
        this.variableIdentifier = variableIdentifier;
        this.dimensions = dimensions;
    }

    public Token getVariableIdentifier() {
        return variableIdentifier;
    }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent + "VarNode " + variableIdentifier.value());
        if (!dimensions.isEmpty()) {
            sb.append("\n").append(indent).append("  Dimensions:");
            for (ExpressionNode dim : dimensions) {
                sb.append("\n");
                sb.append(dim.toTree(indent + "    "));
            }
        }
        return sb.toString();
    }
}