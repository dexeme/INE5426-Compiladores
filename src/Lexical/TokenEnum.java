package Lexical;

import Symbols.SymbolEnum;

public enum TokenEnum  implements SymbolEnum {
    DEF,
    IDENT,
    OPEN_PAREN,
    CLOSE_PAREN,
    OPEN_CURLY_BRACE,
    CLOSE_CURLY_BRACE,
    STRING,
    FLOAT,
    INT,
    SEMICOLON,
    BREAK,
    OPEN_BRACKET,
    INT_CONSTANT,
    CLOSE_BRACKET,
    EQUAL,
    PRINT,
    READ,
    RETURN,
    IF,
    FOR,
    NEW,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    MODULO,
    FLOAT_CONSTANT,
    STRING_CONSTANT,
    NULL,
    COMMA,
    ELSE,
    LESS_THAN,
    GREATER_THAN,
    LESS_THAN_OR_EQUAL,
    GREATER_THAN_OR_EQUAL,
    EQUALS,
    NOT_EQUALS,
    END
}
