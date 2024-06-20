package com.ace.example.chapter04.pizzaaf;

/**
 * 뉴욕 스타일 피자를 만들고 싶은 지점은 NYStylePizzaStore 클래스를 쓰면 된다.
 *
 */
public class NYStylePizzaStore extends PizzaStore{

    /**
     * createPizza()는 Pizza 객체를 리턴하며,
     * Pizza 의 서브클래스 가운데 어느 구상 클래스 객체의
     * 인스턴스를 만들어서 리턴할지는 전적으로 PizzaStore 의 서브클래스(NYStylePizzaStore) 에 의해 결정됩니다.
     * @param item
     * @return
     */
    Pizza createPizza(String item) {
        if (item.equals("cheese")) {
            return new NYStyleCheesePizza();
        } else if (item.equals("veggie")) {
            return new NYStyleVeggiePizza();
        } else if (item.equals("clam")) {
            return new NYStyleClamPizza();
        } else if (item.equals("pepperoni")) {
            return new NYStylePepperoniPizza();
        } else return null;
    }
}
