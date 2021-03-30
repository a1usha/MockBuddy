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
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Method invocation handler.
 * Retains the desired behavior of the method or returns it
 * according to the method, arguments and argument matchers.
 */
public class MockInvocationHandler {

    private final List<DataHolder> dataHolders = new ArrayList<>();
    private final List<InvocationHolder> invocationHolders = new ArrayList<>();

    private Method lastMethod = null;
    private Object[] lastArgs = null;
    private final DelegationStrategy delegationStrategy;

    public MockInvocationHandler(DelegationStrategy delegationStrategy) {
        this.delegationStrategy = delegationStrategy;
    }

    @RuntimeType
    public Object invoke(@SuperCall Callable<?> zuper, @Origin Method method, @AllArguments Object[] args) throws Throwable {

        MockingInfo.setLastMockInvocationHandler(this);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        /*
        Checks if the current method is called by the owner of the same method.
        This check avoids stubbing of nested methods of the same class.

        For example, List.contains(obj) calls List.indexOf(obj) inside.
        According to this check, List.contains() will be stubbed, not indexOf()
         */
        if (!stackTraceElements[2].toString().contains(stackTraceElements[3].getClassName())) {
            lastMethod = method;
            lastArgs = args;
        }

        for (InvocationHolder invocationHolder : invocationHolders) {
            if (invocationHolder.getMethod().equals(method) && Arrays.deepEquals(invocationHolder.getArgs(), args)) {
                invocationHolder.increaseCounter();
            }
        }

        if (invocationHolders.stream().noneMatch(x -> x.getMethod().equals(method) && Arrays.deepEquals(x.getArgs(), args))) {
            InvocationHolder holder = new InvocationHolder(method, args);
            holder.increaseCounter();
            invocationHolders.add(holder);
        }


        // checks if the method was already called with the given arguments (without argument matchers)
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder.getMethod().equals(method) && Arrays.deepEquals(dataHolder.getArgs(), args)) {
                if (!dataHolder.isWithMatchers()) {
                    switch (dataHolder.getDelegationStrategy()) {
                        case CALL_REAL_METHOD:
//                            System.out.println("first call real method");
                            return zuper.call();

                        case THROW_EXCEPTION:
//                            System.out.println("first throw exception");
                            throw dataHolder.getToThrow();

                        case RETURN_CUSTOM:
//                            System.out.println("first return custom");
                            return dataHolder.getRetObj();

                    }
                }
            }
        }

        // checks if the method was already called with the given arguments (with argument matchers)
        for (DataHolder dataHolder : dataHolders) {
            if (dataHolder.getMethod().equals(method)) {
                if (dataHolder.isWithMatchers()) {
                    if (MockingInfo.getArgumentMatcherStorage().getMatcherStack().empty()) {

                        boolean match;
                        for (int i = 0; i < args.length; i++) {

                            match = dataHolder.getLocalArgumentMatchersList().get(i).matches(lastArgs[i]);
                            if (!match) {
                                if (delegationStrategy == DelegationStrategy.CALL_REAL_METHOD) {
//                                    System.out.println("let's try here when");
                                    return zuper.call();
                                }
                                else
                                    return null;
                            }
                        }

                        switch (dataHolder.getDelegationStrategy()) {
                            case CALL_REAL_METHOD:
//                                System.out.println("second call real method");
                                return zuper.call();

                            case THROW_EXCEPTION:
//                                System.out.println("second throw exception");
                                throw dataHolder.getToThrow();

                            case RETURN_CUSTOM:
//                                System.out.println("second return custom");
                                return dataHolder.getRetObj();

                        }

                    } else {
                        // Override matcher
//                        System.out.println("xz what is this");
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

        // Decrease invocation counter because method was in MockBuddy.when
        for (InvocationHolder invocationHolder : invocationHolders) {
            if (invocationHolder.getMethod().equals(lastMethod) && Arrays.deepEquals(invocationHolder.getArgs(), lastArgs)) {
                invocationHolder.decreaseCounter();
            }
        }

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

        // Decrease invocation counter because method was in MockBuddy.when
        for (InvocationHolder invocationHolder : invocationHolders) {
            if (invocationHolder.getMethod().equals(lastMethod) && Arrays.deepEquals(invocationHolder.getArgs(), lastArgs)) {
                invocationHolder.decreaseCounter();
            }
        }

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

        // Decrease invocation counter because method was in MockBuddy.when
        for (InvocationHolder invocationHolder : invocationHolders) {
            if (invocationHolder.getMethod().equals(lastMethod) && Arrays.deepEquals(invocationHolder.getArgs(), lastArgs)) {
                invocationHolder.decreaseCounter();
            }
        }

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

    public int getInvocationCounter() {

        if (invocationHolders.stream().noneMatch(x -> x.getMethod().equals(lastMethod) && Arrays.deepEquals(x.getArgs(), lastArgs))) {
            return 0;
        } else {
            return Objects.requireNonNull(invocationHolders.stream().filter(x -> x.getMethod().equals(lastMethod) && Arrays.deepEquals(x.getArgs(), lastArgs)).findFirst().orElse(null)).getInvocationCounter();
        }
    }
}
