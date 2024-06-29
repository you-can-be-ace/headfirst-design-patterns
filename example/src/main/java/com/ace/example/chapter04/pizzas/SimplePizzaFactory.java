package com.ace.example.chapter04.pizzas;

/**
 * 간단한 팩토리
 * 디자인 패턴이라기 보다는 프로그래밍에서 자주 쓰이는 관용구
 * 피자 객체를 생성하는 팩토리.
 * 이 애플리케이션에서 유일하게 구상 Pizza 클래스를 직접 참조
 */
public class SimplePizzaFactory {

    public Pizza createPizza(String type) {
        Pizza pizza = null;

        if (type.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (type.equals("pepperoni")) {
            pizza = new PepperoniPizza();
        } else if (type.equals("clam")) {
            pizza = new ClamPizza();
        } else if (type.equals("veggie")) {
            pizza = new VeggiePizza();
        }
        return pizza;
    }
}
