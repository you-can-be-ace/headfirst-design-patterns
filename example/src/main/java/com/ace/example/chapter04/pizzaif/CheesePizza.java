package com.ace.example.chapter04.pizzaif;

public class CheesePizza extends Pizza{

    PizzaIngredientFactory ingredientFactory;

    public CheesePizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    void cut() {
        System.out.println("치즈 피자 네모 모양으로 자르기");
    }

    /**
     * 치즈 피자를 만드는 준비 과정
     * 재료가 필요할 때마다 팩토리에 있는 메소드를 호출해서 만든다.
     */
    void prepare() {
        System.out.println("준비 중:"+name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
    }
}
