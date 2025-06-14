package Symbols;

import AL.Lexeme;

public class SymbolTable {
    private SymbolEntry head;
    private int scopeLevel;

    public void clear() {
        head = null;
        scopeLevel = 0;
    }

    public void enterScope() {
        scopeLevel++;
    }

    public void exitScope() {
        while (head != null && head.scope() == scopeLevel) {
            head = head.next();
        }
        if (scopeLevel > 0) {
            scopeLevel--;
        }
    }

    public void add(Lexeme lexeme) {
        head = new SymbolEntry(lexeme, scopeLevel, head);
    }

    public SymbolEntry getHead() {
        return head;
    }

    public int getScopeLevel() {
        return scopeLevel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SymbolEntry current = head;
        while (current != null) {
            sb.append(current.lexeme()).append(", scope=").append(current.scope()).append('\n');
            current = current.next();
        }
        return sb.toString();
    }
}