package aima.core.api.search.online;

import aima.core.api.agent.Action;
import aima.core.api.agent.Agent;
import aima.core.api.search.OnlineSearchProblem;
import aima.core.util.datastructure.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * /**
 * Artificial Intelligence A Modern Approach (4th Edition): Figure ??, page
 * ??.<br>
 * <br>
 *
 * <pre>
 * function ONLINE-DFS-AGENT(s') returns an action
 *   inputs: s', a percept that identifies the current state
 *   persistent: result, a table, indexed by state and action, initially empty
 *               untried, a table that lists, for each state, the actions not yet tried
 *               unbacktracked, a table that lists, for each state, the backtracks not yet tried
 *               s, a, the previous state and action, initially null
 *
 *   if GOAL-TEST(s') then return stop
 *   if s' is a new state (not in untried) then untried[s'] &lt;- ACTIONS(s')
 *   if s is not null then
 *       result[s, a] &lt;- s'
 *       add s to the front of the unbacktracked[s']
 *   if untried[s'] is empty then
 *       if unbacktracked[s'] is empty then return stop
 *       else a &lt;- an action b such that result[s', b] = POP(unbacktracked[s'])
 *   else a &lt;- POP(untried[s'])
 *   s &lt;- s'
 *   return a
 * </pre>
 *
 * Figure ?? An online search agent that uses depth-first exploration. The
 * agent is applicable only in state spaces in which every action can be
 * "undone" by some other action.<br>
 *
 * @author Paul Anton
 */
public interface OnlineDFSAgent<P, S> extends Agent<P> {

    OnlineSearchProblem<S> getOnlineSearchProblem();

    void setOnlineSearchProblem(OnlineSearchProblem<S> onlineSearchProblem);

    // persistent: result, a table, indexed by state and action, initially empty
    //             untried, a table that lists, for each state, the actions not yet tried
    //             unbacktracked, a table that lists,
    //             for each state, the backtracks not yet tried
    //             s, a, the previous state and action, initially null
    Map<Pair<S, Action>, S> getResult();

    Map<S, List<Action>> getUntried();

    Map<S, List<S>> getUnbacktracked();

    S getState();

    void setState(S state);

    Action getAction();

    void setAction(Action action);

    // function ONLINE-DFS-AGENT(s') returns an action
    // inputs: s', a percept that identifies the current state
    @Override
    default Action perceive(P percept) {
        S state = getStateFromPercept(percept);
        Map<S, List<S>> unbacktracked = getUnbacktracked();
        //Map<Pair<S, Action>, S> result = getResult();
        if (getOnlineSearchProblem().isGoalState(state)) {
            setAction(Action.NoOp);
        } else {
            // if s' is a new state (not in untried) then untried[s'] <-
            // ACTIONS(s')
            if (!getUntried().containsKey(state)) {
                Set<Action> actions = getOnlineSearchProblem().actions(state);
                ArrayList<Action> actionss = new ArrayList<>(actions);
                getUntried().put(state, actionss);
            }

            // if s is not null then do
            if (getState() != null) {
                if (!(state.equals(getResult().get(new Pair<>(getState(), getAction()))))) {
                    // result[s, a] <- s'
                    getResult().put(new Pair<>(getState(), getAction()), state);

                    if (!unbacktracked.containsKey(state)) {
                        unbacktracked.put(state, new ArrayList<>());
                    }
                    // add s to the front of the unbacktracked[s']
                    unbacktracked.get(state).add(0, getState());
                }
            }
            // if untried[s'] is empty then
            if (getUntried().get(state).isEmpty()) {
                // if unbacktracked[s'] is empty then return stop
                if (unbacktracked.get(state).isEmpty()) {
                    setAction(Action.NoOp);
                } else {
                    // else a <- an action b such that result[s', b] =
                    // POP(unbacktracked[s'])
                    S popped = unbacktracked.get(state).remove(0);
                    for (Pair<S, Action> sa : getResult().keySet()) {
                        if (sa.getFirst().equals(state) && getResult().get(sa).equals(popped)) {
                            setAction(sa.getSecond());
                            break;
                        }
                    }
                }
            } else {
                // else a <- POP(untried[s'])
                setAction(getUntried().get(state).remove(0));
            }
        }

        // s <- s'
        setState(state);
        // return a
        return getAction();
    }

    /**
     * Get the problem state associated with a Percept.
     *
     * @param percept the percept to be transformed to a problem state.
     * @return a problem state derived from the Percept p.
     */
    S getStateFromPercept(P percept);
}
