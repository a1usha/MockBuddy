package ru.nsu.sd.MockBuddy.internal.matching.matchers;

public class Or implements ArgumentMatcher {

    private final ArgumentMatcher m1;
    private final ArgumentMatcher m2;

    public Or(ArgumentMatcher m1, ArgumentMatcher m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public boolean matches(Object arg) {
        return m1.matches(arg) || m2.matches(arg);
    }

    @Override
    public String toString() {
        return "Or{" +
                "m1=" + m1 +
                ", m2=" + m2 +
                '}';
    }
}
