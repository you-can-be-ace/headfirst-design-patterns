package com.ace.example.chapter04.pizzaif;

public class PizzaTestDrive {

    public static void main(String[] args) {
        PizzaStore nyStore = new NYStylePizzaStore();
        PizzaStore chicagoStore = new ChicagoStylePizzaStore();

        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("미애가 주문한 " + pizza.getName() + "\n");

        pizza = nyStore.orderPizza("clam");
        System.out.println("금비가 주문한 " + pizza.getName() + "\n");

        pizza = chicagoStore.orderPizza("pepperoni");
        System.out.println("보람이가 주문한 " + pizza.getName() + "\n");

        pizza = chicagoStore.orderPizza("veggie");
        System.out.println("혜령이가 주문한 " + pizza.getName() + "\n");
    }
}
