package AS;

import Lexical.TokenEnum;
import Symbols.NonterminalEnum;
import Symbols.ProductionEnum;


/**
 * This table was integrated into the SyntaxAnalyzer class. So it is no longer used, but it is kept here for reference.
 */
public class ParsingTable {

    public ProductionEnum getProduction(NonterminalEnum nonterminal, TokenEnum token) {
        switch (nonterminal) {
            case PROGRAM:
                switch (token) {
                    case DEF: return ProductionEnum.PROGRAM_P2;
                    case IDENT: return ProductionEnum.PROGRAM_P1;
                    case OPEN_CURLY_BRACE: return ProductionEnum.PROGRAM_P1;
                    case STRING: return ProductionEnum.PROGRAM_P1;
                    case FLOAT: return ProductionEnum.PROGRAM_P1;
                    case INT: return ProductionEnum.PROGRAM_P1;
                    case SEMICOLON: return ProductionEnum.PROGRAM_P1;
                    case BREAK: return ProductionEnum.PROGRAM_P1;
                    case PRINT: return ProductionEnum.PROGRAM_P1;
                    case READ: return ProductionEnum.PROGRAM_P1;
                    case RETURN: return ProductionEnum.PROGRAM_P1;
                    case IF: return ProductionEnum.PROGRAM_P1;
                    case FOR: return ProductionEnum.PROGRAM_P1;
                    case END: return ProductionEnum.PROGRAM_P3;
                    default: return null;
                }
            case FUNCLIST:
                switch (token) {
                    case DEF: return ProductionEnum.FUNCLIST_P1;
                    default: return null;
                }
            case FUNCDEF:
                switch (token) {
                    case DEF: return ProductionEnum.FUNCDEF_P1;
                    default: return null;
                }
            case PARAMLIST:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.PARAMLIST_P4;
                    case STRING: return ProductionEnum.PARAMLIST_P1;
                    case FLOAT: return ProductionEnum.PARAMLIST_P2;
                    case INT: return ProductionEnum.PARAMLIST_P3;
                    default: return null;
                }
            case STATEMENT:
                switch (token) {
                    case IDENT: return ProductionEnum.STATEMENT_P2;
                    case OPEN_CURLY_BRACE: return ProductionEnum.STATEMENT_P8;
                    case STRING: return ProductionEnum.STATEMENT_P1;
                    case FLOAT: return ProductionEnum.STATEMENT_P1;
                    case INT: return ProductionEnum.STATEMENT_P1;
                    case SEMICOLON: return ProductionEnum.STATEMENT_P10;
                    case BREAK: return ProductionEnum.STATEMENT_P9;
                    case PRINT: return ProductionEnum.STATEMENT_P3;
                    case READ: return ProductionEnum.STATEMENT_P4;
                    case RETURN: return ProductionEnum.STATEMENT_P5;
                    case IF: return ProductionEnum.STATEMENT_P6;
                    case FOR: return ProductionEnum.STATEMENT_P7;
                    default: return null;
                }
            case VARDECL:
                switch (token) {
                    case STRING: return ProductionEnum.VARDECL_P3;
                    case FLOAT: return ProductionEnum.VARDECL_P2;
                    case INT: return ProductionEnum.VARDECL_P1;
                    default: return null;
                }
            case VARDIM:
                switch (token) {
                    case SEMICOLON: return ProductionEnum.VARDIM_P2;
                    case OPEN_BRACKET: return ProductionEnum.VARDIM_P1;
                    default: return null;
                }
            case ATRIBSTAT:
                switch (token) {
                    case IDENT: return ProductionEnum.ATRIBSTAT_P1;
                    default: return null;
                }
            case FUNCCALL:
                switch (token) {
                    case IDENT: return ProductionEnum.FUNCCALL_P1;
                    default: return null;
                }
            case PARAMLISTCALL:
                switch (token) {
                    case IDENT: return ProductionEnum.PARAMLISTCALL_P1;
                    case CLOSE_PAREN: return ProductionEnum.PARAMLISTCALL_P2;
                    default: return null;
                }
            case PRINTSTAT:
                switch (token) {
                    case PRINT: return ProductionEnum.PRINTSTAT_P1;
                    default: return null;
                }
            case READSTAT:
                switch (token) {
                    case READ: return ProductionEnum.READSTAT_P1;
                    default: return null;
                }
            case RETURNSTAT:
                switch (token) {
                    case RETURN: return ProductionEnum.RETURNSTAT_P1;
                    default: return null;
                }
            case IFSTAT:
                switch (token) {
                    case IF: return ProductionEnum.IFSTAT_P1;
                    default: return null;
                }
            case FORSTAT:
                switch (token) {
                    case FOR: return ProductionEnum.FORSTAT_P1;
                    default: return null;
                }
            case STATELIST:
                switch (token) {
                    case IDENT: return ProductionEnum.STATELIST_P4;
                    case OPEN_CURLY_BRACE: return ProductionEnum.STATELIST_P10;
                    case STRING: return ProductionEnum.STATELIST_P3;
                    case FLOAT: return ProductionEnum.STATELIST_P2;
                    case INT: return ProductionEnum.STATELIST_P1;
                    case SEMICOLON: return ProductionEnum.STATELIST_P12;
                    case BREAK: return ProductionEnum.STATELIST_P11;
                    case PRINT: return ProductionEnum.STATELIST_P5;
                    case READ: return ProductionEnum.STATELIST_P6;
                    case RETURN: return ProductionEnum.STATELIST_P7;
                    case IF: return ProductionEnum.STATELIST_P8;
                    case FOR: return ProductionEnum.STATELIST_P9;
                    default: return null;
                }
            case ALLOCEXPRESSION:
                switch (token) {
                    case NEW: return ProductionEnum.ALLOCEXPRESSION_P1;
                    default: return null;
                }
            case ALOCDIM:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.ALOCDIM_P2;
                    case SEMICOLON: return ProductionEnum.ALOCDIM_P2;
                    case OPEN_BRACKET: return ProductionEnum.ALOCDIM_P1;
                    default: return null;
                }
            case EXPRESSION:
                switch (token) {
                    case IDENT: return ProductionEnum.EXPRESSION_P1;
                    case OPEN_PAREN: return ProductionEnum.EXPRESSION_P1;
                    case INT_CONSTANT: return ProductionEnum.EXPRESSION_P1;
                    case PLUS: return ProductionEnum.EXPRESSION_P1;
                    case MINUS: return ProductionEnum.EXPRESSION_P1;
                    case FLOAT_CONSTANT: return ProductionEnum.EXPRESSION_P1;
                    case STRING_CONSTANT: return ProductionEnum.EXPRESSION_P1;
                    case NULL: return ProductionEnum.EXPRESSION_P1;
                    default: return null;
                }
            case NUMEXPRESSION:
                switch (token) {
                    case IDENT: return ProductionEnum.NUMEXPRESSION_P1;
                    case OPEN_PAREN: return ProductionEnum.NUMEXPRESSION_P1;
                    case INT_CONSTANT: return ProductionEnum.NUMEXPRESSION_P1;
                    case PLUS: return ProductionEnum.NUMEXPRESSION_P1;
                    case MINUS: return ProductionEnum.NUMEXPRESSION_P1;
                    case FLOAT_CONSTANT: return ProductionEnum.NUMEXPRESSION_P1;
                    case STRING_CONSTANT: return ProductionEnum.NUMEXPRESSION_P1;
                    case NULL: return ProductionEnum.NUMEXPRESSION_P1;
                    default: return null;
                }
            case NUMEXPRAUX:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.NUMEXPRAUX_P3;
                    case SEMICOLON: return ProductionEnum.NUMEXPRAUX_P3;
                    case CLOSE_BRACKET: return ProductionEnum.NUMEXPRAUX_P3;
                    case PLUS: return ProductionEnum.NUMEXPRAUX_P1;
                    case MINUS: return ProductionEnum.NUMEXPRAUX_P2;
                    case LESS_THAN: return ProductionEnum.NUMEXPRAUX_P3;
                    case GREATER_THAN: return ProductionEnum.NUMEXPRAUX_P3;
                    case LESS_THAN_OR_EQUAL: return ProductionEnum.NUMEXPRAUX_P3;
                    case GREATER_THAN_OR_EQUAL: return ProductionEnum.NUMEXPRAUX_P3;
                    case EQUALS: return ProductionEnum.NUMEXPRAUX_P3;
                    case NOT_EQUALS: return ProductionEnum.NUMEXPRAUX_P3;
                    default: return null;
                }
            case TERM:
                switch (token) {
                    case IDENT: return ProductionEnum.TERM_P1;
                    case OPEN_PAREN: return ProductionEnum.TERM_P1;
                    case INT_CONSTANT: return ProductionEnum.TERM_P1;
                    case PLUS: return ProductionEnum.TERM_P1;
                    case MINUS: return ProductionEnum.TERM_P1;
                    case FLOAT_CONSTANT: return ProductionEnum.TERM_P1;
                    case STRING_CONSTANT: return ProductionEnum.TERM_P1;
                    case NULL: return ProductionEnum.TERM_P1;
                    default: return null;
                }
            case TERMAUX:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.TERMAUX_P4;
                    case SEMICOLON: return ProductionEnum.TERMAUX_P4;
                    case CLOSE_BRACKET: return ProductionEnum.TERMAUX_P4;
                    case PLUS: return ProductionEnum.TERMAUX_P4;
                    case MINUS: return ProductionEnum.TERMAUX_P4;
                    case MULTIPLY: return ProductionEnum.TERMAUX_P1;
                    case DIVIDE: return ProductionEnum.TERMAUX_P2;
                    case MODULO: return ProductionEnum.TERMAUX_P3;
                    case LESS_THAN: return ProductionEnum.TERMAUX_P4;
                    case GREATER_THAN: return ProductionEnum.TERMAUX_P4;
                    case LESS_THAN_OR_EQUAL: return ProductionEnum.TERMAUX_P4;
                    case GREATER_THAN_OR_EQUAL: return ProductionEnum.TERMAUX_P4;
                    case EQUALS: return ProductionEnum.TERMAUX_P4;
                    case NOT_EQUALS: return ProductionEnum.TERMAUX_P4;
                    default: return null;
                }
            case UNARYEXPR:
                switch (token) {
                    case IDENT: return ProductionEnum.UNARYEXPR_P1;
                    case OPEN_PAREN: return ProductionEnum.UNARYEXPR_P1;
                    case INT_CONSTANT: return ProductionEnum.UNARYEXPR_P1;
                    case PLUS: return ProductionEnum.UNARYEXPR_P2;
                    case MINUS: return ProductionEnum.UNARYEXPR_P3;
                    case FLOAT_CONSTANT: return ProductionEnum.UNARYEXPR_P1;
                    case STRING_CONSTANT: return ProductionEnum.UNARYEXPR_P1;
                    case NULL: return ProductionEnum.UNARYEXPR_P1;
                    default: return null;
                }
            case FACTOR:
                switch (token) {
                    case IDENT: return ProductionEnum.FACTOR_P5;
                    case OPEN_PAREN: return ProductionEnum.FACTOR_P6;
                    case INT_CONSTANT: return ProductionEnum.FACTOR_P1;
                    case FLOAT_CONSTANT: return ProductionEnum.FACTOR_P2;
                    case STRING_CONSTANT: return ProductionEnum.FACTOR_P3;
                    case NULL: return ProductionEnum.FACTOR_P4;
                    default: return null;
                }
            case LVALUE:
                switch (token) {
                    case IDENT: return ProductionEnum.LVALUE_P1;
                    default: return null;
                }
            case LVALUEDIM:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.LVALUEDIM_P2;
                    case SEMICOLON: return ProductionEnum.LVALUEDIM_P2;
                    case OPEN_BRACKET: return ProductionEnum.LVALUEDIM_P1;
                    case CLOSE_BRACKET: return ProductionEnum.LVALUEDIM_P2;
                    case EQUAL: return ProductionEnum.LVALUEDIM_P2;
                    case PLUS: return ProductionEnum.LVALUEDIM_P2;
                    case MINUS: return ProductionEnum.LVALUEDIM_P2;
                    case MULTIPLY: return ProductionEnum.LVALUEDIM_P2;
                    case DIVIDE: return ProductionEnum.LVALUEDIM_P2;
                    case MODULO: return ProductionEnum.LVALUEDIM_P2;
                    case LESS_THAN: return ProductionEnum.LVALUEDIM_P2;
                    case GREATER_THAN: return ProductionEnum.LVALUEDIM_P2;
                    case LESS_THAN_OR_EQUAL: return ProductionEnum.LVALUEDIM_P2;
                    case GREATER_THAN_OR_EQUAL: return ProductionEnum.LVALUEDIM_P2;
                    case EQUALS: return ProductionEnum.LVALUEDIM_P2;
                    case NOT_EQUALS: return ProductionEnum.LVALUEDIM_P2;
                    default: return null;
                }
            case FUNCLIST1:
                switch (token) {
                    case DEF: return ProductionEnum.FUNCLIST1_P1;
                    case END: return ProductionEnum.FUNCLIST1_P2;
                    default: return null;
                }
            case PARAMLIST1:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.PARAMLIST1_P2;
                    case COMMA: return ProductionEnum.PARAMLIST1_P1;
                    default: return null;
                }
            case PARAMLIST2:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.PARAMLIST2_P2;
                    case COMMA: return ProductionEnum.PARAMLIST2_P1;
                    default: return null;
                }
            case PARAMLIST3:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.PARAMLIST3_P2;
                    case COMMA: return ProductionEnum.PARAMLIST3_P1;
                    default: return null;
                }
            case ATRIBSTAT1:
                switch (token) {
                    case IDENT: return ProductionEnum.ATRIBSTAT1_P1;
                    case OPEN_PAREN: return ProductionEnum.ATRIBSTAT1_P6;
                    case INT_CONSTANT: return ProductionEnum.ATRIBSTAT1_P2;
                    case NEW: return ProductionEnum.ATRIBSTAT1_P9;
                    case PLUS: return ProductionEnum.ATRIBSTAT1_P7;
                    case MINUS: return ProductionEnum.ATRIBSTAT1_P8;
                    case FLOAT_CONSTANT: return ProductionEnum.ATRIBSTAT1_P3;
                    case STRING_CONSTANT: return ProductionEnum.ATRIBSTAT1_P4;
                    case NULL: return ProductionEnum.ATRIBSTAT1_P5;
                    default: return null;
                }
            case PARAMLISTCALL1:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.PARAMLISTCALL1_P2;
                    case COMMA: return ProductionEnum.PARAMLISTCALL1_P1;
                    default: return null;
                }
            case IFSTAT1:
                switch (token) {
                    case IDENT: return ProductionEnum.IFSTAT1_P2;
                    case OPEN_CURLY_BRACE: return ProductionEnum.IFSTAT1_P2;
                    case CLOSE_CURLY_BRACE: return ProductionEnum.IFSTAT1_P2;
                    case STRING: return ProductionEnum.IFSTAT1_P2;
                    case FLOAT: return ProductionEnum.IFSTAT1_P2;
                    case INT: return ProductionEnum.IFSTAT1_P2;
                    case SEMICOLON: return ProductionEnum.IFSTAT1_P2;
                    case BREAK: return ProductionEnum.IFSTAT1_P2;
                    case PRINT: return ProductionEnum.IFSTAT1_P2;
                    case READ: return ProductionEnum.IFSTAT1_P2;
                    case RETURN: return ProductionEnum.IFSTAT1_P2;
                    case IF: return ProductionEnum.IFSTAT1_P2;
                    case FOR: return ProductionEnum.IFSTAT1_P2;
                    case ELSE: return ProductionEnum.IFSTAT1_P1;
                    case END: return ProductionEnum.IFSTAT1_P2;
                    default: return null;
                }
            case STATELIST1:
                switch (token) {
                    case IDENT: return ProductionEnum.STATELIST1_P4;
                    case OPEN_CURLY_BRACE: return ProductionEnum.STATELIST1_P10;
                    case CLOSE_CURLY_BRACE: return ProductionEnum.STATELIST1_P13;
                    case STRING: return ProductionEnum.STATELIST1_P3;
                    case FLOAT: return ProductionEnum.STATELIST1_P2;
                    case INT: return ProductionEnum.STATELIST1_P1;
                    case SEMICOLON: return ProductionEnum.STATELIST1_P12;
                    case BREAK: return ProductionEnum.STATELIST1_P11;
                    case PRINT: return ProductionEnum.STATELIST1_P5;
                    case READ: return ProductionEnum.STATELIST1_P6;
                    case RETURN: return ProductionEnum.STATELIST1_P7;
                    case IF: return ProductionEnum.STATELIST1_P8;
                    case FOR: return ProductionEnum.STATELIST1_P9;
                    default: return null;
                }
            case ALLOCEXPRESSION1:
                switch (token) {
                    case STRING: return ProductionEnum.ALLOCEXPRESSION1_P3;
                    case FLOAT: return ProductionEnum.ALLOCEXPRESSION1_P2;
                    case INT: return ProductionEnum.ALLOCEXPRESSION1_P1;
                    default: return null;
                }
            case EXPRESSION1:
                switch (token) {
                    case CLOSE_PAREN: return ProductionEnum.EXPRESSION1_P1;
                    case SEMICOLON: return ProductionEnum.EXPRESSION1_P1;
                    case LESS_THAN: return ProductionEnum.EXPRESSION1_P2;
                    case GREATER_THAN: return ProductionEnum.EXPRESSION1_P3;
                    case LESS_THAN_OR_EQUAL: return ProductionEnum.EXPRESSION1_P4;
                    case GREATER_THAN_OR_EQUAL: return ProductionEnum.EXPRESSION1_P5;
                    case EQUALS: return ProductionEnum.EXPRESSION1_P6;
                    case NOT_EQUALS: return ProductionEnum.EXPRESSION1_P7;
                    default: return null;
                }
            case ATRIBSTAT2:
                switch (token) {
                    case OPEN_PAREN: return ProductionEnum.ATRIBSTAT2_P3;
                    case CLOSE_PAREN: return ProductionEnum.ATRIBSTAT2_P2;
                    case SEMICOLON: return ProductionEnum.ATRIBSTAT2_P2;
                    case OPEN_BRACKET: return ProductionEnum.ATRIBSTAT2_P1;
                    case PLUS: return ProductionEnum.ATRIBSTAT2_P2;
                    case MINUS: return ProductionEnum.ATRIBSTAT2_P2;
                    case MULTIPLY: return ProductionEnum.ATRIBSTAT2_P2;
                    case DIVIDE: return ProductionEnum.ATRIBSTAT2_P2;
                    case MODULO: return ProductionEnum.ATRIBSTAT2_P2;
                    case LESS_THAN: return ProductionEnum.ATRIBSTAT2_P2;
                    case GREATER_THAN: return ProductionEnum.ATRIBSTAT2_P2;
                    case LESS_THAN_OR_EQUAL: return ProductionEnum.ATRIBSTAT2_P2;
                    case GREATER_THAN_OR_EQUAL: return ProductionEnum.ATRIBSTAT2_P2;
                    case EQUALS: return ProductionEnum.ATRIBSTAT2_P2;
                    case NOT_EQUALS: return ProductionEnum.ATRIBSTAT2_P2;
                    default: return null;
                }
            default: return null;
        }
    }

}
