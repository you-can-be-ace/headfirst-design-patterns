# 프록시 패턴 Proxy Pattern
: **특정 객체로의 접근을 제어**하는 대리인(특정 객체를 대변하는 객체)을 제공함

<br><br>

## 방법1: 원격 프록시 remote proxy

<img width="722" alt="image" src="https://github.com/user-attachments/assets/1ef32509-9946-4f9d-80df-0dc1c6b9779c">

- 원격 객체로의 접근 제어
- 다른 JVM에 들어있는 객체의 대리인에 해당하는 로컬 객체
- 프록시의 메소드를 호출하면 그 호출이 네트워크로 전달되어 원격 객체의 메소드가 호출됨

<br><br>

## 방법2: 가상 프록시 virtual proxy

<img width="721" alt="image" src="https://github.com/user-attachments/assets/47419818-e4ad-4625-98b1-a98260a9ea68">


- 생성하는 데 많은 비용이 드는 자원으로의 접근 제어
- 진짜 객체가 필요한 상황이 오기 전까지 객체의 생성을 미룸
- 객체 생성 전이나 객체 생성 도중 객체를 대신하기도 함

<br><br>

## 방법3: 보호 프록시, 동적 프록시 protection proxy, dynamic proxy

- 접근 권한이 필요한 자원으로의 접근 제어
- 즉석에서 하나 이상의 인터페이스를 구현하고, 지정한 클래스에 메소드 호출을 전달하는 프록시 클래스를 생성함
    - 진짜 프록시 클래스는 실행 중에 생성됨
- 프록시 객체의 모든 메소드 호출을 전달받고 할 일을 지정해주는 `InvocationHandler`를 제공해야 함

<br><br>

## RMI

- 프로그래머 대신 클라이언트와 서비스 보조 객체를 만들어줌
- 네트워킹 및 입출력 관련 코드를 직접 작성하지 않아도 됨
- 클라이언트가 원격 객체를 찾아서 접근할 때 사용하는 `lookup` 서비스도 제공함

<img width="740" alt="image" src="https://github.com/user-attachments/assets/41627d9d-cff8-4294-ac17-7e37fcf236ce">


- 클라이언트 보조 객체는 스텁(stub), 서비스 보조 객체는 스켈레톤(skeleton)이라고 부름
- 스텁은 메소드 호출 정보(인자, 메소드 이름 등)를 잘 포장해 네트워크로 스켈레톤에게 전달함
- 스켈레톤은 스텁으로부터 받은 정보를 해석해 어떤 객체의 어떤 메소드를 호출할지 알아낸 다음 진짜 서비스 객체의 메소드를 호출함
- 실행 후 스켈레톤에 리턴된 정보를 잘 포장해서 네트워크로 스텁에게 전달함

<br><br>

## 원격 서비스 구현하기

### 1단계: 원격 인터페이스 생성

```java
import java.rmi.*;

public interface MyRemote extends Remote {
		public String sayHello() throws RemoteException;
}
```

- 마커 인터페이스인 `java.rmi.Remote`를 확장함
- 모든 원격 메소드 호출은 위험이 따르는 것으로 간주하고 모든 메소드에서 `RemoteException`을 선언함
- 원격 메소드의 인자와 리턴값은 반드시 직렬화로 포장 가능한 `primitive` 또는 `Serializable` 형식으로 선언함

<br>

### 2단계: 서비스 구현 클래스 생성

```java
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
		private static final long serialVersionUID = 1L;
	
		public MyRemoteImpl() throws RemoteException { }

		public String sayHello() {
				return "Server says, 'Hey'";
		}
		
		try {
				MyRemote service = new MyRemoteImpl();
				Naming.rebind("RemoteHello", service);
		} catch(Exception ex) { ... }
}
```

- 서비스 클래스에 원격 인터페이스를 구현함
- 원격 객체 기능을 추가하는 `UnicastRemoteObject`를 확장함
- 슈퍼 클래스와 같이 `RemoteException`을 선언하는 생성자를 구현함
- 서비스를 RMI 레지스트리에 등록함

<br>

### 3단계: RMI 레지스트리 실행

- 터미널을 새로 띄워 클래스에 접근할 수 있는 `classes` 디렉토리에서 `rmiregistry`를 실행함

<br>

### 4단계: 원격 서비스 실행

- 다른 터미널을 열고 서비스를 실행함

<br><br>

## 더 많은 프록시 패턴

- 방화벽 프록시(firewall proxy): 네트워크 자원으로의 접근을 제어함으로써 주제를 나쁜 클라이언트로부터 보호함
- 스마트 레퍼런스 프록시(smart reference proxy): 주제가 참조될 때마다 추가 행동을 제공함
- 캐싱 프록시(caching proxy): 비용이 많이 드는 작업의 결과를 임시로 저장하고, 여러 클라이언트에서 여러 결과를 공유하게 해줌
- 동기화 프록시(synchronization proxy): 여러 스레드에서 주제에 접근할 때 안전하게 작업을 처리할 수 있게 해줌
- 복잡도 숨김 프록시(complexity hiding proxy): 복잡한 클래스의 집합으로의 접근을 제어하고 그 접근을 숨겨줌 → 퍼사드 프록시(facade proxy)라고도 함
- 지연 복사 프록시(copy-on-write proxy): 클라이언트에서 필요로 할 때까지 객체가 복사되는 것을 지연시킴으로서 객체의 복사를 제어함

<br><br>

## 핵심 정리

- 프록시 패턴을 사용하면 어떤 객체의 대리인을 내세워서 클라이언트의 접근을 제어할 수 있음
- 원격 프록시는 클라이언트와 원격 객체 사이의 데이터 전달을 관리해줌
- 가상 프록시는 인스턴스를 만드는 데 많은 비용이 드는 객체로의 접근을 제어함
- 보호 프록시는 호출하는 쪽의 권한에 따라 객체에 있는 메소드로의 접근을 제어함
- 프록시 패턴의 구조는 데코레이터 패턴의 구조와 비슷하지만 용도는 다름
    - 데코레이터 패턴은 객체에 행동을 추가하지만 프록시 패턴은 접근을 제어함
- 자바에 내장된 프록시 지원 기능을 사용하면 동적 프록시 클래스를 만들어 원하는 핸들러에서 호출을 처리하도록 할 수 있음
- 다른 래퍼를 쓸 때와 마찬가지로 프록시를 쓰면 디자인에 포함되는 클래스와 객체의 수가 늘어남
