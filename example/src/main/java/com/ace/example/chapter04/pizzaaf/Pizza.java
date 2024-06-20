package com.ace.example.chapter04.pizzaaf;

import java.util.ArrayList;
import java.util.List;

/**
 * 팩토리에서 만드는 피자
 * Pizza 클래스는 메소드를 오버라이드해서 쓸 수 있도록 추상 클래스로 정의
 */
public abstract class Pizza {
    String name;
    String dough;
    String sauce;
    List<String> toppings = new ArrayList<>();

    public String getName() {
        return name;
    }

    void prepare() {

        System.out.println("준비 중: " + name);
        System.out.println("도우를 돌리는 중..");
        System.out.println("소스를 뿌리는 중..");
        System.out.println("토핑을 올리는 중..");
        for (String topping : toppings) {
            System.out.println(" " + topping);
        }
    }

    void bake() {
        System.out.println("175도에서 25분 간 굽기");
    }

    void cut() {
        System.out.println("피자를 사선으로 자르기");
    }

    void box() {
        System.out.println("상자에 피자 담기");
    }

    public String toString() {
        // code to display pizza name and ingredients
        StringBuffer display = new StringBuffer();
        display.append("---- " + name + " ----\n");
        display.append(dough + "\n");
        display.append(sauce + "\n");
        for (String topping : toppings) {
            display.append(topping + "\n");
        }
        return display.toString();
    }

}
