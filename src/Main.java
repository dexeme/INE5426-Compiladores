import Lexical.Automaton;
import Lexical.AutomatonReader;
import Lexical.Token;
import AL.LexicalAnalyzer;
import Constants.Messages;
import AS.SyntaxAnalyzer;
import AST.ProgramNode;
import Semantics.SemanticAnalyzer;
import CodeGeneration.IntermediateCodeGenerator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String automatonFilePath = "resources/automaton.json";

        Automaton automaton = AutomatonReader.readAutomaton(automatonFilePath);

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(automaton);

        Map<Integer, String> sourceCode = new LinkedHashMap<>();
        try {
            String inputFilePath = "resources/instances/problematicCode3.txt";
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
        System.out.println(Messages.SYMBOL_TABLE_HEADER);
        for (Token token : tokens) {
            System.out.println(token);
        }

        if (!lexicalAnalyzer.getErrors().isEmpty()) {
            System.out.println(Messages.TOTAL_LEXICAL_ERRORS + lexicalAnalyzer.getErrors().size());
        }

        SyntaxAnalyzer syntax = new SyntaxAnalyzer(tokens);
        ProgramNode ast = syntax.parse();
        if (!syntax.getErrors().isEmpty()) {
            System.out.println(Messages.TOTAL_SYNTAX_ERRORS + syntax.getErrors().size());
        } else {
            System.out.println(Messages.NO_SYNTAX_ERRORS);
            System.out.println(Messages.AST_HEADER);
            System.out.println(ast.toTree());
            SemanticAnalyzer sem = new SemanticAnalyzer();
            sem.analyze(ast);
            if (!sem.getErrors().isEmpty()) {
                System.out.println(Messages.TOTAL_SEMANTIC_ERRORS + sem.getErrors().size());
                for (var e : sem.getErrors()) System.out.println(e.getMessage());
            }  else {
                System.out.println(Messages.TOTAL_SEMANTIC_ERRORS + 0);

                IntermediateCodeGenerator icg = new IntermediateCodeGenerator();
                List<String> intermediateCode = icg.generate(ast);

                if (intermediateCode.isEmpty()) {
                    return;
                }
                System.out.println("Generated Intermediate Code:");
                for (String instruction : intermediateCode) {
                    System.out.println(instruction);
                }
            }
        }
    }
}