package ru.nsu.sd.MockBuddy.internal.annotations;

import ru.nsu.sd.MockBuddy.MockBuddy;
import java.lang.reflect.Field;

public class Annotations {

    public static void parse(Object instance) {

        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(MockBuddyAnnotation.class)) {

                try {

                    // make it accessible
                    field.setAccessible(true);

                    field.set(instance, MockBuddy.mock(field.getType()));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
