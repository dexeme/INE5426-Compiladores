package Lexical;

public record Token(TokenEnum type, String value, int line, int column) {

    public static Token endToken() {
        return new Token(TokenEnum.END, "", -1, -1);
    }

    @Override
    public String toString() {
        return String.format("%-12s '%s' (line %d, col %d)", type, value, line, column);
    }
}