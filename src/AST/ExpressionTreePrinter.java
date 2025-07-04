package AST;

import Semantics.GenericVisitor;

public class ExpressionTreePrinter {
    public static String collect(ASTNode root) {
        TreeVisitor visitor = new TreeVisitor();
        root.accept(visitor);
        return visitor.sb.toString();
    }

    private static class TreeVisitor extends GenericVisitor<Void> {
        private final StringBuilder sb = new StringBuilder();
        private int index = 1;

        private void appendExpr(ASTNode node) {
            sb.append("(").append(index++).append(")\n");
            sb.append(node.toTree()).append("\n\n");
        }

        private void visitExprIfArithmetic(ExpressionNode expr) {
            if (expr == null) return;
            if (expr instanceof VarNode var) {
                boolean printed = false;
                for (ExpressionNode d : var.getDimensions()) {
                    if (containsArithmetic(d)) {
                        appendExpr(d);
                        printed = true;
                    }
                }
                if (!printed && containsArithmetic(expr)) {
                    appendExpr(expr);
                }
            } else if (containsArithmetic(expr) || expr instanceof IntLiteralNode || expr instanceof FloatLiteralNode) {
                appendExpr(expr);
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
        public Void visit(ProgramNode node) {
            for (ASTNode fn : node.getFunctions()) {
                fn.accept(this);
            }
            return null;
        }

        @Override
        public Void visit(FunctionNode node) {
            for (StatementNode st : node.getBody()) {
                st.accept(this);
            }
            return null;
        }

        @Override
        public Void visit(VarDeclNode node) { return null; }

        @Override
        public Void visit(AssignmentNode node) {
            if (node.getRight() instanceof ExpressionNode expr) {
                if (expr instanceof IntLiteralNode || expr instanceof FloatLiteralNode) {
                    appendExpr(expr);
                } else {
                    visitExprIfArithmetic(expr);
                }
            }
            return null;
        }

        @Override
        public Void visit(BinaryOpNode node) {
            appendExpr(node);
            return null;
        }

        @Override
        public Void visit(VarNode node) { return null; }

        @Override
        public Void visit(IntLiteralNode node) {
            appendExpr(node);
            return null;
        }

        @Override
        public Void visit(StringLiteralNode node) { return null; }

        @Override
        public Void visit(FloatLiteralNode node) {
            appendExpr(node);
            return null;
        }

        @Override
        public Void visit(PrintNode node) {
            visitExprIfArithmetic(node.getExpression());
            return null;
        }

        @Override
        public Void visit(ReadNode node) { return null; }

        @Override
        public Void visit(ReturnNode node) { return null; }

        @Override
        public Void visit(IfNode node) {
            visitExprIfArithmetic(node.getCondition());
            for (StatementNode st : node.getThenBranch()) st.accept(this);
            if (node.getElseBranch() != null && !node.getElseBranch().isEmpty()) {
                for (StatementNode st : node.getElseBranch()) st.accept(this);
            }
            return null;
        }

        @Override
        public Void visit(ForNode node) {
            if (node.getInit() != null) node.getInit().accept(this);
            if (node.getCondition() != null) visitExprIfArithmetic(node.getCondition());
            if (node.getIncrement() != null) node.getIncrement().accept(this);
            if (node.getBody() != null) node.getBody().accept(this);
            return null;
        }

        @Override
        public Void visit(BreakNode node) { return null; }

        @Override
        public Void visit(DummyNode node) { return null; }

        @Override
        public Void visit(AllocExpressionNode node) {
            appendExpr(node);
            return null;
        }

        @Override
        public Void visit(BlockNode node) {
            for (StatementNode st : node.getNodes()) st.accept(this);
            return null;
        }

        @Override
        public Void visit(EmptyStatementNode node) { return null; }

        @Override
        public Void visit(FunctionCallNode node) {
            appendExpr(node);
            return null;
        }

        @Override
        public Void visit(NullNode node) { return null; }

        @Override
        public Void visit(UnaryOpNode node) {
            appendExpr(node);
            return null;
        }
    }
}