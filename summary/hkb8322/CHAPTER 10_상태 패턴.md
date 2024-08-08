# 상태 패턴

## 정의

- 객체의 내부 상태가 바뀜에 따라 객체의 행동을 바꿈
- 객체의 클래스가 바뀌는 것과 같은 결과를 얻음

## 구성 요소

- `Context` : 여러 가지 내부 상태가 들어있을 수 있음
- `State` : 모든 상태의 행동을 정의한 인터페이스
- `ConcreteState` : `State` 인터페이스를 구현한 구현체

## 특징

- 각 상태의 행동을 개별 클래스로 정의
- `if` 문들을 대체
- OCP 준수 (기능 변경 시 기존 상태는 수정하지 않고 신규 상태만 확장 가능)
- 상태를 관리하는 일은 `Context` 객체가 책임짐

## 예제

```java
@Getter
public class GumballMachine { // 상태를 보유하고 있는 Context 객체

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
```

```java
public interface State { // 상태별 행동을 인터페이스로 정의

	void insertQuarter();
	void ejectQuarter();
	void turnCrank();
	void dispense();

	void refill();
}
```

```java
public class SoldState implements State { // 상태 인터페이스 구현

    GumballMachine gumballMachine;

    public SoldState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

	public void insertQuarter() {
		System.out.println("알맹이를 내보내고 있습니다.");
	}

	public void ejectQuarter() {
		System.out.println("이미 알맹이를 뽑으셨습니다.");
	}

	public void turnCrank() {
		System.out.println("손잡이는 한 번만 돌려 주세요.");
	}

	public void dispense() {
		gumballMachine.releaseBall();
		if (gumballMachine.getCount() > 0) {
			gumballMachine.setState(gumballMachine.getNoQuarterState());
		} else {
			System.out.println("Oops, out of gumballs!");
			gumballMachine.setState(gumballMachine.getSoldOutState());
		}
	}

	public void refill() {

	}

	public String toString() {
		return "판매";
	}
}
```

## ✏️ 참고 사항

- 상태 변환을 반드시 상태 인터페이스 구현체에서 결정해야 하는 건 아님
    - 상태 전환이 고정된 경우 `Context` 객체에서도 상태 전환 흐름 결정 가능
    - 상태 전환이 동적인 경우 상태 클래스 내에서 처리하는 것이 좋음 (단, 상태 간 의존성 생김)
- 클라이언트는 `Context` 객체의 상태를 변경할 수 없음
- `Context` 인스턴스가 여러 개인 경우 상태 객체 공유 가능
    - 일반적으로 각 상태를 정적 인스턴스 변수에 할당하여 사용

# 전략 패턴 🆚 상태 패턴

## 전략 패턴

- 클라이언트가 `Context` 객체에게 어떤 전략 객체를 사용할지 지정
- 서브 클래스를 만드는 대신 유연성을 극대화하는 용도

## 상태 패턴

- `Context` 객체에서 여러 상태 객체 중 한 객체에게 모든 행동을 맡기게 됨
    - 객체 내부 상태에 따라 현재 상태를 나타내는 객체가 바뀜
- 클라이언트는 상태 객체를 몰라도 됨
- `Context` 객체 내 수많은 조건문을 넣는 대신 상태 패턴 사용
