package ru.nsu.sd.MockBuddy.internal.annotations;

import ru.nsu.sd.MockBuddy.MockBuddy;
import java.lang.reflect.Field;

public class MockInitializer {

    public static void initMocks(Object instance) {

        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Mock.class)) {

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
