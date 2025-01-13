class SimpleReflexAgent {
    private String state;  // Current state of the environment

    public SimpleReflexAgent() {
        this.state = null;  // Initialize state
    }

    public void perceive(String percept) {
        /** 
         * Interprets the current percept and updates the state.
         */
        this.state = percept;
    }

    public String rules() {
        /** 
         * Defines the condition-action rules for the agent using a switch expression.
         */
        return switch (this.state) {
            case "dirty" -> "clean";
            case "obstacle" -> "move_around";
            case "empty" -> "do_nothing";
            default -> "unknown_action";
        };
    }

    public void act() {
        /** 
         * Executes the action based on the current state.
         */
        String action = rules();
        System.out.println("Action taken: " + action);
    }

    public static void main(String[] args) {
        SimpleReflexAgent agent = new SimpleReflexAgent();

        // Simulating the agent perceiving different states
        String[] percepts = {"dirty", "obstacle", "empty"};

        for (String percept : percepts) {
            agent.perceive(percept);
            agent.act();
        }
    }
}
