# CH05-싱글턴 패턴

### 싱글턴 패턴
- 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근을 제공한다.

### 싱글턴 패턴 만들기 방법1. getInstance() 동기화 
```
public class Singleton {
  private static Singleton uniqueInstance;

  private Singletone() {}

  public static synchronized Singleton getInstance() {
    return uniqueInstance;
  }
}
```
- synchronized 키워드를 추가하면 한 스레드가 getInstance() 메소드 사용을 끝내기 전까지 다른 스레드는 기다려야 한다.
  - 즉, 2개의 스레드가 getInstance() 메소드를 동시에 실행하는 일은 일어나지 않는다.
- 항상 올바른 결과가 나온다.
- 속도 문제가 그리 중요하지 않다면 사용해도 좋다.
  
### 싱글턴 패턴 만들기 방법2. 인스턴스를 시작하자마자 만들기 
```
public class Singleton {
  private static Singleton uniqueInstance = new Singleton();

  private Singletone() {}

  public static synchronized Singleton getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new Singleton();
    }
    return uniqueInstance;
  }
}
```
- 클래스 로딩시 JVM에서 Singleton 인스턴스 생성됨, 그 전까지는 어떤 스레드도 uniqueInstance 정적 변수에 접근 불가능 
- 인스턴스가 항상 필요한 경우라면 시작하자마자 초기화하는 것도 괜찮은 방법이다.
- 표준적인 패턴에 익숙한 개발자들에게는 좀 이상하게 보일 수도 있다.

### 싱글턴 패턴 만들기 방법3. DCL 사용하기 
```
public class Singleton {
  private volatile static Singleton uniqueInstance;

  private Singletone() {}

  public static Singleton getInstance() {
    if (uniqueInstance == null) {
      synchronized (Singleton.class) {
        if (uniqueInstance == null) {
          uniqueInstance = new Singleton();
        }
      }
    }
    return uniqueInstance;
  }
}
```
- 인스턴스가 있는지 확인 후 없다면 동기화 처리되는 블록으로 들어가기 때문에 처음에만 동기화 된다.
- volatile 키워드를 사용하면 멀티스레딩을 쓰더라도 uniqueInstance 변수가 Singleton 인스턴스로 초기화되는 과정이 올바르게 진행된다. 
- 속도 문제가 중요한 상황이라면 사용하면 좋다.
- 반드시 자바 5 이상 버전에서만 쓸 수 있다는 점도 고려하자.

### 싱글턴 패턴 만들기 방법4. enum을 사용하자 ⭐
```
public enum Singleton {
  UNIQUE_INSTANCE;
  //기타 필요한 필드
}

public class SingletonClient {
  public static void main(String[] args) {
    Singleton singleton = Singleton.UNIQUE_INSTANCE;
    //여기서 싱글턴 사용
  }
}
```
- enum을 사용하면 위에서 고려했던 사항들 모두 고려하지 않아도 된다.


### 핵심 정리
- 어떤 클래스에 싱글턴 패턴을 적용하면 그 클래스의 인스턴스가 1개만 있도록 할 수 있다.
- 싱글턴 패턴을 사용하면 하나뿐인 인스턴스를 어디서든지 접근할 수 있도록 할 수 있다.
- 자바에서 싱글턴 패턴을 구현할 때는 private 생성자와 정적 메소드, 정적 변수를 사용한다.
- 멀티 스레드를 사용하는 애플리케이션에서는 속도와 자원 문제를 파악해 보고 적절한 구현법을 사용해야 한다.(사실 모든 애플리케이션에서 멀티스레딩을 쓸 수 있다고 생각해야 한다.)
- DCL을 써서 구현하면 자바 5 이전에 나온 버전에서는 스레드 관련 문제가 생길 수 있다. 주의하자.
- 클래스 로더가 여러 개 있으면 싱글턴이 제대로 작동하지 않고, 여러 개의 인스턴스가 생길 수 있다.
- 자바의 enum을 쓰면 간단하게 싱글턴을 구현할 수 있다. 
