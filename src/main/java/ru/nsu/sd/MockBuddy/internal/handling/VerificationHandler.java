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


public class VerificationHandler {

    private Integer times;
    private Object obj;

    public VerificationHandler(Object obj, Integer times) {
        this.obj = obj;
        this.times = times;
    }

    @RuntimeType
    public Object invoke(@SuperCall Callable<?> zuper, @Origin Method method, @AllArguments Object[] args) throws Throwable {

        if (Objects.requireNonNull(MockingInfo.getListOfHolders(obj)
                .stream()
                .filter(x -> x.getMethod().equals(method) && Arrays.deepEquals(x.getArgs(), args))
                .findFirst().orElse(null))
                .getInvocationCounter() != times) {
            throw new IllegalArgumentException("Verification error");
        }

        return null;

    }
}
