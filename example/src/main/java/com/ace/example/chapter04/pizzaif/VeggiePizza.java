package com.ace.example.chapter04.pizzaif;

public class VeggiePizza extends Pizza{

    PizzaIngredientFactory ingredientFactory;

    public VeggiePizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    /**
     * 야채 피자를 만드는 준비 과정
     * 재료가 필요할 때마다 팩토리에 있는 메소드를 호출해서 만든다.
     */
    void prepare() {
        System.out.println("준비 중:"+name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        veggies = ingredientFactory.createVeggies();
    }
}
