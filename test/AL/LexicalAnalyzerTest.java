package test.AL;

import AL.*;
import Lexical.*;
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
                "def main(){",
                "    x = 10;",
                "    if (x > 5){",
                "        print x;",
                "    }",
                "}"
        );

        List<Token> expected = Arrays.asList(
                new Token(TokenEnum.DEF, "def", 1, 1),
                new Token(TokenEnum.IDENT, "main", 1, 5),
                new Token(TokenEnum.OPEN_PAREN, "(", 1, 9),
                new Token(TokenEnum.CLOSE_PAREN, ")", 1, 10),
                new Token(TokenEnum.OPEN_CURLY_BRACE, "{", 1, 11),
                new Token(TokenEnum.IDENT, "x", 2, 5),
                new Token(TokenEnum.EQUAL, "=", 2, 7),
                new Token(TokenEnum.INT_CONSTANT, "10", 2, 9),
                new Token(TokenEnum.SEMICOLON, ";", 2, 11),
                new Token(TokenEnum.IF, "if", 3, 5),
                new Token(TokenEnum.OPEN_PAREN, "(", 3, 8),
                new Token(TokenEnum.IDENT, "x", 3, 9),
                new Token(TokenEnum.GREATER_THAN, ">", 3, 11),
                new Token(TokenEnum.INT_CONSTANT, "5", 3, 13),
                new Token(TokenEnum.CLOSE_PAREN, ")", 3, 14),
                new Token(TokenEnum.OPEN_CURLY_BRACE, "{", 3, 15),
                new Token(TokenEnum.PRINT, "print", 4, 9),
                new Token(TokenEnum.IDENT, "x", 4, 15),
                new Token(TokenEnum.SEMICOLON, ";", 4, 16),
                new Token(TokenEnum.CLOSE_CURLY_BRACE, "}", 5, 5),
                new Token(TokenEnum.CLOSE_CURLY_BRACE, "}", 6, 1)
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
        }

        @Test
        public void testSemicolon() {
            List<Token> tokens = analyze(
                    "if x == 0 {",
                    "    print x;",
                    "} "
            );

            List<Token> expected = Arrays.asList(
                    new Token(TokenEnum.IF, "if", 1, 1),
                    new Token(TokenEnum.IDENT, "x", 1, 4),
                    new Token(TokenEnum.EQUALS, "==", 1, 6),
                    new Token(TokenEnum.INT_CONSTANT, "0", 1, 9),
                    new Token(TokenEnum.OPEN_CURLY_BRACE, "{", 1, 11),
                    new Token(TokenEnum.PRINT, "print", 2, 5),
                    new Token(TokenEnum.IDENT, "x", 2, 11),
                    new Token(TokenEnum.SEMICOLON, ";", 2, 12),
                    new Token(TokenEnum.CLOSE_CURLY_BRACE, "}", 3, 1)
            );
            assertEquals(expected, tokens);
            assertTrue(analyzer.getErrors().isEmpty());
        }

        @Test
        public void testComplexFunction() {
            List<Token> tokens = analyze(
                    "def soma(int a, int b) {",
                    "    int resultado;",
                    "    resultado = a + b;",
                    "    return resultado;",
                    "}"
            );

            List<Token> expected = Arrays.asList(
                new Token(TokenEnum.DEF, "def", 1, 1),
                new Token(TokenEnum.IDENT, "soma", 1, 5),
                new Token(TokenEnum.OPEN_PAREN, "(", 1, 9),
                new Token(TokenEnum.INT, "int", 1, 10),
                new Token(TokenEnum.IDENT, "a", 1, 14),
                new Token(TokenEnum.COMMA, ",", 1, 15),
                new Token(TokenEnum.INT, "int", 1, 17),
                new Token(TokenEnum.IDENT, "b", 1, 21),
                new Token(TokenEnum.CLOSE_PAREN, ")", 1, 22),
                new Token(TokenEnum.OPEN_CURLY_BRACE, "{", 1, 24),
                new Token(TokenEnum.INT, "int", 2, 5),
                new Token(TokenEnum.IDENT, "resultado", 2, 9),
                new Token(TokenEnum.SEMICOLON, ";", 2, 18),
                new Token(TokenEnum.IDENT, "resultado", 3, 5),
                new Token(TokenEnum.EQUAL, "=", 3, 15),
                new Token(TokenEnum.IDENT, "a", 3, 17),
                new Token(TokenEnum.PLUS, "+", 3, 19),
                new Token(TokenEnum.IDENT, "b", 3, 21),
                new Token(TokenEnum.SEMICOLON, ";", 3, 22),
                new Token(TokenEnum.RETURN, "return", 4, 5),
                new Token(TokenEnum.IDENT, "resultado", 4, 12),
                new Token(TokenEnum.SEMICOLON, ";", 4, 21),
                new Token(TokenEnum.CLOSE_CURLY_BRACE, "}", 5, 1)
        );

                assertEquals(expected, tokens);
                assertTrue(analyzer.getErrors().isEmpty());
            }

            @Test
            public void testLoopAndEndifTokens() {
                List<Token> tokens = analyze(
                        "def loop(){",
                        "    for (i = 0; i < 2; i = i + 1) {",
                        "        print i;",
                        "    }",
                        "}"
                );

                List<TokenEnum> types = tokens.stream().map(Token::type).toList();
                assertTrue(types.contains(TokenEnum.FOR));
                assertTrue(types.contains(TokenEnum.SEMICOLON));
                assertTrue(analyzer.getErrors().isEmpty());
            }

            @Test
            public void testUnterminatedStringError() {
                analyze(
                        "def bad(){",
                        "    x = \"abc",
                        "}"
                );

                assertFalse(analyzer.getErrors().isEmpty());
                assertEquals(LexicalErrorType.INVALID_TOKEN, analyzer.getErrors().getFirst().getType());
            }
        }