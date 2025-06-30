import Lexical.Automaton;
import Lexical.AutomatonReader;
import Lexical.Token;
import AL.LexicalAnalyzer;
import Constants.Messages;
import AS.SyntaxAnalyzer;
import AST.ProgramNode;
import AST.GraphvizVisualizer;
import Semantics.SemanticAnalyzer;
import Util.ErrorFormatter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String automatonFilePath = "resources/automaton.json";

        String inputFile = "resources/instances/simpleCode1WSemErrorXNotDeclared.txt";
        boolean showTree = true;

        Automaton automaton = AutomatonReader.readAutomaton(automatonFilePath);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(automaton);

        System.out.println("Starting compilation of " + inputFile);

        Map<Integer, String> sourceCode = new LinkedHashMap<>();
        try (Scanner scanner = new Scanner(new java.io.File(inputFile))) {
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                sourceCode.put(lineNumber++, scanner.nextLine());
            }
        } catch (Exception e) {
            System.err.println(Messages.ERROR_READING_FILE + e.getMessage());
            return;
        }

        System.out.println("\n[1] Lexical analysis");
        List<Token> tokens = lexicalAnalyzer.analyzeCode(sourceCode);
        for (Token token : tokens) {
            System.out.println("  " + token);
        }
        System.out.println(Messages.TOTAL_LEXICAL_ERRORS + lexicalAnalyzer.getErrors().size());
        for (var e : lexicalAnalyzer.getErrors()) ErrorFormatter.print(e, sourceCode);

        System.out.println("\n[2] Syntax analysis");
        SyntaxAnalyzer syntax = new SyntaxAnalyzer(tokens);
        ProgramNode ast = syntax.parse();
        System.out.println(Messages.TOTAL_SYNTAX_ERRORS + syntax.getErrors().size());
        for (var e : syntax.getErrors()) ErrorFormatter.print(e, sourceCode);

        if (syntax.getErrors().isEmpty()) {
            System.out.println(Messages.NO_SYNTAX_ERRORS);
            System.out.println("\n[3] Semantic analysis");
            SemanticAnalyzer sem = new SemanticAnalyzer();
            sem.analyze(ast);
            System.out.println(Messages.TOTAL_SEMANTIC_ERRORS + sem.getErrors().size());
            for (var e : sem.getErrors()) ErrorFormatter.print(e, sourceCode);

            System.out.println("\nAST:");
            System.out.println(ast.toTree());

            if (showTree) {
                GraphvizVisualizer.show(ast);
            }
        }
    }
}