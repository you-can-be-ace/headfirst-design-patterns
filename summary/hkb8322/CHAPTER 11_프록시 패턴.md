# 프록시 패턴
## 정의

- 특정 객체로의 접근을 제어하는 대리인(특정 객체를 대변하는 객체) 제공

## 구성 요소

- `Subject` : 주제 인터페이스
- `RealSubject` : 주제 인터페이스를 구현한 구현체
- `Proxy` : 주제 인터페이스를 구현한 `RealSubject` 객체의 대리인

## 종류

- `원격 프록시` : 원격 객체로의 접근 제어 ✅
- `가상 프록시` : 생성하기 힘든 자원으로의 접근 제어 ✅
- `보호 프록시` : 권한이 필요한 자원으로의 접근 제어 ✅
- `방화벽 프록시` : 네트워크 자원으로의 접근 제어를 통한 나쁜 클라이언트로부터 주제 보호
- `스마트 레퍼런스 프록시` : 주제가 참조될 때마다 추가 행동 제공
- `캐싱 프록시` : 비용이 많이 드는 작업의 결과를 임시로 저장, 여러 클라이언트에서 결과 공유
    - 계산 시간과 네트워크 지연 감소 효과 보유
- `동기화 프록시` : 여러 스레드에서 주제에 접근할 때 안전하게 작업을 처리할 수 있게 해줌
- `복잡도 숨김 프록시` : 복잡한 클래스 집합으로의 접근을 제어하여 복잡도를 숨김
    - 퍼사드 패턴은 대체 인터페이스만 제공하지만 이 프록시는 접근 제어
- `지연 복사 프록시` : 클라이언트에서 필요로 할 때까지 객체가 복사되는 것을 지연시킴
    - 변형된 가상 프록시
    - `CopyOnWriteArrayList` 와 관련됨

## 특징

- 종류가 많지만 클라이언트가 실제 객체의 메소드를 호출하면 그 호출을 가로챈다는 공통점 존재
- 보통 팩토리를 통하여 클라이언트에서 진짜 객체가 아닌 프록시를 사용하도록 함

# 프록시 패턴 🆚 데코레이터 패턴 🆚 어댑터 패턴

유사해 보이지만 용도에 차이가 있음

## 프록시 패턴

- 어떤 클래스로의 접근을 제어하는 용도
    - 클라이언트로부터 주제(Subject)를 분리
- 주제와 동일한 인터페이스 사용

## 데코레이터 패턴

- 클래스에 새로운 행동을 추가하는 용도

## 어댑터 패턴

- 다른 객체의 인터페이스를 변경
- 클라이언트 역할에 따라 객체에 있는 특정 메소드로의 접근을 제어하는 보호 프록시와 유사

# RMI (Remote Method Invocation)

- 원격 JVM에 있는 객체를 찾아 메소드 호출 가능
- 클라이언트와 서비스 보조 객체 생성
- 로컬 메소드 호출과 동일한 방식으로 메소드 호출 가능하지만, 네트워킹 및 입출력 기능 필수

## 용어

- `스텁` : 클라이언트 보조 객체 ⇒ `java.lang.reflect.Proxy` 인스턴스
- `스켈레톤` : 서비스 보조 객체

⇒ 사실상 RM 런타임에서 리플렉션을 사용해 클라이언트 호출을 처리하

⇒ 스켈레톤은 실제로 존재하지 않고 스텁은 동적 프록시로 처리됨

## 예제

### 1) 원격 인터페이스 생성

```java
public interface MyRemote extends Remote {
    public String sayHello() throws RemoteException;
}
```

- 원격 호출을 지원함을 알리기 위해 `Remote` 인터페이스 확장

### 2) 서비스 구현 클래스 생성

```java
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    private static final long serialVersionUID = 1L; // 슈퍼 클래스가 Serializable 구현

    @Override
    public String sayHello() {
        return "Server says, 'Hey'";
    }

    public MyRemoteImpl() throws RemoteException {} // 슈퍼 클래스의 예외를 선언하기 위함

    public static void main(String[] args) {
        try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("RemoteHello", service);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
```

- `UnicastRemoteObject` 확장하여 원격 객체로서의 역할 추가
    - `Serializable` 을 구현하기 때문에 `serialVersionUID` 가 필요함
- `Naming.rebind` : 레지스트리에 서비스 등록, 등록된 이름으로 클라이언트가 검색함

### 3) rmiregistry 실행하기

- 터미널을 통해 `rmiregistry` 실행

### 4) 원격 서비스 실행하기

- 다른 터미널을 열고 서비스 실행

# 원격 프록시

## 예제

### 1) 인터페이스 생성

```jsx
public interface GumballMachineRemote **extends Remote** {
    public int getCount() throws RemoteException;
    public String getLocation() throws RemoteException;
    public State getState() throws RemoteException;
}
```

### 2) `Remote` 인터페이스 내 메소드의 모든 리턴 타입의 직렬화 처리

```jsx
public interface State **extends Serializable** {
    public void insertQuarter();
    public void ejectQuarter();
    public void turnCrank();
    public void dispense();
}
```

```jsx
public class NoQuarterState implements State {
    **private static final long serialVersionUID = 2L;**
    transient GumballMachine gumballMachine; // 이 필드는 직렬화에서 제외

		// 기타 메소드
}
```

- 직렬화하여 네트워크로 전송 가능하도록 처리

### 3) 구현체 구현

```jsx
@Getter
public class GumballMachine 
	**extends UnicastRemoteObject implements GumballMachineRemote** { // 원격 서비스 가능하도록 구현
    private static final long serialVersionUID = 2L;

    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;
    State winnerState;

    State state;
    String location;
    int count = 0;

		// 슈퍼 클래스에서 해당 예외를 던지기 때문에 동일하게 `throws` 처리
    public GumballMachine(String location, int numberGumballs) **throws RemoteException** {
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

    // 기타 메소드
}
```

### 3) RMI 레지스트리에 등록

```jsx
public class GumballMachineTestDrive {
    public static void main(String[] args) {
        GumballMachineRemote gumballMachine = null;
        int count;

        if(args.length < 2) {
            System.out.println("GumballMachine <name> <inventory>");
            System.exit(1);
        }

        try {
            count = Integer.parseInt(args[1]);

            gumballMachine = new GumballMachine(args[0], count);
            **Naming.rebind("//" + args[0] + "/gumballmachine", gumballMachine);**
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 4) 모니터링 기능 테스트

```jsx
public class GumballMonitorTestDrive {
    public static void main(String[] args) {
        String[] location = {
                "rmi://santafe.mightygumball.com/gumballmachine",
                "rmi://boulder.mightygumball.com/gumballmachine",
                "rmi://austin.mightygumball.com/gumballmachine"
        };

        GumballMonitor[] monitor = new GumballMonitor[location.length];

        for (int i = 0; i < location.length; i++) {
            try {
                **GumballMachineRemote machine = (GumballMachineRemote) Naming.lookup(location[i]);**
                monitor[i] = new GumballMonitor(machine);
                System.out.println(monitor[i]);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < monitor.length; i++) {
            monitor[i].report();
        }
    }
}
```

# 가상 프록시

## 예제

### 1) 프록시

```jsx
public class ImageProxy implements Icon {
    volatile ImageIcon imageIcon;
    final URL imageURL;
    Thread retrievalThread;
    boolean retrieving = false;

    public ImageProxy(URL url) { imageURL = url; }

    public int getIconWidth() {
        if (imageIcon != null) {
            return imageIcon.getIconWidth();
        } else {
            return 800;
        }
    }

    public int getIconHeight() {
        if (imageIcon != null) {
            return imageIcon.getIconHeight();
        } else {
            return 600;
        }
    }

    synchronized void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public void paintIcon(final Component c, Graphics  g, int x, int y) {
        if (imageIcon != null) {
            imageIcon.paintIcon(c, g, x, y);
        } else {
            g.drawString("앨범 커버를 불러오는 중입니다 잠시만 기다려 주세요.", x+300, y+190);
            if (!retrieving) {
                retrieving = true;

                retrievalThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            setImageIcon(new ImageIcon(imageURL, "Album Cover"));
                            c.repaint();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                
                retrievalThread.start();
            }
        }
    }
}
```

## 설명

- `ImageProxy` 는 `ImageIcon` 을 생성하고 네트워크 URL로부터 이미지를 불러옴
- 이미지를 가져오는 동안 특정 문구를 화면에 표시
- 이미지 로딩이 끝나면 모든 메소드 호출을 이미지 아이콘 객체에게 넘김
- 새로운 이미지 요청이 들어오면 프록시를 새로 만들고 위 과정 반복

# 보호 프록시

## ⭐ 동적 프록시
- 특정 인터페이스를 구현하고 지정한 클래스에 메소드 호출을 전달

## 예제

### 1) `InvocationHandler` 생성

```jsx
public class OwnerInvocationHandler **implements InvocationHandler** {
    Person person;

    public OwnerInvocationHandler(Person person) {
        this.person = person;
    }

    **public Object invoke(Object proxy, Method method, Object[] args)**
            throws IllegalAccessException {

        try { // 권한이 필요한 자원으로의 접근 제어
            if (method.getName().startsWith("get")) {
                return method.invoke(person, args);
            } else if (method.getName().equals("setGeekRating")) {
                throw new IllegalAccessException();
            } else if (method.getName().startsWith("set")) {
                return method.invoke(person, args);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

### 2) 동적 프록시 생성

```jsx
public class MatchMakingTestDrive {
    HashMap<String, Person> datingDB = new HashMap<String, Person>();

    public static void main(String[] args) {
        MatchMakingTestDrive test = new MatchMakingTestDrive();
        test.drive();
    }

    public MatchMakingTestDrive() {
        initializeDatabase();
    }

    public void drive() {
        Person kim = getPersonFromDatabase("김자바");
        **Person ownerProxy = getOwnerProxy(kim);**
        System.out.println("이름: " + ownerProxy.getName());
        ownerProxy.setInterests("볼링, 바둑");
        System.out.println("본인 프록시에 관심 사항을 등록합니다.");
        try {
            ownerProxy.setGeekRating(10);
        } catch (Exception e) {
            System.out.println("본인 프록시에는 괴짜 지수를 매길 수 없습니다.");
        }
        System.out.println("괴짜 지수: " + ownerProxy.getGeekRating());

        **Person nonOwnerProxy = getNonOwnerProxy(kim);**
        System.out.println("이름: " + nonOwnerProxy.getName());
        try {
            nonOwnerProxy.setInterests("볼링, 바둑");
        } catch (Exception e) {
            System.out.println("타인 프록시에는 관심 사항을 등록할 수 없습니다.");
        }
        nonOwnerProxy.setGeekRating(3);
        System.out.println("타인 프록시에 괴짜 지수를 매깁니다.");
        System.out.println("괴짜 지수: " + nonOwnerProxy.getGeekRating());
    }

    Person getOwnerProxy(Person person) {

        **return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person));**
    }

    Person getNonOwnerProxy(Person person) {

        **return (Person) Proxy.newProxyInstance( // 동적 프록시 생성
                person.getClass().getClassLoader(), // 프록시 클래스를 만들 클래스 로더
                person.getClass().getInterfaces(),  // 구현 필요 인터페이스
                new NonOwnerInvocationHandler(person)); // InvocationHandler 전달**
    }

    Person getPersonFromDatabase(String name) {
        return (Person)datingDB.get(name);
    }

    void initializeDatabase() {
        Person kim = new PersonImpl();
        kim.setName("김자바");
        kim.setInterests("자동차, 컴퓨터, 음악");
        kim.setGeekRating(7);
        datingDB.put(kim.getName(), kim);

        Person hong = new PersonImpl();
        hong.setName("홍길동");
        hong.setInterests("수집, 영화, 음악");
        hong.setGeekRating(6);
        datingDB.put(hong.getName(), hong);
    }
}
```

- `Proxy.newInstance`
    - `ClassLoader loader` : 프록시 클래스를 만들 클래스 로더
        - 일반적으로 Proxy 객체가 구현할 인터페이스의 클래스 로더로 전달
    - `Class<?>[] interfaces` : 프록시 클래스가 구현하고자 하는 인터페이스 목록
    - `InvocationHandler h` : 프록시의 메서드가 호출되었을 때 실행되는 메서드
