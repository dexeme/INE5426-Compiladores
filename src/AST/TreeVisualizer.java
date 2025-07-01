package AST;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeVisualizer {

    static class Node {
        String label;
        List<Node> children = new ArrayList<>();

        Node(String label) {
            this.label = label.trim();
        }

        void addChild(Node child) {
            this.children.add(child);
        }

        @Override
        public String toString() {
            return "Node{" + "label='" + label + '\'' + ", children=" + children.size() + '}';
        }
    }

    private static Node parseTree(List<String> lines) {
        if (lines == null || lines.isEmpty() || lines.get(0).trim().isEmpty()) {
            return null;
        }

        Node root = new Node(lines.get(0));
        Stack<Node> parentStack = new Stack<>();
        Stack<Integer> indentStack = new Stack<>();

        parentStack.push(root);
        indentStack.push(0);

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().isEmpty()) {
                continue;
            }

            int currentIndent = getIndentationLevel(line);
            Node currentNode = new Node(line);

            while (!indentStack.isEmpty() && currentIndent <= indentStack.peek()) {
                parentStack.pop();
                indentStack.pop();
            }

            if (!parentStack.isEmpty()) {
                parentStack.peek().addChild(currentNode);
            }

            parentStack.push(currentNode);
            indentStack.push(currentIndent);
        }

        return root;
    }

    private static int getIndentationLevel(String s) {
        int level = 0;
        while (level < s.length() && s.charAt(level) == ' ') {
            level++;
        }
        return level;
    }

    private static String generateDot(Node root) {
        if (root == null) {
            return "";
        }
        StringBuilder dotBuilder = new StringBuilder();
        dotBuilder.append("digraph Tree {\n");
        dotBuilder.append("  node [shape=box, style=\"rounded,filled\", fillcolor=\"lightblue\"];\n");
        dotBuilder.append("  edge [color=\"gray\"];\n");

        AtomicInteger nodeIdCounter = new AtomicInteger(0);

        generateDotRecursive(root, nodeIdCounter, dotBuilder);

        dotBuilder.append("}\n");
        return dotBuilder.toString();
    }

    private static int generateDotRecursive(Node node, AtomicInteger nodeIdCounter, StringBuilder dotBuilder) {
        int currentNodeId = nodeIdCounter.getAndIncrement();

        dotBuilder.append(String.format("  node%d [label=\"%s\"];\n",
                currentNodeId, node.label.replace("\"", "\\\"")));

        for (Node child : node.children) {
            int childNodeId = generateDotRecursive(child, nodeIdCounter, dotBuilder);
            dotBuilder.append(String.format("  node%d -> node%d;\n", currentNodeId, childNodeId));
        }

        return currentNodeId;
    }

    private static boolean runDotCommand(String dotFilePath, String outputImgPath) {
        String command = String.format("dot -Tpng %s -o %s", dotFilePath, outputImgPath);
        try {
            Process process = Runtime.getRuntime().exec(command);

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("DOT STDOUT: " + line);
                    }
                } catch (IOException e) {
                }
            }).start();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println("DOT STDERR: " + line);
                    }
                } catch (IOException e) {
                }
            }).start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return true;
            } else {
                System.err.println("Error executing dot command. Exit code: " + exitCode);
                System.err.println("Please ensure Graphviz is installed and the 'dot' command is in your system's PATH.");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to execute dot command. Is Graphviz installed?");
            e.printStackTrace();
            return false;
        }
    }

    public static void generateTreeImage(String indentedTreeString, boolean saveImage) {
        List<String> lines = List.of(indentedTreeString.split("\n"));

        Node root = parseTree(lines);
        if (root == null) {
            System.err.println("Failed to parse tree. Input might be empty or invalid.");
            return;
        }

        String dotContent = generateDot(root);

        if (saveImage) {
            String dotFilePath = "tree.dot";
            String outputImagePath = "tree.png";
            try (PrintWriter out = new PrintWriter(new FileWriter(dotFilePath))) {
                out.println(dotContent);
                System.out.println("Successfully wrote to " + dotFilePath);
            } catch (IOException e) {
                System.err.println("Error writing to file " + dotFilePath);
                e.printStackTrace();
                return;
            }
            runDotCommand(dotFilePath, outputImagePath);
        } else {
            File dotFile = null;
            File imageFile = null;
            try {
                dotFile = File.createTempFile("graph", ".dot");
                imageFile = File.createTempFile("tree", ".png");

                try (PrintWriter out = new PrintWriter(new FileWriter(dotFile))) {
                    out.println(dotContent);
                }

                if (runDotCommand(dotFile.getAbsolutePath(), imageFile.getAbsolutePath())) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(imageFile);
                    } else {
                        System.err.println("Desktop operations not supported on this system. Cannot open file.");
                        System.out.println("Temporary image at: " + imageFile.getAbsolutePath());
                    }
                }
            } catch (IOException e) {
                System.err.println("An error occurred during temporary file processing.");
                e.printStackTrace();
            }
        }
    }

}
