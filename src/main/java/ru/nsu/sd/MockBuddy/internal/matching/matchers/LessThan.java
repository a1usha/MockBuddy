package ru.nsu.sd.MockBuddy.internal.matching.matchers;

public class LessThan<T extends Comparable<T>> extends CompareTo<T> {

    public LessThan(T wanted) {
        super(wanted);
    }

    @Override
    protected boolean interpretResult(int result) {
        return result < 0;
    }
}
