package ru.nsu.sd.MockBuddy.internal.matching.matchers;

public class GreaterOrEqual<T extends Comparable<T>> extends CompareTo<T> implements ArgumentMatcher<T> {

    public GreaterOrEqual(T wanted) {
        super(wanted);
    }

    @Override
    protected boolean interpretResult(int result) {
        return result >= 0;
    }
}
