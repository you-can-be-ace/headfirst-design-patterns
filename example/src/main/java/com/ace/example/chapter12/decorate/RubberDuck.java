package com.ace.example.chapter12.decorate;

public class RubberDuck implements Quakable {
    @Override
    public void quack() {
        System.out.println("삑삑");
    }
}
