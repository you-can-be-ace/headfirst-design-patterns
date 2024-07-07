# 커맨드 패턴의 정의
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/c08a66c4-e88c-41f7-8fce-09b61eae455e)

- `커맨드 객체` : 일련의 행동을 특정 리시버와 연결함으로써 요청을 캡슐화
- 요청 내역을 객체로 캡슐화한 객체인 커맨드 객체를 서로 다른 요청 내역에 따라 매개변수화
- 이를 통해 작업을 요청하는 쪽과 작업을 처리하는 쪽을 분리

## 구성 요소

- `Client` : `ConcreteCommand` 및 `Receiver` 생성 후 `Invoker` 에 설정
- `Invoker` :  수행할 명령이 들어 있으며, 설정된 명령을 `Command`에게 수행 요청
- `Command` : 모든 커맨드 객체에서 구현해야 하는 인터페이스
- `ConcreteCommand` : 인터페이스 구현을 통해 특정 행동과 리시버 연결, `execute` 실행 시 특정 리시버의 동작 연결
- `Receiver`  : 요구사항 수행 시 어떤 일을 처리해야 하는지 알고 있는 객체

## 🤔 항상 `Receiver`가 필요할까?

- 커맨드 객체에서 대부분의 행동을 처리해도 됨
- 하지만, Invoker와 Receiver를 분리하기 어려워지고 Receiver로 Command를 매개변수화할 수 없음

## 메타 커맨드 패턴

- 여러 개의 명령을 매크로로 한 번에 실행 가능

# 예제

## Client: `RemoteLoader`

```java
public class RemoteLoader {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();
        RemoteWithUndo remoteWithUndo = new RemoteWithUndo();

        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        GarageDoor garageDoor = new GarageDoor("Garage");
        Stereo stereo = new Stereo("Living Room");

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

        GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
        GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);

        StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);

        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
        remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);
        remoteControl.setCommand(3, stereoOnWithCD, stereoOff);

        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        remoteControl.onButtonWasPushed(1);
        remoteControl.offButtonWasPushed(1);
        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);
        remoteControl.onButtonWasPushed(3);
        remoteControl.offButtonWasPushed(3);

        System.out.println("\n-------------\n");

        // Undo Button Test
        remoteWithUndo.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteWithUndo.setCommand(1, kitchenLightOn, kitchenLightOff);
        remoteWithUndo.setCommand(2, ceilingFanOn, ceilingFanOff);
        remoteWithUndo.setCommand(3, stereoOnWithCD, stereoOff);

        remoteWithUndo.onButtonWasPushed(0);
        remoteWithUndo.offButtonWasPushed(0);

        System.out.println(remoteWithUndo);
        remoteWithUndo.undoButtonWasPushed();

        remoteWithUndo.offButtonWasPushed(0);
        remoteWithUndo.onButtonWasPushed(0);

        System.out.println(remoteWithUndo);
        remoteWithUndo.undoButtonWasPushed();

        System.out.println("\n-------------\n");

        // CeilingFan Button Test
        RemoteWithUndo remoteWithUndo2 = new RemoteWithUndo();

        CelingFanMediumCommand ceilingFanMedium = new CelingFanMediumCommand(ceilingFan);
        CeilingFanHighCommand ceilingFanHigh = new CeilingFanHighCommand(ceilingFan);
        CeilingFanLowCommand ceilingFanLow = new CeilingFanLowCommand(ceilingFan);

        remoteWithUndo2.setCommand(0, ceilingFanMedium, ceilingFanOff);
        remoteWithUndo2.setCommand(1, ceilingFanHigh, ceilingFanOff);

        remoteWithUndo2.onButtonWasPushed(0);
        remoteWithUndo2.offButtonWasPushed(0);

        System.out.println(remoteWithUndo2);
        remoteWithUndo2.undoButtonWasPushed();

        remoteWithUndo2.onButtonWasPushed(1);

        System.out.println(remoteWithUndo2);
        remoteWithUndo2.undoButtonWasPushed();
    }
}
```

## Invoker: `RemoteWithUndo`

```java
public class RemoteWithUndo {
    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;

    public RemoteWithUndo() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        **Command noCommand = new NoCommand(); // 널 객체 사용**

        for(int i = 0; i < 7; i++) { // 널 객체로 멤버 변수 초기화
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }

        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonWasPushed() {
        undoCommand.undo();
    }
}
```

## Command

```java
public interface Command {
    public void execute(); // 작업 수행
    public void undo();    // 작업 취소
}
```

## ConcreteCommand: `LightOnCommand`

```java
public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}
```

## Receiver: `Light`

```java
public class Light {
    String location;

    public Light(String location) {
        switch(location) {
            case "Living Room":
                this.location = "거실";
                break;
            case "Kitchen":
                this.location = "주방";
                break;
            case "Garage":
                this.location = "차고";
                break;
        }
    }

    public void on() {
        System.out.println(location + " 조명이 켜졌습니다.");
    }

    public void off() {
        System.out.println(location + " 조명이 꺼졌습니다.");
    }
}
```

# 🥉 널 객체(Null Object)

```java
public class NoCommand implements Command {
    @Override
    public void execute() {}

    @Override
    public void undo() {}
}
```

- 리턴할 객체는 없으나 NULL을 처리하지 않게 하고 싶을 경우 사용
- 호출처에서 `null`을 리턴받지 않기 때문에 NPE 방지 가능
- 여러 디자인 패턴에서 유용하게 쓰이고 있어 디자인 패턴으로 분류하기도 함

## 여러 동작을 한 번에 처리하기

## Client: `RemoteMacro`

```java
public class RemoteMacro {
    public static void main(String[] args) {
        RemoteWithUndo remoteControl = new RemoteWithUndo();

        Light light = new Light("Living Room");
        TV tv = new TV("Living Room");
        Stereo stereo = new Stereo("Living Room");
        Hottub hottub = new Hottub();

        LightOnCommand lightOn = new LightOnCommand(light);
        StereoOnCommand stereoOn = new StereoOnCommand(stereo);
        TVOnCommand tvOn = new TVOnCommand(tv);
        HottubOnCommand hottubOn = new HottubOnCommand(hottub);

        LightOffCommand lightOff = new LightOffCommand(light);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);
        TVOffCommand tvOff = new TVOffCommand(tv);
        HottubOffCommand hottubOff = new HottubOffCommand(hottub);

        Command[] partyOn = { lightOn, stereoOn, tvOn, hottubOn };
        Command[] partyOff = { lightOff, stereoOff, tvOff, hottubOff };

        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partyOff);

        remoteControl.setCommand(0, partyOnMacro, partyOffMacro);

        System.out.println(remoteControl);
        System.out.println("--- 매크로 ON ---");
        remoteControl.onButtonWasPushed(0);

        System.out.println("\n--- 매크로 OFF ---");
        remoteControl.offButtonWasPushed(0);

        System.out.println("\n--- UNDO ---");
        remoteControl.undoButtonWasPushed();
    }
}
```

## ConcreteCommand: `MacroCommand`

```java
public class MacroCommand implements Command {
    Command[] commands;

    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for(int i=0; i< commands.length; i++) {
            commands[i].execute();
        }
    }

    @Override
    public void undo() {
        for(int i=commands.length-1; i>=0; i--) {
            commands[i].undo();
        }
    }
}
```

# Swing 라이브러리의 커맨드 패턴

```java
public class SwingObserverExample {

	JFrame frame;

	public static void main(String[] args) {
		SwingObserverExample example = new SwingObserverExample();
		example.go();
	}

	public void go() {
		frame = new JFrame();

		JButton button = new JButton("할까? 말까?");

		button.addActionListener(event -> 
			System.out.println("하지마! 아마 후회할 걸?")
		);
		button.addActionListener(event ->
			System.out.println("그냥 저질러 버렷!!!")
		);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.CENTER, button);
		frame.setSize(300,300);
		frame.setVisible(true);
	}
}
```

- `JButton` : Invoker
- `ActionListener` : Command Interface
- `AngelListener` , `DevilListener` : Concrete Command
- `System` : Receiver
    - 매개변수로 전달 받는 `event`가 아닌 실제 커맨드 호출 시 동작을 수행하는 객체가 `Receiver`

# 커맨드 패턴 활용

- 스케줄러, 스레드 풀, 작업 큐와 같은 다양한 작업에 적용 가능
- 애플리케이션이 다운되었을 때 행동 복구 가능하도록 구현하기 위해 활용 가능
    - 특정 체크 포인트 이후의 모든 행동을 로그에 기록하는 방식으로 복구 시스템 구축 가능
    - 확장하여 일련의 작업에 트랜잭션 적용하여 원자성 보장 가능
