package AST;

import Lexical.Token;

import java.util.List;

public class FunctionNode implements ASTNode {
    private final Token functionIdentifier;
    private final List<Parameter> parameters;
    private final List<StatementNode> body;

    public FunctionNode(Token functionIdentifier, List<Parameter> parameters, List<StatementNode> body) {
        this.functionIdentifier = functionIdentifier;
        this.parameters = parameters;
        this.body = body;
    }

    public Token getFunctionIdentifier() { return functionIdentifier; }
    public List<Parameter> getParameters() { return parameters; }
    public List<StatementNode> getBody() { return body; }

    @Override
    public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Function ").append(functionIdentifier.value());
        if (!parameters.isEmpty()) {
            sb.append("(");
            for (int i = 0; i < parameters.size(); i++) {
                Parameter p = parameters.get(i);
                sb.append(p.type()).append(" ").append(p.name());
                if (i < parameters.size() - 1) sb.append(", ");
            }
            sb.append(")");
        }
        sb.append("\n");
        for (StatementNode st : body) {
            sb.append(st.toTree(indent + "  ")).append("\n");
        }
        if (!body.isEmpty()) sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}