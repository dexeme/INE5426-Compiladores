package CodeGeneration;

import AST.*;
import Semantics.GenericVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** Visitor responsible for generating Intermediate Code from the given Abstract Syntax Tree (AST). */
public class IntermediateCodeGenerator extends GenericVisitor<String> {
    private final List<String> instructions = new ArrayList<>();

    private int tempCounter = 0;
    private int labelCounter = 0;

    private Stack<String> loopEndLabels = new Stack<>();

    public IntermediateCodeGenerator() {
        super(null);
    }

    public List<String> generate(ProgramNode program) {
        instructions.clear();
        tempCounter = 0;
        labelCounter = 0;
        loopEndLabels.clear();

        program.accept(this);

        return instructions;
    }

    private String newTemp() {
        return "t" + (tempCounter++);
    }

    private String newLabel() {
        return "L" + (labelCounter++);
    }

    private boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e1) {
            try {
                Float.parseFloat(s);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }

    private boolean isStringLiteral(String s) {
        return s != null && s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2;
    }

    private boolean isLiteral(String s) {
        return isNumeric(s) || isStringLiteral(s);
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
        instructions.add("FUNC " + node.getFunctionIdentifier().value() + ":");

        for (StatementNode st : node.getBody()) {
            st.accept(this);
        }
        return null;
    }

    @Override
    public String visit(PrintNode node) {
        String exprResult = node.getExpression().accept(this);
        instructions.add("PRINT " + exprResult);
        return null;
    }

    @Override
    public String visit(StringLiteralNode node) {
        return "\"" + node.getValue() + "\"";
    }

    @Override
    public String visit(VarDeclNode node) {
        return null;
    }

    @Override
    public String visit(AssignmentNode node) {
        String exprResult = node.getRight().accept(this);

        instructions.add(node.getLeft().getVariableIdentifier().value() + " = " + exprResult);
        return null;
    }

    @Override
    public String visit(VarNode node) {
        return node.getVariableIdentifier().value();
    }

    @Override
    public String visit(IntLiteralNode node) {
        return node.getValue();
    }

    @Override
    public String visit(FloatLiteralNode node) {
        return node.getValue();
    }

    @Override
    public String visit(BinaryOpNode node) {
        String leftResult = node.getLeft().accept(this);
        String rightResult = node.getRight().accept(this);

        if (isLiteral(leftResult)) {
            String tempLeft = newTemp();
            instructions.add(tempLeft + " = " + leftResult);
            leftResult = tempLeft;
        }

        if (isLiteral(rightResult)) {
            String tempRight = newTemp();
            instructions.add(tempRight + " = " + rightResult);
            rightResult = tempRight;
        }

        String temp = newTemp();
        instructions.add(temp + " = " + leftResult + " " + node.getOperator() + " " + rightResult);

        return temp;
    }

    @Override
    public String visit(ReadNode node) {
        instructions.add("READ " + node.getVariable().getVariableIdentifier().value());
        return null;
    }

    @Override
    public String visit(IfNode node) {
        String conditionResult = node.getCondition().accept(this);

        if (isLiteral(conditionResult)) {
            String tempLiteral = newTemp();
            instructions.add(tempLiteral + " = " + conditionResult);
            conditionResult = tempLiteral;
        }

        String elseLabel = newLabel();
        String endIfLabel = newLabel();

        instructions.add("IF_FALSE " + conditionResult + " GOTO " + elseLabel);
        for (StatementNode st : node.getThenBranch()) {
            st.accept(this);
        }
        instructions.add("GOTO " + endIfLabel);

        instructions.add(elseLabel + ":");
        if (node.getElseBranch() != null && !node.getElseBranch().isEmpty()) {
            for (StatementNode st : node.getElseBranch()) {
                st.accept(this);
            }
        }
        instructions.add(endIfLabel + ":");
        return null;
    }

    @Override
    public String visit(ForNode node) {
        String loopCondLabel = newLabel();
        String loopEndLabel = newLabel();

        loopEndLabels.push(loopEndLabel);

        if (node.getInit() != null) {
            node.getInit().accept(this);
        }

        instructions.add(loopCondLabel + ":");

        String conditionResult = node.getCondition().accept(this);

        if (isLiteral(conditionResult)) {
            String tempLiteral = newTemp();
            instructions.add(tempLiteral + " = " + conditionResult);
            conditionResult = tempLiteral;
        }

        instructions.add("IF_FALSE " + conditionResult + " GOTO " + loopEndLabel);

        if (node.getBody() != null) {
            node.getBody().accept(this);
        }

        if (node.getIncrement() != null) {
            node.getIncrement().accept(this);
        }

        instructions.add("GOTO " + loopCondLabel);

        instructions.add(loopEndLabel + ":");
        loopEndLabels.pop();
        return null;
    }

    @Override
    public String visit(BreakNode node) {
        if (!loopEndLabels.isEmpty()) {
            instructions.add("GOTO " + loopEndLabels.peek());
        }
        return null;
    }

    @Override
    public String visit(ReturnNode node) {
        instructions.add("RETURN");
        return null;
    }
}
