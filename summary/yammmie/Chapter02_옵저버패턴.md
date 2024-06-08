# 옵저버 패턴 Observer Pattern
- 한 객체가 상태가 바뀌면 그 객체에 의존하는 다른 객체에게 연락이 가고 자동으로 내용이 갱신되는 방식
- 일대다(one-to-many) 의존성을 정의함

<br><br>

## 작동 원리

<img width="773" alt="image" src="https://github.com/you-can-be-ace/headfirst-design-patterns/assets/141018558/4afbd17d-96ee-4269-8a40-a35d9ace94fc">

- 주제 객체는 주제에서 중요한 데이터 관리
- 옵저버 객체는 주제를 구독하며 데이터가 바뀌면 새로운 데이터 값이 어떤 방법으로든 옵저버에게 전달됨
    - 옵저버가 아닌 객체는 아무 연락 받지 못함
- 옵저버가 아닌 객체는 주제 객체에게 옵저버 등록을 요청하고, 더 이상 주제 객체로부터 데이터를 받고 싶지 않은 경우 탈퇴를 요청함

<br><br>

## 구조

<img width="776" alt="image" src="https://github.com/you-can-be-ace/headfirst-design-patterns/assets/141018558/48d7d232-c366-4b5b-8dfb-b2ea16e35aa7">

<br>

### Subject 인터페이스

- 주제를 나타내는 인터페이스
- 객체에서 옵저버로 등록/탈퇴, 옵저버에게 변경 내용 전달하는 메소드

<br>

### Observer 인터페이스

- 각 주제마다 여러 개의 옵저버 존재 가능
- 옵저버가 될 가능성이 있는 객체가 반드시 구현해야 하는 인터페이스
- 주제의 상태가 바뀌었을 때 호출되는 메소드

<br>

### ConcreteSubject 클래스

- 주제 역할을 하는 구상 클래스 → 항상 `Subject` 인터페이스 구현
- `Subject` 인터페이스에 정의된 메소드 전부 구현해야 함

<br>

### ConcreteObserver 클래스

- `Observer` 인터페이스를 구현한 옵저버 클래스
- 각 옵저버는 특정 주제에 등록해서 연락받을 수 있음

<br><br>

## 느슨한 결합(Loose Coupling)

- 객체들이 상호작용할 수는 있지만 서로를 잘 모르는 관계
- 느슨한 결합을 활용하면 유연성 향상됨

<br>

### 옵저버 패턴에서의 사용

- 주제는 옵저버가 특정 인터페이스(`Observer` 인터페이스)를 구현한다는 사실만 인지
    - 옵저버의 구상 클래스가 무엇인지, 옵저버가 무엇을 하는지 알 필요 없음
- 옵저버는 언제든 새로 추가 가능
    - 주제는 `Observer` 인터페이스를 구현하는 객체 목록에만 의존함
    - 실행 중 하나의 옵저버를 다른 옵저버로 바꿔도 주제는 계속 다른 옵저버에 데이터 전달 가능
    - 마찬가지로 아무때나 제거 가능
- 새로운 형식의 옵저버를 추가할 때도 주제 변경 불필요
    - 새로운 클래스에서 `Observer` 인터페이스를 구현하고 옵저버로 등록만 하면 됨
- 주제와 옵저버는 서로 독립적으로 재사용 가능
- 주제나 옵저버가 달라져도 서로 영향 없음
    - 주제나 옵저버 인터페이스를 구현한다는 조건만 만족하면 됨

<br>

> 📍 **디자인 원칙**

상호작용하는 객체 사이에는 가능하면 느슨한 결합을 사용해야 한다.
> 

<br><br>

## push와 pull

### push

```java
// ConcreteSubject -> WeatherData
public void notifyObservers() {
		for(Observer observer : observers) {
				observer.update(temperature, humidity, pressure);
		}
}

// ConcreteObserver -> CurrentConditionsDisplay
public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		display();
}
```

- 주제가 옵저버에게 데이터를 보내는 방식
- 나중에 풍속 데이터가 추가될 경우, 모든 디스플레이의 `update()` 메소드를 수정해야 함
    - 풍속 데이터가 없는 디스플레이에도 데이터가 전달되므로 `update` 수정 → 불필요
    
<br>

### pull

```java
// ConcreteSubject -> WeatherData
public void notifyObservers() {
		for(Observer observer : observers) {
				observer.update();
		}
}

public void getTemperature() {
		return temperature;
}

public void getHumidity() {
		return humidity;
}

// Observer interface
public interface Observer {
		public void update();
}

// ConcreteObserver -> CurrentConditionsDisplay
public void update() {
		this.temperature = weatherData.getTemperature();
		this.humidity = weatherData.getHumidity();
		display();
}
```

- 옵저버가 주제로부터 데이터를 가져오는 방식
- 구현 방법의 문제 → 대체로 옵저버가 필요한 데이터를 골라서 가져가는 `pull` 방법이 더 좋음

<br><br>

## 핵심 정리

- 옵저버 패턴은 객체들 사이에 일대다 관계를 정의함
- 주제는 동일한 인터페이스를 써서 옵저버에게 연락함
- `Observer` 인터페이스를 구현하기만 하면 어떤 구상 클래스의 옵저버라도 패턴 참여 가능
- 주제는 옵저버들이 `Observer` 인터페이스를 구현한다는 것만 알고 있음 → 느슨한 결합
- 옵저버 패턴을 사용하면 주제가 데이터를 보내거나(`push`) 옵저버가 데이터를 가져올(`pull`) 수 있음
    - 일반적으로 `pull` 방법이 더 ‘옳은’ 방식이라고 간주함
- 스윙은 다른 여러 GUI 프레임워크와 마찬가지로 옵저버 패턴 많이 사용함
    - `JButton`의 슈퍼 클래스에 이벤트 감지하는 옵저버(리스너)
- 옵저버 패턴은 여러 개의 주제와 메시지 유형이 있는 복잡한 상황에서 사용하는 출판-구독 패턴과 친척
    - 출판-구독: 구독자가 서로 다른 유형의 메시지에 관심 가질 수 있고, 출판사와 구독자 더 세세하게 분리
- 옵저버 패턴은 자주 쓰이는 MVC 패턴과도 관련 있음
- 옵저버를 알림 순서에 의존하지 말라는 JDK 권고
