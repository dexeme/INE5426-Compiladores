package Symbols;

import Lexical.Token;
import Semantics.Type;

import java.util.HashMap;

public class SymbolTable {

    public final HashMap<String,SymbolEntry> symbolTable;

    public SymbolTable() {
        this.symbolTable = new HashMap<>();
    }

    public void clear() {
        symbolTable.clear();
    }

    public void add(Token token) {
        add(token, Type.UNKNOWN);
    }

    public void add(Token token, Type type) {
        String name = token.value();
        if (symbolTable.containsKey(name)) {
            throw new IllegalArgumentException("Symbol already exists: " + name);
        }
        symbolTable.put(name, new SymbolEntry(token, type));
    }

    public SymbolEntry lookup(String name) {
        return symbolTable.getOrDefault(name, null);
    }

    public boolean exists(String name) {
        return symbolTable.containsKey(name);
    }

    public boolean isEmpty() {
        return symbolTable.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SymbolEntry current : symbolTable.values()) {
            String formattedToken = String.format("\tNome: %s | Tipo: %s | Posição: (linha %d, coluna %d)",
                    current.token().value(),
                    current.type() != null ? current.type() : "-",
                    current.token().line(),
                    current.token().column()
            );
            sb.insert(0, formattedToken + "\n");
        }
        return sb.toString();
    }

}