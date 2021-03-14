package ru.nsu.sd.MockBuddy.internal.handling;

import net.bytebuddy.implementation.bind.annotation.*;
import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;

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

//        List<ArgumentMatcher> argumentMatchersList = MockingInfo.getArgumentMatcherStorage().pullMatchers();
//        System.out.println(argumentMatchersList);
//        System.out.println(Arrays.toString(args));

//        System.out.println(dataHolders);

        lastMethod = method;
        lastArgs = args;


//        MockingInfo.setLastMockInvocationHandler(this);


        // checks if the method was already called with the given arguments
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder.getMethod().equals(method)) {
                if (Arrays.deepEquals(dataHolder.getArgs(), args)) {

                    if (dataHolder.isRealMethod()) {
                        System.out.println("real method");
                        return zuper.call();
                    } else {
                        return dataHolder.getRetObj();
                    }

                }
            }
        }

        for (DataHolder dataHolder : dataHolders) {

            if (dataHolder.isWithMatchers()) {
                if (MockingInfo.getArgumentMatcherStorage().getMatcherStack().empty()) {

                    boolean match;
                    for (int i = 0; i < args.length; i++) {

                        match = dataHolder.getLocalArgumentMatchersList().get(i).matches(lastArgs[i]);
                        if (!match) return null;
                    }

                    return dataHolder.getRetObj();

                } else {
                    // Override matcher
                    return null;
                }
            }
        }

        // otherwise return null
        return null;
    }

    public void setRetObj(Object retObj) {
        dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));

        List<ArgumentMatcher> argumentMatchersList = MockingInfo.getArgumentMatcherStorage().pullMatchers();
        if (argumentMatchersList != null) {

                if (argumentMatchersList.size() == lastArgs.length) {
                    // Save matchers
                    dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj, argumentMatchersList));
                } else {
                    System.out.println(argumentMatchersList);
                    System.out.println(lastArgs.length);
                    throw new IllegalArgumentException("Use only ALL arguments as matchers, or ALL regular values");
                }

        } else {
            dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj, (List<ArgumentMatcher>) null));
        }
    }

    public void setRealMethodInvocation() {
        MockingInfo.getArgumentMatcherStorage().pullMatchers();
        dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));
        dataHolders.add(new DataHolder(lastMethod, lastArgs, null, true));
    }
}
