package ru.nsu.sd.MockBuddy.internal.matching.matchers;

/**
 * Allows creating customized argument matchers
 */
public interface ArgumentMatcher {

    /**
     * Checks and informs if an argument meets conditions
     * @param arg argument to check
     * @return true if argument meets conditions, otherwise false
     */
    boolean matches(Object arg);
}
