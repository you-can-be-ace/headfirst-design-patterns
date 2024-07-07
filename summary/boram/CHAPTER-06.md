# CH06. 커맨드패턴
![image](https://github.com/boboram/TIL/assets/14108487/0042544c-ac36-4617-a332-a579b9a93641)

## 커맨드 패턴
- 커맨드 패턴을 사용하면 요청 내역을 객체로 캡슐화해서 객체를 서로 다른 요청 내역에 따라 매개변수화할 수 있다. 이러면 요청을 큐에 저장하거나 로그로 기록하거나 작업 취소 기능을 사용할 수 있다.
- 인보커객체(종업원,리모컨)는 특정 인터페이스만 구현되어 있다면 그 커맨드 객체에서 실제로 어떤 일을 하는지 신경 쓸 필요가 없다.
- 구성 살펴보기
  - Client : ConcreteCommand를 생성하고 Receiver를 설정한다.
  - Invoker : 명령이 들어잇으며, execute() 메소드를 호출함으로써 커맨드 객체에게 특정 작업을 수행해 달라는 요구를 하게 된다.
  - Command 인터페이스 : 모든 커맨드 객체에서 구현해야 하는 인터페이스
    - execute() : 모든 명령은 execute() 메소드 호출로 수행되며, 리시버에 특정 작업을 처리하라는 지시를 전달
    - undo() : 실행된 명령 취소 off() undo -> on을 다시 실행해주도록 처리하면 된다.
      - undo를 이전 명령으로 돌리는 것 뿐 아니라 여러 번 누르도록 하려면 스택(LIFO)에 넣으면 된다.
  - ConcreteCommand : 특정 행동과 리시버를 연결해 준다. 인보커에서 execute() 호출로 요청하면 ConcreteCommand 객체에서 리시버에 있는 메소드를 호출해서 그 작업을 처리
  - Receiver : 요구 사항을 수행할 때 어떤 일을 처리해야 하는지 알고 있는 객체
 
### 메크로 커맨드 패턴
- 실행하고자 하는 여러 개의 명령을 매크로로 한번에 실행 가능
```
  Command[] partyOn = { lightOn, stereoOn, tvOn, hottubOn };
  Command[] partyOff = { lightOff, stereoOff, tvOff, hottubOff };

  MacroCommand partyOnfMacro = new MacroCommand(partyOn);
  MacroCommand partyOfffMacro = new MacroCommand(partyOff);

  remoteControl.set(0, partyOnMacro, partyOffMacro);
```

### 핵심 정리 
- 커맨드 패턴을 사용하면 요청하는 객체와 요청을 수행하는 객체를 분리할 수 있다.
- 이렇게 분리하는 과정의 중심에는 커맨드 객체가 있으며, 이 객체가 행동이 들어있는 리시버를 캡슐화한다.
- 인보커는 무언가 요청할 때 커맨드 객체의 execute() 메소드를 호출하면 된다. execute() 메소드는 리시버에 있는 행동을 호출한다.
- 커맨드는 인보커를 매개변수화 할 수 있다. 실행 중에 동적으로 매개변수화를 설정할 수도 있다.
- execute() 메소드가 마지막으로 호출되기 전에 상태로 되돌리는 작업 취소 메소드(`undo()`)를 구현하면 커맨드 패턴으로 작업 취소 기능을 구현할 수도 있다.
- 매크로 커맨드는 커맨드를 확장해서 여러 개의 커맨드를 한 번에 호출할 수 있게 해 주는 가장 간편한 방법이다. 매크로 커맨드로도 어렵지 않게 작업 취소 기능을 구현할 수 있다.
- 프로그래밍을 하다 보면 요청을 스스로 처리하는 '스마트' 커맨드 객체를 사용하는 경우도 종종 있다.
- 커맨드 패턴을 활용해서 로그 및 트랜잭션 시스템을 구현할 수 있다.
