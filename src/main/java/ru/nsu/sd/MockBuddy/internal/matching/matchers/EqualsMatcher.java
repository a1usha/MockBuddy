package ru.nsu.sd.MockBuddy.internal.matching.matchers;

/**
 * Checks if an argument is equal to some value
 */
public class EqualsMatcher implements ArgumentMatcher {

    private final Object wanted;

    public EqualsMatcher(Object wanted) {
        this.wanted = wanted;
    }

    @Override
    public boolean matches(Object arg) {
        return areEqual(wanted, arg);
    }

    private boolean areEqual(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 == null || o2 == null) {
            return false;
        } else {
            return o1.equals(o2);
        }
    }

    @Override
    public String toString() {
        return "EqualsMatcher{" +
                "wanted=" + wanted +
                '}';
    }
}
