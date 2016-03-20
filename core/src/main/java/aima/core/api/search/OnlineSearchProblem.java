package aima.core.api.search;

import aima.core.api.agent.Action;

import java.util.Set;

/**
 * Artificial Intelligence A Modern Approach (4th Edition): page ??.<br>
 * <br>
 * An online search problem must be solved by an agent executing actions, rather
 * than by pure computation. We assume a deterministic and fully observable
 * environment (Chapter 17 relaxes these assumptions), but we stipulate that the
 * agent knows only the following: <br>
 * <ul>
 * <li>ACTIONS(s), which returns a list of actions allowed in state s;</li>
 * <li>The step-cost function c(s, a, s') - note that this cannot be used until
 * the agent knows that s' is the outcome; and</li>
 * <li>GOAL-TEST(s).</li>
 * </ul>
 *
 * @param <S> the type of the initial state that the agent starts in.
 * @author Paul Anton
 */
public interface OnlineSearchProblem<S> {

    /**
     * A description of the possible actions available to the agent.
     *
     * @param s a particular state s
     * @return the set of actions that can be executed in s.
     */
    Set<Action> actions(S s);

    /**
     * Calculates the step-cost between two states. Used to assign a
     * numeric cost to each path.
     *
     * @param s      the starting state.
     * @param a      the action performed in state s.
     * @param sPrime the resulting state from performing action a.
     * @return the step cost of taking action a in state s to reach state s'.
     */
    double stepCost(S s, Action a, S sPrime);

    /**
     * The goal test, which determines if a given state is a goal state.
     *
     * @param state a state to be tested.
     * @return true if the given state is a goal state, false otherwise.
     */
    boolean isGoalState(S state);
}
