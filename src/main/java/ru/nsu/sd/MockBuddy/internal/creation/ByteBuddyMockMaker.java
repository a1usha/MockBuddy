package ru.nsu.sd.MockBuddy.internal.creation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import ru.nsu.sd.MockBuddy.internal.handling.MockInvocationHandler;

import java.lang.reflect.InvocationTargetException;

public class ByteBuddyMockMaker {

    public static <T> T mock(Class<T> clazz, MockInvocationHandler mockInvocationHandler) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(mockInvocationHandler))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        return byteBuddy.getConstructor().newInstance();
    }
}
