package ru.nsu.sd.MockBuddy.examples;

import ru.nsu.sd.MockBuddy.MockBuddy;
import ru.nsu.sd.MockBuddy.internal.annotations.MockInitializer;
import ru.nsu.sd.MockBuddy.internal.annotations.Mock;

import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.*;

public class Main {

    @Mock
    private A a;

    @Mock
    private B b;

    public static void main(String[] args) {

        Main main = new Main();
        main.test();

    }

    private void test() {

        MockInitializer.initMocks(this);

        Test test = MockBuddy.mock(Test.class);

        // (5 or 9) & (not (9 or 2) or 11) -> 5
        MockBuddy.when(test.foo(and(or(equalsTo(5),equalsTo(9)), or(not(or(equalsTo(9),equalsTo(2))), equalsTo(11))))).thenReturn("Mocked");
        System.out.println(test.foo(5));    // Mocked
        System.out.println(test.foo(11));   // Null

        MockBuddy.when(test.bar(anyInt(), or(equalsTo(123), equalsTo(333)))).thenReturn(11);
        System.out.println(test.bar(333, 123)); // 11
        System.out.println(test.bar(555, 333)); // 11

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
