import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        TestInterface testInterface = MyMockProxy.mock(Test.class);
//
//        MyMockProxy.when(testInterface.foo()).thenReturn("Mocked!");
//        MyMockProxy.when(testInterface.bar(25)).thenReturn(35);
//
//        System.out.println(testInterface.foo()); // Mocked!
//        System.out.println(testInterface.bar(25)); // 35
//        System.out.println(testInterface.bar(44)); // null
//
//        System.out.println(testInterface instanceof TestInterface);

        Test test = MyMockBuddy.mock(Test.class);
        MyMockBuddy.when(test.foo()).thenReturn("Mocked!");
        MyMockBuddy.when(test.bar(25)).thenReturn(35);

        System.out.println(test.foo()); // Mocked!
        System.out.println(test.bar(25)); // 35
        System.out.println(test.bar(44)); // null

        System.out.println(test instanceof Test);
    }
}
