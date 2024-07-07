# 커맨드 패턴이란?

- 작업을 요청하는 쪽과 작업을 처리하는 쪽을 분리하는 패턴이다.
- 요청 내역을 객체로 캡슐화해서 객체를 서로 다른 요청 내역에 따라 매개변수화 할 수 있다.

# 커맨드 패턴 구성요소

### command

- 어떤 행동을 수행할 지 담고 있는 객체
- `execute()` 메소드 하나만 제공하며, 이 메소드는 행동을 캡슐화하고 리시버에 있는 특정 행동을 처리한다.

### invoker

- 커맨드를 실행시키는 주체
- invoker가 command의 `execute()` 메소드를 호출시킨다.

### receiver

- 요구사항을 수행할 때 어떤 일을 처리해야 하는지 알고 있는 객체
- invoker가 command의 `execute()` 메소드를 호출시키면, concreted command 객체에서 리시버를 호출한다.

# 커맨드 패턴 구현하기

```java

public interface Command {
	public void execute();
}

public class ConcreteCommand extends Command {
	Light light;
	
	@Override
	public void execute() {
		light.on();
	}
}

public class RemoteControl {
	Command slot;
	
	public void buttonWasPressed() {
		slot.execute();
	}
}
```

- execute() 메소드만을 가지는 Command 인터페이스를 생성한다.
- Command 인터페이스를 상속받아 execute() 메소드에 각 객체별로 처리되어야 할 내용을 넣는다.
- RemoteControl 클래스는 invoker가 되며, 여기서 Command의 execute() 메소드를 호출시켜 행동을 실행한다.

# 커맨드 패턴 개선포인트

### 매크로 구현하기

```java
public class MacroCommand implements Command {
	Command[] commands;
	
	public void execute() {
		for(Command command : commands) {
			commands[i].execute();
		}
	}
}
```

- 단일 command 객체가 아닌 command 배열을 받아서 저장한다.
- `execute()` 메소드가 실행되면 배열 안의 command를 한 번에 실행한다.

### 히스토리를 스택에 저장하기

- [개선전] UNDO 기능을 위한 히스토리를 invoker 또는 command 객체에서 execute() 실행 전 현재 상태를 저장한다.
- [개선후] 동작들을 스택에 쌓고, UNDO 요청이 들어오면 하나씩 pop 한다.

### execute 요청을 큐에 저장하기

- 커맨드로 컴퓨테이션의 한 부분(리시버와 일련의 행동)을 패키지로 묶어서 일급 객체 형태로 전달한다.
- 그렇게 하면 클라이언트에서 커맨드 객체 생성 뒤 오랜 시간이 지나도 그 컴퓨테이션을 호출할 수 있다.
- 이를 활용하여 스케줄러, 스레드풀, 작업큐 같은 다양한 작업에 활용할 수 있다.

### 모든 행동을 기록해 두었다가 복구에 사용하기

- `store()`와 `load()` 메소드를 추가하여 구현한다.
    - `store()` - 행동 로그 기록
    - `load()` - 애플리케이션 다운 시 커맨드 객체를 다시 로딩해서 execute() 메소드를 순서대로 실행
