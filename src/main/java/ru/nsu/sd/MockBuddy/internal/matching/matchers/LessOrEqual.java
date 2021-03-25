package ru.nsu.sd.MockBuddy.internal.matching.matchers;

public class LessOrEqual<T extends Comparable<T>> extends CompareTo<T> {

    public LessOrEqual(T wanted) {
        super(wanted);
    }

    @Override
    protected boolean interpretResult(int result) {
        return result <= 0;
    }
}
