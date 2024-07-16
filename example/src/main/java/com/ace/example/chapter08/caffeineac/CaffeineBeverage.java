package com.ace.example.chapter08.caffeineac;

public abstract class CaffeineBeverage {

    final void prepareRecipe() {
        boilWater();
        brew(); //서브클래스에서 구현 유도
        pourInCup(); //서브클래스에서 구현 유도
        addCondiments();
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("물 끓이는 중");
    }

    void pourInCup() {
        System.out.println("컵에 따르는 중");
    }
}
