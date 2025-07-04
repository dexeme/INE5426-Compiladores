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
    private final Stack<String> loopEndLabels = new Stack<>();

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

    private int getTypeSize(String type) {
        switch (type.toLowerCase()) {
            case "int", "float":
                return 4;
            case "string":
                return 8;
            default:
                return 0;
        }
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
    public String visit(VarDeclNode node) {
        return null;
    }

    @Override
    public String visit(AssignmentNode node) {
        String rightResult = node.getRight().accept(this);
        String leftResult = node.getLeft().accept(this);
        instructions.add(leftResult + " = " + rightResult);
        return null;
    }

    @Override
    public String visit(PrintNode node) {
        String exprResult = node.getExpression().accept(this);
        instructions.add("PRINT " + exprResult);
        return null;
    }

    @Override
    public String visit(ReadNode node) {
        String var = node.getVariable().accept(this);
        instructions.add("READ " + var);
        return null;
    }

    @Override
    public String visit(ReturnNode node) {
        instructions.add("RETURN");
        return null;
    }

    @Override
    public String visit(IfNode node) {
        String conditionResult = node.getCondition().accept(this);
        String elseLabel = newLabel();
        String endIfLabel = newLabel();

        instructions.add("IF FALSE " + conditionResult + " GOTO " + elseLabel);
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

        instructions.add("IF FALSE " + conditionResult + " GOTO " + loopEndLabel);
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
    public String visit(BinaryOpNode node) {
        String leftResult = node.getLeft().accept(this);
        String rightResult = node.getRight().accept(this);
        String temp = newTemp();
        instructions.add(temp + " = " + leftResult + " " + node.getOperator() + " " + rightResult);
        return temp;
    }

    @Override
    public String visit(VarNode node) {
        if (node.getDimensions().isEmpty()) {
            return node.getVariableIdentifier().value();
        }

        String baseAddress = node.getVariableIdentifier().value();
        List<String> dimResults = new ArrayList<>();

        for (ExpressionNode dim : node.getDimensions()) {
            dimResults.add(dim.accept(this));
        }

        StringBuilder accessIndex = new StringBuilder();
        for (int i = 0; i < dimResults.size(); i++) {
            if (i > 0) accessIndex.append("][");
            accessIndex.append(dimResults.get(i));
        }

        return baseAddress + "[" + accessIndex + "]";
    }

    @Override
    public String visit(AllocExpressionNode node) {
        String type = node.getType();
        int typeSize = getTypeSize(type);

        List<String> dimResults = new ArrayList<>();
        for (ExpressionNode dim : node.getDimensions()) {
            dimResults.add(dim.accept(this));
        }

        String temp = newTemp();
        String totalSizeExpr = String.join(" * ", dimResults);
        String sizeTemp = newTemp();
        instructions.add(sizeTemp + " = " + totalSizeExpr);
        String bytesTemp = newTemp();
        instructions.add(bytesTemp + " = " + sizeTemp + " * " + typeSize);
        instructions.add(temp + " = ALLOC " + bytesTemp);
        return temp;
    }

    @Override
    public String visit(UnaryOpNode node) {
        String exprResult = node.getExpressionNode().accept(this);
        String temp = newTemp();
        instructions.add(temp + " = " + node.getOperator() + " " + exprResult);
        return temp;
    }

    @Override
    public String visit(FunctionCallNode node) {
        for (String param : node.getParameters()) {
            instructions.add("PARAM " + param);
        }
        String temp = newTemp();
        instructions.add(temp + " = CALL " + node.getName() + ", " + node.getParameters().size());
        return temp;
    }

    @Override
    public String visit(BlockNode node) {
        for(StatementNode stmt : node.getNodes()) {
            stmt.accept(this);
        }
        return null;
    }

    @Override
    public String visit(EmptyStatementNode node) {
        return null;
    }

    @Override
    public String visit(NullNode node) {
        return "null";
    }

    @Override
    public String visit(IntLiteralNode node) {
        return node.getValue();
    }

    @Override
    public String visit(StringLiteralNode node) {
        return node.getValue();
    }

    @Override
    public String visit(FloatLiteralNode node) {
        return node.getValue();
    }

    @Override
    public String visit(DummyNode node) {
        return null;
    }
}
