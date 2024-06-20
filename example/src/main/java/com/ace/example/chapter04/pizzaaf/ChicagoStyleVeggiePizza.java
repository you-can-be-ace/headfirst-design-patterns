package com.ace.example.chapter04.pizzaaf;

public class ChicagoStyleVeggiePizza extends Pizza {

    public ChicagoStyleVeggiePizza() {
        name = "시카고 스타일 야채 피자";
        dough = "시카고 스타일 야채 피자";
        sauce = "시카고 스타일 야채 피자";
        toppings.add("잘게 조각난 모짜렐라 치즈");
        toppings.add("잘게 조각난 피망");
        toppings.add("잘게 조각난 양파");
        toppings.add("잘게 조각난 호박");
    }

    void cut() {
        System.out.println("네모난 모양으로 피자 자르기");
    }
}
