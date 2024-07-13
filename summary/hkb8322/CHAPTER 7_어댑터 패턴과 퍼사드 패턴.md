# 1️⃣ 어댑터 패턴

## 정의

- 특정 클래스 인터페이스를 클라이언트에서 요구하는 다른 인터페이스로 변환
- 인터페이스가 호환되지 않아 같이 쓸 수 없었던 클래스를 사용할 수 있게 함

## 특징

- 어댑터가 어댑티 인스턴스를 객체 구성(composition)을 통해 보유
    - 어댑티의 모든 서브클래스를 어댑터에서 사용 가능
- 클라이언트를 특정 구현이 아닌 인터페이스에 의존하도록 함

## 종류

### 객체 어댑터
![image](https://github.com/user-attachments/assets/ad337b0f-47c1-4deb-ad6b-24ec1d60a45a)


### 클래스 어댑터
![image](https://github.com/user-attachments/assets/e652448e-1a29-4f3b-b1ee-3b08b0a93692)


### 차이점

- 클래스 어댑터는 다중 상속이 필요함
- ❗클래스 어댑터는 타깃과 어댑티의 서브 클래스로 만들어짐
객체 어댑터는 구성으로 어댑티에 요청을 전달

## 예제

(사진)

```java
package com.ace.example.chapter07.adapter.enumerationiterator;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator implements Iterator<Object> {

  Enumeration<?> enumeration;

  public EnumerationIterator(Enumeration<?> enumeration) {
    this.enumeration = enumeration;
  }

  @Override
  public boolean hasNext() {
    return enumeration.hasMoreElements();
  }

  @Override
  public Object next() {
    return enumeration.nextElement();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException(); //Iterator의 remove() 메소드는 지원되지 않는다.
  }
}
```

# 데코레이터 패턴 🆚 어댑터 패턴

(288~289pg)

- `어댑터 패턴` : 객체를 감싸 인터페이스를 바꾸는 용도
- `데코레이터 패턴` : 객체를 감싸 새로운 행동 추가

# 2️⃣ 퍼사드 패턴(Facade Pattern)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/31151178-2abf-4285-b27d-d78331d3f581/c883c7cf-35e3-4e07-be25-5c46cb51e3ce/Untitled.png)

## 정의

- 서브 시스템에 있는 일련의 인터페이스를 통합 인터페이스로 묶음
- 고수준 인터페이스 정의를 통해 서브 시스템을 보다 편리하게 사용하도록 함

## 특징

- 퍼사드 클래스는 서브 시스템 클래스를 캡슐화하지 않고 간단한 인터페이스만 제공
- 특정 서브 시스템에 대해 만들 수 있는 퍼사드의 개수 제한 없음
- 클라이언트 구현과 서브 시스템을 분리할 수 있음

## 유관 디자인 원칙

> 진짜 절친에게만 이야기 해야 한다.
> 
- `최소 지식 원칙(Principle of Least Knowledge)` : 시스템 디자인 설계 시 객체 사이의 상호작용의 방식과 범위를 최소화하도록 노력해야 함

## 예제

```java
public class HomeTheaterTestDrive {

  public static void main(String[] args) {

    Amplifier amp = new Amplifier();
    Tuner tuner = new Tuner();
    StreamingPlayer player = new StreamingPlayer();
    Projector projector = new Projector();
    Screen screen = new Screen();
    TheaterLights lights = new TheaterLights();
    PopcornPopper popper = new PopcornPopper();

    HomeTheaterFacade homeTheater = new HomeTheaterFacade(
        amp, tuner, player, projector, lights, screen, popper);

    **homeTheater.watchMovie("인디아나 존스:레이더스");
    homeTheater.endMovie();**
  }

}
```

```java
public class HomeTheaterFacade {

  Amplifier amp;
  Tuner tuner;
  StreamingPlayer player;
  Projector projector;
  TheaterLights lights;
  Screen screen;
  PopcornPopper popper;

  public HomeTheaterFacade(
      Amplifier amp,
      Tuner tuner,
      StreamingPlayer player,
      Projector projector,
      TheaterLights lights,
      Screen screen,
      PopcornPopper popper
  ) {
    this.amp = amp;
    this.tuner = tuner;
    this.player = player;
    this.projector = projector;
    this.lights = lights;
    this.screen = screen;
    this.popper = popper;
  }

  public void watchMovie(String movie) {
    System.out.println("영화 볼 준비 중");
    popper.on();
    popper.pop();
    lights.dim(10);
    screen.down();
    projector.on();
    projector.wideScreenMode();
    amp.on();
    amp.setStreamingPlayer(player);
    amp.setSurroundSound();
    amp.setVolume(5);
    player.on();
    player.play(movie);
  }

  public void endMovie() {
    System.out.println("홈시어터를 끄는 중");
    popper.off();
    lights.on();
    screen.up();
    projector.off();
    amp.off();
    player.stop();
    player.off();
  }

}
```

# 최소 지식 원칙 준수하기

- 한 메소드 내에서 아래 객체만 사용
    - 객체 자체
    - 메소드에 매개변수로 전달된 객체
    - 메소드를 생성하거나 인스턴스를 만든 객체
    - 객체에 속하는 구성 요소 (인스턴스 변수)
- 데메테르(디미터)의 법칙과 동일한 단어
    
    [[OOP] 디미터의 법칙(Law of Demeter)](https://mangkyu.tistory.com/147)
    

## ❌ 원칙을 준수하지 않는 경우

```java
public float getTemp() {
	Thermometer thermometer = station.getThermometer();
	return thermometer.getTemperature();
}
```

## ⭕ 원칙을 준수하는 경우

```java
public float getTemp() {
	return station.getTemperature();
}
```

**⇒ 객체의 데이터를 가져오는 것이 아닌 객체에 데이터를 요청**

## 장점

- 객체 사이의 의존성을 줄일 수 있음
- 소프트웨어 관리가 더 편해짐

## 단점

- 메소드 호출을 처리하는 래퍼 클래스를 더 만들어야 할 수도 있음
    - 이에 따라 시스템이 복잡해지고 개발 기간도 더 소요되고 성능도 떨어질 수 있음

**⇒ 모든 원칙은 상황에 따라 적절히 따라야 함**

# 퍼사드 패턴 🆚 어댑터 패턴

- 감싸는 클래스의 개수가 아닌 용도의 차이
    - `퍼사드 패턴` : 인터페이스를 단순하게 만드는 용도
    - `어댑터 패턴` :  인터페이스를 다른 인터페이스로 변환하는 용도
