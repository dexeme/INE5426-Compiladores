import Lexical.Token;
import AL.LexicalAnalyzer;
import Constants.Messages;
import AS.SyntaxAnalyzer;
import AST.ProgramNode;
import AST.GraphvizVisualizer;
import AS.SyntaxException;
import Semantics.SemanticAnalyzer;
import CodeGeneration.IntermediateCodeGenerator;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Usage: java Main <input_file> [--showTree]");
            return;
        }

        String inputFilePath = args[0];
        if (!inputFilePath.endsWith(".txt")) {
            System.out.println("Input file must have .txt extension");
            return;
        }
        boolean showTree = args.length == 2 && args[1].equals("--showTree");
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

        long startTime = System.currentTimeMillis();
        List<Token> tokens = lexicalAnalyzer.analyzeCode(inputFilePath);
        System.out.println(Messages.NO_LEXICAL_ERRORS);
        System.out.println("Number of tokens: " + tokens.size());

        if (!lexicalAnalyzer.getErrors().isEmpty()) {
            System.out.println(Messages.TOTAL_LEXICAL_ERRORS + lexicalAnalyzer.getErrors().size());
            return;
        }

        SyntaxAnalyzer syntax = new SyntaxAnalyzer(tokens);
        ProgramNode ast;
        try {
            ast = syntax.parse();
        } catch (SyntaxException e) {
            System.out.println("Syntax error: \n" + e.getMessage());
            return;
        }
        System.out.println();
        System.out.println(Messages.NO_SYNTAX_ERRORS);
        System.out.println(Messages.AST_HEADER);
        System.out.println(ast.toTree());
        if (showTree) {
            GraphvizVisualizer.show(ast);
        }

        SemanticAnalyzer sem = new SemanticAnalyzer();
        sem.analyze(ast);
        if (!sem.getErrors().isEmpty()) {
            System.out.println(Messages.NO_SEMANTIC_ERRORS + sem.getErrors().size());
            for (var e : sem.getErrors()) System.out.println(e.getMessage());
            return;
        }

        System.out.println(Messages.NO_SEMANTIC_ERRORS);
        IntermediateCodeGenerator icg = new IntermediateCodeGenerator();
        List<String> intermediateCode = icg.generate(ast);
        if (intermediateCode.isEmpty()) {
            return;
        }
        System.out.println("Generated Intermediate Code:");
        for (String instruction : intermediateCode) {
            System.out.println(instruction);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\n===== Symbol tables (one per scope) =====");
        sem.printScopeTree();
        System.out.println("Compilation time: " + (endTime - startTime) + " ms");
    }
}