package ru.nsu.sd.MockBuddy.internal.matching;

import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.EqualsMatcher;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.InstanceMatcher;

import java.util.EmptyStackException;

/**
 * A set of argument matchers that provide flexible
 * configuration of method parameters
 *
 * Will return 123 for any argument
 * MockBuddy.when(someObject.foo(anyInt()).thenReturn(123)
 *
 * Will return 321 only for 3 and 5 as arguments
 * MockBuddy.when(someObject.foo(or(equalsTo(3), equalsTo(5))).thenReturn(321)
 */
public class ArgumentMatchers {

    /**
     * Any value of specified class
     *
     * @param clazz type of object to check with
     * @return default value of primitive or wrapper or null
     */
    public static <T> T anyOf(Class<T> clazz) {
        putMatcher(new InstanceMatcher(clazz));
        return PrimitiveDefaultValues.getDefaultValue(clazz);
    }

    /**
     * Any int or any non-null Integer
     *
     * @return <code>0</code>
     */
    public static int anyInt() {
        putMatcher(new InstanceMatcher(Integer.class));
        return 0;
    }

    /**
     * Any boolean or any non-null Boolean
     *
     * @return <code>true</code>
     */
    public static boolean anyBool() {
        putMatcher(new InstanceMatcher(Boolean.class));
        return true;
    }

    /**
     * Any byte or any non-null Byte
     *
     * @return <code>0</code>
     */
    public static byte anyByte() {
        putMatcher(new InstanceMatcher(Byte.class));
        return 0;
    }

    /**
     * Any char or any non-null Character
     *
     * @return <code>0</code>
     */
    public static char anyChar() {
        putMatcher(new InstanceMatcher(Character.class));
        return 0;
    }

    /**
     * Any long or any non-null Long
     *
     * @return <code>0</code>
     */
    public static long anyLong() {
        putMatcher(new InstanceMatcher(Long.class));
        return 0;
    }

    /**
     * Any float or any non-null Float
     *
     * @return <code>0</code>
     */
    public static float anyFloat() {
        putMatcher(new InstanceMatcher(Float.class));
        return 0;
    }

    /**
     * Any double or any non-null Double
     *
     * @return <code>0</code>
     */
    public static double anyDouble() {
        putMatcher(new InstanceMatcher(Double.class));
        return 0;
    }

    /**
     * Any short or any non-null Short
     *
     * @return <code>0</code>
     */
    public static short anyShort() {
        putMatcher(new InstanceMatcher(Short.class));
        return 0;
    }

    /**
     * Any non-null String
     *
     * @return <code>""</code>
     */
    public static String anyString() {
        putMatcher(new InstanceMatcher(String.class));
        return "";
    }

    /**
     * Int argument that is equal to the given value
     *
     * @param num value to check with
     * @return <code>0</code>
     */
    public static int equalsTo(int num) {
        putMatcher(new EqualsMatcher(num));
        return 0;
    }

    /**
     * Boolean argument that is equal to the given value
     *
     * @param value value to check with
     * @return <code>true</code>
     */
    public static boolean equalsTo(boolean value) {
        putMatcher(new EqualsMatcher(value));
        return true;
    }

    /**
     * Byte argument that is equal to the given value
     *
     * @param value value to check with
     * @return <code>0</code>
     */
    public static byte equalsTo(byte value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    /**
     * Double argument that is equal to the given value
     *
     * @param value value to check with
     * @return <code>0</code>
     */
    public static double equalsTo(double value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    /**
     * Float argument that is equal to the given value
     *
     * @param value value to check with
     * @return <code>0</code>
     */
    public static float equalsTo(float value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    /**
     * Long argument that is equal to the given value
     *
     * @param value value to check with
     * @return <code>0</code>
     */
    public static long equalsTo(long value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    /**
     * Short argument that is equal to the given value
     *
     * @param value value to check with
     * @return <code>0</code>
     */
    public static short equalsTo(short value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    /**
     * Int argument that matches both matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static int and(int first, int second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return 0;
    }

    /**
     * Boolean argument that matches both matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>true</code>
     */
    public static boolean and(boolean first, boolean second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return true;
    }

    /**
     * Byte argument that matches both matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static byte and(byte first, byte second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return 0;
    }

    /**
     * Char argument that matches both matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static char and(char first, byte second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return 0;
    }

    /**
     * Float argument that matches both matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static float and(float first, float second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return 0;
    }

    /**
     * Long argument that matches both matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static long and(long first, long second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return 0;
    }

    /**
     * Short argument that matches both matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static short and(short first, short second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return 0;
    }

    /**
     * Int argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static int or(int first, int second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    /**
     * Boolean argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static boolean or(boolean first, boolean second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return true;
    }

    /**
     * Short argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static short or(short first, short second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    /**
     * Long argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static long or(long first, long second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    /**
     * Float argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static float or(float first, float second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    /**
     * Double argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static double or(double first, double second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    /**
     * Char argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static char or(char first, char second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    /**
     * Byte argument that matches any of the given matchers
     *
     * @param first placeholder for the first argument
     * @param second placeholder for the second argument
     * @return <code>0</code>
     */
    public static byte or(byte first, byte second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    /**
     * Int argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static int not(int first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    /**
     * Boolean argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static boolean not(boolean first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return true;
    }

    /**
     * Short argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static short not(short first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    /**
     * Long argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static long not(long first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    /**
     * Float argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static float not(float first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    /**
     * Double argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static double not(double first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    /**
     * Char argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static char not(char first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    /**
     * Byte argument that does not match the given matcher
     *
     * @param first placeholder for the first argument
     * @return <code>0</code>
     */
    public static byte not(byte first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    /**
     * Add argument matcher to the {@link ArgumentMatcherStorage}
     * @param argumentMatcher to put in
     */
    private static void putMatcher(ArgumentMatcher argumentMatcher) {
        MockingInfo.getArgumentMatcherStorage().addMatcher(argumentMatcher);
    }
}
