package ru.nsu.sd.MockBuddy.internal.handling;

import java.lang.reflect.Method;
import java.util.Arrays;

public class InvocationHolder {

    private final Object[] args;
    private final Method method;

    private int invocationCounter = 0;

    InvocationHolder(Method method, Object[] args) {
        this.args = args;
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Method getMethod() {
        return method;
    }


    public int getInvocationCounter() {
        return invocationCounter;
    }

    public void increaseCounter() {
        invocationCounter++;

//        System.out.println(this.toString() + " counter:   " + invocationCounter);
    }

    public void decreaseCounter() {
        if (invocationCounter > 0) {
            invocationCounter--;
        }

//        System.out.println(this.toString() + " counter:   " + invocationCounter);

    }

    @Override
    public String toString() {
        return "DataHolder{" +
                "args=" + Arrays.toString(args) +
                ", method=" + method +
                '}';
    }
}
