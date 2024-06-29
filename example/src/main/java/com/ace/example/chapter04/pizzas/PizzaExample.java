package com.ace.example.chapter04.pizzas;

public class PizzaExample {
    public static void main(String[] args) {
        SimplePizzaFactory factory = new SimplePizzaFactory();
        PizzaStore store = new PizzaStore(factory);

        Pizza pizza = store.orderPizza("clam");
        System.out.println("내가 주문한 피자.. " + pizza.getName() + "\n");
        System.out.println(pizza);

        pizza = store.orderPizza("cheese");
        System.out.println("내가 주문한 피자.. " + pizza.getName() + "\n");
        System.out.println(pizza);
    }
}
