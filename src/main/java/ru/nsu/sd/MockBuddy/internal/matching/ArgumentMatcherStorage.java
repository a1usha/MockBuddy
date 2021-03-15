package ru.nsu.sd.MockBuddy.internal.matching;

import ru.nsu.sd.MockBuddy.internal.matching.matchers.And;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.Not;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.Or;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Stores matchers before calling stubbing methods (thenReturn).
 * Storage takes place in the Stack, this allows to perform logical operations on matchers.
 * During the call to the stubbing methods, the storage is emptied.
 */
public class ArgumentMatcherStorage {

    private final Stack<ArgumentMatcher> matcherStack = new Stack<>();

    /**
     * Add argument matcher to the storage
     *
     * @param argumentMatcher matcher to add
     */
    public void addMatcher(ArgumentMatcher argumentMatcher) {
        matcherStack.push(argumentMatcher);
    }

    /**
     * Apply AND matcher to the top-2 matchers
     */
    public void performAnd() {
        ArgumentMatcher m1 = getMatcher();
        ArgumentMatcher m2 = getMatcher();

        addMatcher(new And(m1, m2));
    }

    /**
     * Apply OR matcher to the top-2 matchers
     */
    public void performOr() {
        ArgumentMatcher m1 = getMatcher();
        ArgumentMatcher m2 = getMatcher();

        addMatcher(new Or(m1, m2));
    }

    /**
     * Apply NOT matcher to the top matcher
     */
    public void performNot() {
        ArgumentMatcher m1 = getMatcher();

        addMatcher(new Not(m1));
    }

    /**
     * Get last argument matcher
     *
     * @return matcher
     */
    public ArgumentMatcher getMatcher() {
        return matcherStack.pop();
    }

    /**
     * Get all argument matchers
     *
     * @return list with matchers
     */
    public List<ArgumentMatcher> pullMatchers() {
        if (matcherStack.isEmpty()) {
            return null;
        }
        return resetStack();
    }

    /**
     * Clear storage
     *
     * @return list with matchers
     */
    private List<ArgumentMatcher> resetStack() {
        ArrayList<ArgumentMatcher> lastMatchers = new ArrayList<>(matcherStack);
        matcherStack.clear();
        return lastMatchers;
    }

    public Stack<ArgumentMatcher> getMatcherStack() {
        return matcherStack;
    }
}
