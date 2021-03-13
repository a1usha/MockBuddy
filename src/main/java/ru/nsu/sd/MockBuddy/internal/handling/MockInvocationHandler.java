package ru.nsu.sd.MockBuddy.internal.handling;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class MockInvocationHandler {

    private Method lastMethod;
    private Object[] lastArgs;
    private final List<DataHolder> dataHolders = new ArrayList<>();

    @RuntimeType
    public Object invoke(@SuperCall Callable<?> zuper, @Origin Method method, @AllArguments Object[] args) throws Throwable {

//        MockingInfo.setLastMockInvocationHandler(this);
        lastMethod = method;
        lastArgs = args;

        // checks if the method was already called with the given arguments
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder.getMethod().equals(method) && Arrays.deepEquals(dataHolder.getArgs(), args)) {
                if (dataHolder.getRealMethod().equals(true)) {

                    System.out.println("real method");
                    return zuper.call();

                } else {
                    return dataHolder.getRetObj();
                }
            }
        }

        // otherwise return null
        return null;
    }

    public void setRetObj(Object retObj) {
        dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));
        dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj));
    }

    public void setRealMethodInvocation() {
        dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));
        dataHolders.add(new DataHolder(lastMethod, lastArgs, null, true));
    }
}
