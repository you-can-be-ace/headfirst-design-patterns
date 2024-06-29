# 싱글턴 패턴 Singleton Pattern

- 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근을 제공함

<br>

## 특징

- 다른 어떤 클래스에서도 인스턴스를 추가로 만들지 못하게 해야 함
- 인스턴스가 필요하다면 반드시 클래스 자신을 거치도록 해야 함
- 전역 변수처럼 어디서든 인스턴스에 액세스할 수 있게 해주지만, 애플리케이션이 시작될 때 객체가 생성되는 전역 변수와 달리 필요할 때만 인스턴스 생성 가능
    - 게으른 인스턴스 생성(lazyinstantiation): 자원을 많이 잡아먹는 인스턴스에 유용
- 연결 풀이나 스레드 풀과 같은 자원풀을 관리할 때 사용

<br><br>

## 고전적인 싱글턴 패턴

```java
public class Singleton {
		private static Singleton uniqueInstance;
		
		// 기타 인스턴스 변수
		
		private Singleton() {}
		
		public static Singleton getInstance() {
				if(uniqueInstance == null) {
						uniqueInstance = new Singleton();
				}
				return uniqueInstance;
		}
		
		// 기타 메소드
}
```

- `Singleton` 클래스의 하나뿐인 인스턴스를 저장하는 정적 변수
- `Singleton`에서만 클래스 인스턴스 생성할 수 있도록 선언한 `private` 생성자
- 클래스 인스턴스를 만들어서 리턴하는 `getInstance()` 메소드

<br><br>

## 멀티스레딩 문제 해결

### 방법1: `synchronized` 키워드 사용

```java
public static synchronized Singleton getInstance() {
		if(uniqueInstance == null) {
				uniqueInstance = new Singleton();
		}
		return uniqueInstance;
}
```

- 한 메소드가 메소드 사용을 끝내기 전까지 다른 스레드는 기다려야 함
- 동기화가 꼭 필요한 메소드가 시작되는 부분을 제외하면 동기화는 불필요한 오버헤드만 증가시킴
- 성능이 100배 정도 저하하기 때문에 속도가 그리 중요하지 않은 경우 사용
- `getInstance()`가 애플리케이션에서 병목으로 작용한다면 다른 방법 사용

<br>

### 방법2: 정적 초기화 부분에서 인스턴스 생성

```java
public class Singleton {
		private static Singleton uniqueInstance = new Singleton();

		private Singleton() {}
		
		public static Singleton getInstance() {
				return uniqueInstance;
		}
}
```

- 클래스가 로딩될 때 JVM에서 하나뿐인 인스턴스를 생성해줌
    - 인스턴스 생성 전까지 그 어떤 스레드도 정적 변수에 접근 불가능
- 인스턴스를 계속 사용하거나 인스턴스를 실행 중 수시로 만들고 관리하기 번거로운 경우 사용
- 인스턴스가 자원을 많이 잡아먹지 않는 경우 사용

<br>

### 방법3: DCL 사용(`volatile` 키워드)

```java
public class Singleton {
		private volatile static Singleton uniqueInstance;
		
		private Singleton() {}
		
		public static Singleton getInstance() {
				if(uniqueInstance == null) {
						synchronized (Singleton.class) {
								if(uniqueInstance == null) {
										uniqueInstance = new Singleton();
								}
						}
				}
				return uniqueInstance;
		}
		
		// 기타 메소드
}
```

- DCL(Double-Checked Locking)을 사용하여 인스턴스가 생성되어 있는지 확인 후 생성되어 있지 않은 경우에만 동기화
    - 처음에만 동기화하고 나중에는 동기화하지 않아도 됨
- 블록에서도 다시 한 번 변수가 `null`인지 확인한 후 인스턴스 생성
- 자바 5 이상 버전에서만 가능

🔗 이펙티브 아이템 78: [https://www.notion.so/78-937becfae3b3419b8d55f98977870a9e?pvs=4#7450e119cb21403a98a626fb2f9c3058](https://www.notion.so/78-937becfae3b3419b8d55f98977870a9e?pvs=21)

🔗 이펙티브 아이템 83: [https://www.notion.so/83-865b2362ffe5463889cb7bfa48507c96?pvs=4#a760948d45d34bbb9cd9867eece10673](https://www.notion.so/83-865b2362ffe5463889cb7bfa48507c96?pvs=21)

<br><br>

## Q&A 정리

- 모든 메소드와 변수가 `static`으로 선언된 클래스와 다른 점
    - 필요한 내용이 클래스에 다 들어있고, 복잡한 초기화 필요 없는 경우 사용 가능
    - 정적 초기화를 처리하는 방법 때문에 복잡해질 수 있음
    - 여러 클래스가 얽혀 있다면 더 복잡해짐
    - 초기화 순서 문제로 찾아내기 어려운 버그 발생 가능성 증가
- 클래스 로더 2개가 각기 다른 싱글톤 인스턴스를 가질 수 있다
    - 클래스 로더마다 서로 다른 네임스페이스를 정의하기 때문에 클래스 로더가 2개 이상이라면 같은 클래스를 여러 번 로딩할 수 있음
    - 클래스 로더를 직접 지정하면 방지 가능
- 리플렉션, 직렬화, 역직렬화를 사용하는 경우 문제 발생
- 느슨한 결합 원칙, 한 클래스가 한 가지만 책임지기 원칙에 위배됨

<br><br>

## 싱글턴 문제 해결하는 가장 좋은 방법: `Enum`

```java
public enum Singleton {
		UNIQUE_INSTANCE;
		// 기타 필요한 필드
}

public class SingletonClient {
		public static void main(String[] args) {
				Singleton singleton = Singleton.UNIQUE_INSTANCE;
		}
}
```

- 동기화, 클래스 로딩, 리플렉션, 직렬화, 역직렬화 문제 등은 `enum`을 사용하면 해결됨
