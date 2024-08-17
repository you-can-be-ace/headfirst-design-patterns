# CH12. 복합패턴

## 복합 패턴 
- 여러 패턴을 함께 사용하여 디자인 문제를 해결하는 방법을 복합 패턴이라고 한다.
- 교재에는 여러 예시가 나온다. 

## 오리 시뮬레이션 게임에 다양한 패턴 적용해보기
```
public class MallardDuck implements Quakable {
    @Override
    public void quack() {
        System.out.println("꽥꽥");
    }
}
```
- 오리는 quack 함수에서 소리를 낸다. 
```
Quakable mallardDuck = new MallardDuck();
Quakable redheadDuck = new RedheadDuck();
Quakable duckCall = new DuckCall();
Quakable rubberDuck = new RubberDuck();
```

### 어댑터 패턴 적용하여 오리와 거위 연결하기 
- 위 코드에서 거위를 추가하고 싶다. 거위는 quack 함수가 아닌 honk 함수에서 소리를 낸다.
- 어댑터 패턴을 사용하면 거위도 오리처럼 사용 가능하다.
```
public class Goose {
    public void honk() {
        System.out.println("끽끽");
    }
}

public class GooseAdapter implements Quakable {
    Goose goose;

    public GooseAdapter(Goose goose) {
        this.goose = goose;
    }

    @Override
    public void quack() {
        goose.honk();
    }
}
```
- `Quakable gooseDuck = new GooseAdapter(new Goose());`

### 데코레이터 패턴을 적용하여 오리가 울 때마다 카운팅 하기 
```
public class QuackCounter implements Quakable {
    Quakable duck;
    static int numberOfQuacks;

    public QuackCounter(Quakable duck) {
        this.duck = duck;
    }

    @Override
    public void quack() {
        duck.quack();
        numberOfQuacks++;
    }

    public static int getQuacks() {
        return numberOfQuacks;
    }
}
```
- quack 함수에서 카운팅을 하면서 실제 오리의 quack 함수를 다시 호출하도록 하는 것
```
Quakable mallardDuck = new QuackCounter(new MallardDuck());
Quakable redheadDuck = new QuackCounter(new RedheadDuck());
Quakable duckCall = new QuackCounter(new DuckCall());
Quakable rubberDuck = new QuackCounter(new RubberDuck());
Quakable gooseDuck = new GooseAdapter(new Goose()); //거위는 카운트 되지 않음
```
- 카운팅을 하지 않아도 되는 거위를 제외하고 다른 오리는 QuackCounter로 감싼다. -> 데코레이터 패턴을 적용 

### 팩토리 패턴 적용
- 위 코드에서는 새로운 행동을 하려면 객체를 데코레이터로 감싸야 한다.
- 오리 객체를 생성하는 부분을 한곳에서 처리하고 싶다! 팩토리 패턴을 사용하면 된다.
```
public abstract class AbstractDuckFactory {
public abstract Quakable createMallardDuck();
public abstract Quakable createRedheadDuck();
public abstract Quakable createDuckCall();
public abstract Quakable createRubberDuck();
public abstract Quakable createGooseDuck();
}

// 오리 + 카운팅 기능 추가 팩토리 클래스
public class CountingDuckFactory extends AbstractDuckFactory {

    public Quakable createMallardDuck() {
        return new QuackCounter(new MallardDuck());
    }

    public Quakable createRedheadDuck() {
        return new QuackCounter(new RedheadDuck());
    }

    public Quakable createDuckCall() {
        return new QuackCounter(new DuckCall());
    }

    public Quakable createRubberDuck() {
        return new QuackCounter(new RubberDuck());
    }

    public Quakable createGooseDuck() { // 거위는 카운트 되지 않음
        return new GooseAdapter(new Goose());
    }
}
```
- 카운팅 팩토리 클래스에서는 거위를 제외하고 다른 오리에게만 데코레이터 패턴을 적용하도록 하면 된다. 
```
void simulate(AbstractDuckFactory duckFactory) {
    Quakable mallardDuck = duckFactory.createMallardDuck();
}
```
- 위와 같이 클라이언트에서 오리 객체를 생성하는 것을 팩토리 함수를 통해 생성하도록 한다.

### 반복자 패턴을 적용하여 오리 무리를 만들어 보기
```
public class Flock implements Quakable{

  List<Quakable> quackers = new ArrayList<Quakable>();

  public void add(Quakable quacker) {
      quackers.add(quacker);
  }

  @Override
  public void quack() {
      //반복자 패턴을 사용하여 Flock에 있는 Quakable 객체들을 순회하면서 quack() 메소드를 호출한다.
      Iterator<Quakable> iterator = quackers.iterator();

      while (iterator.hasNext()) {
          Quakable quakable = iterator.next();
          quakable.quack();
      }
  }
}
```
```
Flock flockOfDucks = new Flock(); //전체 무리 

flockOfDucks.add(redheadDuck);

Flock flockOfMallards = new Flock(); //물오리 무리

Quakable mallardOne = duckFactory.createMallardDuck();
Quakable mallardTwo = duckFactory.createMallardDuck();

flockOfMallards.add(mallardOne);
flockOfMallards.add(mallardTwo);

flockOfDucks.add(flockOfMallards);

System.out.println("\n오리 시뮬레이션 게임: 전체 무리");
simulate(flockOfDucks);

System.out.println("\n오리 시뮬레이션 게임: 물오리 무리");
simulate(flockOfMallards);
```
- 메인에서 quack을 호출하면 Flock > quack에서 Quackable 개별 객체들의 quack 함수를 호출한다.

### 옵저버 패턴을 적용하여 오리의 행동 관찰하기 
- 어떤 클래스에서 Observer를 구현한다는 것은 그 클래스의 객체에서 Quackable을 감시할 수 있다는 것을 뜻한다.
  - 그 얘기는 결국 Quackable의 quack() 메소드가 실행될 때마다 연락을 받게 된다는 것을 의미
```
public interface QuackObservable {

    public void registerObserver(Observer observer);
    public void notifyObservers();
}

public interface Observer {
    void update(QuackObservable duck);
}

public class Quackologist implements Observer {
    @Override
    public void update(QuackObservable duck) {
        System.out.println("꽥꽥학자: " + duck.toString() + " 가 방금 소리쳤다.");
    }
}

public class Observable implements QuackObservable {
    List<Observer> observers = new ArrayList<Observer>();
    QuackObservable duck;

    public Observable(QuackObservable duck) {
        this.duck = duck;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        Iterator iterator = observers.iterator();
        while (iterator.hasNext()) {
            Observer observer = (Observer) iterator.next();
            observer.update(duck);
        }
    }
}
```
#### 메인에서 오리 무리의 옵저버 설정 
```
Quackologist quackologist = new Quackologist();
flockOfDucks.registerObserver(quackologist);
```
- 위처럼 처리하면 오리가 울때마다 오리학자들이 연락을 받을 수 있다.

## 복합 패턴 
- 복합 패턴은 여러 디자인 패턴을 결합하여 더 복잡한 문제를 해결하는 패턴.
- 헤드퍼스트 디자인패턴 책에서는 MVC(Model-View-Controller) 패턴을 예로 들어 설명.
- 이 패턴은 모델, 뷰, 컨트롤러 세 가지 구성 요소로 나뉘며, 각 구성 요소는 특정 역할을 담당.

### MVC 
- 모델, 뷰, 컨트롤러 3가지
- MVC는 옵저버, 전략, 컴포지트 패턴으로 이루어진 복합 패턴이다.
- 모델
  - 모델에는 모든 데이터, 상태와 애플리케이션 로직이 들어있다.
  - 뷰와 컨트롤러에서 모델의 상태를 조작하거나 가져올 때 필요한 인터페이스를 제공
  - 모델이 자신의 상태 변화를 옵저버들에게 연락해 주긴 하지만, 기본적으로 모델은 뷰와 컨트롤러를 신경쓰지 않음
  - 예: HeartModel, BeatModel
- 뷰 (View)
  - 모델을 표현하는 방법을 제공한다.
    - 컴포지트 패턴 사용 
  - 일반적으로 화면에 표시할 때 필요한 상태와 데이터는 모델에서 직접 가져온다.
  - 예: DJView
- 컨트롤러
  - 사용자로부터 입력을 받으며 입력받은 내용이 모델에게 어떤 의미가 있는지 파악한다.
  - 전략 패턴을 통해 뷰를 모델로부터 분리한다. 
  - 예: HeartController, BeatController
- 관계
  - 사용자는 뷰에만 접촉 가능
  - 컨트롤러가 모델에게 상태 변경하라고 요청함
  - 컨트롤러가 뷰를 변경해 달라고 요청할 수도 있다.
  - 상태가 변경도면 모델이 뷰에게 그 사실을 알립니다.
  - 뷰가 모델에게 상태를 요청한다.
