package Util;

import Lexical.LexicalException;
import Syntax.SyntaxException;
import Semantics.SemanticException;

import java.util.Map;

public class ErrorFormatter {
    public static void print(RuntimeException e, Map<Integer, String> source) {
        int line = -1;
        int column = -1;
        if (e instanceof LexicalException le) {
            line = le.getLine();
            column = le.getColumn();
        } else if (e instanceof SyntaxException se) {
            line = se.getLine();
            column = se.getColumn();
        } else if (e instanceof SemanticException sem) {
            line = sem.getLine();
            column = sem.getColumn();
        }
        System.out.println("  " + e.getMessage());
        String src = source.get(line);
        if (src != null && column > 0) {
            System.out.println("  " + src);
            System.out.println("  " + " ".repeat(Math.max(0, column - 1)) + "^");
        }
    }
}