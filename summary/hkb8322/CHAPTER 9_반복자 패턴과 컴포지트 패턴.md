# 반복자 패턴

## 정의

- 컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법 제공

## 구성 요소

- `Aggregate` : 반복자를 사용할 대상 객체 인터페이스
- `ConcreteAggregate` : 객체 인터페이스 구현체 (반복자 구현체 사용)
- `Iterator` : 반복자 객체 인터페이스 (책에서는 `java.util.Iterator` 사용)
- `ConcreteIterator` : 반복자 객체 인터페이스 구현체

## 장점

- 컬렉션 객체 내 접근하는 방식을 통일하여 모든 집합체에 사용할 수 있는 다형적 코드 구현 가능
- 내부 구현 방법을 외부로 노출하지 않으면서 집합체 내 항목에 접근 가능
- 각 항목에 접근하는 기능을 집합체가 아닌 반복자 객체가 책임짐
- 집합체 인터페이스와 구현이 간단해지고 필요한 로직만 구현 가능

## 예제

```java
public interface Menu {
	public Iterator<MenuItem> createIterator();
    // 클라이언트에서 메뉴에 들어있는 항목의 반복자를 
    // 획득할 수 있게 해주는 간단한 인터페이스
}
```

```java
public class PancakeHouseMenu implements Menu {

  List<MenuItem> menuItems;

  public PancakeHouseMenu() {
    menuItems = new ArrayList<MenuItem>();

    addItem("K&B 팬케이크 세트", "스크램블 에그와 토스트가 곁들여진 팬케이크", true, 2.99);
    addItem("레귤려 팬케이크 세트", "달걀 프라이와 소시지가 곁들여진 팬케이크", false, 2.99);
    addItem("블루베리 팬케이크 세트", "신선한 블루베리와 블루베리 시럽으로 만든 팬케이크", true, 3.49);
    addItem("와플", "취향에 따라 블루베리나 딸기를 얹을 수 있는 와플", true, 3.59);
  }

  public void addItem(String name, String description, boolean vegetarian, double price) {
    var menuItem = new MenuItem(name, description, vegetarian, price);
    menuItems.add(menuItem);
  }

  @Override
  public Iterator createIterator() {
    return menuItems.iterator();
  }

}
```

```java

public class Waitress {
		Menu pancakeHouseMenu;
    Menu dinerMenu;
    Menu cafeMenu;
    
    public Waitress(Menu pancakeHouseMenu, Menu dinerMenu, Menu cafeMenu) {
    	this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu = dinerMenu;
        this.cafeMenu = cafeMenu;
    }
    
    public void printMenu() {
   		Iterator<MenuItem> pancakeIterator = pancakehouseMenu.createIterator();
        Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();
        Iterator<MenuItem> cafeIterator = cafeMenu.createIterator();
   		
        System.out.println("메뉴\n----\n아침 메뉴");
        printMenu(pancakeIterator);
        System.out.println("\n점심 메뉴");
        printMenu(dinerIterator);
        System.out.println("\n저녁 메뉴");
        printMenu(cafeIterator);
    }
    
    private void printMenu(Iterator iterator) {
    	while (iterator.hasNext()) {
        	MenuItem menuItem = iterator.next();
            System.out.print(menuItem.getName() + ", ");;
            System.out.print(menuItem.getPrice() + " -- ");
            System.out.println(menuItem.getDescription());
        }
    }
}
```

# 단일 역할 원칙(SRP)

> 어떤 클래스가 바뀌는 이유는 하나뿐이어야 한다.
> 
- 하나의 역할은 하나의 클래스에서만 맡아야 함
- SRP와 응집도는 서로 밀접하게 연관되어 있음
    - 이 원칙을 잘 따르는 클래스는 응집도가 높고 관리하기 쉬움

⇒ 집합체에서 내부 컬렉션 관련 기능과 반복자용 메소드 관련 기능을 구현할 경우,

클래스의 변경될 이유는 두 가지가 됨

# 반복자의 종류

## 내부 반복자

- 반복자 자신이 반복 작업을 제어
- 클라이언트가 반복자에게 작업을 넘겨주는 형태
- 클라이언트가 작업을 제어할 수 없어 유연성이 떨어짐

## 외부 반복자

- 클라이언트가 반복 작업을 제어

# `Iterable` 과 `Iterator`

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/31151178-2abf-4285-b27d-d78331d3f581/4cf574b8-6c7b-4e20-b6d3-0ddae2ea366f/Untitled.png)

```java
for (MenuItem item: menu) {
	System.out.print(menuItem.getname() +", ");
    System.out.print(menuItem.getPrice() + " -- ");
    System.out.println(menuItem.getDescription());
}
```

- 컬렉션 프레임워크는 `Iterable` 인터페이스를 구현
- `Collection`  인터페이스의 `iterator()` 메소드를 통하여 반복자 조회 가능
- `Iterator` 인터페이스에 다른 기능을 추가하고 싶다면 `Iterator` 인터페이스를 확장하여 사용
- 자바에서는 향상된 `for`문 지원
- 배열은 `Iterable` 구현하지 않음

# OCP 위배 코드 개선

## AS-IS

```java
  public void printMenu() {
    var pancakeIterator = pancakeHouseMenu.createIterator();
    var dinerIterator = dinerMenu.createIterator();

    System.out.println("메뉴\n---\n아침 메뉴");
    printMenu(pancakeIterator);
    System.out.println("\n점심 메뉴");
    printMenu(dinerIterator);
  }
```

- 새로운 메뉴가 추가될 때마다 종업원에 코드를 추가해야 함 **⇒ OCP위배**

## TO-BE

```java
public void printMenu() {
	Iterator<Menu> menuIterator = menus.iterator();
	while(menuIterator.hasNext()) {
		Menu menu = menuIterator.next();
		printMenu(menu.createIterator());
	}
}

void printMenu(Iterator iterator) {
  while (iterator.hasNext()) {
    var menuItem = iterator.next();
    System.out.print(menuItem.getName() + ", ");
    System.out.print(menuItem.getPrice() + " -- ");
    System.out.println(menuItem.getDescription());
  }
}
```

# 컴포지트 패턴

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/31151178-2abf-4285-b27d-d78331d3f581/cf676f3e-1225-48a6-a3eb-501c30c14082/Untitled.png)

## 정의

- 객체를 트리 구조로 구성하여 부분-전체 계층 구조 구현
- 클라이언트에서 개별 객체와 복합 객체를 동일한 방법으로 다룰 수 있음

## 구성 요소

- `Component` : 모든 객체가 구현할 인터페이스
- `Leaf` : 잎 객체 (개별 객체)
- `Composite` : 복합 객체

## 특징

- SRP 대신 투명성을 확보하는 패턴
- 아래 두 기능이 포함되어 있어 안정성은 다소 떨어짐
    - 계층 구조를 관리하는 책임
    - 컴포넌트를 관리하는 책임

<aside>
💡 **투명성이란?**
클라이언트가 개별 객체와 복합 객체를 구분하지 않고 동일한 방식으로 사용할 수 있게 하는 것

</aside>

## 사용 시 고려사항

1. 트리 내 순회를 위해 자식 내 부모 노드의 포인터(참조) 추가
2. 자식 노드의 순서
3. 계층 구조에서의 순회
4. 전체 노드 순회에 많은 비용 발생 시 캐싱 고려

## 예제

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/31151178-2abf-4285-b27d-d78331d3f581/c4a56e14-cc3f-46fb-b379-c904764f94ed/Untitled.png)

- 예제에서는 `MenuComponent` 를 추상 클래스로 구현
- 모든 구성 요소가 `MenuComponent` 인터페이스 구현

```java
public class Waitress {

  **MenuComponent allMenus;**

  public Waitress(
  
  
  **allMenus**) {
    this.allMenus = allMenus;
  }

  **public void printMenu() {
    allMenus.print();
  }**

}
```

```java
/**
 * leaf, node 역할이 달라 모든 메소드에 알맞는 기본 메소드를 구현하기 불가능하므로 자기 역할에 맞지 않는 상황을 기준으로 예외를 던지는 코드를 기본 구현으로 제공한다.
 */
public abstract class MenuComponent {

  public void add(MenuComponent menuComponent) {
    throw new UnsupportedOperationException();
  }

  public void remove(MenuComponent menuComponent) {
    throw new UnsupportedOperationException();
  }

  public MenuComponent getChild(int position) {
    throw new UnsupportedOperationException();
  }

  public String getName() {
    throw new UnsupportedOperationException();
  }

  public String getDescription() {
    throw new UnsupportedOperationException();
  }

  public double getPrice() {
    throw new UnsupportedOperationException();
  }

  public boolean isVegetarian() {
    throw new UnsupportedOperationException();
  }

  public void print() {
    throw new UnsupportedOperationException();
  }

}
```

```java
public class MenuCategory **extends MenuComponent** {

  **List<MenuComponent> menuComponents = new ArrayList<>();**
  String name;
  String description;

  public MenuCategory(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void add(MenuComponent menuComponent) {
    menuComponents.add(menuComponent);
  }

  public void remove(MenuComponent menuComponent) {
    menuComponents.remove(menuComponent);
  }

  public MenuComponent getChild(int index) {
    return menuComponents.get(index);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void print() {
    System.out.print("\n" + getName());
    System.out.println(", " + getDescription());
    System.out.println("---------------------------");

    **for(MenuComponent menuComponent : menuComponents) {
      menuComponent.print();
    }**
  }

}
```

```java
public class MenuItem **extends MenuComponent** {

  String name;
  String description;
  boolean vegetarian;
  double price;

  public MenuItem(String name, String description, boolean vegetarian, double price) {
    this.name = name;
    this.description = description;
    this.vegetarian = vegetarian;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getPrice() {
    return price;
  }

  public boolean isVegetarian() {
    return vegetarian;
  }

  public void print() {
    System.out.print(" " + getName());
    if (isVegetarian()) {
      System.out.print("(v)");
    }
    System.out.println(" " + getPrice());
    System.out.println("   -- " + getDescription());
  }

}
```
