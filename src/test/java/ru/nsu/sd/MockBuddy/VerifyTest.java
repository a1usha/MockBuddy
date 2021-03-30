package ru.nsu.sd.MockBuddy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.sd.MockBuddy.testclasses.TestClass;

import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.*;

public class VerifyTest {

    private TestClass testClass;

    @Before
    public void setup() {
        testClass = MockBuddy.mock(TestClass.class);
    }

    @Test
    public void test_1() {
        // 123 || 333
        MockBuddy.when(testClass.bar(123, 3)).thenReturn(11);

        Assert.assertEquals(0, MockBuddy.temporaryVerify());

        System.out.println(testClass.bar(123, 3));
        System.out.println(testClass.bar(123, 3));

        Assert.assertEquals(2, MockBuddy.temporaryVerify());

        MockBuddy.when(testClass.bar(123, 3)).thenReturn(12);

        Assert.assertEquals(2, MockBuddy.temporaryVerify());

        System.out.println(testClass.bar(123, 3));

        Assert.assertEquals(3, MockBuddy.temporaryVerify());

        System.out.println(testClass.bar(123, 4));

        Assert.assertEquals(1, MockBuddy.temporaryVerify());

        System.out.println(testClass.bar(0, 0));

        Assert.assertEquals(1, MockBuddy.temporaryVerify());

//        MockBuddy.verify(testClass, 3).bar(123, 3);


    }
}
