package Symbols;

import Lexical.Token;
import Semantics.Type;

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

    public void add(Token token) {
        head = new SymbolEntry(token, null, scopeLevel, head);
    }

    /** Add an identifier with its type to the current scope. */
    public void add(Token token, Type type) {
        head = new SymbolEntry(token, type, scopeLevel, head);
    }

    /** Find the first entry with given name, searching outward through scopes. */
    public SymbolEntry lookup(String name) {
        SymbolEntry current = head;
        while (current != null) {
            if (current.token().value().equals(name)) return current;
            current = current.next();
        }
        return null;
    }

    /** Check if an identifier exists in the current scope only. */
    public boolean existsInCurrentScope(String name) {
        SymbolEntry current = head;
        while (current != null && current.scope() == scopeLevel) {
            if (current.token().value().equals(name)) return true;
            current = current.next();
        }
        return false;
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
            sb.append(current.token()).append(", scope=").append(current.scope()).append('\n');
            current = current.next();
        }
        return sb.toString();
    }
}