package AST;

import java.util.List;

public class FunctionNode implements ASTNode {
    private final String name;
    private final List<StatementNode> body;

    public FunctionNode(String name, List<StatementNode> body) {
        this.name = name;
        this.body = body;
    }

    public String getName() { return name; }
    public List<StatementNode> getBody() { return body; }

    @Override
    public String toTree(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("Function ").append(name).append("\n");
        for (StatementNode st : body) {
            sb.append(st.toTree(indent + "  ")).append("\n");
        }
        if (!body.isEmpty()) sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}