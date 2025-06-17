package test.Syntax;

import AL.*;
import AST.ProgramNode;
import Syntax.Parser;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ParserTest {
    private static final LexicalAnalyzer analyzer;
    static {
        Automaton automaton = AutomatonReader.readAutomaton("resources/automaton.json");
        analyzer = new LexicalAnalyzer(automaton);
    }

    private Parser newParser(String... lines) {
        Map<Integer, String> src = new LinkedHashMap<>();
        for (int i = 0; i < lines.length; i++) {
            src.put(i + 1, lines[i]);
        }
        List<Token> tokens = analyzer.analyzeCode(src);
        return new Parser(tokens);
    }

    private ProgramNode parse(String... lines) {
        Parser parser = newParser(lines);
        return parser.parse();
    }

    @Test
    public void testSimpleProgramAST() {
        ProgramNode program = parse(
                "def main(){",
                "    x = 10",
                "    if x > 5",
                "        print(x)}"
        );
        String tree = program.toTree().trim();
        String expected = String.join("\n",
                "Program",
                "  Function main",
                "    Assignment x",
                "      IntLiteral 10",
                "    If",
                "      BinaryOp >",
                "        Var x",
                "        IntLiteral 5",
                "      Print",
                "        Var x"
        );
        assertEquals(expected, tree);
    }

    @Test
    public void testProgramWithBraces() {
        ProgramNode program = parse(
                "def main(){",
                "    x = 10",
                "    if x > 5",
                "        print(x)}"
        );
        String tree = program.toTree().trim();
        String expected = String.join("\n",
                "Program",
                "  Function main",
                "    Assignment x",
                "      IntLiteral 10",
                "    If",
                "      BinaryOp >",
                "        Var x",
                "        IntLiteral 5",
                "      Print",
                "        Var x"
        );
        assertEquals(expected, tree);
    }

    @Test
    public void testMainWithParametersAndLoop() {
        Parser parser = newParser(
                "def main(int a, int b){",
                "    string operacao;",
                "    int i;",
                "    for (i = 1; i > 0; i = i + 1) {",
                "        print \"Qual operação desejas que eu calcule?\";",
                "        read operacao;",
                "        if (operacao == \"sair\") {",
                "            break;",
                "        } endif",
                "        if (operacao == \"fatorial\") {",
                "            read a;",
                "            int j;",
                "            j = calcularFatorial(a);",
                "        } else {",
                "            read a;",
                "            read b;",
                "            int j;",
                "            j = calcular(operacao, a, b);",
                "        } endif",
                "    }",
                "}"
        );

        ProgramNode program = parser.parse();
        assertFalse(program.getFunctions().isEmpty());
    }
}