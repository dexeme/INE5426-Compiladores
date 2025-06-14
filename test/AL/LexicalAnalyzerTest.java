package AL;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class LexicalAnalyzerTest {
    private static final LexicalAnalyzer analyzer;
    static {
        Automaton automaton = AutomatonReader.readAutomaton("resources/automaton.json");
        analyzer = new LexicalAnalyzer(automaton);
    }

    private List<Token> analyze(String... lines) {
        Map<Integer, String> source = new LinkedHashMap<>();
        for (int i = 0; i < lines.length; i++) {
            source.put(i + 1, lines[i]);
        }
        return analyzer.analyzeCode(source);
    }

    @Test
    public void testSimpleProgram() {
        List<Token> tokens = analyze(
                "def main()",
                "    x = 10",
                "    if x > 5",
                "        print(x)"
        );

        List<Token> expected = Arrays.asList(
                new Token(TokenEnum.DEF, "def", 1, 1),
                new Token(TokenEnum.IDENT, "main", 1, 5),
                new Token(TokenEnum.OPEN_PAREN, "(", 1, 9),
                new Token(TokenEnum.CLOSE_PAREN, ")", 1, 10),
                new Token(TokenEnum.IDENT, "x", 2, 5),
                new Token(TokenEnum.EQUAL, "=", 2, 7),
                new Token(TokenEnum.INT_CONSTANT, "10", 2, 9),
                new Token(TokenEnum.IF, "if", 3, 5),
                new Token(TokenEnum.IDENT, "x", 3, 8),
                new Token(TokenEnum.GREATER_THAN, ">", 3, 10),
                new Token(TokenEnum.INT_CONSTANT, "5", 3, 12),
                new Token(TokenEnum.PRINT, "print", 4, 9),
                new Token(TokenEnum.OPEN_PAREN, "(", 4, 14),
                new Token(TokenEnum.IDENT, "x", 4, 15),
                new Token(TokenEnum.CLOSE_PAREN, ")", 4, 16)
        );
        assertEquals(expected, tokens);
    }

    @Test
    public void testFloatAndOperators() {
        List<Token> tokens = analyze("value = 1.5 + 2");
        List<Token> expected = Arrays.asList(
                new Token(TokenEnum.IDENT, "value", 1, 1),
                new Token(TokenEnum.EQUAL, "=", 1, 7),
                new Token(TokenEnum.FLOAT_CONSTANT, "1.5", 1, 9),
                new Token(TokenEnum.PLUS, "+", 1, 13),
                new Token(TokenEnum.INT_CONSTANT, "2", 1, 15)
        );
        assertEquals(expected, tokens);
    }

    @Test
    public void testOneInvalidSymbolInInputCode() {
        List<Token> tokens = analyze("x @ 5");
        List<Token> expected = List.of(
                new Token(TokenEnum.IDENT, "x", 1, 1),
                new Token(TokenEnum.INT_CONSTANT, "5", 1, 5));
        assertEquals(expected, tokens);
        assertFalse(analyzer.getErrors().isEmpty());
        assertEquals(1, analyzer.getErrors().size());
        assertEquals(LexicalErrorType.INVALID_START, analyzer.getErrors().getFirst().getType());
    }

    @Test
    public void TestMultipleLexicalErrors() {
        List<Token> tokens = analyze(
                "def main()",
                "    x @ 10",
                "    if x > 5",
                "        prin#(x)");
        List<Token> expected = List.of(
                new Token(TokenEnum.DEF, "def", 1, 1),
                new Token(TokenEnum.IDENT, "main", 1, 5),
                new Token(TokenEnum.OPEN_PAREN, "(", 1, 9),
                new Token(TokenEnum.CLOSE_PAREN, ")", 1, 10),
                new Token(TokenEnum.IDENT, "x", 2, 5),
                new Token(TokenEnum.INT_CONSTANT, "10", 2, 9),
                new Token(TokenEnum.IF, "if", 3, 5),
                new Token(TokenEnum.IDENT, "x", 3, 8),
                new Token(TokenEnum.GREATER_THAN, ">", 3, 10),
                new Token(TokenEnum.INT_CONSTANT, "5", 3, 12),
                new Token(TokenEnum.IDENT, "prin", 4, 9),
                new Token(TokenEnum.OPEN_PAREN, "(", 4, 14),
                new Token(TokenEnum.IDENT, "x", 4, 15),
                new Token(TokenEnum.CLOSE_PAREN, ")", 4, 16)
        );
        assertEquals(expected, tokens);
        assertFalse(analyzer.getErrors().isEmpty());
        assertEquals(2, analyzer.getErrors().size());
    }
}