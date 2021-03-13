package ru.nsu.sd.MockBuddy.internal;

import ru.nsu.sd.MockBuddy.internal.handling.MockInvocationHandler;
import ru.nsu.sd.MockBuddy.internal.creation.ByteBuddyMockMaker;
import ru.nsu.sd.MockBuddy.internal.stubbing.Stubber;

public class MockingInfo {

    private static MockInvocationHandler lastMockInvocationHandler;

    static {
        lastMockInvocationHandler = new MockInvocationHandler();
    }

    public static MockInvocationHandler getLastMockInvocationHandler() {
        return lastMockInvocationHandler;
    }

    public static void setLastMockInvocationHandler(MockInvocationHandler handler) {
        lastMockInvocationHandler = handler;
    }
}
