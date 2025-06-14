package AL;
import java.util.*;

public class LexicalAnalyzer {
    private static final Map<String, TokenEnum> keywords;
    private static final Map<String, TokenEnum> operators;
    private static final Map<String, TokenEnum> comparators;
    private static final Map<String, TokenEnum> specialSymbols;

    private final Automaton automaton;
    private final List<LexicalException> errors = new ArrayList<>();

    public LexicalAnalyzer(Automaton automaton) {
        this.automaton = automaton;
    }

    public List<Lexeme> analyzeCode(Map<Integer, String> lines) {
        errors.clear();
        List<Lexeme> lexemes = new ArrayList<>();

        for (Map.Entry<Integer, String> lineEntry : lines.entrySet()) {
            Integer lineNumber = lineEntry.getKey();
            String line = lineEntry.getValue();
            int position = 0;

            while (position < line.length()) {
                while (position < line.length() && Character.isWhitespace(line.charAt(position))) {
                    position++;
                }
                if (position >= line.length()) break;

                int column = position + 1;
                String startState = automaton.getInitialState();
                String startCheck = automaton.getNextState(startState, String.valueOf(line.charAt(position)));
                if (startCheck == null) {
                    StringBuilder invalid = new StringBuilder();
                    int startCol = column;
                    while (position < line.length() && automaton.getNextState(startState, String.valueOf(line.charAt(position))) == null) {
                        invalid.append(line.charAt(position));
                        position++;
                    }
                    recordError(new LexicalException(LexicalErrorType.INVALID_START, invalid.toString(), lineNumber, startCol));
                    continue;
                }

                StringBuilder token = new StringBuilder();
                String currentState = startState;

                while (position < line.length()) {
                    char symbol = line.charAt(position);
                    String nextState = automaton.getNextState(currentState, String.valueOf(symbol));
                    if (nextState == null) {
                        break;
                    }
                    token.append(symbol);
                    currentState = nextState;
                    position++;
                }

                if (!token.isEmpty()) {
                    if (automaton.isFinalState(currentState)) {
                        String lexemeStr = token.toString();
                        try {
                            TokenEnum type = classifyToken(lexemeStr, currentState, lineNumber, column);
                            lexemes.add(new Lexeme(type, lexemeStr, lineNumber, column));
                        } catch (LexicalException e) {
                            recordError(e);
                        }
                    } else {
                        recordError(new LexicalException(LexicalErrorType.INVALID_TOKEN, token.toString(), lineNumber, column));
                    }
                } else {
                    char invalid = line.charAt(position);
                    position++;
                    recordError(new LexicalException(LexicalErrorType.INVALID_SYMBOL, String.valueOf(invalid), lineNumber, column));
                }
            }
        }

        return lexemes;
    }

    public List<LexicalException> getErrors() {
        return new ArrayList<>(errors);
    }

    private void recordError(LexicalException e) {
        errors.add(e);
        System.out.println(e.getMessage());
    }

    private TokenEnum classifyToken(String lexeme, String finalState, int line, int column) {
        if (keywords.containsKey(lexeme)) {
            return keywords.get(lexeme);
        }
        if (operators.containsKey(lexeme)) {
            return operators.get(lexeme);
        }
        if (comparators.containsKey(lexeme)) {
            return comparators.get(lexeme);
        }
        if (specialSymbols.containsKey(lexeme)) {
            return specialSymbols.get(lexeme);
        }

        if (finalState.startsWith("ident")) {
            return TokenEnum.IDENT;
        }
        if (finalState.contains("int_constant")) {
            return TokenEnum.INT_CONSTANT;
        }
        if (finalState.contains("float_constant")) {
            return TokenEnum.FLOAT_CONSTANT;
        }
        if (finalState.contains("string_constant") || finalState.contains("string")) {
            return TokenEnum.STRING_CONSTANT;
        }

        throw new LexicalException(LexicalErrorType.UNRECOGNISED_TOKEN, lexeme, line, column);
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