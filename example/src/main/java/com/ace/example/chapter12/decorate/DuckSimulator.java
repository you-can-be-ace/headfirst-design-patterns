package com.ace.example.chapter12.decorate;

public class DuckSimulator {
    public static void main(String[] args) {
        DuckSimulator simulator = new DuckSimulator();
        simulator.simulate();
    }

    void simulate() {
        Quakable mallardDuck = new QuackCounter(new MallardDuck());
        Quakable redheadDuck = new QuackCounter(new RedheadDuck());
        Quakable duckCall = new QuackCounter(new DuckCall());
        Quakable rubberDuck = new QuackCounter(new RubberDuck());
        Quakable gooseDuck = new GooseAdapter(new Goose()); //거위는 카운트 되지 않음

        System.out.println("\n오리 시뮬레이션 게임 (+데코레이터)");

        simulate(mallardDuck);
        simulate(redheadDuck);
        simulate(duckCall);
        simulate(rubberDuck);
        simulate(gooseDuck);

        System.out.println("오리가 소리 낸 횟수: " + QuackCounter.getQuacks() + " 번");
    }

    void simulate(Quakable duck) {
        duck.quack();
    }
}
