package com.ace.example.chapter11.remote;

public class NoQuarterState implements State {
    private static final long serialVersionUID = 2L;
    transient GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("동전을 넣으셨습니다.");
        gumballMachine.setState(gumballMachine.getHasQuarterState());
    }

    public void ejectQuarter() {
        System.out.println("동전을 넣어 주세요.");
    }

    public void turnCrank() {
        System.out.println("동전을 넣어 주세요.");
    }

    public void dispense() {
        System.out.println("동전을 넣어 주세요.");
    }

    public String toString() {
        return "동전 없음";
    }
}
