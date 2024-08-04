# 템플릿 메소드 패턴

## 정의

- 알고리즘의 골격을 정의
- 템플릿 메소드를 사용하면 알고리즘의 일부 단계를 서브 클래스에서 구현할 수 있음
- 알고리즘의 구조는 그대로 유지하면서 특정 단계를 서브 클래스에서 재정의

## 장점

- 추상 클래스에서 작업을 처리하여 알고리즘 독점
- 추상 클래스의 로직을 서브 클래스에서 재사용 가능
- 알고리즘이 한 군데에 모여 있어 수정 필요 시 한 부분만 수정하면 됨
- 추상 클래스에 알고리즘이 집중되어 있어 일부 구현만 서브 클래스에 의존

## 예제

```java
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

```java
public class Coffee extends CaffeineBeverage{

    @Override
    void brew() {
        System.out.println("필터로 커피를 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕과 우유를 추가하는 중");
    }
}
```

```java
public class CaffeineExample {
    public static void main(String[] args) {

        Tea tea = new Tea();
        Coffee coffee = new Coffee();

        tea.prepareRecipe();

        coffee.prepareRecipe();
    }
}
```

# 후크(Hook)

```java
public abstract class CaffeineBeverageWithHook {

    final void prepareRecipe() {
        boilWater();
        brew(); //서브클래스에서 구현 유도
        pourInCup(); //서브클래스에서 구현 유도
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

    boolean customerWantsCondiments() { 
		    //이 메소드는 서브클래스에서 필요시 오버라이드 할 수 있음
        return true;
    }
}
```

```java
public class CoffeeWithHook extends CaffeineBeverageWithHook {

    @Override
    void brew() {
        System.out.println("필터로 커피를 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕과 우유를 추가하는 중");
    }

    @Override
    boolean customerWantsCondiments() { //후크를 오버라이드해서 원하는 기능을 넣는다.
        String answer = getUserInput();

        // 첨가물 추가 여부를 고객에게 물어보고 고객이 입력한 내용에 따라 boolean 값 리턴
        if (answer.toLowerCase().startsWith("y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 고객에게 우유와 설탕을 추가할지 말지를 물어보고, 명령줄로 추가 여부를 입력 받는다.
     * @return
     */
    private String getUserInput() {
        String answer = null;

        System.out.print("커피에 설탕과 우유를 넣어 드릴까요? (y/n)");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = in.readLine();
        } catch (IOException e) {
            System.out.println("IO 오류");
        }

        if (answer == null) {
            return "no";
        }

        return answer;
    }
}
```

- 추상 클래스에서 선언되지만 기본적인 내용만 구현되어 있거나 아무 코드도 들어있지 않은 메소드
- 알고리즘에서 필수적이지 않은 부분을 서브 클래스에서 구현하도록 만들고 싶을 때 사용
- 추상 클래스에서 진행되는 작업을 서브 클래스가 결정하게 하는 기능을 부여하는 용도로도 사용 가능

# 추상 메소드 🆚 후크

- 서브 클래스가 알고리즘의 특정 단계를 제공해야만 한다면 추상 메소드
    - 추상 메소드가 너무 많아지면 구현이 어려워지므로 유연하게 적절히 나누는 것이 필요
- 알고리즘의 특정 단계가 선택적으로 적용된다면 후크

# 할리우드 원칙

> 먼저 연락하지 마세요. 저희가 연락 드리겠습니다.
> 
- 저수준 구성 요소가 어떻게 사용될지는 고수준 구성 요소가 결정
- 의존성 부패를 방지하기 위함

<aside>
💡 순환 참조와 같이 의존성이 복잡하게 꼬여 있는 상황을 **의존성이 부패**했다고 부름

</aside>

# 할리우드 원칙 🆚 의존성 뒤집기

## 공통점

- 객체를 분리한다는 하나의 목표를 공유

## 차이점

### 의존성 뒤집기

- 구상 클래스 사용을 줄이고 추상화된 것을 사용해야 한다는 원칙

### 할리우드 원칙

- 저수준 구성 요소가 컴퓨테이션에 참여하면서도 저수준 구성 요소와 고수준 계층 간 의존을 없애도록 프레임워크나 구성 요소를 구축하는 기법
- 저수준 구성 요소를 다양하게 사용할 수 있으면서도, 다른 클래스가 구성 요소에 너무 의존하지 안게 만들어주는 기법 제공

# 자바 API 속 템플릿 메소드 패턴

## 1️⃣ 배열 정렬

1. 정렬할 배열을 받음
    
    ```java
    Duck[] ducks = {new Duck("Daffy", 8), ...};
    ```
    
2. `Arrays` 클래스에 있는 `sort()` 템플릿 메소드 호출하여 인자 전달
    
    ```java
    Arrays.sort(ducks);
    ```
    
3. 전체 목록이 정렬될 때까지 두 항목을 하나씩 비교
`sort()` 메소드는 비교 시 `compareTo()` 메소드에 의존
    
    ```java
    ducks[0].compareTo(ducks[1]);
    ```
    
    ```java
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
    
4. 비교한 결과 순서가 맞지 않으면 `Arrays` 의 `swap()` 구상 메소드 사용하여 순서 변경
    
    ```java
    swap()
    ```
    
5. `sort()` 메소드는 배열이 완전히 정렬될 때까지 3~4번 작업 반복

⇒ 완전한 템플릿 메소드는 아니지만 `sort()` 메소드 구현 자체는 템플릿 메소드 패턴의 기본을 따름

## 2️⃣ JFrame

```java
public class MyFrame extends JFrame {

    public MyFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setVisible(true);
    }

    /**
     * JFrame 의 update() 알고리즘은 paint()를 호출한다.
     * paint 메소드를 오버라이드해서 원하는 기능을 넣는다.
     * JFrame 의 paint() 는 아무 일도 하지 않는다.
     * 그냥 후크 메소드이다.
     * @param graphics the specified Graphics window
     */
    public void paint(Graphics graphics) {
        super.paint(graphics);
        String msg = "내가 최고!!!!!!!!!!!!";
        graphics.drawString(msg, 100, 100);
    }

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame("Head First Design Patterns");
    }
}
```

- `JFrame` 의 `update()` 알고리즘은 `paint()`를 호출
- 기본적으로 `paint()` 는 아무 일도 하지 않지만 `JFrame`을 상속 받아 이를 구현할 수 있음

## 3️⃣ AbstractList

```java
public class MyStringList extends AbstractList<String> {
    private String[] myList;

    MyStringList(String[] strings) {
        myList = strings;
    }

    @Override
    public String get(int index) {
        return myList[index];
    }

    @Override
    public int size() {
        return myList.length;
    }

    public String set(int index, String item) {
        System.out.println("set 호출");
        String oldString = myList[index];
        myList[index] = item;
        return oldString;
    }
}
```

- `ArrayList`, `LinkedList` 와 같은 컬렉션은 리스트에서 필요한 기능을 구현해주는 `AbstractList` 클래스를 확장
- 확장을 통해 기본 기능은 그대로 상속 받고 추상 메소드만 서브 클래스에서 구현
