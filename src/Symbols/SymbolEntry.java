package Symbols;

import Lexical.Token;
import Semantics.Type;

public record SymbolEntry(Token token, Type type) {}