package com.ace.example.chapter04.pizzaif;

public class PepperoniPizza extends Pizza{

    PizzaIngredientFactory ingredientFactory;

    public PepperoniPizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    void prepare() {
        System.out.println("준비 중:"+name);
        dough = ingredientFactory.createDough();
        /**
         * sauce: Pizza 에 있는 인스턴스 변수에 이 피자에서 사용할 특정 소스의 레퍼런스를 대입
         * ingredientFactory: 우리가 사용하는 원재료 팩토리
         * createSauce: createSauce() 메소드에서는 해당 지역에서 사용하는 소스를 리턴
         */
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        pepperoni = ingredientFactory.createPepperoni();
    }
}
