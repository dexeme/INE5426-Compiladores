package AL;

public record Lexeme(TokenEnum type, String value, int lineNumber) {

    @Override
    public String toString() {
        return "(" + type + ", \"" + value + "\", " + lineNumber + ")";
    }
}
