# CH08. 템플릿 메소드 패턴


## 템플릿 메소드 패턴 
- Template Method Pattern
- 알고리즘의 골격을 정의한다. 템플릿 메소드를 사용하면 알고리즘의 일부 단계를 서브클래스에서 구현할 수 잇으며, 알고리즘의 구조는 그대로 유지하면서 알고리즘의 특정 단계를 서브클래스에서 재정의할 수도 있다.

![image](https://github.com/user-attachments/assets/5a1b05f0-0c1c-4020-884f-12181a245adf)

### 템플릿 메소드 패턴 적용 코드
```
public abstract class CaffeineBeverage {

    final void prepareRecipe() {
        boilWater();
        brew(); //서브클래스에서 구현 유도
        pourInCup(); //서브클래스에서 구현 유도
        addCondiments();
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("물 끓이는 중");
    }

    void pourInCup() {
        System.out.println("컵에 따르는 중");
    }
}
```
- `final void prepareRecipe()`
  - 서브클래스가 변경하지 못하도록 final 키워드 사용
  -  템플릿 메소드는 각 단계를 순서대로 정의하는대 각 단계는 메소드로 표현된다. 
- `abstract` : 서브클래스별로 다른 내용은 서브클래스의 구현을 유도하고자 abstract 키워드 사용
- abstract class CaffeineBeverage 장점
  - CaffeineBeverage 클래스에서 작업을 처리한다. **알고리즘을 독점**한다.
  - CaffeineBeverage 덕분에 서브클래스에서 코드를 재사용할 수 있다.
  - 알고리즘이 한 군데에 모여 있으므로 한 부분만 고치면 된다.
  - 다른 음료도 쉽게 추가할 수 있는 프레임워크를 제공한다. 음료를 추가할 때 몇 가지 메소드만 더 만들면 된다.
  - CaffeineBeverage 클래스에 알고리즘 지식이 집중되어 있으며 일부 구현만 서브클래스에 의존한다.
 
### 템플릿 메소드 속 후크 사용하기
- **후크(hook)** 는 추상 클래스에서 선언되지만 기본적인 내용만 구현되어 있거나 아무 코드도 들어있지 않은 메소드이다.
```
public abstract class CaffeineBeverageWithHook {
    final void prepareRecipe() {
        boilWater();
        brew(); //서브클래스에서 구현 유도
        pourInCup(); //서브클래스에서 구현 유도
        addCondiments();
        if ( customerWantsCondiments() ) {
            addCondiments();
        }
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("물 끓이는 중");
    }

    void pourInCup() {
        System.out.println("컵에 따르는 중");
    }
    
    boolean customerWantsCondiments() { //이 메소드는 서브클래스에서 필요시 오버라이드 할 수 있음 
        return true;
    }
}
```
- `boolean customerWantsCondiments()` : 오버라이드 강제화를 하지 않았기 때문에 서브클래스에서 해당 Hook 메소드를 변경하고자 하는 경우에만 변경 가능

## 디자인원칙 : 할리우드 원칙
- (고수준 구성요소 왈:)먼저 연락하지 마세요. 저희가 연락 드리겠습니다.
- 저수준 구성 요소가 시스템에 접속할 수는 있지만 언제, 어떻게 그 구성요소를 사용할지는 고수준 구성 요소가 결정한다.

## 템플릿 메소드 패턴 사용 예시 : Arrays.sort 
```
public class Duck implements Comparable<Duck>{
    String name;
    int weight;

    public Duck(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name + " 체중: " + weight;
    }

    @Override
    public int compareTo(Duck object) { //무게가 더 적은 오리가 앞으로 가게끔 정렬 
        Duck otherDuck = object;
        if (this.weight < otherDuck.weight) { 
            return -1;
        } else if (this.weight == otherDuck.weight) {
            return 0;
        } else { // this.weight > otherDuck.weight
            return 1;
        }
    }
}
```
- `implements Comparable<Duck>` : 비교에 필요한 Comparable 인터페이스를 구현한다.
- `compareTo(Duck object)` : sort() 메소드에서 필요로하는 것, 다른 Duck 객체를 인자로 받아서 비교한다.
- 그 외 사용 예시
  - JFrame의 paint 메소드
    - JFrame 의 update() 알고리즘은 paint()를 호출한다.
    - JFrame 의 paint() 는 아무 일도 하지 않는다.
    - paint 메소드를 오버라이드해서 원하는 기능을 넣는다.
    - 그냥 후크 메소드이다.
  - AbstractList로 나만의 리스트 만들기
    - abstract 메소드인 get(), size() 메소드 구현해줘야 함
    - hook 메소드인 set() 메소드도 리스트를 수정할 수 있도록 구현한다.
  
## 핵심 정리 
- 템플릿 메소드는 알고리즘의 단계를 정의하며 일부 단계를 서브 클래스에서 구현하도록 할 수 있다.
- 템플릿 메소드 패턴은 코드 재사용에 큰 도움이 된다.
- 템플릿 메소드가 들어있는 추상 클래스는 구상 메소드, 추상 메소드, 후크를 정의할 수 있다.
- 추상 메소드는 서브클래스에서 구현한다.
- 후크는 추상 클래스에 들어있는 메소드로 아무 일도 하지 않거나 기본 행동만을 정의한다. 서브 클래스에서 후크를 오버라이드 할 수 있다.
- 할리우드 원칙에 의하면, 저수준 모듈을 언제 어떻게 호출할지는 고수준 모듈에서 결정하는 것이 좋다.
- 템플릿 메소드 패턴은 실전에서도 꽤 자주 쓰이지만 반드시 '교과서적인' 방식으로 적용되진 않는다.
- 전략 패턴과 템플릿 메소드 패턴은 모두 알고리즘을 캡슐화하는 패턴이지만 전략 패턴은 구성을, 템플릿 메소드 패턴은 상속을 사용한다.
- 팩토리 메소드 패턴은 특화된 템플릿 메소드 패턴이다. 
