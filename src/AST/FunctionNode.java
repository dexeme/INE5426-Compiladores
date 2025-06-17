package AST;

import java.util.List;

public class FunctionNode implements ASTNode {
    private final String name;
    private final List<String> parameters;
    private final List<StatementNode> body;

    public FunctionNode(String name, List<String> parameters, List<StatementNode> body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    public String getName() { return name; }
    public List<String> getParameters() { return parameters; }
    public List<StatementNode> getBody() { return body; }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Function ").append(name);
        if (!parameters.isEmpty()) {
            sb.append("(");
            for (int i = 0; i < parameters.size(); i++) {
                sb.append(parameters.get(i));
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