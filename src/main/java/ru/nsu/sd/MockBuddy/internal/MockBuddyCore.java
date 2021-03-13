package ru.nsu.sd.MockBuddy.internal;

import ru.nsu.sd.MockBuddy.internal.creation.ByteBuddyMockMaker;
import ru.nsu.sd.MockBuddy.internal.stubbing.Stubber;

import java.lang.reflect.InvocationTargetException;

public class MockBuddyCore {

    public <T> T mock(Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return ByteBuddyMockMaker.mock(clazz, MockingInfo.getLastMockInvocationHandler());
    }

    public <T> Stubber<T> when(T obj) {
        return new Stubber<>();
    }
}
