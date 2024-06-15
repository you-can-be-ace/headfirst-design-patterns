# Chapter01. 전략 패턴 Strategy Pattern

<br>

## Before: 상속 사용

<img width="707" alt="image" src="https://github.com/you-can-be-ace/headfirst-design-patterns/assets/141018558/a445dcf5-777f-43cb-8dcd-575bb258edd8">

- 슈퍼클래스에 메소드를 추가하면 몇몇 서브클래스에만 적용되어야 하는 부분이 모든 서브클래스에 적용됨
- 규격이 바뀔 때마다 프로그램에 추가했던 메소드를 일일이 살펴보고 상황에 따라 오버라이드해야 함
- 유지보수를 생각하면 좋지 않음

<br>

### 단점

- 서브클래스에서 코드가 중복된다
- 실행 시에 특징을 바꾸기 힘들다(동적 설정 불가능)
- 모든 오리의 행동을 알기 힘들다
- 코드를 변경했을 때 다른 오리들에게 원치 않은 영향을 끼칠 수 있다

<br><br>

## 문제 해결1: 캡슐화

> **📍 디자인 원칙** <br>
애플리케이션에서 달라지는 부분을 찾아내고, 달라지지 않는 부분과 분리한다.
> 

<img width="716" alt="image" src="https://github.com/you-can-be-ace/headfirst-design-patterns/assets/141018558/2940d01c-455b-4f3d-b6bf-93b9ccd51b1f">

- **바뀌는 부분을 따로 뽑아서 캡슐화하면 나중에 바뀌지 않는 부분에는 영향을 미치지 않고 그 부분만 고치거나 확장할 수 있음 → 시스템 유연성 향상**
- 변화하는 부분과 그대로 있는 부분을 분리하기 위해 별개인 2개의 클래스 집합을 만들어야 함
    - 각 클래스 집합에는 각자의 행동을 구현한 것을 전부 집어넣음
- `Duck` 클래스는 여전히 모든 오리의 슈퍼클래스
    - `fly()`와 `quack()`은 오리 종류에 따라 달라짐

<br><br>

## 문제 해결2: 추상 상위 형식

> **📍 디자인 원칙** <br>
구현보다는 인터페이스에 맞춰서 프로그래밍한다.
> 

<img width="744" alt="image" src="https://github.com/you-can-be-ace/headfirst-design-patterns/assets/141018558/822a4ea7-7122-4712-91d6-0f7733bcb7d2">

- `Duck`의 몇몇 서브클래스에만 적용되어야 했던 `fly()`, `quack()` 메소드가 들어있는 인터페이스 생성 후 서브클래스에서 구현해서 사용
- 상속의 일부 문제점은 해결할 수 있지만 코드를 재사용하지 않으므로 코드 관리가 어려움
    - **한 가지 행동을 바꿀 때마다 그 행동이 정의되어 있는 서로 다른 서브클래스를 전부 찾아서 코드를 일일이 고쳐야 하고 그 과정에서 새로운 버그가 생길 가능성 높음 → 특정 구현에 의존**
    - `fly()` 메소드로 예를 들면 난다는 공통점은 있으나 나는 방식이 다를 수 있다는 문제 발생

<img width="726" alt="image" src="https://github.com/you-can-be-ace/headfirst-design-patterns/assets/141018558/958b8b65-dd79-4e9a-9c48-0e1620ad0f81">

- 행동 인터페이스는 `Duck` 클래스가 아닌 특정 행동만을 목적으로 하는 클래스의 집합에 속하는 행동 클래스에서 구현함
- `Duck` 클래스에서 행동을 구체적으로 구현하거나 서브클래스 자체에서 별도로 구현하는 이전 방법들과는 상반된 방법

<br>

### 장점

- **`Duck` 서브클래스는 인터페이스로 표현되는 행동을 사용하므로 실제 행동 구현은 `Duck` 서브클래스에 국한되지 않음**
- 다른 형식의 객체에서도 재사용 가능
- 기존의 행동 클래스를 수정하거나 사용하고 있는 `Duck` 클래스를 건드리지 않고도 새로운 행동 추가 가능

<br>

### 추상 상위 형식 사용 이유

```java
Dog d = new Dog();
d.bark();

Animal animal = new Dog();
animal.makeSound();

a = getAnimal();
a.makeSound();
```

- 실제 실행 시에 쓰이는 객체가 코드에 고정되지 않도록 상위 타입에 맞춰 다형성을 활용하기 위함
- **더 바람직한 방법은 상위 타입의 인스턴스 생성 과정을 직접 코드로 입력하는 대신 구체적으로 구현된 객체를 실행 시 대입하는 것**

<br><br>

## 문제 해결3: 구성(composition)

> **📍 디자인 원칙** <br>
상속보다는 구성을 활용한다.
”A는 B이다”보다 “A에는 B가 있다”가 나을 수 있다.
> 

<img width="711" alt="image" src="https://github.com/you-can-be-ace/headfirst-design-patterns/assets/141018558/004f165c-8261-472c-8286-ea228694264d">

- 각 오리에는 각각 나는 행동과 꽥꽥거리는 행동을 위임받는 인터페이스 형식의 인스턴스 변수 `FlyBehavior`, `QuackBehavior`가 있음
    - 이런 식으로 두 클래스를 합치는 것을 ‘구성(composition)을 이용한다’라고 함
    - 각 오리 객체에서는 실행 시 이 변수에 특정 행동 형식의 레퍼런스를 다형성으로 설정함

<br><br>

```java
public abstract class Duck {
		QuackBehavior quackBehavior;
		
		public void performQuack() {
				quackBehavior.quack();
		}
}
```

- 꽥꽥거리는 행동을 직접 처리하는 대신 `quackBehavior`로 참조되는 객체에 그 행동을 위임함

<br><br>

```java
public class MallardDuck extends Duck {
		public MallardDuck() {
				quackBehavior = new Quack();
				flyBehavior = new FlyWithWings();
		}
		
		public void display() {
				System.out.println("저는 물오리입니다.");
		}
}
```

- `MallardDuck` 인스턴스가 생성될 때 생성자는 `Duck`으로부터 상속받은 `quackBehavior` 인스턴스 변수에 `Quack`(`QuackBehavior`를 구현한 구상 클래스) 형식의 새 인스턴스를 대입함
    - `flyBehavior` 인스턴스 변수에도 `FlyWithWings`(`FlyBehavior`를 구현한 구상 클래스) 형식의 새 인스턴스가 대입됨
- `MallardDuck`이 꽥꽥거리는 행동을 처리할 때는 `Quack` 클래스 사용 → `performQuack()`이 호출되면 `Quack` 객체에게 위임됨

<br>

### 동적으로 행동 지정

```java
public abstract class Duck {
		FlyBehavior flyBehavior;
		QuackBehavior quackBehavior;
		
		// ...

		// setter
		public void setFlyBehavior(FlyBehavior fb) {
				flyBehavior = fb;
		}
		
		public void setQuackBehavior(QuackBehavior qb) {
				quackBehavior = qb;
		}
		
		// 행동 클래스에 위임
		public void performFly() {
				flyBehavior.fly();
		}
		
		public void performQuack() {
				quackBehavior.quack();
		}
}
```

```java
public class MiniDuckSimulator {
		public static void main(String[] args) {
				Duck mallard = new MallardDuck();
				mallard.performQuack();
				mallard.performFly();
				
				Duck model = new ModelDuck();
				model.performFly();
				model.setFlyBehavior(new FlyRocketPowered());
				model.performFly();
		}
}
```

- `Duck`의 서브클래스에서 세터 메소드를 호출해 나는 방법 변경

<br>

### 장점

- 유연성 크게 향상됨
- 알고리즘군을 별도의 클래스 집합으로 캡슐화 가능
- 구성 요소로 사용하는 객체에서 올바른 행동 인터페이스를 구현하기만 하면 동적으로 행동 지정 가능

<br><br>

## ⇒ 전략 패턴(Strategy Pattern)

- 알고리즘군을 정의하고 캡슐화해서 각각의 알고리즘군을 수정해서 쓸 수 있게 해줌
- 전략 패턴을 사용하면 클라이언트로부터 알고리즘을 분리해서 독립적으로 변경 가능

<br><br>

## 핵심 정리

- 훌륭한 객체지향 디자인이라면 재사용성, 확장성, 관리의 용이성을 갖춰야 함
- 패턴은 검증받은 객체지향 경험의 산물로, 디자인 문제의 보편적인 해법을 제공함
- 대부분의 패턴과 원칙은 소프트웨어의 변경 문제와 연관되어 있고, 시스템의 일부분을 나머지 부분과 무관하게 변경하는 방법을 제공함
- 많은 경우에 시스템에서 바뀌는 부분을 골라내 캡슐화해야 함

<br><br>

## 소감

- 상속이 항상 올바른 방법은 아니다
- 이펙티브 자바에서 예제로 봤던 패턴이라 익숙했는데 정리하면서 이해하고 알아보는 시간이었다
- 객체지향 기초와 다른 원칙들도 궁금해졌다
