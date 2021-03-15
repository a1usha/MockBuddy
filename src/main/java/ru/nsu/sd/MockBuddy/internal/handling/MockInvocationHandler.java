package ru.nsu.sd.MockBuddy.internal.handling;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Method invocation handler.
 * Retains the desired behavior of the method or returns it
 * according to the method, arguments and argument matchers.
 */
public class MockInvocationHandler {

    private final List<DataHolder> dataHolders = new ArrayList<>();
    private Method lastMethod;
    private Object[] lastArgs;

    @RuntimeType
    public Object invoke(@SuperCall Callable<?> zuper, @Origin Method method, @AllArguments Object[] args) throws Throwable {

        lastMethod = method;
        lastArgs = args;

        // checks if the method was already called with the given arguments
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder.getMethod().equals(method)) {
                if (Arrays.deepEquals(dataHolder.getArgs(), args)) {

                    if (dataHolder.isRealMethod()) {
                        return zuper.call();
                    } else {
                        return dataHolder.getRetObj();
                    }
                }
            }
        }

        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder.getMethod().equals(method)) {
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
