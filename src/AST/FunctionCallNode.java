package AST;

import java.util.List;

public class FunctionCallNode implements AssignableNode {
        private final String name;
        private final List<String> parameters;

        public FunctionCallNode(String name, List<String> parameters) {
            this.name = name;
            this.parameters = parameters;
        }

        public String getName() { return name; }
        public List<String> getParameters() { return parameters; }

        @Override
        public <T> T accept(Semantics.ASTVisitor<T> visitor) { return visitor.visit(this); }

        @Override
        public String toTree(String indent) {
            StringBuilder sb = new StringBuilder(indent + "FunctionCall " + name);
            if (!parameters.isEmpty()) {
                sb.append(" (");
                for (int i = 0; i < parameters.size(); i++) {
                    sb.append(parameters.get(i));
                    if (i < parameters.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(")");
            }
            return sb.toString();
        }
    }