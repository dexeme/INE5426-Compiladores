package Symbols;

import AL.Token;

public record SymbolEntry(Token token, int scope, SymbolEntry next) {}