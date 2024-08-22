package com.ace.example.chapter12.iterator;

public class DuckSimulator {
    public static void main(String[] args) {
        DuckSimulator simulator = new DuckSimulator();
        AbstractDuckFactory duckFactory = new CountingDuckFactory();

        simulator.simulate(duckFactory);
    }

    void simulate(AbstractDuckFactory duckFactory) {

        Quakable redheadDuck = duckFactory.createRedheadDuck();
        Quakable duckCall = duckFactory.createDuckCall();
        Quakable rubberDuck = duckFactory.createRubberDuck();
        Quakable gooseDuck = duckFactory.createGooseDuck(); // 거위는 카운트 되지 않음

        Flock flockOfDucks = new Flock();

        flockOfDucks.add(redheadDuck);
        flockOfDucks.add(duckCall);
        flockOfDucks.add(rubberDuck);
        flockOfDucks.add(gooseDuck);

        Flock flockOfMallards = new Flock();

        Quakable mallardOne = duckFactory.createMallardDuck();
        Quakable mallardTwo = duckFactory.createMallardDuck();
        Quakable mallardThree = duckFactory.createMallardDuck();
        Quakable mallardFour = duckFactory.createMallardDuck();

        flockOfMallards.add(mallardOne);
        flockOfMallards.add(mallardTwo);
        flockOfMallards.add(mallardThree);
        flockOfMallards.add(mallardFour);

        flockOfDucks.add(flockOfMallards);

        System.out.println("\n오리 시뮬레이션 게임: 전체 무리");
        simulate(flockOfDucks);

        System.out.println("\n오리 시뮬레이션 게임: 물오리 무리");
        simulate(flockOfMallards);

        System.out.println("오리가 소리 낸 횟수: " + QuackCounter.getQuacks() + " 번");

    }

    void simulate(Quakable duck) {
        duck.quack();
    }
}
