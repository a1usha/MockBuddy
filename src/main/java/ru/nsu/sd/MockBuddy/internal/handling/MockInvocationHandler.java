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
    private final DelegationStrategy delegationStrategy;

    public MockInvocationHandler(DelegationStrategy delegationStrategy) {
        this.delegationStrategy = delegationStrategy;
    }

    @RuntimeType
    public Object invoke(@SuperCall Callable<?> zuper, @Origin Method method, @AllArguments Object[] args) throws Throwable {

        MockingInfo.setLastMockInvocationHandler(this);

        lastMethod = method;
        lastArgs = args;

        // checks if the method was already called with the given arguments
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder.getMethod().equals(method) && Arrays.deepEquals(dataHolder.getArgs(), args)) {
                if (!dataHolder.isWithMatchers()) {
                    switch (dataHolder.getDelegationStrategy()) {
                        case CALL_REAL_METHOD:
                            return zuper.call();

                        case THROW_EXCEPTION:
                            throw dataHolder.getToThrow();

                        case RETURN_CUSTOM:
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
                            if (!match) {
                                if (delegationStrategy == DelegationStrategy.CALL_REAL_METHOD)
                                    return zuper.call();
                                else
                                    return null;
                            }
                        }

                        switch (dataHolder.getDelegationStrategy()) {
                            case CALL_REAL_METHOD:
                                return zuper.call();

                            case THROW_EXCEPTION:
                                throw dataHolder.getToThrow();

                            case RETURN_CUSTOM:
                                return dataHolder.getRetObj();

                        }

                    } else {
                        // Override matcher
                        return null;
                    }
                }
            }
        }

        // otherwise return null
        if (delegationStrategy == DelegationStrategy.CALL_REAL_METHOD)
            return zuper.call();
        else
            return null;
    }

    public void setRetObj(Object retObj) {

        // Remove existing rule with same method and arguments
        dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));

        // Pull argument matchers from argument matcher storage
        List<ArgumentMatcher> argumentMatchersList = MockingInfo.getArgumentMatcherStorage().pullMatchers();

        if (argumentMatchersList != null) {
            // If argument matchers is not null, then create rule with matching
            if (argumentMatchersList.size() == lastArgs.length) {
                dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj, argumentMatchersList));
            } else {
                throw new IllegalArgumentException("Use only ALL arguments as matchers, or ALL regular values");
            }

        } else {
            // Else create default rule with return object
            dataHolders.add(new DataHolder(lastMethod, lastArgs, retObj));
        }
    }

    public void setThrowable(Throwable throwable) {

        // Remove existing rule with same method and arguments
        dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));

        // Pull argument matchers from argument matcher storage
        List<ArgumentMatcher> argumentMatchersList = MockingInfo.getArgumentMatcherStorage().pullMatchers();

        if (argumentMatchersList != null) {
            // If argument matchers is not null, then create rule with matching
            if (argumentMatchersList.size() == lastArgs.length) {
                dataHolders.add(new DataHolder(lastMethod, lastArgs, argumentMatchersList, throwable));
            } else {
                throw new IllegalArgumentException("Use only ALL arguments as matchers, or ALL regular values");
            }

        } else {
            // Else create default rule with return object
            dataHolders.add(new DataHolder(lastMethod, lastArgs, throwable));
        }
    }

    public void setRealMethodInvocation() {

        // Remove existing rule with same method and arguments
        dataHolders.removeIf(dh -> dh.getMethod().equals(lastMethod) && Arrays.deepEquals(dh.getArgs(), lastArgs));

        // Pull argument matchers from argument matcher storage
        List<ArgumentMatcher> argumentMatchersList = MockingInfo.getArgumentMatcherStorage().pullMatchers();

        if (argumentMatchersList != null) {
            // If argument matchers is not null, then create rule with matching
            if (argumentMatchersList.size() == lastArgs.length) {
                dataHolders.add(new DataHolder(lastMethod, lastArgs, argumentMatchersList));
            } else {
                throw new IllegalArgumentException("Use only ALL arguments as matchers, or ALL regular values");
            }

        } else {
            // Else create default rule with return object
            dataHolders.add(new DataHolder(lastMethod, lastArgs));
        }
    }
}
