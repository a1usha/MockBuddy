package ru.nsu.sd.MockBuddy.internal.matching.matchers;

/**
 * Performs logical NOT with argument matchers
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Not implements ArgumentMatcher<Object> {

    private final ArgumentMatcher m1;

    public Not(ArgumentMatcher<?> m1) {
        this.m1 = m1;
    }

    @Override
    public boolean matches(Object arg) {
        return !m1.matches(arg);
    }

    @Override
    public String toString() {
        return "Not{" +
                "m1=" + m1 +
                '}';
    }
}
