package ru.nsu.sd.MockBuddy;

import ru.nsu.sd.MockBuddy.internal.MockBuddyCore;
import ru.nsu.sd.MockBuddy.internal.stubbing.Stubber;

public class MockBuddy {

    private static final MockBuddyCore mockBuddyCore;

    static {
        mockBuddyCore = new MockBuddyCore();
    }

    public static <T> T mock(Class<T> clazz) {
        return mockBuddyCore.mock(clazz);
    }

    public static <T> Stubber<T> when(T obj) {
        return mockBuddyCore.when(obj);
    }
}
