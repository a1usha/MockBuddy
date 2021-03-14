import ru.nsu.sd.MockBuddy.MockBuddy;
import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatcher;
import ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Test test = MockBuddy.mock(Test.class);
//        MockBuddy.when(test.foo()).thenReturn("Mocked!");
//        MockBuddy.when(test.foo()).thenReturn("Mockedd!");
//        MockBuddy.when(test.foo()).invokeRealMethod();
//        MockBuddy.when(test.bar(25)).thenReturn(35);
//        MockBuddy.when(test.bar(25)).invokeRealMethod();

//        System.out.println(test.foo()); // Mocked!
//        System.out.println(test.bar(25)); // 35
//        System.out.println(test.bar(44)); // null

//        MockBuddy.when(test.bar(ArgumentMatchers.equalsTo(4), ArgumentMatchers.anyInt())).thenReturn(35);
//        MockBuddy.when(test.bar(25, 36)).invokeRealMethod();
//        System.out.println(test.bar(4, 66));
//        System.out.println(test.bar(25, 36));

//        MockBuddy.when(test.foo(22)).thenReturn("22");
//        MockBuddy.when(test.foo(33)).thenReturn("33");

//        MockBuddy.when(test.foo(ArgumentMatchers.equalsTo(43))).thenReturn("Mockedddd");
//        MockBuddy.when(test.foo(ArgumentMatchers.anyInt())).thenReturn("Mocked");

        MockBuddy.when(test.bar(ArgumentMatchers.equalsTo(34), ArgumentMatchers.anyInt())).thenReturn(34);



//        MockBuddy.when(test.foo(44)).thenReturn("44");

        System.out.println(test.bar(35, 190));

//        System.out.println(test.foo(123));
//        System.out.println(test.foo(43));
//        System.out.println(test.foo(22));
//        System.out.println(test.foo(33));
//        System.out.println(test.foo(44));

    }
}
