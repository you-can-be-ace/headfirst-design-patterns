# CH11 프록시 패턴

## 프록시 패턴(Proxy Pattern)이란? 
- 특정 객체로의 접근을 제어하는 대리인(특정 객체를 대변하는 객체)를 제공
- 프록시 패턴을 사용하면 원격 객체라든가 생성하기 힘든 객체, 보안이 중요한 객체와 같은 다른 객체로의 접근을 제어하는 대리인 객체를 만들 수 있다.

## 프록시 패턴 다이어그램 

<img width="379" alt="image" src="https://github.com/user-attachments/assets/0c584ad5-4a98-4dc5-a9da-409bad96c7a7">

- Subject : Proxy와 RealSubject 모두 Subject 인터페이스 구현, 이러면 어떤 클라이언트에서든 프록시를 주제와 똑같은 식으로 다룰 수 있다. 
- RealSubject : 진짜 작업을 대부분 처리하는 객체
- Proxy : 진짜 작업을 처리하는 객체의 **레퍼런스**가 들어있다. 진짜 객체가 필요하면 그 레퍼런스를 사용해서 요청을 전달 

<img width="442" alt="image" src="https://github.com/user-attachments/assets/1332813e-452a-47e7-9b0d-94fa8b860e45">

- Client: 클라이언트는 프록시 객체에 요청을 보낸다.
- Proxy: 프록시는 요청을 받아서 실제 객체에 전달, 이 과정에서 접근 제어, 로깅, 캐싱 등의 부가 작업 수행 가능 
- Real Subject: 실제 객체는 프록시로부터 전달받은 요청을 처리하고, 결과를 프록시를 통해 클라이언트에 반환

## 프록시에서 접근을 제어하는 방법 | 1. 원격 프록시 : 원격 객체로의 접근 제어 가능
- 교재에서는 모니터링을 통해 뽑기 기계의 재고 확인 가능하게 함

### 원격프록시 - GumballMachine의 원격 인터페이스 만들기
- 원격 클라이언트에서 호출할 수 있는 메소드 정의하기
```
import java.rmi.*;

public interface GumballMachineRemote extends Remote {
    int getCount() throws RemoteException;
    String getLocation() throws RemoteException;
    String getState() throws RemoteException;
}
```
- 모든 리턴 형식은 원시 형식 또는 Serializable이어야 한다.
- 지원해야 하는 메소드 모두 RemoteException을 던질 수 있다. 

### 원격프록시 - 인터페이스의 모든 리턴 형식을 직렬화할 수 있는지 확인
```
import java.io.Serializable;

public interface State extends Serializable {
    public void insertQuarter();
    public void ejectQuarter();
    public void turnCrank();
    public void dispense();
}
```
- (아무 메소드도 없는) Serializable 인터페이스를 확장한다. 이러면 State의 서브 클래스를 직렬화해서 네트워크로 전송 가능 

### 원격프록시 - 구상 클래스에서 인터페이스 구현
```
import java.rmi.*;
import java.rmi.server.*; 

public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
    private int count;
    private String location;
    private String state;

    public GumballMachine(int count, String location) throws RemoteException {
        this.count = count;
        this.location = location;
        this.state = "NoQuarterState"; // Example initial state
    }

    @Override
    public int getCount() {
        return count;
    }

    // getLocation, getState 오버라이드 함수 구현 
}
```
- rmi 패키지 임포트 
- 슈퍼 클래스에서 RemoteException를 던질 수도 있으므로 이 생성자에도 RemoteException를 던질 수 있어야 한다. 
### 원격프록시 - RMI 레지스트리 등록 
- `Naming.rebind("//" + args[0] + "/gumballmachine", gumballmachine);`

### 원격프록시 - GumballMonitor 클라이언트 코드 고치기 
```
import java.rmi.Naming;

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
                GumballMachineRemote machine = (GumballMachineRemote) Naming.lookup(location[i]);
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
- 메소드를 네트워크로 호출하므로 RemoteException이 던져질 때 잡아낼 수 있어야 하므로 try~catch절 사용 

### 프록시에서 접근을 제어하는 방법 | 2. 가상 프록시(virtual proxy) : 생성하기 힘든 자원으로의 접근 제어 가능, 메모리 비용이 큰 객체의 생성을 지연시킴
- 교재에서는 앨범 커버 가상 프록시를 통해서 스레드를 사용하여 아직 외부 사진을 읽어오기 전이라면 기본이미지를 표시하다가 모두 읽은 경우 이미지 표시되도록 함
```
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageProxy implements Icon {
    //....

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
- 위 코드에서는 imageIcon에 메소드 호출을 위임해서 아이콘을 화면에 표시한다.
- 하지만 ImageIcon이 아직 완전히 생성되지 않았으면 이미지를 불러오는 중이라는 메시지를 그림 형태로 표시한다. 

### 프록시에서 접근을 제어하는 방법 | 3. 보호 프록시(protection proxy): 접근 권한이 필요한 자원으로의 접근 제어 가능
- 교재에서는 데이팅 서비스에서 본인만 본인의 개인정보를 수정하고 본인은 본인의 괴짜 지수를 고칠 수 없도록 제어 가능 
- 본인의 괴짜 지수를 고칠 수 없도록 하게 하는 invoke 함수 코드
```
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OwnerInvocationHandler implements InvocationHandler {
    private final Object target;

    public OwnerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (method.getName().startsWith("get")) {
                return method.invoke(target, args);
            } else if (method.getName().startsWith("setGeekRating")) {
                throw new IllegalAccessException();
            } else if (method.getName().startsWith("set")) {
                return method.invoke(target, args);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected invocation exception: " + e.getMessage());
        }
    }
}
```
  - 위 invoke 메소드에서 두번째 조건절을 보면 본인 객체가 setGeekRating 함수를 실행하면 예외처리됨을 알 수 있다.
  - 그 외 get, set은 가능 

## 프록시 패턴 VS 데코레이턴 패턴 
- 프록시 패턴의 구조는 테코레이터 패턴의 구조와 비슷하지만 그 용도는 다르다.
- 데코레이터 패턴은 객체가 할 수 있는 행동을 추가한다.
- 프록시 패턴은 접근을 제어한다.
  - 접근시에 필요한 행동을 추가하는 것 

## 프록시 종류 이모저모
- 방화벽 프록시(Firewall Proxy) : 일력의 네트워크 자원으로의 접근을 제어함으로써 주제를 '나쁜' 클라이언트로부터 보호해 준다.
- 스마트 레퍼런스 프록시(Smart Reference Proxy) : 주제가 참조될 때마다 추가 행동을 제공한다. 객체의 레퍼런스 개수를 센다든가 하는 식
- 캐싱 프록시(Caching Proxy) : 비용이 많이 드는 작업의 결과를 임시로 저장, 여러 클라이언트에서 결과를 공유하게 해 줌으로써 계산 시간과 네트워크 지연을 줄여 주는 효과가 있다.
- 동기화 프록시(Synchronization Proxy) : 여러 스레드에서 주제에 접근할 때 안전하게 작업을 처리할 수 있게 해 준다.
- 복잡도 숨김 프록시(Complexity Hiding Proxy) : 복잡한 클래스의 집합으로의 접근을 제어하고, 그 복잡도를 숨겨 준다.
- 지연 복사 프록시(Copy-On-Write Proxy) : 클라이언트에서 필요로 할 때까지 객체가 복사되는 것을 지연시킴으로써 객체의 복사를 제어 
