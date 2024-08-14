package com.ace.example.chapter12.iterator;

//오리 객체 생성을 위한 팩토리 클래스
public class DuckFactory extends AbstractDuckFactory {
    public Quakable createMallardDuck() {
        return new MallardDuck();
    }

    public Quakable createRedheadDuck() {
        return new RedheadDuck();
    }

    public Quakable createDuckCall() {
        return new DuckCall();
    }

    public Quakable createRubberDuck() {
        return new RubberDuck();
    }

    public Quakable createGooseDuck() {
        return new GooseAdapter(new Goose());
    }
}
