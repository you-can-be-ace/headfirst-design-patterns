package com.ace.example.chapter11.remote;

import lombok.Getter;
import java.rmi.*;
import java.rmi.server.*;

@Getter
public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
    private static final long serialVersionUID = 2L;

    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;
    State winnerState;

    State state;
    String location;
    int count = 0;

    public GumballMachine(String location, int numberGumballs) throws RemoteException {
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);
        winnerState = new WinnerState(this);

        this.count = numberGumballs;

        if (numberGumballs > 0) {
            state = noQuarterState;
        } else {
            state = soldOutState;
        }

        this.location = location;
    }

    public void insertQuarter() { // 동전이 들어올 때 해야 할 일
        state.insertQuarter();
    }

    public void ejectQuarter() { // 동전을 반환할 때 해야 할 일
        state.ejectQuarter();
    }

    public void turnCrank() { // 손잡이가 돌아갔을 때 해야 할 일
        state.turnCrank();
        state.dispense();
    }

    void setState(State state) {
        this.state = state;
    }

    void releaseBall() {
        System.out.println("알맹이를 내보내고 있습니다.");
        if (count > 0) {
            count = count -1;
        }
    }

    public void refill(int numGumBalls) {
        this.count += count;
        state = noQuarterState;
    }

    public int getCount() {
        return count;
    }

    public State getState() {
        return state;
    }

    public String getLocation() {
        return location;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getWinnerState() {
        return winnerState;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("\n주식회사 왕뽑기");
        result.append("\n자바로 돌아가는 최신형 뽑기 기계\n");
        result.append("남은 개수: " + count + " 개\n");
        result.append(state + "\n");
        return result.toString();
    }
}
