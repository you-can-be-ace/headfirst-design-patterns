# CH07. 어댑터 패턴과 퍼사드 패턴

## 어댑터 패턴
![image](https://github.com/boboram/TIL/assets/14108487/94b3bc8a-44b7-4339-a583-e65e419b08ea)
```
public class TurkeyAdapter implements Duck {
  Turkey turkey;

  public TurkeyAdapter(Turkey turkey) {
    this.turkey = turkey;
  }

  public void quack() { //Duck에 있는 quack() 메소드에 터키 함수 심기
    turkey.gobble();
  }

  public void fly() { //Duck에 있는 fly() 메소드에 터키 함수 심기
    turkey.fly();
  }
}   
```
- **어댑터 패턴** 정의
  - 특정 클래스 인터페이스를 클라이언트에서 요구하는 다른 인터페이스로 변환한다. 인터페이스가 호환되지 않아 같이 쓸 수 없었던 클래스를 사용할 수 있게 도와준다. 
- 한국에서 사용하던 휴대전화 충전기를 영국에서도 사용하려면 플러그 모양을 바꿔 주는 **어댑터**가 필요하다.
- 한국식 충전기 -> **어댑터** -> 영국식전원소켓
- 클라이언트에서 어댑터를 사용하는 방법
  - 클라이언트에서 타깃 인터페이스로 메소드를 호출해서 어댑터에 요청을 보낸다.
  - 어댑터는 어댑티 인터페이스로 그 요청을 어댑티에 관한 (하나 이상의) 메소드 호출로 변환한다.
  - 클라이언트는 호출 결과를 받긴 하지만 중간에 어댑터가 있다는 사실을 모른다.
- 어댑터 패턴 구성
  - Client : 클라이언트는 타깃 인터페이스만 볼 수 있다.
  - Target 인터페이스 : 어댑터에서 타깃 인터페이스를 구현한다. -> 어댑티를 새로 바뀐 인터페이스로 감쌀 때는 객체 구성(composition)을 사용한다.
  - Adapter 클래스 : 어댑터는 어댑티로 구성되어 있다.
  - Adaptee 클래스 : 모든 요청은 어댑티에 위임된다.
- Enumberation, Iterator에 어댑터 패턴을 적용한다면 호환되지 않는 Iterator.remove() 메소드엔 예외처리를 하는 식으로 처리해야 한다.

## 퍼사드 패턴
![image](https://github.com/boboram/TIL/assets/14108487/1518006e-6b1b-4b3e-8b0e-bfb741cc4a83)
```
public class HomeTheaterFacade { //HomeTheaterFacade facade class 
  Amplifier amp;
  Tuner tuner;
  StreamingPlayer player;
  Projector projector;
  TheaterLights lights;
  Screen screen;
  PopcornPopper popper;

  public HomeTheaterFacade (
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
- **퍼사드 패턴** 정의
  - 서브시스템에 있는 일련의 인터페이스를 통합 인터페이스로 묶어 준다. 또한 고수준 인터페이스도 정의하므로 서브시스템을 더 편리하게 사용할 수 있다. 
- Facade : 겉모양, 외관
- 퍼사드는 인터페이스를 단순하게 만들고 클라이언트와 구성 요소로 이루어진 서브시스템을 분리하는 역할을 한다.
- 퍼사드와 어댑터는 모두 여러 개의 클래스를 감쌀 수 있지만
  - 퍼사드는 인터페이스를 단순하게 만드는 용도로 쓰이는 반면,
  - 어댑터는 인터페이스를 다른 인터페이스로 변환하는 용도로 쓰인다.

## 디자인 원칙 : 최소 지식 원칙 == 데메테르의 법칙
- Principle of Least Knowledge
- 진짜 절친에게만 이야기해야 한다.
- 시스템의 한 부분을 변경했을 때 다른 부분까지 줄줄이 고쳐야 하는 상황을 미리 방지할 수 있다.
- 절친에게만 메소드 호출하기 가이드라인 4가지, 아래 4가지 항목의 메소드는 호출 가능 
  - 객체 자체
  - 메소드에 매개변수로 전달된 객체
  - 메소드를 생성하거나 인스턴스를 만든 객체
  - 객체에 속하는 구성요소
 
## 핵심 정리
- 기존 클래스를 사용하려고 하는데 인터페이스가 맞지 않으면 어댑터를 쓰면 된다.
- 큰 인터페이스와 여러 인터페이스를 단순하게 바꾸거나 통합해야 하면 퍼사드를 쓰면 된다.
- 어댑터는 인터페이스를 클라이언트에서 원하는 인터페이스로 바꾸는 역할을 한다.
- 퍼사드는 클라이언트를 복잡한 서브시스템과 분리하는 역할을 한다.
- 어댑터를 구현할 때는 타깃 인터페이스의 크기와 구조에 따라 코딩해야 할 분량이 결정된다.
- 퍼사드 패턴에서는 서브시스템으로 퍼사드를 만들고 진짜 작업은 서브클래스에 맡긴다.
- 어댑터 패턴에는 객체 어댑터 패턴과 클래스 어댑터 패턴이 있다. 클래스 어댑터를 쓰려면 다중 상속이 가능해야 한다.
- 한 서브시스템에 퍼사드를 여러개 만들어도 된다.
- 어댑터는 객체를 감싸서 인터페이스를 바꾸는 용도로, 데코레이터는 객체를 감싸서 새로운 행동을 추가하는 용도로, 퍼사드는 일련의 객체를 감싸서 단순하게 만드는 용도로 쓰인다. 
