package ru.nsu.sd.MockBuddy.internal.handling;

import java.lang.reflect.Method;

class DataHolder {
    private final Object[] args;
    private final Method method;
    private final Object retObj;
    private final Boolean realMethod;

    DataHolder(Method method, Object[] args, Object retObj) {
        this.args = args;
        this.method = method;
        this.retObj = retObj;
        this.realMethod = false;
    }

    DataHolder(Method method, Object[] args, Object retObj, Boolean realMethod) {
        this.args = args;
        this.method = method;
        this.retObj = retObj;
        this.realMethod = realMethod;
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

    Boolean getRealMethod() {
        return realMethod;
    }
}