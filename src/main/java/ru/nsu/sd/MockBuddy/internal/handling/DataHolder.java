package ru.nsu.sd.MockBuddy.internal.handling;

import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Storage of specified behavior for mock object methods.
 * Is a single entry for one method.
 * It stores the full name of the method, the arguments with
 * which it was called, matchers and the corresponding flags.
 */
class DataHolder {
    private final Object[] args;
    private final Method method;
    private final Object retObj;
    private final boolean realMethod;

    private final boolean withMatchers;
    private final List<ArgumentMatcher> localArgumentMatchersList;

    DataHolder(Method method, Object[] args, Object retObj, List<ArgumentMatcher> localArgumentMatchersList) {
        this.args = args;
        this.method = method;
        this.retObj = retObj;
        this.realMethod = false;

        this.localArgumentMatchersList = localArgumentMatchersList;
        this.withMatchers = !(localArgumentMatchersList == null);
    }

    DataHolder(Method method, Object[] args, Object retObj, Boolean realMethod) {
        this.args = args;
        this.method = method;
        this.retObj = retObj;
        this.realMethod = realMethod;
        this.localArgumentMatchersList = null;
        this.withMatchers = false;
    }

    Object[] getArgs() {
        return args;
    }

    Method getMethod() {
        return method;
    }

    Object getRetObj() {
        return retObj;
    }

    public List<ArgumentMatcher> getLocalArgumentMatchersList() {
        return localArgumentMatchersList;
    }

    public boolean isWithMatchers() {
        return withMatchers;
    }

    public boolean isRealMethod() {
        return realMethod;
    }

    @Override
    public String toString() {
        return "DataHolder{" +
                "args=" + Arrays.toString(args) +
                ", method=" + method +
                ", retObj=" + retObj +
                ", realMethod=" + realMethod +
                ", withMatchers=" + withMatchers +
                ", localArgumentMatchersList=" + localArgumentMatchersList +
                "}";
    }
}