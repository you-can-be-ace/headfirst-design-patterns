package com.ace.example.chapter12.iterator;

// 오리 + 카운팅 기능 추가 팩토리 클래스
public class CountingDuckFactory extends AbstractDuckFactory {

    public Quakable createMallardDuck() {
        return new QuackCounter(new MallardDuck());
    }

    public Quakable createRedheadDuck() {
        return new QuackCounter(new RedheadDuck());
    }

    public Quakable createDuckCall() {
        return new QuackCounter(new DuckCall());
    }

    public Quakable createRubberDuck() {
        return new QuackCounter(new RubberDuck());
    }

    public Quakable createGooseDuck() { // 거위는 카운트 되지 않음
        return new GooseAdapter(new Goose());
    }

}
