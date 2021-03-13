import ru.nsu.sd.MockBuddy.MockBuddy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Test test = MockBuddy.mock(Test.class);
        MockBuddy.when(test.foo()).thenReturn("Mocked!");
        MockBuddy.when(test.foo()).thenReturn("Mockedd!");
//        MockBuddy.when(test.foo()).invokeRealMethod();
        MockBuddy.when(test.bar(25)).thenReturn(35);
//        MockBuddy.when(test.bar(25)).invokeRealMethod();

        System.out.println(test.foo()); // Mocked!
        System.out.println(test.bar(25)); // 35
        System.out.println(test.bar(44)); // null

    }
}
