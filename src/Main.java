import AL.Automaton;
import AL.AutomatonReader;
import AL.Lexeme;
import AL.LexicalAnalyzer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String automatonFilePath = "AL/automaton.json";

        Automaton automaton = AutomatonReader.readAutomaton(automatonFilePath);

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(automaton);

        Map<Integer, String> sourceCode = new LinkedHashMap<>();
        sourceCode.put(1, "def main()");
        sourceCode.put(2, "    x = 10");
        sourceCode.put(3, "    if x > 5");
        sourceCode.put(4, "        print(x)");

        List<Lexeme> lexemes = lexicalAnalyzer.analyzeCode(sourceCode);

        for (Lexeme lexeme : lexemes) {
            System.out.println(lexeme);
        }
    }
}
