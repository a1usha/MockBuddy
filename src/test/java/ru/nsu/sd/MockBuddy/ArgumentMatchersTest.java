package ru.nsu.sd.MockBuddy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.sd.MockBuddy.testclasses.TestClass;

import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.*;
import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.equalsTo;

public class ArgumentMatchersTest {

    private TestClass testClass;

    @Before
    public void setup() {
        testClass = MockBuddy.mock(TestClass.class);
    }

    @Test
    public void test_1() {
        // 123 || 333
        MockBuddy.when(testClass.bar(anyInt(), or(equalsTo(123), equalsTo(333)))).thenReturn(11);

        Assert.assertEquals(Integer.valueOf(11), testClass.bar(333, 333));
        Assert.assertNull(testClass.bar(555, 444));
    }

    @Test
    public void test_2() {
        MockBuddy.when(testClass.foo(equalsTo(255))).thenReturn("mocked");

        Assert.assertEquals("mocked", testClass.foo(255));
        Assert.assertNull(testClass.foo(11));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getException() {
        MockBuddy.when(testClass.foo(or(equalsTo(255), 100))).thenReturn("mocked");
    }

    @Test
    public void invokeRealMethodWithMatcher() {
        MockBuddy.when(testClass.foo(equalsTo(100))).invokeRealMethod();

        Assert.assertEquals("Foo", testClass.foo(100));
    }

    @Test
    public void withManyNestedMatchers() {
        // (5 or 9) & (not (9 or 2) or 11) -> 5
        MockBuddy.when(testClass.foo(
                and(or(
                        equalsTo(5),
                        equalsTo(9)),
                    or(
                        not(or(
                                equalsTo(9),
                                equalsTo(2))),
                        equalsTo(11)))))
                .thenReturn("Mocked");

        Assert.assertEquals("Mocked", testClass.foo(5));
        Assert.assertNull(testClass.foo(11));
    }

    @Test
    public void geqTest() {
        MockBuddy.when(testClass.foo(geq(90))).thenReturn("geq");

        Assert.assertEquals("geq", testClass.foo(90));
        Assert.assertEquals("geq", testClass.foo(91));
        Assert.assertEquals("geq", testClass.foo(92));

        Assert.assertNull(testClass.foo(89));
    }
}
