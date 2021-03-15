package ru.nsu.sd.MockBuddy.examples;

public class C {

    private A a;
    private B b;

    public String sayAll() {
        return a.say() + " " + b.say();
    }

    public void setA(A a) {
        this.a = a;
    }

    public void setB(B b) {
        this.b = b;
    }
}
