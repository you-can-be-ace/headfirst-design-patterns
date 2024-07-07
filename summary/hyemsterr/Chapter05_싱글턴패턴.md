# 싱글턴 패턴이란?

- 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근을 제공하는 패턴을 싱글턴이라고 한다.
- 전역 변수를 사용할 때처럼 객체 인스턴스를 어디서든 액세스할 수 있다.
- 전역 변수와는 다르게 싱글턴 패턴을 사용하면 필요할 때만 객체를 만들 수 있다.

# 싱글턴 패턴 구현 방법과 동작 원리

```java
public class Singleton {
	private static Singleton uniqueInstance;
	
	private Singleton() {}
	
	public static Singleton getInstance() {
		if(uniqueInstunce == null) uniqueInstance = new Singleton();
		return uniqueInstance;
	}
}
```

- **public 생성자가 없고, private 생성자만 존재한다.**
    - 해당 클래스 내부에서만 객체를 생성할 수 있다.
- **외부에서 인스턴스를 요청할 땐 getInstance() 정적 메소드를 통해서만 요청할 수 있다.**
    - 싱글턴 클래스의 하나뿐인 인스턴스를 저장하는 정적 변수에 담긴 인스턴스를 리턴한다.
    - 해당 변수가 null이라면(객체 생성 전이라면) 객체를 생성하여 정적 변수에 저장한다.

→ **싱글턴에서 인스턴스가 하나뿐이라는 것을 보장한다.**

# 싱글턴에서의 멀티스레딩 문제 해결하기

> 2개 이상의 스레드에서 getInstance() 메소드를 호출하여 2개 이상의 인스턴스가 생성될 수 있다.
> 

### 메소드를 동기화한다.

```java
public static **synchronized** Singleton getInstance() {}
```

- 메소드에 `synchronized` 키워드를 사용해서 동기화한다.
    - 한 스레드가 메소드 사용을 끝내기 전까지 다른 스레드에서 메소드를 사용할 수 없다.
- 속도 이슈가 발생할 수 있다.

### 인스턴스를 요청할 때 생성하지 않고 처음부터 생성해둔다.

```java
private static Singleton uniqueInstance = **new Singleton();**

public static Singleton getInstance() { return uniqueInstance; }
```

- static initializer에서 Singleton 인스턴스를 생성한다.
- 여러 스레드에서 동시에 접근하더라도 JVM에서 생성한 하나의 인스턴스에만 접근할 수 있다.
- 전역변수와 마찬가지로 해당 인스턴스를 한 번도 사용하지 않더라도 인스턴스가 존재하게 된다.

### DCL(Double-Checked Locking)을 사용한다.

```java
private **volatile** static Singleton uniqueInstance;

public static Singleton getInstance() {
	if(uniqueInstance == null) {
		synchronized (Singleton.class) {
			if(uniqueInstance == null) { uniqueInstance = new Singleton(); }
		}
	}
}
```

- 인스턴스가 생성되어 있지 않았을 때만 `synchronized` 키워드를 사용하여 동기화한다.
- `volatile` 키워드를 사용하면 멀티스레딩 환경에서도 uniqueInstance 변수가 싱글턴으로 초기화되는 것을 보장한다.

# 싱글턴과 Enum

- Enum은 private 생성자로 인스턴스 생성을 제어하며, 상수만 갖는 특별한 클래스이기 때문에 싱글톤의 성질을 갖는다.
- Enum은 동기화, 클래스 로딩, 리플렉션, 직렬화/역직렬화 문제들을 알아서 해결한다.

→ 싱글턴이 필요할 때 enum을 쓰면 된다.
