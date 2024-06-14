package com.ace.example.chapter03.component;

public class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "다스 로스트 커피";
    }

    @Override
    public double cost() {
        return .99;
    }
}
