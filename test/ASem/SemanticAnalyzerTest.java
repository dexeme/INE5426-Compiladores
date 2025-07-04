package test.ASem;

import AL.*;
import AS.SyntaxAnalyzer;
import AST.ProgramNode;
import Lexical.Automaton;
import Lexical.AutomatonReader;
import Lexical.Token;
import Semantics.SemanticAnalyzer;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SemanticAnalyzerTest {
    private static final LexicalAnalyzer analyzer;
    static {
        Automaton automaton = AutomatonReader.readAutomaton("resources/automaton.json");
        analyzer = new LexicalAnalyzer();
    }

    private ProgramNode parse(String... lines) {
        Map<Integer, String> src = new LinkedHashMap<>();
        for (int i = 0; i < lines.length; i++) {
            src.put(i + 1, lines[i]);
        }
        List<Token> tokens = analyzer.analyzeCode(src);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(tokens);
        return sa.parse();
    }

    @Test
    public void testValidProgramHasNoErrors() {
        ProgramNode program = parse(
                "def main(){",
                "    int x;",
                "    x = 10;",
                "    print x;",
                "}"
        );
        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.analyze(program);
        assertTrue(sem.getErrors().isEmpty());
    }

    @Test
    public void testDetectUndeclaredVariable() {
        ProgramNode program = parse(
                "def main(){",
                "    x = 1;",
                "}"
        );
        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.analyze(program);
        assertFalse(sem.getErrors().isEmpty());
    }

    @Test
    public void testBreakOutsideLoop() {
        ProgramNode program = parse(
                "def main(){",
                "    break;",
                "}"
        );
        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.analyze(program);
        assertFalse(sem.getErrors().isEmpty());
    }

    @Test
    public void testDuplicateDeclaration() {
        ProgramNode program = parse(
                "def main(){",
                "    int x;",
                "    int x;",
                "}"
        );
        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.analyze(program);
        assertFalse(sem.getErrors().isEmpty());
    }

    @Test
    public void testTypeMismatchAssignment() {
        ProgramNode program = parse(
                "def main(float f){",
                "    f = 1;",
                "}"
        );
        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.analyze(program);
        assertFalse(sem.getErrors().isEmpty());
    }
}