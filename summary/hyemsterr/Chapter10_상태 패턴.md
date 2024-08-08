# 상태 패턴

<aside>
💡 **객체의 내부 상태가 변할 때 객체의 행동을 함께 바꾸도록 하는 패턴**

</aside>

```java
public class GumballMachine {
	State soldOutState;
	State noQuarterState;
	State hasQuarterState;
	State soldState;
	
	State state;
	
	// 생성자에서 초기 상태를 지정한다.
	public Gunballmachine() {
		state = noQuarterState;
	}
	
	// Context 객체(Gumballmachine)에서 실제 동작은 상태 객체에 위임한다.
	public void insertQuarter() {
		state.insertQuarter();
	}
	
	public void ejectQuarter() {
	state.ejectQuarter();
	}
}
```

- 상태를 별도의 클래스로 캡슐화하여 현재 상태를 나타내는 객체에게 행동을 위임한다.
- 여기서 GumbalMachine은 **Context 객체**, State 타입의 객체들은 **상태 객체**이다.
    - Context 객체에서 실제 동작은 상태 객체에 위임하여 상태 객체가 처리하게 되는 구조이다.
    - 클라이언트는 상태 객체의 존재를 몰라도 된다.
- **상태 전환 로직이 상태 클래스에 존재해야 할까?**
    - 상태 전환이 고정되어 있으면 상태 전환 흐름을 결정하는 코드를 Context에 넣어도 된다.
    - 상태 전환이 동적으로 결정된다면 상태 클래스 내에서 처리하는 것이 좋다.
        - 단, 이렇게 하면 상태 클래스 사이에 의존성이 생기는 단점이 있다.

### 🧐 언제 사용할까?

- 객체의 행동이 상태에 따라 달라지는 경우 (ex. GumballMachine)
- 상태 전환 로직이 복잡하여 if-else 구문이 많아질 때
- 상태에 따른 행동을 독립적으로 관리하고 싶을 때

### 🤩 어떤 점이 좋을까?

- 각 상태의 모든 행동을 한 클래스에 넣으므로 행동이 국지화된다.
- 새로운 상태를 추가할 때, 기존 코드는 수정하지 않고 새로운 상태 클래스만 추가하면 되므로 유연성과 확장성이 증가한다.
- **OCP**(개방-폐쇄 원칙, 변경에는 닫혀 있고 확장에는 열려 있다.)를 준수한다.

# 상태 패턴 vs 전략 패턴

### 상태 패턴

- 상황에 따라 Context 객체에서 한 상태 객체에게 모든 행동을 맡기게 된다. → 클라이언트는 상태 객체를 몰라도 됨
- 상태를 캡슐화하여 상태별 행동을 독립적으로 관리하는 용도로 사용된다.

### 전략 패턴

- 클라이언트가 Context 객체에게 어떤 전략 객체를 사용할 지 지정해준다.
- 전략 패턴은 주로 실행 시에 전략 객체를 변경할 수 있는 유연성을 제공하는 용도로 사용된다.
