package ru.nsu.sd.MockBuddy.examples;

public class FieldTest {

    private static final String stFnStr = "Static final string";
    private final String fnStr;

    public FieldTest(String fnStr) {
        this.fnStr = fnStr;
    }

    public static String getStFnStr() {
        return stFnStr;
    }

    public String getFnStr() {
        return fnStr;
    }
}
