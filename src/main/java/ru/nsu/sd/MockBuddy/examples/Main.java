package ru.nsu.sd.MockBuddy.examples;

import ru.nsu.sd.MockBuddy.MockBuddy;
import ru.nsu.sd.MockBuddy.internal.MockingInfo;
import ru.nsu.sd.MockBuddy.internal.annotations.MockInitializer;
import ru.nsu.sd.MockBuddy.internal.annotations.Mock;
import ru.nsu.sd.MockBuddy.internal.annotations.Spy;

import java.util.InvalidPropertiesFormatException;

import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.*;

public class Main {

    @Mock
    private A a;

    @Mock
    private B b;

    @Spy
    private Cat spytest = new Cat("cat");

    @Spy
    private Cat cat;

    @Spy
    private FieldTest fieldTest = new FieldTest("Final string");

    public static void main(String[] args) {

        Main main = new Main();
        main.test();

    }

    private void test() {

        MockInitializer.initMocks(this);

        Test test = MockBuddy.mock(Test.class);

        MockBuddy.when(test.foo(or(equalsTo(35), equalsTo(89)))).thenThrow(new IllegalArgumentException("Exception occurred"));

        try {
            test.foo(89);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }

        MockBuddy.when(cat.getCat()).thenThrow(new ArithmeticException("Cat exception"));

        try {
            cat.getCat();
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }

        System.out.println(fieldTest.getFnStr());

        System.out.println(spytest.getCat());

        MockBuddy.when(spytest.performComputations(equalsTo(5), equalsTo(6))).thenReturn(5);
        System.out.println(spytest.performComputations(5, 6));

        // (5 or 9) & (not (9 or 2) or 11) -> 5
        MockBuddy.when(test.foo(and(or(equalsTo(5),equalsTo(9)), or(not(or(equalsTo(9),equalsTo(2))), equalsTo(11))))).thenReturn("Mocked");
        System.out.println(test.foo(5));    // Mocked
        System.out.println(test.foo(11));   // Null

        MockBuddy.when(test.bar(anyInt(), or(equalsTo(123), equalsTo(333)))).thenReturn(11);
        System.out.println(test.bar(333, 123)); // 11
        System.out.println(test.bar(555, 444)); // Null

        MockBuddy.when(test.boolTest(true)).invokeRealMethod();
        System.out.println(test.boolTest(true));    // True
        System.out.println(test.boolTest(false));   // Null

        MockBuddy.when(test.tmp()).thenReturn("Mock_1");
        MockBuddy.when(test.tmp()).thenReturn("Mock_2");
        System.out.println(test.tmp()); // Mock_2

        C c = new C();

        c.setA(a);
        c.setB(b);

        MockBuddy.when(a.say()).thenReturn("Mock_A");
        MockBuddy.when(b.say()).thenReturn("Mock_B");

        System.out.println(c.sayAll()); // Mock_A Mock_B

    }
}
