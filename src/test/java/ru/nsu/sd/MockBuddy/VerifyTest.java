package ru.nsu.sd.MockBuddy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.sd.MockBuddy.testclasses.TestClass;

import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.*;

public class VerifyTest {

    private TestClass testClass;
    private TestClass testClass2;

    @Before
    public void setup() {
        testClass = MockBuddy.mock(TestClass.class);
        testClass2 = MockBuddy.mock(TestClass.class);
    }

    @Test
    public void test_1() {

        // 123 || 333
        MockBuddy.when(testClass.bar(123, 3)).thenReturn(11);
        MockBuddy.when(testClass2.bar(7, 3)).thenReturn(17);

        MockBuddy.verify(testClass, 0).bar(123, 3);
        MockBuddy.verify(testClass2, 0).bar(7, 3);

        testClass.bar(123, 3);  // 1
        testClass.bar(123, 3);   // 2

        MockBuddy.verify(testClass, 2).bar(123, 3);

        MockBuddy.when(testClass.bar(123, 3)).thenReturn(12);

        MockBuddy.verify(testClass, 2).bar(123, 3);

        testClass.bar(123, 3);  // 3

        MockBuddy.verify(testClass, 3).bar(123, 3);

        testClass.bar(123, 4);  // 1

        MockBuddy.verify(testClass, 1).bar(123, 4);

        testClass.bar(0, 0);  // 1

        MockBuddy.verify(testClass, 1).bar(0, 0);
        MockBuddy.verify(testClass, 3).bar(123, 3);


    }
}
