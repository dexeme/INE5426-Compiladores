package Syntax;

import AL.TokenEnum;
import Symbols.NonterminalEnum;
import Symbols.ProductionEnum;

public class ParsingTable {

    public ProductionEnum getProduction(NonterminalEnum nonterminal, TokenEnum token) {
        switch (nonterminal) {
            case NonterminalEnum.PROGRAM:
                switch (token) {
                    case TokenEnum.DEF: return ProductionEnum.PROGRAM_P2;
                    case TokenEnum.IDENT: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.OPEN_CURLY_BRACE: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.STRING: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.FLOAT: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.INT: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.SEMICOLON: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.BREAK: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.PRINT: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.READ: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.RETURN: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.IF: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.FOR: return ProductionEnum.PROGRAM_P1;
                    case TokenEnum.END: return ProductionEnum.PROGRAM_P3;
                    default: return null;
                }
            case NonterminalEnum.FUNCLIST:
                switch (token) {
                    case TokenEnum.DEF: return ProductionEnum.FUNCLIST_P1;
                    default: return null;
                }
            case NonterminalEnum.FUNCDEF:
                switch (token) {
                    case TokenEnum.DEF: return ProductionEnum.FUNCDEF_P1;
                    default: return null;
                }
            case NonterminalEnum.PARAMLIST:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.PARAMLIST_P4;
                    case TokenEnum.STRING: return ProductionEnum.PARAMLIST_P1;
                    case TokenEnum.FLOAT: return ProductionEnum.PARAMLIST_P2;
                    case TokenEnum.INT: return ProductionEnum.PARAMLIST_P3;
                    default: return null;
                }
            case NonterminalEnum.STATEMENT:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.STATEMENT_P2;
                    case TokenEnum.OPEN_CURLY_BRACE: return ProductionEnum.STATEMENT_P8;
                    case TokenEnum.STRING: return ProductionEnum.STATEMENT_P1;
                    case TokenEnum.FLOAT: return ProductionEnum.STATEMENT_P1;
                    case TokenEnum.INT: return ProductionEnum.STATEMENT_P1;
                    case TokenEnum.SEMICOLON: return ProductionEnum.STATEMENT_P10;
                    case TokenEnum.BREAK: return ProductionEnum.STATEMENT_P9;
                    case TokenEnum.PRINT: return ProductionEnum.STATEMENT_P3;
                    case TokenEnum.READ: return ProductionEnum.STATEMENT_P4;
                    case TokenEnum.RETURN: return ProductionEnum.STATEMENT_P5;
                    case TokenEnum.IF: return ProductionEnum.STATEMENT_P6;
                    case TokenEnum.FOR: return ProductionEnum.STATEMENT_P7;
                    default: return null;
                }
            case NonterminalEnum.VARDECL:
                switch (token) {
                    case TokenEnum.STRING: return ProductionEnum.VARDECL_P3;
                    case TokenEnum.FLOAT: return ProductionEnum.VARDECL_P2;
                    case TokenEnum.INT: return ProductionEnum.VARDECL_P1;
                    default: return null;
                }
            case NonterminalEnum.VARDIM:
                switch (token) {
                    case TokenEnum.SEMICOLON: return ProductionEnum.VARDIM_P2;
                    case TokenEnum.OPEN_BRACKET: return ProductionEnum.VARDIM_P1;
                    default: return null;
                }
            case NonterminalEnum.ATRIBSTAT:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.ATRIBSTAT_P1;
                    default: return null;
                }
            case NonterminalEnum.FUNCCALL:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.FUNCCALL_P1;
                    default: return null;
                }
            case NonterminalEnum.PARAMLISTCALL:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.PARAMLISTCALL_P1;
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.PARAMLISTCALL_P2;
                    default: return null;
                }
            case NonterminalEnum.PRINTSTAT:
                switch (token) {
                    case TokenEnum.PRINT: return ProductionEnum.PRINTSTAT_P1;
                    default: return null;
                }
            case NonterminalEnum.READSTAT:
                switch (token) {
                    case TokenEnum.READ: return ProductionEnum.READSTAT_P1;
                    default: return null;
                }
            case NonterminalEnum.RETURNSTAT:
                switch (token) {
                    case TokenEnum.RETURN: return ProductionEnum.RETURNSTAT_P1;
                    default: return null;
                }
            case NonterminalEnum.IFSTAT:
                switch (token) {
                    case TokenEnum.IF: return ProductionEnum.IFSTAT_P1;
                    default: return null;
                }
            case NonterminalEnum.FORSTAT:
                switch (token) {
                    case TokenEnum.FOR: return ProductionEnum.FORSTAT_P1;
                    default: return null;
                }
            case NonterminalEnum.STATELIST:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.STATELIST_P4;
                    case TokenEnum.OPEN_CURLY_BRACE: return ProductionEnum.STATELIST_P10;
                    case TokenEnum.STRING: return ProductionEnum.STATELIST_P3;
                    case TokenEnum.FLOAT: return ProductionEnum.STATELIST_P2;
                    case TokenEnum.INT: return ProductionEnum.STATELIST_P1;
                    case TokenEnum.SEMICOLON: return ProductionEnum.STATELIST_P12;
                    case TokenEnum.BREAK: return ProductionEnum.STATELIST_P11;
                    case TokenEnum.PRINT: return ProductionEnum.STATELIST_P5;
                    case TokenEnum.READ: return ProductionEnum.STATELIST_P6;
                    case TokenEnum.RETURN: return ProductionEnum.STATELIST_P7;
                    case TokenEnum.IF: return ProductionEnum.STATELIST_P8;
                    case TokenEnum.FOR: return ProductionEnum.STATELIST_P9;
                    default: return null;
                }
            case NonterminalEnum.ALLOCEXPRESSION:
                switch (token) {
                    case TokenEnum.NEW: return ProductionEnum.ALLOCEXPRESSION_P1;
                    default: return null;
                }
            case NonterminalEnum.ALOCDIM:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.ALOCDIM_P2;
                    case TokenEnum.SEMICOLON: return ProductionEnum.ALOCDIM_P2;
                    case TokenEnum.OPEN_BRACKET: return ProductionEnum.ALOCDIM_P1;
                    default: return null;
                }
            case NonterminalEnum.EXPRESSION:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.EXPRESSION_P1;
                    case TokenEnum.OPEN_PAREN: return ProductionEnum.EXPRESSION_P1;
                    case TokenEnum.INT_CONSTANT: return ProductionEnum.EXPRESSION_P1;
                    case TokenEnum.PLUS: return ProductionEnum.EXPRESSION_P1;
                    case TokenEnum.MINUS: return ProductionEnum.EXPRESSION_P1;
                    case TokenEnum.FLOAT_CONSTANT: return ProductionEnum.EXPRESSION_P1;
                    case TokenEnum.STRING_CONSTANT: return ProductionEnum.EXPRESSION_P1;
                    case TokenEnum.NULL: return ProductionEnum.EXPRESSION_P1;
                    default: return null;
                }
            case NonterminalEnum.NUMEXPRESSION:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.NUMEXPRESSION_P1;
                    case TokenEnum.OPEN_PAREN: return ProductionEnum.NUMEXPRESSION_P1;
                    case TokenEnum.INT_CONSTANT: return ProductionEnum.NUMEXPRESSION_P1;
                    case TokenEnum.PLUS: return ProductionEnum.NUMEXPRESSION_P1;
                    case TokenEnum.MINUS: return ProductionEnum.NUMEXPRESSION_P1;
                    case TokenEnum.FLOAT_CONSTANT: return ProductionEnum.NUMEXPRESSION_P1;
                    case TokenEnum.STRING_CONSTANT: return ProductionEnum.NUMEXPRESSION_P1;
                    case TokenEnum.NULL: return ProductionEnum.NUMEXPRESSION_P1;
                    default: return null;
                }
            case NonterminalEnum.NUMEXPRAUX:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.SEMICOLON: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.CLOSE_BRACKET: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.PLUS: return ProductionEnum.NUMEXPRAUX_P1;
                    case TokenEnum.MINUS: return ProductionEnum.NUMEXPRAUX_P2;
                    case TokenEnum.LESS_THAN: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.GREATER_THAN: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.LESS_THAN_OR_EQUAL: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.GREATER_THAN_OR_EQUAL: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.EQUALS: return ProductionEnum.NUMEXPRAUX_P3;
                    case TokenEnum.NOT_EQUALS: return ProductionEnum.NUMEXPRAUX_P3;
                    default: return null;
                }
            case NonterminalEnum.TERM:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.TERM_P1;
                    case TokenEnum.OPEN_PAREN: return ProductionEnum.TERM_P1;
                    case TokenEnum.INT_CONSTANT: return ProductionEnum.TERM_P1;
                    case TokenEnum.PLUS: return ProductionEnum.TERM_P1;
                    case TokenEnum.MINUS: return ProductionEnum.TERM_P1;
                    case TokenEnum.FLOAT_CONSTANT: return ProductionEnum.TERM_P1;
                    case TokenEnum.STRING_CONSTANT: return ProductionEnum.TERM_P1;
                    case TokenEnum.NULL: return ProductionEnum.TERM_P1;
                    default: return null;
                }
            case NonterminalEnum.TERMAUX:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.SEMICOLON: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.CLOSE_BRACKET: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.PLUS: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.MINUS: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.MULTIPLY: return ProductionEnum.TERMAUX_P1;
                    case TokenEnum.DIVIDE: return ProductionEnum.TERMAUX_P2;
                    case TokenEnum.MODULO: return ProductionEnum.TERMAUX_P3;
                    case TokenEnum.LESS_THAN: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.GREATER_THAN: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.LESS_THAN_OR_EQUAL: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.GREATER_THAN_OR_EQUAL: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.EQUALS: return ProductionEnum.TERMAUX_P4;
                    case TokenEnum.NOT_EQUALS: return ProductionEnum.TERMAUX_P4;
                    default: return null;
                }
            case NonterminalEnum.UNARYEXPR:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.UNARYEXPR_P1;
                    case TokenEnum.OPEN_PAREN: return ProductionEnum.UNARYEXPR_P1;
                    case TokenEnum.INT_CONSTANT: return ProductionEnum.UNARYEXPR_P1;
                    case TokenEnum.PLUS: return ProductionEnum.UNARYEXPR_P2;
                    case TokenEnum.MINUS: return ProductionEnum.UNARYEXPR_P3;
                    case TokenEnum.FLOAT_CONSTANT: return ProductionEnum.UNARYEXPR_P1;
                    case TokenEnum.STRING_CONSTANT: return ProductionEnum.UNARYEXPR_P1;
                    case TokenEnum.NULL: return ProductionEnum.UNARYEXPR_P1;
                    default: return null;
                }
            case NonterminalEnum.FACTOR:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.FACTOR_P5;
                    case TokenEnum.OPEN_PAREN: return ProductionEnum.FACTOR_P6;
                    case TokenEnum.INT_CONSTANT: return ProductionEnum.FACTOR_P1;
                    case TokenEnum.FLOAT_CONSTANT: return ProductionEnum.FACTOR_P2;
                    case TokenEnum.STRING_CONSTANT: return ProductionEnum.FACTOR_P3;
                    case TokenEnum.NULL: return ProductionEnum.FACTOR_P4;
                    default: return null;
                }
            case NonterminalEnum.LVALUE:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.LVALUE_P1;
                    default: return null;
                }
            case NonterminalEnum.LVALUEDIM:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.SEMICOLON: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.OPEN_BRACKET: return ProductionEnum.LVALUEDIM_P1;
                    case TokenEnum.CLOSE_BRACKET: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.EQUAL: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.PLUS: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.MINUS: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.MULTIPLY: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.DIVIDE: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.MODULO: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.LESS_THAN: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.GREATER_THAN: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.LESS_THAN_OR_EQUAL: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.GREATER_THAN_OR_EQUAL: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.EQUALS: return ProductionEnum.LVALUEDIM_P2;
                    case TokenEnum.NOT_EQUALS: return ProductionEnum.LVALUEDIM_P2;
                    default: return null;
                }
            case NonterminalEnum.FUNCLIST1:
                switch (token) {
                    case TokenEnum.DEF: return ProductionEnum.FUNCLIST1_P1;
                    case TokenEnum.END: return ProductionEnum.FUNCLIST1_P2;
                    default: return null;
                }
            case NonterminalEnum.PARAMLIST1:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.PARAMLIST1_P2;
                    case TokenEnum.COMMA: return ProductionEnum.PARAMLIST1_P1;
                    default: return null;
                }
            case NonterminalEnum.PARAMLIST2:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.PARAMLIST2_P2;
                    case TokenEnum.COMMA: return ProductionEnum.PARAMLIST2_P1;
                    default: return null;
                }
            case NonterminalEnum.PARAMLIST3:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.PARAMLIST3_P2;
                    case TokenEnum.COMMA: return ProductionEnum.PARAMLIST3_P1;
                    default: return null;
                }
            case NonterminalEnum.ATRIBSTAT1:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.ATRIBSTAT1_P1;
                    case TokenEnum.OPEN_PAREN: return ProductionEnum.ATRIBSTAT1_P6;
                    case TokenEnum.INT_CONSTANT: return ProductionEnum.ATRIBSTAT1_P2;
                    case TokenEnum.NEW: return ProductionEnum.ATRIBSTAT1_P9;
                    case TokenEnum.PLUS: return ProductionEnum.ATRIBSTAT1_P7;
                    case TokenEnum.MINUS: return ProductionEnum.ATRIBSTAT1_P8;
                    case TokenEnum.FLOAT_CONSTANT: return ProductionEnum.ATRIBSTAT1_P3;
                    case TokenEnum.STRING_CONSTANT: return ProductionEnum.ATRIBSTAT1_P4;
                    case TokenEnum.NULL: return ProductionEnum.ATRIBSTAT1_P5;
                    default: return null;
                }
            case NonterminalEnum.PARAMLISTCALL1:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.PARAMLISTCALL1_P2;
                    case TokenEnum.COMMA: return ProductionEnum.PARAMLISTCALL1_P1;
                    default: return null;
                }
            case NonterminalEnum.IFSTAT1:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.OPEN_CURLY_BRACE: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.CLOSE_CURLY_BRACE: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.STRING: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.FLOAT: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.INT: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.SEMICOLON: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.BREAK: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.PRINT: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.READ: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.RETURN: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.IF: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.FOR: return ProductionEnum.IFSTAT1_P2;
                    case TokenEnum.ELSE: return ProductionEnum.IFSTAT1_P1;
                    case TokenEnum.END: return ProductionEnum.IFSTAT1_P2;
                    default: return null;
                }
            case NonterminalEnum.STATELIST1:
                switch (token) {
                    case TokenEnum.IDENT: return ProductionEnum.STATELIST1_P4;
                    case TokenEnum.OPEN_CURLY_BRACE: return ProductionEnum.STATELIST1_P10;
                    case TokenEnum.CLOSE_CURLY_BRACE: return ProductionEnum.STATELIST1_P13;
                    case TokenEnum.STRING: return ProductionEnum.STATELIST1_P3;
                    case TokenEnum.FLOAT: return ProductionEnum.STATELIST1_P2;
                    case TokenEnum.INT: return ProductionEnum.STATELIST1_P1;
                    case TokenEnum.SEMICOLON: return ProductionEnum.STATELIST1_P12;
                    case TokenEnum.BREAK: return ProductionEnum.STATELIST1_P11;
                    case TokenEnum.PRINT: return ProductionEnum.STATELIST1_P5;
                    case TokenEnum.READ: return ProductionEnum.STATELIST1_P6;
                    case TokenEnum.RETURN: return ProductionEnum.STATELIST1_P7;
                    case TokenEnum.IF: return ProductionEnum.STATELIST1_P8;
                    case TokenEnum.FOR: return ProductionEnum.STATELIST1_P9;
                    default: return null;
                }
            case NonterminalEnum.ALLOCEXPRESSION1:
                switch (token) {
                    case TokenEnum.STRING: return ProductionEnum.ALLOCEXPRESSION1_P3;
                    case TokenEnum.FLOAT: return ProductionEnum.ALLOCEXPRESSION1_P2;
                    case TokenEnum.INT: return ProductionEnum.ALLOCEXPRESSION1_P1;
                    default: return null;
                }
            case NonterminalEnum.EXPRESSION1:
                switch (token) {
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.EXPRESSION1_P1;
                    case TokenEnum.SEMICOLON: return ProductionEnum.EXPRESSION1_P1;
                    case TokenEnum.LESS_THAN: return ProductionEnum.EXPRESSION1_P2;
                    case TokenEnum.GREATER_THAN: return ProductionEnum.EXPRESSION1_P3;
                    case TokenEnum.LESS_THAN_OR_EQUAL: return ProductionEnum.EXPRESSION1_P4;
                    case TokenEnum.GREATER_THAN_OR_EQUAL: return ProductionEnum.EXPRESSION1_P5;
                    case TokenEnum.EQUALS: return ProductionEnum.EXPRESSION1_P6;
                    case TokenEnum.NOT_EQUALS: return ProductionEnum.EXPRESSION1_P7;
                    default: return null;
                }
            case NonterminalEnum.ATRIBSTAT2:
                switch (token) {
                    case TokenEnum.OPEN_PAREN: return ProductionEnum.ATRIBSTAT2_P3;
                    case TokenEnum.CLOSE_PAREN: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.SEMICOLON: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.OPEN_BRACKET: return ProductionEnum.ATRIBSTAT2_P1;
                    case TokenEnum.PLUS: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.MINUS: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.MULTIPLY: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.DIVIDE: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.MODULO: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.LESS_THAN: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.GREATER_THAN: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.LESS_THAN_OR_EQUAL: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.GREATER_THAN_OR_EQUAL: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.EQUALS: return ProductionEnum.ATRIBSTAT2_P2;
                    case TokenEnum.NOT_EQUALS: return ProductionEnum.ATRIBSTAT2_P2;
                    default: return null;
                }
            default: return null;
        }
    }

}
