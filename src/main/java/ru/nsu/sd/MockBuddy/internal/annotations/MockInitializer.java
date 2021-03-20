package ru.nsu.sd.MockBuddy.internal.annotations;

import ru.nsu.sd.MockBuddy.MockBuddy;
import java.lang.reflect.Field;

/**
 * Initializes fields annotated with Mock annotations.
 *
 * <pre><code>MockInitializer.initMock(this)</code></pre> method has to be called to initialize annotated fields.
 *
 * See examples in javadoc for {@link Mock} class.
 *
 */
public class MockInitializer {

    /**
     * Initializes objects annotated with Mock annotations for given testClass
     * @param instance - an object of the given testClass
     */
    public static void initMocks(Object instance) {

        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Mock.class)) {

                try {

                    // make it accessible
                    field.setAccessible(true);

                    // sets the field on the specified object argument to the specified new value.
                    field.set(instance, MockBuddy.mock(field.getType()));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
