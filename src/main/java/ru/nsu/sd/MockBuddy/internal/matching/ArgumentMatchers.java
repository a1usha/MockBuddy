package ru.nsu.sd.MockBuddy.internal.matching;

import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.ArgumentMatcher;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.EqualsMatcher;
import ru.nsu.sd.MockBuddy.internal.matching.matchers.InstanceMatcher;

import java.util.EmptyStackException;

public class ArgumentMatchers {

    public static int anyInt() {
        putMatcher(new InstanceMatcher(Integer.class));
        return 0;
    }

    public static boolean anyBool() {
        putMatcher(new InstanceMatcher(Boolean.class));
        return true;
    }

    public static byte anyByte() {
        putMatcher(new InstanceMatcher(Byte.class));
        return 0;
    }

    public static char anyChar() {
        putMatcher(new InstanceMatcher(Character.class));
        return 0;
    }

    public static long anyLong() {
        putMatcher(new InstanceMatcher(Long.class));
        return 0;
    }

    public static float anyFloat() {
        putMatcher(new InstanceMatcher(Float.class));
        return 0;
    }

    public static float anyDouble() {
        putMatcher(new InstanceMatcher(Double.class));
        return 0;
    }

    public static short anyShort() {
        putMatcher(new InstanceMatcher(Short.class));
        return 0;
    }

    public static String anyString() {
        putMatcher(new InstanceMatcher(Short.class));
        return "";
    }

    public static int equalsTo(int num) {
        putMatcher(new EqualsMatcher(num));
        return 0;
    }

    public static boolean equalsTo(boolean value) {
        putMatcher(new EqualsMatcher(value));
        return true;
    }

    public static byte equalsTo(byte value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    public static double equalsTo(double value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    public static float equalsTo(float value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    public static long equalsTo(long value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    public static short equalsTo(short value) {
        putMatcher(new EqualsMatcher(value));
        return 0;
    }

    public static int and(int first, int second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performAnd();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside AND");
        }
        return 0;
    }

    public static int or(int first, int second) {
        try {
            MockingInfo.getArgumentMatcherStorage().performOr();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matchers inside OR");
        }
        return 0;
    }

    public static int not(int first) {
        try {
            MockingInfo.getArgumentMatcherStorage().performNot();
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Use only matcher inside NOT");
        }
        return 0;
    }

    private static void putMatcher(ArgumentMatcher argumentMatcher) {
        MockingInfo.getArgumentMatcherStorage().addMatcher(argumentMatcher);
    }

}
