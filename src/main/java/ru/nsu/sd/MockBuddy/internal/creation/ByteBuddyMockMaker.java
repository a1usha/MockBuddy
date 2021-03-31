package ru.nsu.sd.MockBuddy.internal.creation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import ru.nsu.sd.MockBuddy.internal.handling.DelegationStrategy;
import ru.nsu.sd.MockBuddy.internal.handling.InvocationHolder;
import ru.nsu.sd.MockBuddy.internal.handling.MockInvocationHandler;
import ru.nsu.sd.MockBuddy.internal.handling.VerificationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Creates a mock object using ByteBuddy library.
 * The object is instantiated without calling the constructor using objenesis library.
 */
public class ByteBuddyMockMaker {

    public static <T> T mock(Class<T> clazz, DelegationStrategy delegationStrategy) {
        MockingInfo.setLastMockInvocationHandler(new MockInvocationHandler(delegationStrategy));

        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(MockingInfo.getLastMockInvocationHandler()))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        // Create object without calling constructor
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<? extends T> thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
        return thingyInstantiator.newInstance();
    }

    /**
     * Creates a spy of the real object.
     * The spy calls real methods unless they are stubbed.
     *
     * @param obj real object to spy on
     * @return a spy of the real object
     */
    public static <T> T spy(T obj, MockInvocationHandler mockInvocationHandler) {
        MockingInfo.setLastMockInvocationHandler(new MockInvocationHandler(DelegationStrategy.CALL_REAL_METHOD));

        @SuppressWarnings("unchecked")
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass((Class<T>) obj.getClass())
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(MockingInfo.getLastMockInvocationHandler()))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        // Create object without calling constructor
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<? extends T> thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
        T instance = (T) thingyInstantiator.newInstance();

        // Get an array of Field objects reflecting all the fields declared by the class
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {

                // make it accessible
                field.setAccessible(true);

                if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                // get the value of the field
                Object value = field.get(obj);

                // if value is not null, save it to the same field of the created object
                if (value != null) {

                    Field fieldInstance = instance.getClass().getSuperclass().getDeclaredField(field.getName());
                    fieldInstance.setAccessible(true);

                    fieldInstance.set(instance, value);

                }

            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return instance;

    }

    public static <T> T verify(T obj, Integer times) {

        VerificationHandler verificationHandler = new VerificationHandler(obj, times);

        @SuppressWarnings("unchecked")
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass((Class<T>) obj.getClass().getSuperclass())
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(verificationHandler))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        // Create object without calling constructor
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator<? extends T> thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
        return (T) thingyInstantiator.newInstance();
    }
}
