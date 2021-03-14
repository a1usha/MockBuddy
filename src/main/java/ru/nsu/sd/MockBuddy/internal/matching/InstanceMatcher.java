package ru.nsu.sd.MockBuddy.internal.matching;

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
