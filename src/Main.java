import AL.Automaton;
import AL.AutomatonReader;
import AL.Lexeme;
import AL.LexicalAnalyzer;
import Constants.Messages;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String automatonFilePath = "AL/automaton.json";

        Automaton automaton = AutomatonReader.readAutomaton(automatonFilePath);

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(automaton);

        Map<Integer, String> sourceCode = new LinkedHashMap<>();
        try {
            String inputFilePath = "Constants/Instances/simpleCode1WithLexicalErrors.txt";
            Scanner scanner = new Scanner(new java.io.File(inputFilePath));
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sourceCode.put(lineNumber++, line);
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println(Messages.ERROR_READING_FILE + e.getMessage());
            return;
        }

        List<Lexeme> lexemes = lexicalAnalyzer.analyzeCode(sourceCode);
        for (Lexeme lexeme : lexemes) {
            System.out.println(lexeme);
        }

        if (!lexicalAnalyzer.getErrors().isEmpty()) {
            System.out.println(Messages.TOTAL_LEXICAL_ERRORS + lexicalAnalyzer.getErrors().size());
        }
    }
}
