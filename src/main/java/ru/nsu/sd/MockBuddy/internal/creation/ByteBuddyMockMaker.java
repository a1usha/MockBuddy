package ru.nsu.sd.MockBuddy.internal.creation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import ru.nsu.sd.MockBuddy.internal.handling.MockInvocationHandler;

public class ByteBuddyMockMaker {

    public static <T> T mock(Class<T> clazz, MockInvocationHandler mockInvocationHandler) {
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(mockInvocationHandler))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        // Create object without calling constructor
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<? extends T> thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
        return thingyInstantiator.newInstance();
    }
}
