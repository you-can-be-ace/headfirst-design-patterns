# 객체 직접 생성의 한계

- 클라이언트가 구상 클래스에 의존하게 됨
- 다형성을 사용하고자 할 경우 클라이언트에 각 구상 클래스를 사용하기 위한 분기문이 추가됨
- OCP 위반

# 간단한 팩토리(Simple Factory)
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/356256e0-51a7-4ad3-8986-aa37a7ce69fd)

```java
public class SimplePizzaFactory {
    public Pizza createPizza(String type) {
        Pizza pizza;
        
        if (type.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (type.equals("greek")) {
            pizza = new GreekPizza();
        } else if (type.equals("PepperoniPizza")) {
            pizza = new PepperoniPizza();
        } else {
            pizza = new DefaultPizza();
        }
        return pizza;
    }
}
```

- 디자인 패턴이라기 보다는 프로그래밍에서 자주 쓰이는 관용구에 가까움
- 디자인 패턴이 아님

# 팩토리 메소드 패턴

- 객체를 생성할 때 필요한 인터페이스를 만들고, 어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정
- 서브클래스에서 어떤 클래스를 만들지 결정함으로써 객체 생성을 캡슐화
- 생산자 클래스가 하나여도 제품 생산과 사용하는 부분을 분리할 수 있는 측면에서 유용함
    - 생산자 클래스가 생성하는 제품 객체의 종류가 여러 가지가 아니더라도 마찬가지임

## 생산자 클래스
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/94d8acad-2926-4268-98a3-3f181d7f331b)

```java
abstract Product factoryMethod(String type); // 팩토리 메소드 인터페이스 제공
```

```java
public class NYStylePizzaStore extends PizzaStore {

    Pizza createPizza(String item) {
        if (item.equals("cheese")) {
            return new NYStyleCheesePizza();
        } else if (item.equals("veggie")) {
            return new NYStyleVeggiePizza();
        } else if (item.equals("clam")) {
            return new NYStyleClamPizza();
        } else if (item.equals("pepperoni")) {
            return new NYStylePepperoniPizza();
        } else return null;
    }
}
```

- 팩토리 메소드와 생산자 클래스는 반드시 추상으로 선언해야 하는 부분은 아님

## 제품 클래스
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/b1b14d32-1b45-468e-9bc1-01987d9baba7)

# ⭐ 핵심 디자인 원칙

- DIP : 추상화된 것에 의존하게 하고 구상 클래스에 의존하지 않게 함

## DIP 준수하기

- 변수에 구상 클래스의 레퍼런스를 저장하지 않기 
**⇒ 팩토리를 사용하여 구상 클래스 직접 사용 방지**
- 구상 클래스에서 유도된 클래스를 만들지 않기
- 베이스 클래스에 이미 구현되어 있는 메소드 오버라이드 하지 않기

# 추상 팩토리 패턴
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/bdbc6395-8c00-4623-b306-f1f7cdf03411)

```java
public interface PizzaIngredientFactory {

    public Dough createDough();

    public Sauce createSauce();

    public Cheese createCheese();

    public Veggies[] createVeggies();

    public Pepperoni createPepperoni();

    public Clams createClam();
}
```

- 구상 클래스에 의존하지 않고도 서로 연관되거나 의존적인 객체로 이루어진 제품군을 생산하는 인터페이스 제공
- 구상 클래스는 서브 클래스에서 생성
- 클라이언트와 팩토리에서 생산되는 제품 분리 가능

# 팩토리 메소드 패턴 🆚 추상 팩토리 패턴

## 공통점

- 애플리케이션을 특정 구현으로부터 분리하여 객체 생성을 캡슐화
- 애플리케이션의 결합도를 낮추고 특정 구현에 덜 의존하도록 함

## 차이점

### 팩토리 메소드 패턴

- 상속을 통한 객체 생성
    - 상속을 통해 서브 클래스에서 객체 생성
- 클라이언트는 자신이 사용할 추상 형식만 알면 됨
- 서브 클래스에서 하나의 제품만을 생성

### 추상 팩토리 패턴

- 연관된 여러 제품군을 만드는 추상 형식 제공
- 제품군이 생산되는 방법은 서브 클래스에서 제공
- 새로운 제품 추가 필요 시 인터페이스 변경 필요
- 팩토리 메소드로 구상 팩토리를 구현할 때도 있음

# 📝 정리

- 팩토리를 사용하면 객체 생성을 캡슐화할 수 있음
- 간단한 팩토리는 디자인 패턴은 아니지만 클라이언트와 구상 클래스를 분리 가능
- 팩토리 메소드 패턴은 상속을 활용
    - 객체 생성을 서브 클래스에 맡김
    - 서브 클래스는 팩토리 메소드를 구현하여 객체 생성
- 추상 팩토리 패턴은 객체 구성을 활용
    - 팩토리 인터페이스에서 선언한 메소드에서 객체 생성 구현
    - 구상 클래스에 직접 의존하지 않고도 서로 관련된 객체로 이루어진 제품군을 이용하는 용도로 쓰임
- 모든 팩토리 패턴은 구상 클래스 의존성을 낮춰 느슨한 결합 추구
- DIP를 통해 추상화 지향 가능
- 팩토리는 추상 클래스와 인터페이스에 맞춰 코딩할 수 있게 하는 강력한 기법
