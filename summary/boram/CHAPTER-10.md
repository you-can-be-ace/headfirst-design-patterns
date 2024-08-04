# Ch10. 상태 패턴 

## 상태패턴
- 객체의 내부 상태가 바뀜에 따라서 객체의 행동을 바꿀 수 있다. 마치 객체의 클래스가 바뀌는 것과 같은 결과를 얻을 수 있다.

### 상태패턴 클래스 다이어그램 
<img width="453" alt="image" src="https://github.com/user-attachments/assets/8e6ac64f-4e1e-4633-9de3-eadbd1bd60dd">

- Context : Context 클래스에는 여러 가지 내부 상태가 들어있을 수 있다. 앞에서 살펴본 예에서는 GumballMachine이 Context에 해당한다.
- State : State 인터페이스는 모든 구상 상태 클래스의 공통 인터페이스를 정의한다. 모든 상태 클래스에서 같은 인터페이스를 구현하므로 바꿔 가면서 쓸 수 있다.
  - `state.handle()` : Context의 request() 메소드가 호출되면 그 작업은 상태 객체에게 맡겨진다. 
- ConcreteStateA, ConcreteStateB : ConcreteState는 Context로부터 전달된 요청을 자기 나름의 방식으로 구현해서 처리한다. 이러면 Context에서 상태를 바꿀 때마다 행동도 바뀌게 된다. 

### 상태 패턴 적용하기 

#### 상태 인터페이스 
```
public interface State {

	void insertQuarter();
	void ejectQuarter();
	void turnCrank();
	void dispense();

	void refill();
}
```

#### 상태 인터페이스 구현 클래스 -> NoQuarterState 
```
- 상태가 추가될 때마다 구현 클래스 추가 
public class NoQuarterState implements State {

    GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) { //Context 객체 gumballMachine
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

	public void refill() {

	}

	public String toString() {
		return "동전 없음";
	}
}
```


## 상태패턴과 전략패턴 
- 다이어그램은 같으나 상태패턴과 전략패턴의 용도는 다르다.
- 상태패턴
  - 상태 객체에 일련의 행동이 캡슐화된다.
  - Context 객체의 행동이 자연스럽게 바뀌게 된다.
  - 클라이언트는 상태 객체를 몰라도 된다.
  - Context 객체에 수많은 조건문을 넣는 전략패턴과 달리 인터페이스 구현 클래스에서 Context 객체의 행동이 자연스럽게 바뀌게 된다.
- 전략패턴
  - 클라이언트가 Context 객체에게 어떤 전략 객체를 사용할지를 지정해준다.
  - 주로 실행 시에 전략 객체를 변경할 수 있는 유연성을 제공하는 용도로 쓰인다.
  - 서브클래스를 만드는 방법을 대신해서 유연성을 극대화하는 용도로 사용

## 핵심 정리 
- 상태 패턴을 사용하면 내부 상태를 바탕으로 여러 가지 서로 다른 행동을 사용할 수 있다.
- 상태 패턴을 사용하면 프로시저형 상태 기계를 쓸 때와는 달리 각 상태를 클래스로 표현한다.
- Context 객체는 현재 상태에게 행동을 위임한다.
- 각 상태를 클래스로 캡슐화해서 나중에 변경해야 하는 내용을 국지화 할 수 있다.
- 상태 패턴을 쓰면 디자인에 필요한 클래스의 개수가 늘어난다.
- State 클래스를 여러 Context 객체의 인스턴스에서 공유하도록 디자인할 수도 있다.
