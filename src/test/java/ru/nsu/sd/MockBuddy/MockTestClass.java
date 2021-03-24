package ru.nsu.sd.MockBuddy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.nsu.sd.MockBuddy.testclasses.A;
import ru.nsu.sd.MockBuddy.testclasses.B;
import ru.nsu.sd.MockBuddy.testclasses.C;
import ru.nsu.sd.MockBuddy.testclasses.TestClass;

import static ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers.*;


public class MockTestClass {

    private A a;
    private B b;
    private TestClass test;
    private C c;

    @Before
    public void setup() {
        a = MockBuddy.mock(A.class);
        b = MockBuddy.mock(B.class);
        test = MockBuddy.mock(TestClass.class);
        c = new C(a, b);
    }

    @Test
    public void shouldReturnNull() {
        Assert.assertNull(test.foo(35));
    }

    @Test
    public void shouldStub() {
        MockBuddy.when(test.foo(111)).thenReturn("mocked");

        Assert.assertEquals("mocked", test.foo(111));
    }

    @Test
    public void shouldOverrideStub() {
        MockBuddy.when(test.foo(anyInt())).thenReturn("anyint");
        MockBuddy.when(test.foo(11)).thenReturn("override");

        Assert.assertEquals("anyint", test.foo(56));
        Assert.assertEquals("override", test.foo(11));
    }

    @Test
    public void shouldCallRealMethod() {
        MockBuddy.when(test.foo(123)).invokeRealMethod();

        Assert.assertEquals("Foo", test.foo(123));
    }

    @Test
    public void shouldReturnStubbedValues() {
        MockBuddy.when(a.say()).thenReturn("Mocked A");
        MockBuddy.when(b.say()).thenReturn("Mocked B");

        Assert.assertEquals("Mocked A Mocked B", c.sayAll());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrow() {
        MockBuddy.when(test.foo(333)).thenThrow(new IllegalArgumentException());

        test.foo(333);
    }
}
