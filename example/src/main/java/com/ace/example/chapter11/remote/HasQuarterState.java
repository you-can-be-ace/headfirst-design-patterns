package com.ace.example.chapter11.remote;

import java.util.Random;

public class HasQuarterState implements State {
    private static final long serialVersionUID = 2L;
    transient GumballMachine gumballMachine;
    Random randomWinner = new Random(System.currentTimeMillis()); // 10%의 확률로 당첨 여부 결정

    public HasQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("동전은 한 개만 넣어주세요.");
    }

    public void ejectQuarter() {
        System.out.println("동전이 반환됩니다.");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

    public void turnCrank() {
        System.out.println("손잡이를 돌리셨습니다.");
        int winner = randomWinner.nextInt(10);
        if (winner == 0 && gumballMachine.getCount() > 1) {
            gumballMachine.setState(gumballMachine.getWinnerState());
        } else {
            gumballMachine.setState(gumballMachine.getSoldState());
        }
    }

    public void dispense() {
        System.out.println("알맹이를 내보낼 수 없습니다.");
    }

    public String toString() {
        return "동전 있음";
    }
}