package com.ace.example.chapter11.remote;

public class SoldOutState implements State {
    private static final long serialVersionUID = 2L;
    transient GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("매진되어 동전을 넣을 수 없습니다.");
    }

    public void ejectQuarter() {
        System.out.println("동전을 넣지 않아 반환 받을 수 없습니다.");
    }

    public void turnCrank() {
        System.out.println("손잡이를 돌렸지만 알맹이가 없습니다.");
    }

    public void dispense() {
        System.out.println("매진입니다");
    }

    public String toString() {
        return "매진";
    }
}
