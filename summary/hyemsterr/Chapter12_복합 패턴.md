# 복합 패턴이란?

- 여러 패턴을 함께 사용해서 다양한 디자인 문제를 해결하는 방법
- 패턴으로 이루어진 패턴인 셈

# 오리 시뮬레이션 게임에 복합 패턴 적용하기

```java
public interface Quackable {
	public void quack();
}

public class MallardDuck implements Quackable {
	public void quack() {
		System.out.println("꽥꽥");
	}
}
```

- `Quackable` 인터페이스를 구현해서 오리 클래스를 만든다.

### 나 거위인데 나도 Quackable이 되고 싶어! 어댑터 패턴

- *특정 클래스 인터페이스를 클라이언트에서 요구하는 다른 인터페이스로 변환하는 패턴*
    
    ```java
    public class GooseAdapter implements Quackable {
    	Goose goose;
    	
    	public void quack() {
    		goose.honk();
    	}
    }
    ```
    
    - `Goose` 클래스를 감싸는 `GooseAdapter` 클래스를 생성하여 `Quackable` 인터페이스를 구현한다.
    - 오리의 탈을 쓴 거위(어댑터)가 만들어졌다.

### 나 꽥꽥학자인데 꽥꽥 소리가 난 횟수를 세고 싶어! 데코레이터 패턴

- *객체에 추가 요소를 동적으로 더하는 패턴*
    
    ```java
    public class QuackCounter implements Quackable {
    	Quackable duck;
    	static int numberOfQuacks;
    	
    	public void quack() {
    		duck.quack();
    		numberOfQuacks++;
    	}
    	
    	public static int getQuacks() {
    		return numberOfQuacks;
    	}
    }
    ```
    
    - `Quackable` 인터페이스를 구현하여 추가 동작을 지정하는 `QuackCounter` 데코레이터 객체를 생성한다.
    - `quack()` 메소드가 호출되면 그 호출을 데코레이터 안에 들어있는 `Quackable` 객체에 위임한다.

### 나 꽥꽥학자인데 전부 QuackCounter 맞아?🧐 추상 팩토리 패턴

- *구상 클래스에 의존하지 않고도 서로 연관되거나 의존적인 객체로 이루어진 제품군을 생산하는 인터페이스를 제공하는 패턴*
    
    ```java
    public class CountingDuckFactory extends AbstractDuckFactory {
    	public Quackable createMallardDuck() {
    		return new QuackCounter(new MallardDuck());
    	}
    	
    	public Quackable createRedheadDuck() {
    		return new QuackCounter(new RedheadDuck());
    	}
    }
    ```
    
    - 오리 객체를 생성하는 작업을 팩토리 한 군데에서 전담하게 되었다.
    - 시뮬레이터는 그저 `Quackable` 타입의 객체가 생성된다는 것만 알고 있으면 된다.

### 오리, 거위 등등.. Quackable 관리포인트가 너무 많아.. 컴포지트 패턴

- *객체를 트리 구조로 구성해서 부분-전체 계층 구조를 구현하는 패턴*
    
    ```java
    public class Flock implements Quackable {
    	List<Quackable> quackers = new ArrayList<Quackable>();
    	
    	public void add(Quackable quacker) {
    		quackers.add(quacker);
    	}
    }
    ```
    
    - `Quackable`이 leaf 원소가 되고, Flock에 속하는 객체들은 ArrayList에 저장한다.

### 같은 무리가 되었으니 반복자 패턴

- *컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법을 제공하는 패턴*
    
    ```java
    public class Flock implements Quackable {
    	public void quack() {
    		Iterator<Quackable> iterator = quackers.iterator();
    		while(iterator.hasNext()) {
    			Quackable quacker = iterator.next();
    			quacker.quack();
    		}
    	}
    }
    ```
    
    - 여러 종류의 오리 객체들을 하나의 무리로 관리할 수 있게 되었으니 반복자 패턴을 사용할 수 있다.

### 나 꽥꽥학자인데 꽥꽥 소리가 나면 바로 알려줘! 옵저버 패턴

- *한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체에게 연락이 가고 자동으로 내용이 갱신되는 패턴*
    
    ```java
    public interface Quackable extends QuackObservable {
    	public void quack();
    }
    
    public class Observable implements QuackObservable {
    	List<Observer> observers = new ArrayList<Observer>();
    	QuackObservable duck;
    	
    	public void registerObserver(Observer observer) {
    		observers.add(observer);
    	}
    	
    	public void notifyObservers() {
    		Iterator iterator = observers.iterator();
    		while(iterator.hasNext()) {
    			Observer observer = iterator.next();
    			observer.update(duck);
    		}
    	}
    }
    
    public class Mallarduck implements Quackable {
    	Observable observable;
    	
    	public void quack() {
    		notifyObservers();
    	}
    	
    	public void registerObserver(Observer observer) {
    		observable.registerObserver(observer);
    	}
    	
    	public void notifyObservers() {
    		observable.notifyObservers();
    	}
    }
    ```
    
    - `Quackable` 인터페이스
        - 다른 객체에서 관측하도록 하기 위한 `QuackObservable` 인터페이스를 확장한다.
    - `Observable` 클래스
        - 옵저버 등록 코드와 옵저버들에게 연락을 돌리는 코드를 구현한다.
    - `Mallarduck` 클래스
        - 클래스 안에 `Observable` 인스턴스를 추가하고, 변경이 일어날 때 옵저버들에게 알린다.
        - `QuackObservable`에 정의된 옵저버 등록/노티 메소드를 재정의하여 `Observable` 객체에 동작을 위임한다.

# MVC 알아보기

### Model

- 모든 데이터, 상태, 어플리케이션 로직이 들어있는 곳
- 뷰와 컨트롤러에서 모델의 상태를 조작하거나 가져올 대 필요한 인터페이스 제공

### View

- 모델을 표현하는 방법 제공
- 화면에 표시할 때 필요한 상태와 데이터는 모델에서 직접 가져옴

### Controller

- 사용자로부터 입력을 받아 모델에게 어떤 의미가 있는지 파악

### 🤝 MVC의 관계

- 사용자는 뷰에만 접근할 수 있다.
- 컨트롤러가 모델에게 상태 변경을 요청하거나, 뷰를 변경해달라고 요청한다.
- 모델의 상태가 변경되면 모델이 뷰에게 그 사실을 알린다.
- 뷰는 모델에게 상태를 요청한다.

### 🧐 컨트롤러는 중계자 그 이상 그 이하도 아니다?!

- 컨트롤러는 사용자가 입력한 내용을 해석해서 모델을 조작한다.
    - 왜 뷰에서 그런 기능을 바로 제공하지 않을까?
        - 뷰에서 2가지 역할을 하게 되면 뷰 코드가 복잡해진다. 🥵
        - 뷰와 모델이 강하게 결합된다. 🥵
- 컨트롤러는 제어 로직을 분리해서 뷰와 모델의 결합을 끊고 느슨한 결합을 유지시켜준다.

# MVC에 사용되는 복합 패턴

### 옵저버 패턴

- 모델은 옵저버 패턴을 써서 상태가 변경되었을 때 그 모델과 연관된 객체들에게 연락한다.
- 옵저버 패턴을 사용하면 모델을 뷰와 컨트롤러부터 완전히 독립시킬 수 있다. (느슨한 결합)

### 전략 패턴

- 뷰와 컨트롤러는 전략 패턴으로 구현되어 있다.
- 뷰는 사용자의 행동을 처리하는 작업을 컨트롤러에 맡긴다.
- 컨트롤러는 뷰의 전략 객체에 해당한다.

### 컴포지트 패턴

- 디스플레이는 여러 단계로 겹쳐 있는 윈도우, 패널, 버튼 등으로 구성된다.
- 각 디스플레이 항목은 복합 객체나 잎이 될 수 있다.
