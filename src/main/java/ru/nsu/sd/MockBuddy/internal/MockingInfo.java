package ru.nsu.sd.MockBuddy.internal;

import ru.nsu.sd.MockBuddy.internal.handling.MockInvocationHandler;
import ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatcherStorage;

public class MockingInfo {

    private static final ArgumentMatcherStorage argumentMatcherStorage;
    private static MockInvocationHandler lastMockInvocationHandler;

    static {
        lastMockInvocationHandler = new MockInvocationHandler();
        argumentMatcherStorage = new ArgumentMatcherStorage();
    }

    public static MockInvocationHandler getLastMockInvocationHandler() {
        return lastMockInvocationHandler;
    }

    public static void setLastMockInvocationHandler(MockInvocationHandler handler) {
        lastMockInvocationHandler = handler;
    }

    public static ArgumentMatcherStorage getArgumentMatcherStorage() {
        return argumentMatcherStorage;
    }
}
