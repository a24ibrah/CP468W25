/*
 * An example of a simple reflex agent that uses a switch expression 
 * to define the condition-action rules.
 */
class SimpleReflexAgent {
    public SimpleReflexAgent() {
        // No state needed for a stateless agent
    }

    public String rules(String percept) {
        /** 
         * Defines the condition-action rules for the agent using a switch expression.
         */
        return switch (percept) {
            case "dirty" -> "clean";
            case "obstacle" -> "move_around";
            case "empty" -> "do_nothing";
            default -> "unknown_action";
        };
    }

    public void act(String percept) {
        /** 
         * Executes the action based on the current percept.
         */
        String action = rules(percept);
        System.out.println("Perceived: " + percept + " -> Action taken: " + action);
    }

    public static void main(String[] args) {
        SimpleReflexAgent agent = new SimpleReflexAgent();

        // Simulating the agent perceiving different states
        String[] percepts = {"dirty", "obstacle", "empty"};

        for (String percept : percepts) {
            agent.act(percept); // Pass the percept directly
        }
    }
}
