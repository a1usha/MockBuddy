package ru.nsu.sd.MockBuddy.internal.matching.matchers;

/**
 * Checks if an argument is of the appropriate type
 */
public class InstanceMatcher implements ArgumentMatcher {

    private final Class<?> clazz;

    public InstanceMatcher(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean matches(Object arg) {
        return (arg != null) && clazz.isAssignableFrom(arg.getClass());
    }

    @Override
    public String toString() {
        return "InstanceMatcher{" +
                "clazz=" + clazz +
                '}';
    }
}
