package Lexical;

import java.util.*;
import java.io.*;

public class AutomatonReader {
    public static Automaton readAutomaton(String filePath) {
        try {
            StringBuilder jsonContent = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line.trim());
            }
            reader.close();

            String content = jsonContent.toString();
            String json = content.substring(content.indexOf("{"), content.lastIndexOf("}") + 1);

            return parseAutomaton(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Automaton parseAutomaton(String json) {
        Set<String> states = new TreeSet<>();
        Map<TransitionKey, String> transitions = new TreeMap<>();

        String initialState = extractValue(json, "\"initial_state\"").trim();
        states.add(initialState);

        List<String> finalStates = extractArray(json, "\"final_states\"");
        states.addAll(finalStates);

        String transitionsBlock = extractBlock(json);
        Map<String[], String> rawTransitions = extractTransitions(transitionsBlock);

        for (Map.Entry<String[], String> entry : rawTransitions.entrySet()) {
            String fromState = clean(entry.getKey()[0]);
            String symbol = clean(entry.getKey()[1]);
            String toState = clean(entry.getValue());

            states.add(fromState);
            states.add(toState);
            transitions.put(new TransitionKey(fromState, symbol), toState);
        }

        return new Automaton(states, transitions, initialState, finalStates);
    }

    private static String clean(String text) {
        return text.replace("\\", "").trim();
    }

    private static String extractValue(String json, String key) {
        int index = json.indexOf(key) + key.length();
        index = json.indexOf("\"", index + 1) + 1;
        int end = json.indexOf("\"", index);
        return json.substring(index, end);
    }

    private static List<String> extractArray(String json, String key) {
        List<String> list = new ArrayList<>();
        int start = json.indexOf(key) + key.length();
        start = json.indexOf("[", start) + 1;
        int end = json.indexOf("]", start);
        String arrayContent = json.substring(start, end).trim();

        if (arrayContent.isEmpty()) return list;

        boolean insideString = false;
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);
            if (c == '\"') {
                insideString = !insideString;
            } else if (c == ',' && !insideString) {
                list.add(current.toString().replace("\\", "").replace("[", "").replace("]", "").replace("\"", "").trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        if (current.length() > 0) {
            list.add(current.toString().replace("\\", "").replace("[", "").replace("]", "").replace("\"", "").trim());
        }
        return list;
    }

    private static String extractBlock(String json) {
        int start = json.indexOf("\"transition_map\"") + "\"transition_map\"".length();
        start = json.indexOf("{", start);
        int braceCount = 1;
        int end = start + 1;
        while (braceCount > 0 && end < json.length()) {
            if (json.charAt(end) == '{') braceCount++;
            else if (json.charAt(end) == '}') braceCount--;
            end++;
        }
        return json.substring(start, end - 1);
    }

    private static Map<String[], String> extractTransitions(String block) {
        Map<String[], String> transitions = new LinkedHashMap<>();

        block = block.trim();
        if (block.startsWith("{")) block = block.substring(1);
        if (block.endsWith("}")) block = block.substring(0, block.length() - 1);

        String[] entries = block.split("],");

        for (String entry : entries) {
            int sep = entry.indexOf(":");
            if (sep == -1) continue;

            String key = entry.substring(0, sep).trim();
            String value = entry.substring(sep + 1).trim();

            // Corrige o final dos entries (pode faltar ])
            if (!key.endsWith("]")) {
                key = key + "]";
            }

            String arrayPart = key.trim();
            arrayPart = arrayPart.substring(1, arrayPart.length() - 1); // remove [ e ]
            String[] parts = parseJsonArray(arrayPart);

            if (parts.length != 2) continue;

            String fromState = parts[0].replace("\\", "").trim();
            String symbol = parts[1].replace("\\", "").trim();

            value = value.replaceAll("[\\[\\]\"]", "").replace("\\", "").trim();

            transitions.put(new String[]{fromState, symbol}, value);
        }
        return transitions;
    }

    private static String[] parseJsonArray(String jsonArray) {
        List<String> result = getStrings(jsonArray);

        for (int i = 0; i < result.size(); i++) {
            String elem = result.get(i).trim();
            if (elem.startsWith("\"") && elem.endsWith("\"")) {
                elem = elem.substring(1, elem.length() - 1);
            }
            elem = elem.replace("\\", "").replace("[", "").replace("]", "").trim();
            result.set(i, elem);
        }
        return result.toArray(new String[0]);
    }

    private static List<String> getStrings(String jsonArray) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideString = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            char c = jsonArray.charAt(i);
            if (c == '\"') {
                insideString = !insideString;
            } else if (c == ',' && !insideString) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result;
    }
}

// esse record aqui serve pra representar a chave de transição
// e implementa Comparable para ordenar as transições
record TransitionKey(String fromState, String symbol) implements Comparable<TransitionKey> {

    @Override
    public int compareTo(TransitionKey o) {
        int cmp = this.fromState.compareTo(o.fromState);
        if (cmp != 0) return cmp;
        return this.symbol.compareTo(o.symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof TransitionKey key) {
            return this.fromState.equals(key.fromState()) &&
                    this.symbol.equals(key.symbol());
        }
        return false;
    }

}
