package ru.nsu.sd.MockBuddy.examples;

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
