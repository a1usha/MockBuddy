package ru.nsu.sd.MockBuddy.internal.stubbing;

import ru.nsu.sd.MockBuddy.internal.MockingInfo;

public class Stubber<T> {

    public void thenReturn(T retObj) {
        MockingInfo.getLastMockInvocationHandler().setRetObj(retObj);
    }

    public void invokeRealMethod() {
        MockingInfo.getLastMockInvocationHandler().setRealMethodInvocation();
    }

    public void thenThrow(Throwable throwable) {
        MockingInfo.getLastMockInvocationHandler().setThrowable(throwable);
    }
}
