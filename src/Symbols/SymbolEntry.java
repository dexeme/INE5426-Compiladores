package Symbols;

import AL.Lexeme;

public record SymbolEntry(Lexeme lexeme, int scope, SymbolEntry next) {}