# 복합 패턴

## 정의

- 반복적으로 생길 수 있는 일반적인 문제를 해결하는 용도로 2개 이상의 패턴을 결합하여 사용하는 것

## 특징

- 단순히 여러 패턴을 섞어 사용했다고 복합 패턴이라 할 수 없음

# 여러 패턴을 사용한 오리 시뮬레이터

## 1️⃣ 어댑터 패턴

```java
public class GooseAdapter implements Quackable {
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

- 거위를 `Quakable` 인터페이스 호환 가능하도록 함

## 2️⃣ 데코레이터 패턴

```java
public class QuackCounter implements Quackable {
    Quackable duck;
    static int numberOfQuacks;

    public QuackCounter(Quackable duck) {
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

- `Quackable` 객체를 감싸 꽥꽥거린 횟수를 카운팅

## 3️⃣ 추상 팩토리 패턴

```java
public abstract class AbstractDuckFactory {
    public abstract Quakable createMallardDuck();
    public abstract Quakable createRedheadDuck();
    public abstract Quakable createDuckCall();
    public abstract Quakable createRubberDuck();
    public abstract Quakable createGooseDuck();
}
```

```java
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

- `Quackable` 객체를 한 곳에서 생성하도록 함

## 4️⃣ 컴포지트 패턴

```java
public class Flock implements Quakable { // 복합 객체

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

- 여러 `Quackable` 객체를 하나의 복합 객체로 관리 가능하도록 함
- 이 때 복합 객체도 `Quackable` 구현하여 투명성 보장

## 5️⃣ 옵저버 패턴

### Subject 구현

```java
public interface QuackObservable {

    public void registerObserver(Observer observer);
    public void notifyObservers();
}
```

```java
public interface Quakable **extends QuackObservable** {
    public void quack();
}
```

```java
public class Observable **implements QuackObservable** {
    **List<Observer> observers = new ArrayList<Observer>();**
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

```java
public class MallardDuck **implements Quakable** {

    **Observable observable;**

    public MallardDuck() {
        **observable = new Observable(this);**
    }

    @Override
    public void quack() {
        System.out.println("꽥꽥");
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        observable.notifyObservers();
    }

    @Override
    public String toString() {
        return "물오리";
    }
}
```

### Observer 구현

```java
public interface Observer {
    void update(QuackObservable duck);
}
```

```java
public class Quackologist implements Observer {
    @Override
    public void update(QuackObservable duck) {
        System.out.println("꽥꽥학자: " + duck.toString() + " 가 방금 소리쳤다.");
    }
}
```

## Test Driver

```java
public class DuckSimulator {
    public static void main(String[] args) {
        DuckSimulator simulator = new DuckSimulator();
        AbstractDuckFactory duckFactory = new CountingDuckFactory();

        simulator.simulate(duckFactory);
    }

    void simulate(AbstractDuckFactory duckFactory) {

        Quakable redheadDuck = duckFactory.createRedheadDuck();
        Quakable duckCall = duckFactory.createDuckCall();
        Quakable rubberDuck = duckFactory.createRubberDuck();
        Quakable gooseDuck = duckFactory.createGooseDuck(); // 거위는 카운트 되지 않음

        Flock flockOfDucks = new Flock();

        flockOfDucks.add(redheadDuck);
        flockOfDucks.add(duckCall);
        flockOfDucks.add(rubberDuck);
        flockOfDucks.add(gooseDuck);

        Flock flockOfMallards = new Flock();

        Quakable mallardOne = duckFactory.createMallardDuck();
        Quakable mallardTwo = duckFactory.createMallardDuck();
        Quakable mallardThree = duckFactory.createMallardDuck();
        Quakable mallardFour = duckFactory.createMallardDuck();

        flockOfMallards.add(mallardOne);
        flockOfMallards.add(mallardTwo);
        flockOfMallards.add(mallardThree);
        flockOfMallards.add(mallardFour);

        flockOfDucks.add(flockOfMallards);

        System.out.println("\n오리 시뮬레이션 게임: (+옵저버)");
        Quackologist quackologist = new Quackologist();
        **flockOfDucks.registerObserver(quackologist);**

        simulate(flockOfDucks);

        System.out.println("오리가 소리 낸 횟수: " + QuackCounter.getQuacks() + " 번");

    }

    void simulate(Quakable duck) {
        duck.quack();
    }
}
```

⇒ 여러 패턴을 사용하면 복합 패턴이다? ❌

# MVC 패턴

## 구성 요소

### 모델

- 모든 데이터, 상태와 애플리케이션 로직 포함
- 뷰와 컨트롤러에서 모델의 상태를 조작하거나 가져올 때 필요한 인터페이스 제공
- 모델이 자신의 상태 변화를 옵저버들에게 연락
- 기본적으로 모델은 뷰와 컨트롤러에게 관심 없음

### 컨트롤러

- 사용자로부터 입력을 받으면 입력 받은 내용이 모델에게 어떤 의미가 있는지 파악
- 뷰-모델 간 결합을 끊어주는 역할 수행

### 뷰

- 모델을 표현하는 방법 제공
- 일반적으로 화면에 표시할 때 필요한 상태와 데이터는 모델에서 직접 조회

## 흐름

1. 사용자는 뷰에만 접근 가능
2. 컨트롤러가 모델에게 상태를 변경하라고 요청
3. 컨트롤러가 뷰를 변경해달라고 요청 가능
4. 상태가 변경되면 모델이 뷰에게 알림
5. 뷰가 모델에게 상태를 요청

## 특징

- 컨트롤러에서 애플리케이션 로직을 구현하지 않음
- 컨트롤러는 완벽하지는 않지만 어느 정도 중재자 역할을 함
    - 중재자 패턴에서 중재자는 객체 사이의 상호작용을 캡슐화하여 두 객체 사이의 연결을 느슨하게 함
    - 컨트롤러가 진정한 의미의 중재자라면 모델의 상태를 알아낼 때도 컨트롤러를 거쳤어야 함
- 하나의 뷰에 하나의 컨트로를 만드는 것이 일반적

# MVC에 사용되는 패턴

## `Model`: 옵저버 패턴

- 상태가 변경되었을 때 연관된 객체들에게 연락
- 사용함으로써 뷰와 컨트롤러부터 완전히 독립
    - 한 모델에서 서로 다른 뷰를 사용할 수도 있고, 여러 개의 뷰를 동시 사용 가능

## `View`: 컴포지트 패턴

- 디스플레이는 여러 복합 객체와 잎 객체 컴포넌트로 구성
- 컨트롤러가 뷰에게 화면 갱신 요청 시 최상위 뷰 구성 요소에만 요청하면 됨

## `Controller`: 전략 패턴

- 뷰와 컨트롤러는 고전적인 전략 패턴으로 구현되어 있음
- 컨트롤러가 제공하는 전략을 통해 뷰 객체를 여러 전략을 사용해 설정 가능
    - 컨트롤러는 뷰의 전략 객체에 해당
- 뷰는 애플리케이션의 겉모습에만 신경을 쓰고, 인터페이스의 행동을 결정하는 일은 컨트롤러에게 맡김
- 사용 시 뷰를 모델로부터 분리하는 데 도움이 됨
- 사용자가 요청한 내역을 처리하기 위해 모델과 통신하는 것을 뷰는 알지 못함

# 기존 View, Controller 활용하기

```java
public interface HeartModelInterface {
    int getHeartRate();
    void registerObserver(BeatObserver o);
    void removeObserver(BeatObserver o);
    void registerObserver(BPMObserver o);
    void removeObserver(BPMObserver o);
}
```

```java
public class HeartAdapter implements BeatModelInterface {

    HeartModelInterface heart;

    public HeartAdapter(HeartModelInterface heart) {
        this.heart = heart;
    }

    public void initialize() {}

    public void on() {}

    public void off() {}

    public int getBPM() {
        return heart.getHeartRate();
    }

    public void setBPM(int bpm) {}

    public void registerObserver(BeatObserver o) {
        heart.registerObserver(o);
    }

    public void removeObserver(BeatObserver o) {
        heart.removeObserver(o);
    }

    public void registerObserver(BPMObserver o) {
        heart.registerObserver(o);
    }

    public void removeObserver(BPMObserver o) {
        heart.removeObserver(o);
    }
}
```

```java
public class HeartModel implements HeartModelInterface, Runnable {
    ArrayList<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
    ArrayList<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
    int time = 1000;
    int bpm = 90;
    Random random = new Random(System.currentTimeMillis());
    Thread thread;

    public HeartModel() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        int lastrate = -1;

        for(;;) {
            int change = random.nextInt(10);
            if (random.nextInt(2) == 0) {
                change = 0 - change;
            }
            int rate = 60000/(time + change);
            if (rate < 120 && rate > 50) {
                time += change;
                notifyBeatObservers();
                if (rate != lastrate) {
                    lastrate = rate;
                    notifyBPMObservers();
                }
            }
            try {
                Thread.sleep(time);
            } catch (Exception e) {}
        }
    }
    public int getHeartRate() {
        return 60000/time;
    }

    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    public void removeObserver(BeatObserver o) {
        int i = beatObservers.indexOf(o);
        if (i >= 0) {
            beatObservers.remove(i);
        }
    }

    public void notifyBeatObservers() {
        for(int i = 0; i < beatObservers.size(); i++) {
            BeatObserver observer = (BeatObserver)beatObservers.get(i);
            observer.updateBeat();
        }
    }

    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    public void removeObserver(BPMObserver o) {
        int i = bpmObservers.indexOf(o);
        if (i >= 0) {
            bpmObservers.remove(i);
        }
    }

    public void notifyBPMObservers() {
        for(int i = 0; i < bpmObservers.size(); i++) {
            BPMObserver observer = (BPMObserver)bpmObservers.get(i);
            observer.updateBPM();
        }
    }
}
```

```java
public class HeartController implements ControllerInterface {
    HeartModelInterface model;
    DJView view;

    public HeartController(HeartModelInterface model) {
        this.model = model;
        **view = new DJView(this, new HeartAdapter(model));**
        view.createView();
        view.createControls();
        view.disableStopMenuItem();
        view.disableStartMenuItem();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void increaseBPM() {
    }

    @Override
    public void decreaseBPM() {
    }

    @Override
    public void setBPM(int bpm) {

    }
}
```

```java
public class HeartTestDrive {
    public static void main(String[] args) {
        HeartModel heartModel = new HeartModel();
        ControllerInterface model = new HeartController(heartModel);
    }
}
```

- 어댑터 패턴을 활용하여 모델 재구성 후 뷰, 컨트롤러 재사용 가능

# 결론

- MVC는 옵저버, 전략, 컴포지트 패턴으로 이루어진 복합 패턴
- 모델은 옵저버 패턴을 사용해 의존성을 없애면서도 옵저버들에게 상태 알림
- 컨트롤러는 뷰의 전략 객체, 뷰는 컨트롤러를 바꿔 다른 행동 가능
- 뷰는 컴포지트 패턴을 사용하여 사용자 인터페이스 구현
- 모델, 뷰, 컴포넌트는 3가지 패턴으로 서로 느슨하게 결합됨
- 새로운 모델을 기존의 뷰와 컨트롤러에 연결이 필요할 땐 어댑터 패턴 활용
- MVC는 웹에도 적용됨
- 클라이언트-서버 애플리케이션 구조에 MVC를 적응시켜 주는 다양한 프레임워크 존재
