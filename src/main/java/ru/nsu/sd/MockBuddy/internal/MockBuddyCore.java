package ru.nsu.sd.MockBuddy.internal;

import ru.nsu.sd.MockBuddy.internal.creation.ByteBuddyMockMaker;
import ru.nsu.sd.MockBuddy.internal.handling.DelegationStrategy;
import ru.nsu.sd.MockBuddy.internal.stubbing.Stubber;

public class MockBuddyCore {

    public <T> T mock(Class<T> clazz) {
        return ByteBuddyMockMaker.mock(clazz, DelegationStrategy.RETURN_NULL);
    }

    public <T> T spy(Class<T> clazz) {
        return ByteBuddyMockMaker.mock(clazz, DelegationStrategy.CALL_REAL_METHODS);
    }

    public <T> T spy(T obj) {
        return ByteBuddyMockMaker.spy(obj, MockingInfo.getLastMockInvocationHandler());
    }

    public <T> Stubber<T> when(T obj) {
        return new Stubber<>();
    }
}
