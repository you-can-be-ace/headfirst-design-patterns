package com.ace.example.chapter12.think;

public class DuckSimulator {
    public static void main(String[] args) {
        DuckSimulator simulator = new DuckSimulator();
        simulator.simulate();
    }

    void simulate() {
        Quakable mallardDuck = new MallardDuck();
        Quakable redheadDuck = new RedheadDuck();
        Quakable duckCall = new DuckCall();
        Quakable rubberDuck = new RubberDuck();
        Quakable gooseDuck = new GooseAdapter(new Goose());

        System.out.println("\n오리 시뮬레이션 게임");

        simulate(mallardDuck);
        simulate(redheadDuck);
        simulate(duckCall);
        simulate(rubberDuck);
        simulate(gooseDuck);
    }

    void simulate(Quakable duck) {
        duck.quack();
    }
}
