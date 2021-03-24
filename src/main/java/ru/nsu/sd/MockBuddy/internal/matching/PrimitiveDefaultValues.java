package ru.nsu.sd.MockBuddy.internal.matching;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveDefaultValues {

    private static final Map<Class<?>, Object> DEFAULT_VALUES = new HashMap<>();

    /**
     * Returns default value for a primitive or a primitive wrapper as defined
     * in Java Language Specification.
     * Returns null if the type is neither a primitive nor a primitive wrapper.
     *
     * @param primitiveOrWrapperType The type to check for default value
     * @return Default value or <code>null</code> if type is not a primitive or wrapper
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDefaultValue(Class<T> primitiveOrWrapperType) {
        return (T) DEFAULT_VALUES.get(primitiveOrWrapperType);
    }

    static {
        DEFAULT_VALUES.put(Boolean.class, false);
        DEFAULT_VALUES.put(Character.class, '\u0000');
        DEFAULT_VALUES.put(Byte.class, (byte) 0);
        DEFAULT_VALUES.put(Short.class, (short) 0);
        DEFAULT_VALUES.put(Integer.class, 0);
        DEFAULT_VALUES.put(Long.class, 0L);
        DEFAULT_VALUES.put(Float.class, 0F);
        DEFAULT_VALUES.put(Double.class, 0D);

        DEFAULT_VALUES.put(boolean.class, false);
        DEFAULT_VALUES.put(char.class, '\u0000');
        DEFAULT_VALUES.put(byte.class, (byte) 0);
        DEFAULT_VALUES.put(short.class, (short) 0);
        DEFAULT_VALUES.put(int.class, 0);
        DEFAULT_VALUES.put(long.class, 0L);
        DEFAULT_VALUES.put(float.class, 0F);
        DEFAULT_VALUES.put(double.class, 0D);
    }
}
