package aima.core.search.online;

import aima.core.api.agent.Action;
import aima.core.api.search.OnlineSearchProblem;
import aima.core.util.datastructure.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Paul Anton
 */
public class OnlineDFSAgent<P, S> implements aima.core.api.search.online.OnlineDFSAgent<P, S> {

    private Function<P, S> perceptToStateFunction;
    private Map<Pair<S, Action>, S> result;
    private Map<S, List<Action>> untried;
    private Map<S, List<S>> unbacktracked;
    private OnlineSearchProblem<S> onlineSearchProblem;
    private S state;
    private Action action;

    public OnlineDFSAgent (Function<P, S> perceptToStateFunction, OnlineSearchProblem<S> onlineSearchProblem) {
        this.perceptToStateFunction = perceptToStateFunction;
        this.onlineSearchProblem = onlineSearchProblem;
        setState(null);
        setAction(null);
    }
    @Override
    public OnlineSearchProblem getOnlineSearchProblem() {
        return onlineSearchProblem;
    }

    @Override
    public void setOnlineSearchProblem(OnlineSearchProblem onlineSearchProblem) {
        this.onlineSearchProblem = onlineSearchProblem;
    }

    @Override
    public Map getResult() {
        return null;
    }

    @Override
    public Map getUntried() {
        return null;
    }

    @Override
    public Map getUnbacktracked() {
        return null;
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public void setState(S state) {
        this.state = state;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public S getStateFromPercept(P percept) {
        return perceptToStateFunction.apply(percept);
    }

}
