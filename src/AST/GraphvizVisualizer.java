package AST;

import Semantics.GenericVisitor;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/** Utility to visualize AST using Graphviz. */
public class GraphvizVisualizer {
    /** Generate a Graphviz dot representation and open it with the default viewer. */
    public static void show(ASTNode root) {
        try {
            DotVisitor visitor = new DotVisitor();
            String dot = visitor.generate(root);
            Path dotFile = Files.createTempFile("ast", ".dot");
            Files.writeString(dotFile, dot);
            Path pngFile = Files.createTempFile("ast", ".png");
            new ProcessBuilder("dot", "-Tpng", dotFile.toString(), "-o", pngFile.toString())
                    .inheritIO()
                    .start()
                    .waitFor();
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pngFile.toFile());
            } else {
                System.out.println("AST image saved at: " + pngFile);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to render AST: " + e.getMessage());
        }
    }

    private static class DotVisitor extends GenericVisitor<String> {
        private final StringBuilder sb = new StringBuilder();
        private final AtomicInteger counter = new AtomicInteger();

        String generate(ASTNode root) {
            sb.append("digraph AST {\n");
            String id = root.accept(this);
            sb.append("}\n");
            return sb.toString();
        }

        private String newNode(String label) {
            String id = "n" + counter.getAndIncrement();
            sb.append("  ").append(id).append(" [label=\"").append(label.replace("\"", "\\\"")).append("\"];\n");
            return id;
        }

        private void edge(String from, String to) {
            sb.append("  ").append(from).append(" -> ").append(to).append(";\n");
        }

        @Override
        public String visit(ProgramNode node) {
            String id = newNode("Program");
            for (FunctionNode fn : node.getFunctions()) {
                String cid = fn.accept(this);
                edge(id, cid);
            }
            return id;
        }

        @Override
        public String visit(FunctionNode node) {
            String id = newNode("Function\n" + node.getName());
            for (Parameter p : node.getParameters()) {
                String pid = newNode("Param\n" + p.type() + " " + p.name());
                edge(id, pid);
            }
            for (StatementNode st : node.getBody()) {
                String sid = st.accept(this);
                edge(id, sid);
            }
            return id;
        }

        @Override
        public String visit(VarDeclNode node) {
            return newNode("VarDecl\n" + node.getType() + " " + node.getName());
        }

        @Override
        public String visit(AssignmentNode node) {
            String id = newNode("Assign\n" + node.getName());
            if (node.getExpression() != null) {
                String expr = node.getExpression().accept(this);
                edge(id, expr);
            }
            return id;
        }

        @Override
        public String visit(BinaryOpNode node) {
            String id = newNode("Op\n" + node.getOperator());
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            edge(id, left);
            edge(id, right);
            return id;
        }

        @Override
        public String visit(VarNode node) {
            return newNode("Var\n" + node.getName());
        }

        @Override
        public String visit(IntLiteralNode node) {
            return newNode("Int\n" + node.getValue());
        }

        @Override
        public String visit(StringLiteralNode node) {
            return newNode("String\n" + node.getValue());
        }

        @Override
        public String visit(FloatLiteralNode node) {
            return newNode("Float\n" + node.getValue());
        }

        @Override
        public String visit(PrintNode node) {
            String id = newNode("Print");
            String expr = node.getExpression().accept(this);
            edge(id, expr);
            return id;
        }

        @Override
        public String visit(ReadNode node) {
            return newNode("Read\n" + node.getName());
        }

        @Override
        public String visit(ReturnNode node) {
            String id = newNode("Return");
            if (node.getExpression() != null) {
                String expr = node.getExpression().accept(this);
                edge(id, expr);
            }
            return id;
        }

        @Override
        public String visit(IfNode node) {
            String id = newNode("If");
            String cond = node.getCondition().accept(this);
            edge(id, cond);
            String thenId = newNode("Then");
            edge(id, thenId);
            for (StatementNode st : node.getThenBranch()) {
                String stId = st.accept(this);
                edge(thenId, stId);
            }
            if (node.getElseBranch() != null && !node.getElseBranch().isEmpty()) {
                String elseId = newNode("Else");
                edge(id, elseId);
                for (StatementNode st : node.getElseBranch()) {
                    String stId = st.accept(this);
                    edge(elseId, stId);
                }
            }
            return id;
        }

        @Override
        public String visit(ForNode node) {
            String id = newNode("For");
            if (node.getInit() != null) {
                String init = node.getInit().accept(this);
                edge(id, init);
            }
            if (node.getCondition() != null) {
                String cond = node.getCondition().accept(this);
                edge(id, cond);
            }
            if (node.getIncrement() != null) {
                String incr = node.getIncrement().accept(this);
                edge(id, incr);
            }
            for (StatementNode st : node.getBody()) {
                String body = st.accept(this);
                edge(id, body);
            }
            return id;
        }

        @Override
        public String visit(BreakNode node) {
            return newNode("Break");
        }

        @Override
        public String visit(DummyNode node) {
            return newNode(node.getName());
        }
    }
}