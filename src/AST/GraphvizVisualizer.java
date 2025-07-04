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

    /** Generate and return the dot representation for the expression trees. */
    public static String generateDot(ASTNode root) {
        DotVisitor visitor = new DotVisitor();
        return visitor.generate(root);
    }

    private static class DotVisitor extends GenericVisitor<String> {
        private final StringBuilder sb = new StringBuilder();
        private final AtomicInteger counter = new AtomicInteger();

        String generate(ASTNode root) {
            sb.append("digraph AST {\n");
            root.accept(this);
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

        private void visitExprIfArithmetic(ExpressionNode expr) {
            if (expr == null) return;
            if (expr instanceof VarNode var) {
                boolean printed = false;
                for (ExpressionNode d : var.getDimensions()) {
                    if (containsArithmetic(d)) {
                        d.accept(this);
                        printed = true;
                    }
                }
                if (!printed && containsArithmetic(expr)) {
                    expr.accept(this);
                }
            } else if (containsArithmetic(expr)) {
                expr.accept(this);
            }
        }

        private boolean containsArithmetic(ExpressionNode node) {
            if (node instanceof BinaryOpNode || node instanceof UnaryOpNode) {
                return true;
            }
            if (node instanceof VarNode var) {
                for (ExpressionNode d : var.getDimensions()) {
                    if (containsArithmetic(d)) return true;
                }
            } else if (node instanceof AllocExpressionNode alloc) {
                for (ExpressionNode d : alloc.getDimensions()) {
                    if (containsArithmetic(d)) return true;
                }
            }
            return false;
        }

        @Override
        public String visit(ProgramNode node) {
            for (ASTNode fn : node.getFunctions()) {
                fn.accept(this);
            }
            return null;
        }

        @Override
        public String visit(FunctionNode node) {
            for (StatementNode st : node.getBody()) {
                st.accept(this);
            }
            return null;
        }

        @Override
        public String visit(VarDeclNode node) {
            return null;
        }

        @Override
        public String visit(AssignmentNode node) {
            if (node.getRight() instanceof ExpressionNode expr) {
                if (expr instanceof IntLiteralNode || expr instanceof FloatLiteralNode) {
                    expr.accept(this);
                } else {
                    visitExprIfArithmetic(expr);
                }
            }
            return null;
        }

        @Override
        public String visit(BinaryOpNode node) {
            String id = newNode("Op\n" + node.getOperator().value());
            String left = node.getLeft().accept(this);
            String right = node.getRight().accept(this);
            edge(id, left);
            edge(id, right);
            return id;
        }

        @Override
        public String visit(VarNode node) {
            String id = newNode("Var\n" + node.getVariableIdentifier().value());
            for (ExpressionNode dim : node.getDimensions()) {
                String did = dim.accept(this);
                edge(id, did);
            }
            return id;
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
            visitExprIfArithmetic(node.getExpression());
            return null;
        }

        @Override
        public String visit(ReadNode node) {
            return null;
        }

        @Override
        public String visit(ReturnNode node) {
            return null;
        }

        @Override
        public String visit(IfNode node) {
            visitExprIfArithmetic(node.getCondition());
            for (StatementNode st : node.getThenBranch()) {
                st.accept(this);
            }
            if (node.getElseBranch() != null && !node.getElseBranch().isEmpty()) {
                for (StatementNode st : node.getElseBranch()) {
                    st.accept(this);
                }
            }
            return null;
        }

        @Override
        public String visit(ForNode node) {
            if (node.getInit() != null) {
                node.getInit().accept(this);
            }
            if (node.getCondition() != null) {
                visitExprIfArithmetic(node.getCondition());
            }
            if (node.getIncrement() != null) {
                node.getIncrement().accept(this);
            }
            if (node.getBody() != null) {
                node.getBody().accept(this);
            }
            return null;
        }

        @Override
        public String visit(BreakNode node) {
            return null;
        }

        @Override
        public String visit(DummyNode node) {
            return null;
        }

        @Override
        public String visit(AllocExpressionNode node) {
            String id = newNode("Alloc\n" + node.getType());
            for (ExpressionNode dim : node.getDimensions()) {
                String d = dim.accept(this);
                edge(id, d);
            }
            return id;
        }

        @Override
        public String visit(BlockNode node) {
            for (StatementNode st : node.getNodes()) {
                st.accept(this);
            }
            return null;
        }

        @Override
        public String visit(EmptyStatementNode node) {
            return null;
        }

        @Override
        public String visit(FunctionCallNode node) {
            String id = newNode("Call\n" + node.getName());
            for (String p : node.getParameters()) {
                String pid = newNode("Arg\n" + p);
                edge(id, pid);
            }
            return id;
        }

        @Override
        public String visit(NullNode node) {
            return newNode("Null");
        }

        @Override
        public String visit(UnaryOpNode node) {
            String id = newNode("Unary\n" + node.getOperator().value());
            String expr = node.getExpressionNode().accept(this);
            edge(id, expr);
            return id;
        }
    }
}