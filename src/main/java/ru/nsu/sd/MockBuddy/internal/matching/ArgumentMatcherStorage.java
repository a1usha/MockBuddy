package ru.nsu.sd.MockBuddy.internal.matching;

import ru.nsu.sd.MockBuddy.internal.matching.matchers.And;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.Not;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.Or;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class ArgumentMatcherStorage {

    private final Stack<ArgumentMatcher> matcherStack = new Stack<>();

    public void addMatcher(ArgumentMatcher argumentMatcher) {
        matcherStack.push(argumentMatcher);
    }

    public void performAnd() throws EmptyStackException {
        ArgumentMatcher m1 = getMatcher();
        ArgumentMatcher m2 = getMatcher();

        addMatcher(new And(m1, m2));
    }

    public void performOr() throws EmptyStackException {
        ArgumentMatcher m1 = getMatcher();
        ArgumentMatcher m2 = getMatcher();

        addMatcher(new Or(m1, m2));
    }

    public void performNot() throws EmptyStackException {
        ArgumentMatcher m1 = getMatcher();

        addMatcher(new Not(m1));
    }

    public ArgumentMatcher getMatcher() {
        return matcherStack.pop();
    }

    public List<ArgumentMatcher> pullMatchers() {
        if (matcherStack.isEmpty()) {
            return null;
        }
        return resetStack();
    }

    private List<ArgumentMatcher> resetStack() {
        ArrayList<ArgumentMatcher> lastMatchers = new ArrayList<>(matcherStack);
        matcherStack.clear();
        return lastMatchers;
    }

    public Stack<ArgumentMatcher> getMatcherStack() {
        return matcherStack;
    }
}
