package com.ace.example.chapter12.factory;

public class QuackCounter implements Quakable {
    Quakable duck;
    static int numberOfQuacks;

    public QuackCounter(Quakable duck) {
        this.duck = duck;
    }

    @Override
    public void quack() {
        duck.quack();
        numberOfQuacks++;
    }

    public static int getQuacks() {
        return numberOfQuacks;
    }
}
