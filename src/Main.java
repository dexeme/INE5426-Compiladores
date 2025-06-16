import AL.Automaton;
import AL.AutomatonReader;
import AL.Token;
import AL.LexicalAnalyzer;
import Constants.Messages;
import Syntax.Parser;
import AST.ProgramNode;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String automatonFilePath = "resources/automaton.json";

        Automaton automaton = AutomatonReader.readAutomaton(automatonFilePath);

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(automaton);

        Map<Integer, String> sourceCode = new LinkedHashMap<>();
        try {
            String inputFilePath = "resources/instances/simpleCode1.txt";
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

        List<Token> tokens = lexicalAnalyzer.analyzeCode(sourceCode);
        for (Token token : tokens) {
            System.out.println(token);
        }

        if (!lexicalAnalyzer.getErrors().isEmpty()) {
            System.out.println(Messages.TOTAL_LEXICAL_ERRORS + lexicalAnalyzer.getErrors().size());
        }

        Parser parser = new Parser(tokens);
        ProgramNode program = parser.parse();
        System.out.println(Messages.AST_HEADER);
        System.out.println(program.toTree());
    }
}
