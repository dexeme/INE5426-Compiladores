package AL;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Automaton {
    private final Set<String> states;
    private final Map<TransitionKey, String> transitions;
    private final String initialState;
    private final List<String> finalStates;

    public Automaton(Set<String> states, Map<TransitionKey, String> transitions, String initialState, List<String> finalStates) {
        this.states = states;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

    public void printAutomaton() {
        System.out.print("Estados: [");
        Iterator<String> it = states.iterator();
        while (it.hasNext()) {
            System.out.print("\"" + it.next() + "\"");
            if (it.hasNext()) System.out.print(",");
        }
        System.out.println("]");
        System.out.println("Estado inicial: \"" + initialState + "\"");
        System.out.print("Estados finais: [");
        Iterator<String> itFinal = finalStates.iterator();
        while (itFinal.hasNext()) {
            System.out.print("\"" + itFinal.next() + "\"");
            if (itFinal.hasNext()) System.out.print(",");
        }
        System.out.println("]");
        System.out.println("Transições:");
        for (Map.Entry<TransitionKey, String> entry : transitions.entrySet()) {
            System.out.println("<\"" + entry.getKey().fromState() + "\",\"" + entry.getKey().symbol() + "\",\"" + entry.getValue() + "\">");
        }
    }

    public String getInitialState() {
        return this.initialState;
    }

    public boolean isFinalState(String state) {
        return this.finalStates.contains(state);
    }

    public String getNextState(String currentState, String symbol) {
        return this.transitions.getOrDefault(new TransitionKey(currentState, symbol), null);
    }
}
