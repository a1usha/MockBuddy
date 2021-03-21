package ru.nsu.sd.MockBuddy.examples;

public class Cat {

    private String name;
    private Integer age;

    public Cat(Integer age) {
        this.age = age;
    }

    public Cat(String a) {
        this.name = a;
    }

    public Cat(String a, Integer b) {
        this.name = a;
        this.age = b;
    }

    public String getCat() {
        return name;
    }

    public int performComputations(int a, int b) {
        return a + b;
    }

    public void sayMeow() {
        System.out.println("Meow");
    }

}
