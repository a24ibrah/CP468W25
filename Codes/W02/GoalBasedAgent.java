import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Goal (goal): The target state or condition that the agent is trying to achieve, 
 * defined as a string (e.g., "EatingCookie").
 */
class GoalBasedAgent {
    private final Map<String, Boolean> currentState; // Current state of the agent
    private final List<Action> availableActions; // List of possible actions
    private String goal; // Goal the agent is trying to achieve

    public GoalBasedAgent(Map<String, Boolean> initialState) {
        this.currentState = new HashMap<>(initialState);
        this.availableActions = new ArrayList<>();
        initializeActions();
    }

    private void initializeActions() {
        // Define available actions with their preconditions and effects
        availableActions.add(new Action("TakeCookie", Map.of("CookieInHand", false), Map.of("CookieInHand", true)));
        availableActions.add(new Action("EatCookie", Map.of("CookieInHand", true), Map.of("EatingCookie", true, "CookieInHand", false)));
        availableActions.add(new Action("GetWater", Map.of("WaterInHand", false), Map.of("WaterInHand", true)));
        availableActions.add(new Action("DrinkWater", Map.of("WaterInHand", true), Map.of("DrinkingWater", true, "WaterInHand", false)));
    /*
     * Each Action represents a possible behavior of the agent, defined by:
     - A name: The action's identifier (e.g., "TakeCookie").
     - Preconditions: The conditions that must be true in the agent's current state
      for the action to be executable.
     - Effects: The changes to the agent's state after the action is executed.
     */
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void act() {
        for (Action action : availableActions) {
            if (action.canExecute(currentState)) {
                System.out.println("Executing action: " + action.getName());
                action.execute(currentState);
                if (goalAchieved()) {
                    System.out.println("Goal achieved: " + goal);
                    break;
                }
            }
        }
    }

    private boolean goalAchieved() {
        return currentState.getOrDefault(goal, false);
    }

    public static void main(String[] args) {
        // Initial state of the agent
        Map<String, Boolean> initialState = Map.of(
            "CookieInHand", false,
            "WaterInHand", false,
            "EatingCookie", false,
            "DrinkingWater", false
        );

        GoalBasedAgent agent = new GoalBasedAgent(initialState);
        agent.setGoal("EatingCookie");

        // Simulate the agent's actions
        while (!agent.goalAchieved()) {
            agent.act();
        }
    }
}

/*The Action class encapsulates the details of an action, 
including its name, preconditions, and effects. */
class Action {
    private final String name; // Name of the action
    private final Map<String, Boolean> preconditions; // Preconditions for the action
    private final Map<String, Boolean> effects; // Effects of the action

    public Action(String name, Map<String, Boolean> preconditions, Map<String, Boolean> effects) {
        this.name = name;
        this.preconditions = new HashMap<>(preconditions);
        this.effects = new HashMap<>(effects);
    }

    public String getName() {
        return name;
    }

    public boolean canExecute(Map<String, Boolean> currentState) {
        // Check if all preconditions are satisfied
        for (Map.Entry<String, Boolean> entry : preconditions.entrySet()) {
            if (!currentState.getOrDefault(entry.getKey(), false).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    public void execute(Map<String, Boolean> currentState) {
        // Apply effects to the current state
        for (Map.Entry<String, Boolean> entry : effects.entrySet()) {
            currentState.put(entry.getKey(), entry.getValue());
        }
    }
}

/*
Simulation:

Initial State: {"CookieInHand": false, "WaterInHand": false, "EatingCookie": false, "DrinkingWater": false}
Goal: "EatingCookie"

Actions Taken:
"TakeCookie" (preconditions met, "CookieInHand" updated to true).
"EatCookie" (preconditions met, "EatingCookie" updated to true).

Expected Output:
Executing action: TakeCookie
Executing action: EatCookie
Goal achieved: EatingCookie
 */

 /*
  * Limitations
  - No Learning:The agent cannot learn new actions or 
  modify its behavior based on experience.
  
  * Complexity:For large environments with many states and actions, 
  maintaining and evaluating preconditions/effects can become 
  computationally expensive.

  * Reactive Behavior: The agent reacts to the current state and 
  does not anticipate future states or plan beyond its immediate goal.
  */