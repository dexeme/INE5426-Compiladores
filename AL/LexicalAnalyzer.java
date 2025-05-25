package AL;
import java.util.List;
import java.util.Map;

public class LexicalAnalyzer {
    private static final Map<String, TokenEnum> keywords;
    private static final Map<String, TokenEnum> operators;
    private static final Map<String, TokenEnum> comparators;
    private static final Map<String, TokenEnum> specialSymbols;

    public List<Lexeme> analyzeCode(Map<Integer, String> lines) {
        return null;
    };

    public LexicalAnalyzer() {
        Automaton automaton = new Automaton();
    }

    static {
        keywords = Map.ofEntries(
                Map.entry("def", TokenEnum.DEF),
                Map.entry("if", TokenEnum.IF),
                Map.entry("else", TokenEnum.ELSE),
                Map.entry("for", TokenEnum.FOR),
                Map.entry("return", TokenEnum.RETURN),
                Map.entry("break", TokenEnum.BREAK),
                Map.entry("new", TokenEnum.NEW),
                Map.entry("print", TokenEnum.PRINT),
                Map.entry("read", TokenEnum.READ),
                Map.entry("null", TokenEnum.NULL)
        );

        operators = Map.ofEntries(
                Map.entry("+", TokenEnum.PLUS),
                Map.entry("-", TokenEnum.MINUS),
                Map.entry("*", TokenEnum.MULTIPLY),
                Map.entry("/", TokenEnum.DIVIDE),
                Map.entry("%", TokenEnum.MODULO),
                Map.entry("=", TokenEnum.EQUAL)
        );

        comparators = Map.ofEntries(
                Map.entry("==", TokenEnum.EQUALS),
                Map.entry("!=", TokenEnum.NOT_EQUALS),
                Map.entry("<", TokenEnum.LESS_THAN),
                Map.entry(">", TokenEnum.GREATER_THAN),
                Map.entry("<=", TokenEnum.LESS_THAN_OR_EQUAL),
                Map.entry(">=", TokenEnum.GREATER_THAN_OR_EQUAL)
        );

        specialSymbols = Map.ofEntries(
                Map.entry("(", TokenEnum.OPEN_PAREN),
                Map.entry(")", TokenEnum.CLOSE_PAREN),
                Map.entry("[", TokenEnum.OPEN_BRACKET),
                Map.entry("]", TokenEnum.CLOSE_BRACKET),
                Map.entry("{", TokenEnum.OPEN_CURLY_BRACE),
                Map.entry("}", TokenEnum.CLOSE_CURLY_BRACE),
                Map.entry(",", TokenEnum.COMMA),
                Map.entry(";", TokenEnum.SEMICOLON)
        );
    }
}





