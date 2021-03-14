package ru.nsu.sd.MockBuddy.internal.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ArgumentMatcherStorage {

    private final Stack<ArgumentMatcher> matcherStack = new Stack<>();

    public void addMatcher(ArgumentMatcher argumentMatcher) {
        matcherStack.push(argumentMatcher);
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
