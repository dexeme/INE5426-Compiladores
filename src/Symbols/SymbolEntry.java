package Symbols;

import Lexical.Token;
import Semantics.Type;

/** Entry of a symbol in the table. */
public record SymbolEntry(Token token, Type type, int scope, SymbolEntry next) {}