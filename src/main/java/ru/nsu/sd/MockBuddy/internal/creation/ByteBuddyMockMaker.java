package ru.nsu.sd.MockBuddy.internal.creation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import ru.nsu.sd.MockBuddy.MockBuddy;
import ru.nsu.sd.MockBuddy.examples.SpyTest;
import ru.nsu.sd.MockBuddy.examples.Test;
import ru.nsu.sd.MockBuddy.internal.annotations.Mock;
import ru.nsu.sd.MockBuddy.internal.handling.MockInvocationHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Creates a mock object using ByteBuddy library.
 * The object is instantiated without calling the constructor using objenesis library.
 */
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


    public static <T> T mock(T obj, MockInvocationHandler mockInvocationHandler) {

        Field[] fields = obj.getClass().getDeclaredFields();

//        for (Field field : fields) {
//            field.setAccessible(true);
//        }

        @SuppressWarnings("unchecked")
        Class<? extends T> byteBuddy = new ByteBuddy()
                .subclass((Class<T>)obj.getClass())
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(mockInvocationHandler))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

//        Objenesis objenesis = new ObjenesisStd();
//        ObjectInstantiator thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
//        @SuppressWarnings("unchecked")
//        T instance = (T) thingyInstantiator.newInstance();

        T instance = null;
        try {
            instance = byteBuddy.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }


        fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field);

        }

        System.out.println("l");

        fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field);

        }


//        Objenesis objenesis = new ObjenesisStd();
//        ObjectInstantiator thingyInstantiator = objenesis.getInstantiatorOf(byteBuddy);
//        Object instance = thingyInstantiator.newInstance();
////        Object instance = byteBuddy;
//
//        Field[] fields = obj.getClass().getDeclaredFields();
//
//        for (Field field : fields) {
//
//            try {
//
//                field.setAccessible(true);
//
//                Object value = field.get(obj);
//
//                if (value != null) {
//                    System.out.println(field.getName() + "=" + value);
//                }
//
//                Field fieldInstance = instance.getClass().getDeclaredField(field.getName());
//                fieldInstance.setAccessible(true);
//
//                fieldInstance.set(instance, value);
//
//            } catch (IllegalAccessException | NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        System.out.println("heheh new obj");
//
//        Field[] fieldsinst = instance.getClass().getDeclaredFields();
//
//        for (Field field : fieldsinst) {
//
//            try {
//
//                field.setAccessible(true);
//
//                Object value = field.get(obj);
//
//                if (value != null) {
//                    System.out.println(field.getName() + "=" + value);
//                }
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        System.out.println("all fields");
//
//        fieldsinst = instance.getClass().getFields();
//
//        for (Field field : fieldsinst) {
//
//            try {
//
//                field.setAccessible(true);
//
//                Object value = field.get(obj);
//
//                if (value != null) {
//                    System.out.println(field.getName() + "=" + value);
//                }
//
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//
//        }


        @SuppressWarnings("unchecked")
        Class<? extends T> byteBuddy2 = new ByteBuddy()
                .subclass((Class<T>) SpyTest.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(mockInvocationHandler))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded();

        // Create object without calling constructor
        Objenesis objenesis2 = new ObjenesisStd();
        ObjectInstantiator<? extends T> thingyInstantiator2 = objenesis2.getInstantiatorOf(byteBuddy2);
        return thingyInstantiator2.newInstance();

    }

}

