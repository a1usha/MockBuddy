package ru.nsu.sd.MockBuddy.internal;

import ru.nsu.sd.MockBuddy.internal.creation.ByteBuddyMockMaker;
import ru.nsu.sd.MockBuddy.internal.stubbing.Stubber;

public class MockBuddyCore {

    public <T> T mock(Class<T> clazz) {
        return ByteBuddyMockMaker.mock(clazz, MockingInfo.getLastMockInvocationHandler());
    }

    public <T> T spy(T obj) {
        return ByteBuddyMockMaker.spy(obj, MockingInfo.getLastMockInvocationHandler());
    }

    public <T> Stubber<T> when(T obj) {
        return new Stubber<>();
    }
}
