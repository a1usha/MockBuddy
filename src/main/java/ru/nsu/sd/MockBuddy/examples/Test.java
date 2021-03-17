package ru.nsu.sd.MockBuddy.examples;

import ru.nsu.sd.MockBuddy.internal.annotations.MockBuddyAnnotation;

public class Test {

    public Test(int a) {

    }

    public String boolTest(boolean b) {
        if (b) {
            return "True";
        } else {
            return "False";
        }
    }

    public String foo(Integer num) {
        return "Foo";
    }

    public Integer bar(Integer num, int baz) {
        return num;
    }

    public String tmp() {
        return "Baz";
    }
}
