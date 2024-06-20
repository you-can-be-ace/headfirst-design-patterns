package com.ace.example.chapter04.pizzaaf;

/**
 * 캘리포니아 스타일 피자를 만들고 싶은 지점은 CaliforniaStylePizzaStore 클래스를 쓰면 된다.
 */
public class CaliforniaStylePizzaStore extends PizzaStore{

    Pizza createPizza(String item) {
        if (item.equals("cheese")) {
            return new CaliforniaStyleCheesePizza();
        } else if (item.equals("veggie")) {
            return new CaliforniaStyleVeggiePizza();
        } else if (item.equals("clam")) {
            return new CaliforniaStyleClamPizza();
        } else if (item.equals("pepperoni")) {
            return new CaliforniaStylePepperoniPizza();
        } else return null;
    }
}
