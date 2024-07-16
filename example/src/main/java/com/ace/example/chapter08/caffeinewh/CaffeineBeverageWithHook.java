package com.ace.example.chapter08.caffeinewh;

public abstract class CaffeineBeverageWithHook {

    final void prepareRecipe() {
        boilWater();
        brew(); //서브클래스에서 구현 유도
        pourInCup(); //서브클래스에서 구현 유도
        if ( customerWantsCondiments() ) {
            addCondiments();
        }
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("물 끓이는 중");
    }

    void pourInCup() {
        System.out.println("컵에 따르는 중");
    }

    boolean customerWantsCondiments() { //이 메소드는 서브클래스에서 필요시 오버라이드 할 수 있음
        return true;
    }
}
