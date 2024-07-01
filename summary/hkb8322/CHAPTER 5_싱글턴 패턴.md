# 싱글턴 패턴의 정의

- 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근 제공
- `private` 생성자, 정적 메소드, 정적 변수를 사용하여 구현

# 싱글턴 🆚 전역 변수

## 공통점

- 전역적인 접근을 제공할 때 사용

## 차이점

- 싱글턴은 하나의 인스턴스만 가지도록 함
- 전역 변수는 객체의 정적 레퍼런스
    - 인스턴스 지연 생성 불가
    - 처음부터 끝까지 인스턴스를 가지고 있어야 함
    - 하나의 인스턴스만 가질 수 있게 할 수 없음

# 고전적인 싱글턴 패턴 구현법

```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Singleton {

    private static Singleton uniqueInstance;

    // 멀티스레딩 시 문제 발생
    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton(); // 지연(게으른) 생성
        }

        return uniqueInstance;
    }
}
```

- 애플리케이션에 들어있는 어떤 객체에서도 같은 자원을 활용할 수 있음
- `private` 생성자를 사용하여 외부에서 동일 객체를 생성하지 못하도록 함
- 싱글턴 객체 필요 시에는 `getInstance()`라는 메서드를 호출하여 받아야 함

# 멀티스레딩 문제 해결

## 1) `getInstance()` 시그니처 내 `synchronized` 사용

```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSafeSingleton1 {

    private static ThreadSafeSingleton1 uniqueInstance;

    public static **synchronized** ThreadSafeSingleton1 getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ThreadSafeSingleton1();
        }

        return uniqueInstance;
    }
}

```

- 동기화가 필요한 것은 메소드가 시작할 때뿐임
- `uniqueInstance` 변수에 `Singleton` 인스턴스를 대입하면 메소드를 동기화한 상태로 유지할 필요가 없음
    - 처음을 제외하고 불필요한 오버헤드만 증가시킴
    - **메소드 동기화 시 성능이 100배 정도 저하됨**
- 하지만 성능에 크게 부담을 주지 않는다면 그냥 두어도 됨

## 2) 변수 선언과 함께 초기화

```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSafeSingleton2 {

    private static ThreadSafeSingleton2 uniqueInstance = new ThreadSafeSingleton2();

    public static synchronized ThreadSafeSingleton2 getInstance() {
        return uniqueInstance;
    }
}

```

- 정적 초기화 구문에서 인스턴스를 생성하므로 thread-safe함
- JVM에서 하나뿐인 인스턴스를 생성하고, 생성하기 전까지 어떤 스레드도 정적 변수에 접근 불가

## 3) DCL(Double Check Locking) 사용

```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSafeSingleton3 {

    private **volatile** static ThreadSafeSingleton3 uniqueInstance;

    public static ThreadSafeSingleton3 getInstance() {
        if (uniqueInstance == null) { // 인스턴스 존재 여부 확인
            **synchronized (Singleton.class) { // 처음에만 동기화되도록 유도**
                if (uniqueInstance == null) {
                    uniqueInstance = new ThreadSafeSingleton3();
                }
            **}**
        }

        return uniqueInstance;
    }
}
```

- 자바 1.4 이전의 경우 해당 방식으로 싱글턴 구현 불가

<aside>
💡 `volatile`은 **멀티 스레드 프로그래밍에 사용되는 키워드**입니다. 스레드는 실행되고 있는 CPU 메모리 영역에 데이터를 캐싱합니다. 따라서 멀티 코어 프로세서에서 다수의 스레드가 변수 `a`를 공유하더라도 캐싱된(갱신) 시점에 따라 데이터가 다를 수도 있습니다.

이런 경우 `volatile` 키워드를 사용하여 **CPU메모리 영역에 캐싱된 값이 아니라 항상 최신의 값을 가지도록 메인 메모리 영역에서 값을 참조하도록 할 수 있습니다.** 즉, 동일 시점에 모든 스레드가 동일한 값을 가지도록 동기화합니다. 이런 동기화를 통신-동기화 또는 메모리-동기화라고 합니다.

</aside>

# ⚠️ 주의 사항

- 클래스 로더가 2개 이상인 경우 인스턴스가 여러 개 만들어지는 문제가 발생할 수 있음
    - 클래스 로더마다 서로 다른 네임스페이스를 정의하기 때문
- 리플렉션, 직렬화, 역직렬화도 고려해야 함
- 느슨한 결합 원칙에 위배되기 쉬움
- 싱글턴 클래스는 SRP를 준수하지 못함
    - 자신의 인스턴스를 관리하는 책임
    - 인스턴스를 사용하고자 하는 목적에 부합하는 작업에 대한 책임
- 상속에 유의 필요
    - 싱글턴을 확장해서 무엇을 할 것인지 정의 필요
- 애플리케이션 내 싱글톤이 많이 사용된 경우 전반적인 디자인 개선을 고려해야 함

# `enum` 을 활용한 싱글톤

```java
public enum Singleton {

    UNIQUE_INSTANCE;

    public String getDescription() {
        return "Thread-Safe Singleton";
    }
}

public class SingletonClient {

	public static void main(String[] args) {
		Singleton singleton = Singleton.UNIQUE_INSTANCE;
		System.out.println(singleton.getDescription());
	}
}

```

- 동기화, 클래스 로딩, 리플렉션, 직렬화/역직렬화 문제 등은 열거형을 통하여 해결 가능

# 참고 자료

[[Java] 클래스 로더란?](https://velog.io/@ddangle/Java-클래스-로더란)
