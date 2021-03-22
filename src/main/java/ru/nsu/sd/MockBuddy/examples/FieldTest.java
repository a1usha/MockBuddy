package ru.nsu.sd.MockBuddy.examples;

public class FieldTest {

    private static final String stFnStr = "static final string";
    private final String fnStr;
    private static String stStr;

    public FieldTest(String fnStr, String stStr) {
        this.fnStr = fnStr;
        FieldTest.stStr = stStr;
    }

    public static String getStFnStr() {
        return stFnStr;
    }

    public static String getStStr() {
        return stStr;
    }

    public String getFnStr() {
        return fnStr;
    }
}
