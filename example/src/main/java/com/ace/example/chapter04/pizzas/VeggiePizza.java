package com.ace.example.chapter04.pizzas;

/**
 * 팩토리에서 생산하는 제품에 해당하는 구상 클래스
 * 각 피자는 Pizza 인터페이스를 구현해야 하며
 * 구상클래스여야 한다.
 * 이 두 조건 만족시 팩토리에서 피자를 만들고 클라이언트로 넘길 수 있습니다.
 * 디자인 패턴을 얘기할 때, "인터페이스를 구현한다" 라는 표현이 나온다고 해서
 * "클래스를 선언하는 부분에 implements 키워드를 써서 어떤 자바 인터페이스를 구현하는 클래스를 만든다" 라고
 * 생각하면 안된다.
 * 일반적으로 어떤 상위 형식(클래스와 인터페이스)에 있는
 * 구상 클래스는 그 상위 형식의 '인터페이스를 구현하는' 클래스라고 생각하면 된다.
 */
public class VeggiePizza extends Pizza{

    public VeggiePizza() {
        name = "Veggie Pizza";
        dough = "Crust";
        sauce = "Marinara sauce";
        toppings.add("VeggiePizza toping1");
        toppings.add("VeggiePizza toping2");
        toppings.add("VeggiePizza toping3");
        toppings.add("VeggiePizza toping4");
    }
}
