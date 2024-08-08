# CH09. 반복자 패턴과 컴포지트 패턴

## 반복자 패턴(Iterator Pattern) 
- 컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법을 제공한다.
- 또한 각 항목에 일일이 접근할 수 있게 해 주는 기능을 집합체가 아닌 반복자 객체가 책임진다는 장점도 있다.
  - 그러면 집합체 인터페이스와 구현이 간단해지고, 각자에게 중요한 일만을 처리할 수 있게 된다.

### 교재 내 문제 사항 
- 루의 팬케이스 하우스 => 새로운 항목을 추가하기 쉽게 하기 위해 **ArrayList**를 사용
- 객체마을 식당 => 메뉴에 들어가는 항목의 최대 개수를 정해놓은 **배열**을 사용하여 메뉴를 구현
- 식당이 합병을 해야 하나 메뉴를 관리하는 주체가 다른 이슈 발생
- 메뉴를 출력해야 하는 함수에서 서로 다른 종류의 메뉴에 들어있는 항목에 일일이 접근하려면 서로 다른 순환문이 필요하고 ArrayList, 배열 외 다른 메뉴 항목을 추가한다면 또다른 순환문을 작업해야 한다.

### 반복자 패턴 구조
<img width="646" alt="image" src="https://github.com/user-attachments/assets/2ed50dbb-53b2-4e95-b4ad-04cce04e8064">

- Aggregate 인터페이스 : 공통된 인터페이스가 있으면 클라이어트는 매우 편리하게 작업을 처리할 수 있다. 클라이언트와 객체 컬렉션의 구현이 분리되기 때문
- Iterator 인터페이스
  - Iterator 인터페이스는 모든 반복자가 구현해야 하는 인터페이스를 제공하며, 컬렉션에 들어있는 원소에 돌아가면서 접근할 수 있게 해 주는 메소드를 제공한다.
  - 여기서는 java.util.Iterator 를 사용한다.
- ConcreteAggregate 클래스
  - ConcreteAggregate 에는 객체 컬렉션이 들어있으며, 그 안에 들어있는 컬렉션을 Iterator로 리턴하는 메소드를 구현한다.
  - 모든 ConcreteAggregate는 그 안에 있는 객체 컬렉션을 대상으로 돌아가면서 반복 작업을 처리할 수 있게 해 주는 ConcreteIterator 의 인스턴스를 만들 수 있어야 한다. 
- ConcreteIterator 클래스 : 반복 작업 중에 현재 위치를 관리하는 일을 맡는다.

### 반복자 패턴 적용하기 

#### 반복 캡슐화 
- 'breakfastItems' 순환문 : ArrayList의 size()와 get()메소드 사용
```
for (int i = 0; i < breakfastItems.size(); i++) {
	MenuItem menuItem = breakfastItems.get(i);
}
```
- 'lunchItems' 순환문 : 배열의 length 필드와 배열 첨자 사용
```
for (int i = 0; i < lunchItems.length(); i++) {
	MenuItem menuItem = breakfastItems[i];
}
```

#### Iterator 객체 만들기 
- ArrayList Iterator 적용
```
Iterator iterator = breakfastMenu.createIterator();

while (iterator.hasNext()) {
  MenuItem menuItem = iterator.next();
}
```
- 배열에 Iterator 적용
```
Iterator iterator = lunchMenu.createIterator();

while (iterator.hasNext()) {
    MenuItem menuItem = iterator.next();
}
```

#### 반복자 추가하기 
- Iterator 인터페이스 정의
```
public interface Iterator {
    boolean hasNext();
    MenuItem next();
}
```
- Iterator 구상클래스 정의
```
public class DinerMenuIterator implements Iterator {
    MenuItem[] items;
    int position = 0;
    
    public DinerMenuIterator(MenuItem[] items) {
      this.items = items;
    }
    
    public MenuItem next() {
      MenuItem menuItem = items[position];
      position = position + 1;
      return menuItem;
    }
    
    public boolean hasNext() {
    	if (position >= items.length || items[position] == null) {
        // 아직 더 돌아야 할 원소가 있고, 다음 항목이 널이면
        	return false;
        } else {
        	return true;
        }
    }
}
```

#### 반복자 사용하기 
```
public class DinerMenu {
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;
    
    // 생성자
    
    // addItem 메소드 호출
    
    // 내부 구조를 다 드러내는 단점이 있는 getMenuItems() 메소드 삭제
    
    public Iterator createIterator() {
      return new DinerMenuIterator(menuItems);
    }
    // 기타 메뉴 관련 메소드
}
```
-> PancakeHouseMenu와 DinerMenu의 인터페이스가 완전히 똑같음에도 아직 인터페이스가 통일되지 않음 

#### 반복자 패턴 | 인터페이스 개선하기 | java.util.Iterator 사용하기 
```
public Iterator<MenuItem> createIterator(){
    return menuItems.iterator(); //* 반복자를 직접 만드는 대신 menuItems ArrayList의 iterator() 메소드만 호출하면 된다. 
}
```

#### java.util.Iterator 적용하기 
```
import java.util.Iterator;

public class DinerMenuIterator implements Iterator<MenuItem> {
    MenuItem[] items;
    int position = 0;
    
    public DinerMenuIterator(MenuItem[] items) {
    	this.items = items;
    }
    
    public MenuItem next() {
    	//기타 코드
    }
    
    public boolean hasNext() {
    	//기타 코드
    }
    
    public void remove() {
    	throw new UnsupportedOperationException 
        ("메뉴 항목은 지우면 안됩니다.);
    }
}
```
#### Menu 인터페이스 통일 
```
public interface Menu {
  public Iterator<MenuItem> createIterator();
}
```
#### 종업원 코드 수정 
- 아래 코드 집중
  - AS-IS : `PancakeHouseMenu pancakeHouseMenu;`, TO-BE : `Menu pancakeHouseMenu;`
  - AS-IS : `DinerMenu dinerMenu`, TO-BE : `Menu dinerMenu;` 
```
import java.util.Iterator;

public class Waitress {
    Menu pancakeHouseMenu;
    Menu dinerMenu;
    
    public Waitress(Menu pancakehousemenu, Menu dinermenu) {
      // 구상 메뉴 클래스를 Menu 인터페이스로 변경
      this.pancakeHouseMenu = pancakeHouseMenu;
      this.dinerMenu = dinerMenu;
    }
    
    public void printMenu() {
        Iterator<MenuItem> pancakeIterator = pancakeHouseMenu.createIterator();
        Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();
        System.out.println("Menu\n---\n아침 식사");
        printMenu(pancakeIterator);
        System.out.println("\n점심 식사");
        printMenu(dinerIterator);
    }
    
    private void printMenu(Iterator iterator) {
    	while (iterator.hasNext()) {
        MenuItem menuItem = iterator.next();
        System.out.print(menuItem.getName() + ", ");
        System.out.print(menuItem.getPrice() + " -- ");
        System.out.println(menuItem.getDescription());
      }
    }
    // 기타 메소드
}
```

## 디자인원칙 : SRP(단일역할원칙)
- 어떤 클래스가 바귀는 이유는 하나뿐이어야 한다.
- SRP와 응집도(cohesion)은 밀접하게 연관되어져 있다.
  - 응집도가 높다는 것은 서로 연관된 기능이 묶여있다는 것을, 응집도가 낮다는 것은 서로 상관 없는 기능들이 묶여있다는 것을 뜻한다. 


## 컴포지트 패턴(Composite Pattern)
- 객체를 트리구조로 구성해서 부분-전체 계층구조를 구현한다. 컴포지트 패턴을 사용하면 클라이언트에서 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있다.
- 즉, 복합 객체와 개별 객체를 구분할 필요가 거의 없어진다.

### 컴포지트 패턴 구조
![image](https://github.com/user-attachments/assets/7d9243c1-5e12-444f-b549-f593adab18fc)
- Client
  - Componet 인터페이스를 사용해서 복합 객체 내의 객체들을 조작할 수 있다.
- Component
  - Component는 복합 객체 내에 들어있는 모든 객체의 인터페이스를 정의한다. 복합 노드와 잎에 관한 메소드까지 정의하는 것
  - add(), remove(), getChild()와 몇 가지 작업의 기본 행동을 정의할 수도 있다.
- Leaf
  - 잎에서는 add(), remove(), getChild() 같은 메소드가 전혀 쓸모가 없음에도 그 메소드를 상속 받아야 한다. (한 컴포넌트에 자식이 있을 수도 없을수도 있음)
  - 잎에는 자식 X
  - 잎에는 그 안에 들어있는 원소의 행동을 정의한다. Composite에서 지원하는 기능을 구현하면 된다.
- Composite
  - Composite에서 Leaf와 관련된 기능도 구현해야 한다. 그런 기능들이 복합 객체에게 별 쓸모가 없다면 예외를 던지는 방법으로 처리해도 된다.
  - Composite는 자식이 있는 구성 요소의 행동을 정의하고 자식 구성 요소를 저장하는 역할을 맡는다.

### 컴포지트 패턴 적용하기 
#### 메뉴 구성 요소 구현하기 -> abstract class MenuComponent
```
public abstract class MenuComponent
	
    public void add(MenuComponent menuComponent) {
    	throw new UnsupportedOperationException();
    }
    public void remove(MenuComponent menuComponent) {
    	throw new UnsupportedOperationException();
    }
    public void MenuComponent getChild(int i ){
    	throw new UnsupportedOperationException(); // 기본적으로 모두 UnsupportedOperationException을 던지도록 
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
    public boolean isVegetarain() {
    	throw new UnsupportedOperationException();
    }
    
    public void print() {
    	throw new InsupportedOperationException();
    }
}
```
#### 메뉴 항목 구현하기 
```
public class MenuItem extends MenuComponent {
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
    	System.out.print("  " + getName());
        if (isVegetarian()) {
        	System.out.print("(v)");
        }
        System.out.println(", " + getPrice());
        System.out.println("      -- " + getDescription());
    }
}
```

#### 메뉴 구현하기 
```
public class Menu extends MenuComponent {
    List<MenuComponent> menuComponents = new ArrayList<MenuComponent>(); // Menu에는 MenuComponent 형식의 자식을 몇 개든지 저장 가능 
    String name;
    String description;
    
    public Menu(String name, String description) {
    	this.name = name;
        this.description = description;
    }
    
    public void add(MenuComponent menuComponent) {
    	menuComponents.add(menuComponent);
    }
    
    public void remove(MenuComponent menuComponent) {
    	menuComponents.remove(menuComponent);
    }
    
    public MenuComponent getChild(int i) {
    	return menuComponents.get(i)
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
        System.out.println("---------------");
        
        for (MenuComponent menuComponent : menuComponents) {
        	menuComponent.print();
            // 구성요소를 대상으로 반복작업 처리
            // Menu, MenuItem에서 모두 print()를 구현하므로
            // print()만 호출하고 각 클래스에 맡겨두면 된다.
        }
    }
}
```
#### 종업원 코드에 컴포지트 적용하기
```
public class Waitress {
    MenuComponent allmenus;
    
    public Waitress(menuComponent allmenus) { 
    	this.allmenus = allMenus;
    }
    
    public void printMenu() {
    	allMenus.print(); 
    }
}
```
#### 메뉴 코드 테스트
```
public class MenuTestDrive(){
    public static void main(String args[]) {
    	MenuComponent pancakehouseMenu = new Menu("팬케이크 하우스 메뉴", "아침 메뉴");
        MenuComponent dinerMenu = new Menu("객체마을 식당 메뉴", "점심 메뉴");
        MenuComponent cafeMenu = new Menu("카페 메뉴", "저녁 메뉴");
        MenuComponent desertMenu =new Menu("디저트 메뉴", "디저트를 즐겨 보세요");
            
        MenuComponent allMenus = new Menu("전체 메뉴", "전체 메뉴");

        // 최상위 메뉴
        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);
        
        // 메뉴 항목을 추가하는 부분
        dinerMenu.add(new MenuItem("파스타", "마리나라 소스 스파게티, 효모빵도 드립니다.", true, 3.79));            
        dinerMenu.add(desertMenu); //하위 항목인 디저트 추가 
        desertMenu.add(new MenuItem("애플파이", "바삭바삭한 크러스트에 바닐라 아이스크림이 얹혀진 애플 파이", "true", 1.56));
            
        // 메뉴 항목을 추가하는 부분
        
        Waitress waitress = new Waitress(allmenus);
        
        waitress.printMenu();
    }
}
```


### 핵심 정리
- 반복자를 사용하면 내부 구조를 드러내지 않으면서도 클라이언트가 컬렉션 안에 들어있는 모든 원소에 접근하도록 할 수 있다.
- 반복자 패턴을 사용하면 집합체를 대상으로 하는 반복 작업을 별도의 객체로 캡슐화할 수 있다.
- 반복자 패턴을 사용하면 컬렉션에 있는 모든 데이터를 대상으로 반복 작업을 하는 역할을 컬렉션에서 분리할 수 있다.
- 반복자 패턴을 쓰면 반복 작업에 똑같은 인터페이스를 적용할 수 있으므로 집합체에 있는 객체를 활용하는 코드를 만들 때 다형성을 활용할 수 있다.
- 한 클래스에는 될 수 있으면 한 가지 역할만 부여하는 것이 좋다.
- 컴포지트 패턴은 개별 객체와 복합 객체를 모두 담아 둘 수 있는 구조를 제공한다.
- 컴포지트 패턴을 사용하면 클라이언트가 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있다.
- 복합 구조에 들어있는 것을 구성요소라고 부른다. 구성 요소에는 복합 객체와 잎 객체가 있다.
- 컴포지트 패턴을 적용할 때는 여러 가지 장단점을 고려해야 한다. 상황에 따라 투명성과 안정성 사이에서 적절한 균형을 찾아야 한다. 
