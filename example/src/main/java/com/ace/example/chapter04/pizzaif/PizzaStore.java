package com.ace.example.chapter04.pizzaif;

/**
 * 이제 PizzaStore 는 추상 클래스가 된다. 
 */
public abstract class PizzaStore {

    /**
     * 각 서브클래스는 createPizza() 메소드를 오버라이드하지만, 
     * orderPizza() 메소드는 PizzaStore 에서 정의한 내용을 그대로 사용한다. 
     * 우리가 정의한 메소드를 고쳐 쓸 수 없게 하고 싶다면 orderPizza() 메소드를 final로 선언하면 된다.
     * 슈퍼클래스(PizzaStore)에 있는 orderPizza() 메소드는 어떤 피자가 만들어지는지 전혀 알 수 없다.
     * 피자를 준비하고, 굽고, 자르고, 포장하는 작업을 처리할 뿐이다.
     * @param type
     * @return
     */
    public Pizza orderPizza(String type) {
        Pizza pizza;

        //팩토리 객체가 아닌 PizzaStore 에 있는 createPizza 호출
        pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
    
    //팩토리 객체가 아닌 아래 메소드 이용
    abstract Pizza createPizza(String type);
}
