package ru.nsu.sd.MockBuddy.testclasses;

public class C {

    private A a;
    private B b;

    public C(A a, B b) {
        this.a = a;
        this.b = b;
    }

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
