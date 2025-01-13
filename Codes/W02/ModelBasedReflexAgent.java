import java.util.HashMap;
import java.util.Map;

class ModelBasedReflexAgent {
    private String currentState; // The agent's current understanding of the environment
    private Map<String, String> rules; // Condition-action rules
    // Map: Stores elements in an unordered fashion. It maps keys to values.

    public ModelBasedReflexAgent() {
        currentState = "clean"; // Initial state
        rules = new HashMap<>();
        initializeRules();
    }

    private void initializeRules() {
        // Define condition-action rules
        rules.put("dirty", "clean");
        rules.put("obstacle", "move_around");
        rules.put("empty", "do_nothing");
    }

    public void perceive(String percept) {
        /**
         * Updates the agent's internal state based on the current percept.
         */
        this.currentState = percept;
    }

    public String decideAction() {
        /**
         * Selects an action based on the current state using defined rules.
         */
        return rules.getOrDefault(currentState, "unknown_action");
    }

    public void act() {
        /**
         * Executes the action determined by the current state.
         */
        String action = decideAction();
        System.out.println("Action taken: " + action);
    }

    public static void main(String[] args) {
        
        /* State-Based Behavior: The agent updates its internal 
        currentState based on the percept it receives (perceive method).*/
        
        ModelBasedReflexAgent agent = new ModelBasedReflexAgent();

        // Simulating the agent perceiving different states
        String[] percepts = {"dirty", "obstacle", "empty"};

        for (String percept : percepts) {
            agent.perceive(percept);
            agent.act();
        }
    }
}
/*Limitations
No Long-Term Memory: The agent only remembers the latest percept. 
It does not retain a history of past states or actions, 
so it cannot handle situations requiring sequential reasoning.

Static Rules: The agent's decision-making is limited to the predefined 
condition-action rules in the rules map. 
It cannot learn or adapt to new situations dynamically. */
