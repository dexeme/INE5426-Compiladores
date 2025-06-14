package AL;

public record Lexeme(TokenEnum type, String value, int line, int column) {

    @Override
    public String toString() {
        return type + ", \"" + value + "\", " + line + ", " + column;
    }
}
