package com.ace.example.chapter04.pizzaaf;

public class PizzaTestDrive {

    public static void main(String[] args) {
        PizzaStore nyStore = new NYStylePizzaStore();
        PizzaStore chicagoStore = new ChicagoStylePizzaStore();

        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("보나가 주문한" + pizza.getName() + "\n");

        pizza = chicagoStore.orderPizza("veggie");
        System.out.println("금비가 주문한" + pizza.getName() + "\n");
    }
}
