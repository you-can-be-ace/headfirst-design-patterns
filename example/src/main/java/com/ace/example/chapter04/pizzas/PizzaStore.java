package com.ace.example.chapter04.pizzas;

/**
 * 팩토리를 사용하는 클라이언트
 * 이제 PizzaStore 는 SimplePizzaFactory 로부터 피자 인스턴스를 받게 됨
 */
public class PizzaStore {

    SimplePizzaFactory factory;

    public PizzaStore(SimplePizzaFactory factory) {
        this.factory = factory;
    }

    public Pizza orderPizza(String type) {
        Pizza pizza;

        pizza = factory.createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
