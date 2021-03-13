import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Test test = MyMockBuddy.mock(Test.class);
        MyMockBuddy.when(test.foo()).thenReturn("Mocked!");
        MyMockBuddy.when(test.foo()).thenReturn("Mockedd!");
        MyMockBuddy.when(test.foo()).invokeRealMethod();
        MyMockBuddy.when(test.bar(25)).thenReturn(35);
        MyMockBuddy.when(test.bar(25)).invokeRealMethod();

        System.out.println(test.foo()); // Mocked!
        System.out.println(test.bar(25)); // 35
        System.out.println(test.bar(44)); // null

    }
}
