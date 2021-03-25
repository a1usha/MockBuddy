package ru.nsu.sd.MockBuddy.internal.matching.matchers;

public class GreaterThan<T extends Comparable<T>> extends CompareTo<T> {

    public GreaterThan(T wanted) {
        super(wanted);
    }

    @Override
    protected boolean interpretResult(int result) {
        return result > 0;
    }
}
