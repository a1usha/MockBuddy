package ru.nsu.sd.MockBuddy.internal.matching.matchers;

public abstract class CompareTo<T extends Comparable<T>> implements ArgumentMatcher<T> {

    private final T wanted;

    public CompareTo(T wanted) {
        this.wanted = wanted;
    }

    @Override
    public final boolean matches(T actual) {
        if (actual == null)
            return false;

        if (!actual.getClass().isInstance(wanted))
            return false;

        return interpretResult(actual.compareTo(wanted));
    }

    protected abstract boolean interpretResult(int result);
}
