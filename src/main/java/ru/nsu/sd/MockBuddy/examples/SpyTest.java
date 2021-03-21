package ru.nsu.sd.MockBuddy.examples;

public class SpyTest {

    private String cat;
    private Integer age;
    private Integer sum;

    public SpyTest() {

    }

    public SpyTest(A a) {

    }

    public SpyTest(B b) {

    }

    public SpyTest(String a) {
        this.cat = a;
    }

    public SpyTest(String a, Integer b) {
        this.cat = a;
        this.age = b;
    }

}
