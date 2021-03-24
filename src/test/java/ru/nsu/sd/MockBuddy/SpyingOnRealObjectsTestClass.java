package ru.nsu.sd.MockBuddy;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.sd.MockBuddy.internal.matching.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

public class SpyingOnRealObjectsTestClass {

    List<String> stringList = new ArrayList<>();
    List<String> spiedStringList = MockBuddy.spy(stringList);

    @Test
    public void shouldCreateSpy() {
        MockBuddy.spy(new Object());
    }

    @Test
    public void shouldStub() {
        spiedStringList.add("one");
        MockBuddy.when(spiedStringList.get(0)).thenReturn("stubbed one");

        Assert.assertEquals("stubbed one", spiedStringList.get(0));
        Assert.assertEquals(1, spiedStringList.size());
    }

    @Test
    public void shouldOverrideStub() {
        MockBuddy.when(spiedStringList.contains(ArgumentMatchers.anyString())).invokeRealMethod();
        MockBuddy.when(spiedStringList.contains("obj")).thenReturn(true);

        Assert.assertFalse(spiedStringList.contains("some string"));
        Assert.assertTrue(spiedStringList.contains("obj"));
    }

    @Test
    public void shouldCallRealMethod() {
        MockBuddy.when(spiedStringList.contains("obj")).invokeRealMethod();

        Assert.assertFalse(spiedStringList.contains("obj"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrow() {
        MockBuddy.when(spiedStringList.contains("exception")).thenThrow(new IllegalArgumentException());

        spiedStringList.contains("exception");
    }
}
