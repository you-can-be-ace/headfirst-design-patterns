package com.ace.example.chapter10;

import lombok.Getter;

@Getter
public class GumballMachine {

    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;
    State winnerState;

    State state;
    int count = 0;

    public GumballMachine(int numberGumballs) {
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);

        this.count = numberGumballs;
        if (numberGumballs > 0) {
            state = noQuarterState;
        } else {
            state = soldOutState;
        }
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
        state.refill();
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("\n주식회사 왕뽑기");
        result.append("\n자바로 돌아가는 최신형 뽑기 기계\n");
        result.append("남은 개수: ").append(count).append(" 개\n");
        result.append(state).append("\n");
        return result.toString();
    }
}
