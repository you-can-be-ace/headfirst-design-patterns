# CHAPTER.04 팩토리 패턴 

```
Pizza orderPizza(String type) {
  Pizza pizza;

  if (type.equals("cheese")) {
    pizza = new CheesePizza();
  } else if (type.equals("pepperoni") {
    pizza = new PepperoniPizza();
  } else if (type.equals("veggie") {
    pizza = new VeggiePizza();
  }

  pizza.prepare();
  pizza.bake();
  pizza.cut();
  pizza.box();

  return pizza;
}
```
- 해당코드에서 가장 문제가 되는 부분은 인스턴스를 만드는 구상 클래스를 선택하는 부분
- 메뉴를 변경하려면 해당 조건절을 계속해서 수정해야 함
- SimplePizzaFactory : 객체 생성을 처리하는 클래스를 **팩토리** 라고 부른다.

## 타입에 대한 피자 객체를 생성하는 SimplePizzaFactory
```
public class SimplePizzaFactory {

  public Pizza createPizza(String type) {
    Pizza pizza = null;
    
    if (type.equals("cheese")) {
      pizza = new CheesePizza();
    } else if (type.equals("pepperoni") {
      pizza = new PepperoniPizza();
    } else if (type.equals("veggie") {
      pizza = new VeggiePizza();
    }

    return pizza;
  }
}
```
- 피자 객체를 생성하는 곳이 orderPizza 한 군데가 아닌 경우라면, 한곳에 모아서 필요시 사용하면 된다.
- 새로운 피자 객체를 추가하고자 할 때 SimplePizzaFactory 한 곳만 수정하면 된다.
- 클라이언트 코드에서 객체 생성부분에 조건절이 사라지고 `pizza = factory.createPizza(type);` 와 같이 처리하면 된다.

## Simple Factory
- 팩토리에서 생산하는 구상 클래스는 인터페이스를 구현해야 하며 구상 클래스여야 한다.

## 피자 가게 프레임워크 - 서브 클래스에서 피자 결정 
```
public abstract class PizzaStore {

  public Pizza orderPizza(String type) {
    Pizza pizza;

    pizza = createPizza(type);

    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();

    return pizza;
  }

  abstract Pizza createPizza(String type);
}
```

- 재료 손질하고 굽고 자르고 포장하는 것은 동일하지만 피자 종류는 서브 클래스에서 결정되도록 처리 가능
- 슈퍼클래스 PizzaStore 에 있는 orderPizza() 메소드는 어떤 피자가 만들어지는지는 전혀 알 수 없다.
- `abstract Pizza createPizza(String type);`
  - 팩토리 메소드를 추상 메소드로 선언해서 서브 클래스가 객체 생성을 책임지도록 한다.
  - 팩토리 메소드는 특정 객체(Product)를 지턴하며, 그 객체는 보통 슈퍼클래스가 정의한 메소드 내에서 사용됨
  - 슈퍼클래스 내부 메소드는 구상 객체가 무엇인지 알 수 없다. -> 서브 클래스에서 type에 따라서 알아서 생성되기 때문
 
## 팩토리 메소드 패턴 살펴보기
- 생산자(Creator) 클래스
  - 추상 생산자 클래스 `PizzaStore`
  - 생산자 클래스에 추상 제품 클래스의 객체는 클래스의 서브클래스에 의해 만들어지기 때문에 어떤 구상 제품 클래스가 만들어질지 미리 알 수 없다.
  - 구상 생산자(concrete creator)
    - 필요 객체를 생성하는 `createPizza()` 가 팩토리 메소드 
- 제품(Product) 클래스
  - 팩토리는 제품을 생산한다.

 ![image](https://github.com/boboram/TIL/assets/14108487/aca2097e-31c8-466c-98d4-45a7726d503f)

## 팩토리 메소드 패턴의 정의 
- Factory Method Pattern
- 객체를 생성할 때 필요한 인터페이스를 만든다. 어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정한다. 팩토리 메소드 패턴을 사용하면 클래스 인스턴스 만드는 일을 서브클래스에게 맡기게 된다.
- `Product` - `ConcreteProduct`
  - 제품 클래스는 모두 똑같은 인터페이스를 구현해야 한다. 그래야 그 제품을 사용할 클래스에서 구상 클래스가 아닌 인터페이스의 레퍼런스로 객체를 참조할 수 있다.  
- `Creator` : Creator의 모든 서브클래스에서 factoryMethod() 추상 메소드를 구현해야 한다. 
  - `ConcreteCreator` : ConcreteCreator 는 실제로 제품을 생산하는 factoryMethod()를 구현한다.
  - 구상 클래스 인스턴스를 만드는 일은 ConcreteCreator가 책임진다. 실제 제품을 만드는 방법을 알고 있는 클래스는 ConcreteCreator 클래스 뿐이다.
 
## 디자인 원칙 : 의존성 뒤집기 원칙 (DIP)
- Dependency Inversion Principle
- 추상화된 것에 의존하게 만들고 구상 클래스에 의존하지 않게 만든다.
- 고수준 구성 요소(PizzaStore)가 저수준 구성 요소(피자 객체)에 의존하면 안 되며, 항상 추상화에 의존하게 만들어야 한다.
- DIP를 지키는 방법 : 항상 지켜야 하는 규칙이 아닌, 우리가 지향해야 하는 바라는 것을 잊지 말자. 
  - 변수에 구상 클래스의 레퍼런스를 저장하지 말자. : 팩토리를 써서 `new` 연산자 금지
  - 구상 클래스에서 유도된 클래스를 만들지 말자. : 인터페이스나 추상 클래스처럼 추상화된 것으로부터 클래스를 만들어야 한다.
  - 베이스 클래스에 이미 구현되어 있는 메소드를 오버라이드하지 말자. : 베이스 클래스에서 메소드를 정의할 때는 모든 서브클래스에서 공유가능한 것만 정의해야 한다.
 
## 객체마을 추상 원재료 팩토리 (인터페이스)
```
public interface PizzaIngredientFactory {

  public Dough createDough();
  public Sauce createSauce();
  public Cheese createCheese();
  public Veggies[] createVeggies();
  public Pepperoni createPepperoni();
  public Clams createClam();
}

public class CheesePizza extends Pizza {
  PizzaIngredientFactory ingredientFactory;

  public CheesePizza(PizzaIngredientFactory ingredientFactory) {
    this.ingredientFactory = ingredientFactory;
  }

  void prepare() {
    System.out.println("준비 중:" + name);
    dough = ingredientFactory.createDough();
    sauce = ingredientFactory.createSauce();
    cheese = ingredientFactory.createCheese();
  }
}
```
- `sauce = ingredientFactory.createSauce();`
  - `sauce` : Pizza 클래스에 있는 인스턴스 변수에 이 피자에서 사용할 특정 소스의 레퍼런스 대입
  - `ingredientFactory` : 우리가 사용하는 원재료 팩토리. Pizza 클래스는 원재료 팩토리가 맞기만 하면 어떤 팩토리를 쓰든 상관하지 않는다.
  - `createSauce` : createSauce() 메소드에서는 해당 지역에서 사용하는 소스 리턴
 
## 추상 팩토리 패턴의 정의 
- Abstract Factory Pattern
- 구상 클래스에 의존하지 않고도 서로 연관되거나 의존적인 객체로 이루어진 제품군을 생산하는 인터페이스를 제공한다. 구상 클래스는 서브클래스에서 만든다.

## 핵심 정리
- 팩토리를 쓰면 객체 생성을 캡슐화할 수 있다.
- 간단한 팩토리는 엄밀하게 말해서 디자인 패턴은 아니지만, 클라이언트와 구상 클래스를 분리하는 간단한 기법으로 활용할 수 있다.
- 팩토리 메소드 패턴은 상속을 활용한다. 객체 생성을 서브클래스에게 맡긴다. 서브클래스는 팩토리 메소드를 구현해서 객체를 생산한다.
- 추상 팩토리 패턴은 객체 구성을 활용한다. 팩토리 인터페이스에서 선언한 메소드에서 객체 생성이 구현된다.
- 모든 팩토리 패턴은 애플리케이션의 구상 클래스 의존성을 줄여줌으로써 느슨한 결합을 도와준다.
- 팩토리 메소드 패턴은 특정 클래스에서 인스턴스를 만드는 일을 서브클래스에게 넘긴다.
- 추상 팩토리 패턴은 구상 클래스에 직접 의존하지 않고도 서로 관련된 객체로 이루어진 제품군을 만드는 용도로 쓰인다.
- 의존성 뒤집기 원칙을 따르면 구상 형식 의존을 피하고 추상화를 지향할 수 있다.
- 팩토리는 구상 클래스가 아닌 추상 클래스와 인터페이스에 맞춰서 코딩할 수 있게 해 주는 강력한 기법이다. 
