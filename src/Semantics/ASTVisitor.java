package Semantics;

import AST.*;

public interface ASTVisitor<T> {
    T visit(ProgramNode node);
    T visit(FunctionNode node);
    T visit(VarDeclNode node);
    T visit(AssignmentNode node);
    T visit(BinaryOpNode node);
    T visit(VarNode node);
    T visit(IntLiteralNode node);
    T visit(StringLiteralNode node);
    T visit(PrintNode node);
    T visit(ReadNode node);
    T visit(ReturnNode node);
    T visit(IfNode node);
    T visit(ForNode node);
    T visit(BreakNode node);
    T visit(DummyNode node);
}