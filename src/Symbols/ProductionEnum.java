package Symbols;

import AL.TokenEnum;

import java.util.List;

public enum ProductionEnum {
    PROGRAM_P1(
            NonterminalEnum.PROGRAM,
            List.of(NonterminalEnum.STATEMENT)
    ),

    PROGRAM_P2(
            NonterminalEnum.PROGRAM,
            List.of(NonterminalEnum.FUNCLIST)
    ),

    PROGRAM_P3(
            NonterminalEnum.PROGRAM,
            List.of()
    ),

    FUNCLIST_P1(
            NonterminalEnum.FUNCLIST,
            List.of(NonterminalEnum.FUNCDEF, NonterminalEnum.FUNCLIST1)
    ),

    FUNCDEF_P1(
            NonterminalEnum.FUNCDEF,
            List.of(TokenEnum.DEF, TokenEnum.IDENT, TokenEnum.OPEN_PAREN, NonterminalEnum.PARAMLIST, TokenEnum.CLOSE_PAREN, TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE)
    ),

    PARAMLIST_P1(
            NonterminalEnum.PARAMLIST,
            List.of(TokenEnum.STRING, TokenEnum.IDENT, NonterminalEnum.PARAMLIST3)
    ),

    PARAMLIST_P2(
            NonterminalEnum.PARAMLIST,
            List.of(TokenEnum.FLOAT, TokenEnum.IDENT, NonterminalEnum.PARAMLIST2)
    ),

    PARAMLIST_P3(
            NonterminalEnum.PARAMLIST,
            List.of(TokenEnum.INT, TokenEnum.IDENT, NonterminalEnum.PARAMLIST1)
    ),

    PARAMLIST_P4(
            NonterminalEnum.PARAMLIST,
            List.of()
    ),

    STATEMENT_P1(
            NonterminalEnum.STATEMENT,
            List.of(NonterminalEnum.VARDECL, TokenEnum.SEMICOLON)
    ),

    STATEMENT_P2(
            NonterminalEnum.STATEMENT,
            List.of(NonterminalEnum.ATRIBSTAT, TokenEnum.SEMICOLON)
    ),

    STATEMENT_P3(
            NonterminalEnum.STATEMENT,
            List.of(NonterminalEnum.PRINTSTAT, TokenEnum.SEMICOLON)
    ),

    STATEMENT_P4(
            NonterminalEnum.STATEMENT,
            List.of(NonterminalEnum.READSTAT, TokenEnum.SEMICOLON)
    ),

    STATEMENT_P5(
            NonterminalEnum.STATEMENT,
            List.of(NonterminalEnum.RETURNSTAT, TokenEnum.SEMICOLON)
    ),

    STATEMENT_P6(
            NonterminalEnum.STATEMENT,
            List.of(NonterminalEnum.IFSTAT)
    ),

    STATEMENT_P7(
            NonterminalEnum.STATEMENT,
            List.of(NonterminalEnum.FORSTAT)
    ),

    STATEMENT_P8(
            NonterminalEnum.STATEMENT,
            List.of(TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE)
    ),

    STATEMENT_P9(
            NonterminalEnum.STATEMENT,
            List.of(TokenEnum.BREAK, TokenEnum.SEMICOLON)
    ),

    STATEMENT_P10(
            NonterminalEnum.STATEMENT,
            List.of(TokenEnum.SEMICOLON)
    ),

    VARDECL_P1(
            NonterminalEnum.VARDECL,
            List.of(TokenEnum.INT, TokenEnum.IDENT, NonterminalEnum.VARDIM)
    ),

    VARDECL_P2(
            NonterminalEnum.VARDECL,
            List.of(TokenEnum.FLOAT, TokenEnum.IDENT, NonterminalEnum.VARDIM)
    ),

    VARDECL_P3(
            NonterminalEnum.VARDECL,
            List.of(TokenEnum.STRING, TokenEnum.IDENT, NonterminalEnum.VARDIM)
    ),

    VARDIM_P1(
            NonterminalEnum.VARDIM,
            List.of(TokenEnum.OPEN_BRACKET, TokenEnum.INT_CONSTANT, TokenEnum.CLOSE_BRACKET, NonterminalEnum.VARDIM)
    ),

    VARDIM_P2(
            NonterminalEnum.VARDIM,
            List.of()
    ),

    ATRIBSTAT_P1(
            NonterminalEnum.ATRIBSTAT,
            List.of(NonterminalEnum.LVALUE, TokenEnum.EQUAL, NonterminalEnum.ATRIBSTAT1)
    ),

    FUNCCALL_P1(
            NonterminalEnum.FUNCCALL,
            List.of(TokenEnum.IDENT, TokenEnum.OPEN_PAREN, NonterminalEnum.PARAMLISTCALL, TokenEnum.CLOSE_PAREN)
    ),

    PARAMLISTCALL_P1(
            NonterminalEnum.PARAMLISTCALL,
            List.of(TokenEnum.IDENT, NonterminalEnum.PARAMLISTCALL1)
    ),

    PARAMLISTCALL_P2(
            NonterminalEnum.PARAMLISTCALL,
            List.of()
    ),

    PRINTSTAT_P1(
            NonterminalEnum.PRINTSTAT,
            List.of(TokenEnum.PRINT, NonterminalEnum.EXPRESSION)
    ),

    READSTAT_P1(
            NonterminalEnum.READSTAT,
            List.of(TokenEnum.READ, NonterminalEnum.LVALUE)
    ),

    RETURNSTAT_P1(
            NonterminalEnum.RETURNSTAT,
            List.of(TokenEnum.RETURN)
    ),

    IFSTAT_P1(
            NonterminalEnum.IFSTAT,
            List.of(TokenEnum.IF, TokenEnum.OPEN_PAREN, NonterminalEnum.EXPRESSION, TokenEnum.CLOSE_PAREN, TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.IFSTAT1)
    ),

    FORSTAT_P1(
            NonterminalEnum.FORSTAT,
            List.of(TokenEnum.FOR, TokenEnum.OPEN_PAREN, NonterminalEnum.ATRIBSTAT, TokenEnum.SEMICOLON, NonterminalEnum.EXPRESSION, TokenEnum.SEMICOLON, NonterminalEnum.ATRIBSTAT, TokenEnum.CLOSE_PAREN, NonterminalEnum.STATEMENT)
    ),

    STATELIST_P1(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.INT, TokenEnum.IDENT, NonterminalEnum.VARDIM, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P2(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.FLOAT, TokenEnum.IDENT, NonterminalEnum.VARDIM, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P3(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.STRING, TokenEnum.IDENT, NonterminalEnum.VARDIM, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P4(
            NonterminalEnum.STATELIST,
            List.of(NonterminalEnum.LVALUE, TokenEnum.EQUAL, NonterminalEnum.ATRIBSTAT1, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P5(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.PRINT, NonterminalEnum.EXPRESSION, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P6(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.READ, NonterminalEnum.LVALUE, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P7(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.RETURN, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P8(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.IF, TokenEnum.OPEN_PAREN, NonterminalEnum.EXPRESSION, TokenEnum.CLOSE_PAREN, TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.IFSTAT1, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P9(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.FOR, TokenEnum.OPEN_PAREN, NonterminalEnum.ATRIBSTAT, TokenEnum.SEMICOLON, NonterminalEnum.EXPRESSION, TokenEnum.SEMICOLON, NonterminalEnum.ATRIBSTAT, TokenEnum.CLOSE_PAREN, NonterminalEnum.STATEMENT, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P10(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P11(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.BREAK, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST_P12(
            NonterminalEnum.STATELIST,
            List.of(TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    ALLOCEXPRESSION_P1(
            NonterminalEnum.ALLOCEXPRESSION,
            List.of(TokenEnum.NEW, NonterminalEnum.ALLOCEXPRESSION1)
    ),

    ALOCDIM_P1(
            NonterminalEnum.ALOCDIM,
            List.of(TokenEnum.OPEN_BRACKET, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_BRACKET, NonterminalEnum.ALOCDIM)
    ),

    ALOCDIM_P2(
            NonterminalEnum.ALOCDIM,
            List.of()
    ),

    EXPRESSION_P1(
            NonterminalEnum.EXPRESSION,
            List.of(NonterminalEnum.NUMEXPRESSION, NonterminalEnum.EXPRESSION1)
    ),

    NUMEXPRESSION_P1(
            NonterminalEnum.NUMEXPRESSION,
            List.of(NonterminalEnum.TERM, NonterminalEnum.NUMEXPRAUX)
    ),

    NUMEXPRAUX_P1(
            NonterminalEnum.NUMEXPRAUX,
            List.of(TokenEnum.PLUS, NonterminalEnum.TERM, NonterminalEnum.NUMEXPRAUX)
    ),

    NUMEXPRAUX_P2(
            NonterminalEnum.NUMEXPRAUX,
            List.of(TokenEnum.MINUS, NonterminalEnum.TERM, NonterminalEnum.NUMEXPRAUX)
    ),

    NUMEXPRAUX_P3(
            NonterminalEnum.NUMEXPRAUX,
            List.of()
    ),

    TERM_P1(
            NonterminalEnum.TERM,
            List.of(NonterminalEnum.UNARYEXPR, NonterminalEnum.TERMAUX)
    ),

    TERMAUX_P1(
            NonterminalEnum.TERMAUX,
            List.of(TokenEnum.MULTIPLY, NonterminalEnum.UNARYEXPR, NonterminalEnum.TERMAUX)
    ),

    TERMAUX_P2(
            NonterminalEnum.TERMAUX,
            List.of(TokenEnum.DIVIDE, NonterminalEnum.UNARYEXPR, NonterminalEnum.TERMAUX)
    ),

    TERMAUX_P3(
            NonterminalEnum.TERMAUX,
            List.of(TokenEnum.MODULO, NonterminalEnum.UNARYEXPR, NonterminalEnum.TERMAUX)
    ),

    TERMAUX_P4(
            NonterminalEnum.TERMAUX,
            List.of()
    ),

    UNARYEXPR_P1(
            NonterminalEnum.UNARYEXPR,
            List.of(NonterminalEnum.FACTOR)
    ),

    UNARYEXPR_P2(
            NonterminalEnum.UNARYEXPR,
            List.of(TokenEnum.PLUS, NonterminalEnum.FACTOR)
    ),

    UNARYEXPR_P3(
            NonterminalEnum.UNARYEXPR,
            List.of(TokenEnum.MINUS, NonterminalEnum.FACTOR)
    ),

    FACTOR_P1(
            NonterminalEnum.FACTOR,
            List.of(TokenEnum.INT_CONSTANT)
    ),

    FACTOR_P2(
            NonterminalEnum.FACTOR,
            List.of(TokenEnum.FLOAT_CONSTANT)
    ),

    FACTOR_P3(
            NonterminalEnum.FACTOR,
            List.of(TokenEnum.STRING_CONSTANT)
    ),

    FACTOR_P4(
            NonterminalEnum.FACTOR,
            List.of(TokenEnum.NULL)
    ),

    FACTOR_P5(
            NonterminalEnum.FACTOR,
            List.of(NonterminalEnum.LVALUE)
    ),

    FACTOR_P6(
            NonterminalEnum.FACTOR,
            List.of(TokenEnum.OPEN_PAREN, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_PAREN)
    ),

    LVALUE_P1(
            NonterminalEnum.LVALUE,
            List.of(TokenEnum.IDENT, NonterminalEnum.LVALUEDIM)
    ),

    LVALUEDIM_P1(
            NonterminalEnum.LVALUEDIM,
            List.of(TokenEnum.OPEN_BRACKET, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_BRACKET, NonterminalEnum.LVALUEDIM)
    ),

    LVALUEDIM_P2(
            NonterminalEnum.LVALUEDIM,
            List.of()
    ),

    FUNCLIST1_P1(
            NonterminalEnum.FUNCLIST1,
            List.of(TokenEnum.DEF, TokenEnum.IDENT, TokenEnum.OPEN_PAREN, NonterminalEnum.PARAMLIST, TokenEnum.CLOSE_PAREN, TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.FUNCLIST1)
    ),

    FUNCLIST1_P2(
            NonterminalEnum.FUNCLIST1,
            List.of()
    ),

    PARAMLIST1_P1(
            NonterminalEnum.PARAMLIST1,
            List.of(TokenEnum.COMMA, NonterminalEnum.PARAMLIST)
    ),

    PARAMLIST1_P2(
            NonterminalEnum.PARAMLIST1,
            List.of()
    ),

    PARAMLIST2_P1(
            NonterminalEnum.PARAMLIST2,
            List.of(TokenEnum.COMMA, NonterminalEnum.PARAMLIST)
    ),

    PARAMLIST2_P2(
            NonterminalEnum.PARAMLIST2,
            List.of()
    ),

    PARAMLIST3_P1(
            NonterminalEnum.PARAMLIST3,
            List.of(TokenEnum.COMMA, NonterminalEnum.PARAMLIST)
    ),

    PARAMLIST3_P2(
            NonterminalEnum.PARAMLIST3,
            List.of()
    ),

    ATRIBSTAT1_P1(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.IDENT, NonterminalEnum.ATRIBSTAT2)
    ),

    ATRIBSTAT1_P2(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.INT_CONSTANT, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT1_P3(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.FLOAT_CONSTANT, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT1_P4(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.STRING_CONSTANT, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT1_P5(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.NULL, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT1_P6(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.OPEN_PAREN, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_PAREN, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT1_P7(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.PLUS, NonterminalEnum.FACTOR, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT1_P8(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.MINUS, NonterminalEnum.FACTOR, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT1_P9(
            NonterminalEnum.ATRIBSTAT1,
            List.of(TokenEnum.NEW, NonterminalEnum.ALLOCEXPRESSION1)
    ),

    PARAMLISTCALL1_P1(
            NonterminalEnum.PARAMLISTCALL1,
            List.of(TokenEnum.COMMA, NonterminalEnum.PARAMLISTCALL)
    ),

    PARAMLISTCALL1_P2(
            NonterminalEnum.PARAMLISTCALL1,
            List.of()
    ),

    IFSTAT1_P1(
            NonterminalEnum.IFSTAT1,
            List.of(TokenEnum.ELSE, TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE)
    ),

    IFSTAT1_P2(
            NonterminalEnum.IFSTAT1,
            List.of()
    ),

    STATELIST1_P1(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.INT, TokenEnum.IDENT, NonterminalEnum.VARDIM, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P2(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.FLOAT, TokenEnum.IDENT, NonterminalEnum.VARDIM, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P3(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.STRING, TokenEnum.IDENT, NonterminalEnum.VARDIM, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P4(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.IDENT, NonterminalEnum.LVALUEDIM, TokenEnum.EQUAL, NonterminalEnum.ATRIBSTAT1, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P5(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.PRINT, NonterminalEnum.EXPRESSION, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P6(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.READ, NonterminalEnum.LVALUE, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P7(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.RETURN, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P8(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.IF, TokenEnum.OPEN_PAREN, NonterminalEnum.EXPRESSION, TokenEnum.CLOSE_PAREN, TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.IFSTAT1, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P9(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.FOR, TokenEnum.OPEN_PAREN, NonterminalEnum.ATRIBSTAT, TokenEnum.SEMICOLON, NonterminalEnum.EXPRESSION, TokenEnum.SEMICOLON, NonterminalEnum.ATRIBSTAT, TokenEnum.CLOSE_PAREN, NonterminalEnum.STATEMENT, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P10(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.OPEN_CURLY_BRACE, NonterminalEnum.STATELIST, TokenEnum.CLOSE_CURLY_BRACE, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P11(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.BREAK, TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P12(
            NonterminalEnum.STATELIST1,
            List.of(TokenEnum.SEMICOLON, NonterminalEnum.STATELIST1)
    ),

    STATELIST1_P13(
            NonterminalEnum.STATELIST1,
            List.of()
    ),

    ALLOCEXPRESSION1_P1(
            NonterminalEnum.ALLOCEXPRESSION1,
            List.of(TokenEnum.INT, TokenEnum.OPEN_BRACKET, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_BRACKET, NonterminalEnum.ALOCDIM)
    ),

    ALLOCEXPRESSION1_P2(
            NonterminalEnum.ALLOCEXPRESSION1,
            List.of(TokenEnum.FLOAT, TokenEnum.OPEN_BRACKET, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_BRACKET, NonterminalEnum.ALOCDIM)
    ),

    ALLOCEXPRESSION1_P3(
            NonterminalEnum.ALLOCEXPRESSION1,
            List.of(TokenEnum.STRING, TokenEnum.OPEN_BRACKET, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_BRACKET, NonterminalEnum.ALOCDIM)
    ),

    EXPRESSION1_P1(
            NonterminalEnum.EXPRESSION1,
            List.of()
    ),

    EXPRESSION1_P2(
            NonterminalEnum.EXPRESSION1,
            List.of(TokenEnum.LESS_THAN, NonterminalEnum.NUMEXPRESSION)
    ),

    EXPRESSION1_P3(
            NonterminalEnum.EXPRESSION1,
            List.of(TokenEnum.GREATER_THAN, NonterminalEnum.NUMEXPRESSION)
    ),

    EXPRESSION1_P4(
            NonterminalEnum.EXPRESSION1,
            List.of(TokenEnum.LESS_THAN_OR_EQUAL, NonterminalEnum.NUMEXPRESSION)
    ),

    EXPRESSION1_P5(
            NonterminalEnum.EXPRESSION1,
            List.of(TokenEnum.GREATER_THAN_OR_EQUAL, NonterminalEnum.NUMEXPRESSION)
    ),

    EXPRESSION1_P6(
            NonterminalEnum.EXPRESSION1,
            List.of(TokenEnum.EQUALS, NonterminalEnum.NUMEXPRESSION)
    ),

    EXPRESSION1_P7(
            NonterminalEnum.EXPRESSION1,
            List.of(TokenEnum.NOT_EQUALS, NonterminalEnum.NUMEXPRESSION)
            ),

    ATRIBSTAT2_P1(
            NonterminalEnum.ATRIBSTAT2,
            List.of(TokenEnum.OPEN_BRACKET, NonterminalEnum.NUMEXPRESSION, TokenEnum.CLOSE_BRACKET, NonterminalEnum.LVALUEDIM, NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT2_P2(
            NonterminalEnum.ATRIBSTAT2,
            List.of(NonterminalEnum.TERMAUX, NonterminalEnum.NUMEXPRAUX, NonterminalEnum.EXPRESSION1)
    ),

    ATRIBSTAT2_P3(
            NonterminalEnum.ATRIBSTAT2,
            List.of(TokenEnum.OPEN_PAREN, NonterminalEnum.PARAMLISTCALL, TokenEnum.CLOSE_PAREN)
    );

    private final NonterminalEnum head;
    private final List<Symboll> body;

    ProductionEnum(NonterminalEnum head, List<Symboll> body) {
        this.head = head;
        this.body = body;
    }

    public NonterminalEnum getHead() {
        return head;
    }

    public List<Symboll> getBody() {
        return body;
    }

}
