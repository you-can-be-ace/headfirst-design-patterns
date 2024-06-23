# CH03-데코레이터 패턴
<img width="983" alt="image" src="https://github.com/boboram/TIL/assets/14108487/08bca30a-da84-4314-8b49-5ab265b95da9">

### 데코레이터 패턴 정의 
- 객체에 추가 요소를 동적으로 더할 수 있다. 데코레이터를 사용하면 서브클래스를 만들 때보다 훨씬 유연하게 기능 확장이 가능하다. -> **OCP**
  - 객체를 동적으로 구성하면 기존 코드를 고치는 대신 새로운 코드를 만들어서 기능을 추가할 수 있다.
- 스타벅스에는 다양한 음료, 옵션 추가가 존재한다.
  - 각각의 옵션에 따른 가격을 리턴하게끔 하는 클래스를 만든다면? 무수히 많은 클래스들이 생성된다.
  - 음료와 동일한 클래스를 확장하는 데코레이션 클래스를 구현하고 필요한 데코레이션 클래스(=옵션)들만 감싸서 올바른 가격을 리턴받을 수 있다.
- 데코레이터는 자신이 장식하고 있는 객체에게 어떤 행동을 위임하는 일 말고도 추가 작업을 수행할 수 있다.
- 자바 I/O 도 데코레이터 패턴을 바탕으로 만들어짐

### 데코레이터 패턴 구현
```
public class StarbuzzCoffee {
  public static void main(String args[]) {
    Beverage beverage = new DarkRoast();
    beverage = new Mocha(beverage); //DarkRoast를 감싸고 있는 모카
    beverage = new Mocha(beverage); //DarkRoast를 깜싸고 있는 더블모카
    beverage = new Whip(beverage); //DarkRoast를 감싸고 있는 더블모카를 감싼 휘핑크림
    System.out.println(beverage.getDescription() + " $" + beverage.cost()); //휘핑크림.cost + (모카.cost +(모카.cost +(다크로스트.cost)))
  }
}

public class Mocha extends CondimentDecorate { //CondimentDecorate 추상 클래스는 Beverage 클래스를 상속받았다. 
  public Mocha(Beverage beverage) {
    this.beverage = beverage; //인스턴스변수 + 해당 인스턴스 변수를 세팅하는 생성자 필요
  }

  public String getDescription() {
    return beverage.getDescription() + ", 모카"; //이전 장식에 현 장식의 설명을 추가하는 식 ex) "다크로스트, 모카"
  }

  public double cost() {
    return beverage.cost() + .20; //이전 장식에 현 장식의 가격을 더하는 식 ex) .99 + .20 = 1.19$
  }
}
```

### 디자인 원칙 - OCP
- 개방폐쇄 원칙(Open-Closed Principle)
- 클래스는 확장에는 열려 있어야 하지만 변경에는 닫혀 있어야 한다.
- 데코레이터 패턴으로 OCP 원칙을 준수 할 수 있다.
- 주의 사항 : 무조건 OCP를 적용한다면 괜히 쓸데없는 일을 하며 시간을 낭비할 수 있으며, 필요 이상으로 복잡하고 이해하기 힘든 코드를 만들게 되는 부작용이 발생할 수 있다.
  - 가장 바뀔 가능성이 높은 부분을 중점적으로 살펴보고 OCP를 적용하는 방법이 가장 좋다.
