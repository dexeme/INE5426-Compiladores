package test.AL;
import AL.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class LexicalAnalyzerTest {
    private static final LexicalAnalyzer analyzer;
    static {
        Automaton automaton = AutomatonReader.readAutomaton("AL/automaton.json");
        analyzer = new LexicalAnalyzer(automaton);
    }

    private List<Lexeme> analyze(String... lines) {
        Map<Integer, String> source = new LinkedHashMap<>();
        for (int i = 0; i < lines.length; i++) {
            source.put(i + 1, lines[i]);
        }
        return analyzer.analyzeCode(source);
    }

    @Test
    public void testSimpleProgram() {
        List<Lexeme> tokens = analyze(
                "def main()",
                "    x = 10",
                "    if x > 5",
                "        print(x)"
        );

        List<Lexeme> expected = Arrays.asList(
                new Lexeme(TokenEnum.DEF, "def", 1),
                new Lexeme(TokenEnum.IDENT, "main", 1),
                new Lexeme(TokenEnum.OPEN_PAREN, "(", 1),
                new Lexeme(TokenEnum.CLOSE_PAREN, ")", 1),
                new Lexeme(TokenEnum.IDENT, "x", 2),
                new Lexeme(TokenEnum.EQUAL, "=", 2),
                new Lexeme(TokenEnum.INT_CONSTANT, "10", 2),
                new Lexeme(TokenEnum.IF, "if", 3),
                new Lexeme(TokenEnum.IDENT, "x", 3),
                new Lexeme(TokenEnum.GREATER_THAN, ">", 3),
                new Lexeme(TokenEnum.INT_CONSTANT, "5", 3),
                new Lexeme(TokenEnum.PRINT, "print", 4),
                new Lexeme(TokenEnum.OPEN_PAREN, "(", 4),
                new Lexeme(TokenEnum.IDENT, "x", 4),
                new Lexeme(TokenEnum.CLOSE_PAREN, ")", 4)
        );
        assertEquals(expected, tokens);
    }

    @Test
    public void testFloatAndOperators() {
        List<Lexeme> tokens = analyze("value = 1.5 + 2");
        List<Lexeme> expected = Arrays.asList(
                new Lexeme(TokenEnum.IDENT, "value", 1),
                new Lexeme(TokenEnum.EQUAL, "=", 1),
                new Lexeme(TokenEnum.FLOAT_CONSTANT, "1.5", 1),
                new Lexeme(TokenEnum.PLUS, "+", 1),
                new Lexeme(TokenEnum.INT_CONSTANT, "2", 1)
        );
        assertEquals(expected, tokens);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidSymbol() {
        analyze("$");
    }
}