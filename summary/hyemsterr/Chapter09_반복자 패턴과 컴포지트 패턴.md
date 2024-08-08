# 반복자(Iterator) 패턴

<aside>
💡 **컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법을 제공하는 패턴**

</aside>

```java
// ArrayList
public Iterator createIterator() {
	return menuItems.iterator();
}

// 배열 (배열은 iterator가 기본으로 제공되지 않기 때문에 커스터마이징 클래스를 작성하였다.)
public Iterator createIterator() {
  return new DinerMenuIterator(menuItems);
}

// HashMap
public Iterator<MenuItem> createIterator() {
  return menuItems.values().iterator();
}
```

```java
// 호출자
public void printMenu() {
  var pancakeIterator = pancakeHouseMenu.createIterator();
  var dinerIterator = dinerMenu.createIterator();
  var cafeIterator = cafeMenu.createIterator();

  printMenu(pancakeIterator);
  printMenu(dinerIterator);
  printMenu(cafeIterator);
}

private void printMenu(Iterator iterator) {
  while (iterator.hasNext()) {
    var menuItem = (MenuItem) iterator.next();
    System.out.print(menuItem.getName() + ", ");
    System.out.print(menuItem.getPrice() + " -- ");
    System.out.println(menuItem.getDescription());
  }
}
```

- **DinerMenuIterator**
    
    ```java
    public class DinerMenuIterator implements Iterator {
    
      MenuItem[] items;
      int position = 0;
    
      public DinerMenuIterator(MenuItem[] items) {
        this.items = items;
      }
    
      @Override
      public boolean hasNext() {
        if (position >= items.length || items[position] == null) {
          return false;
        }
    
        return true;
      }
    
      @Override
      public MenuItem next() {
        var item = items[position];
        position++;
        return item;
      }
    
      @Override
      public void remove() {
        throw new UnsupportedOperationException("메뉴 항목은 지우면 안됩니다.");
      }
    }
    ```
    

**java.util.Iterator**

- iterator를 구현해서 ArrayList용, 배열용, HashMap용 메소드를 재작성하였다.
- 호출자에서는 Iterator 타입으로 객체에 접근한다. (구현보다는 인터페이스에 맞춰서 프로그래밍)

### 🧐 언제 사용할까?

- 다양한 모양(List, 배열, Map 등)의 컬렉션들을 하나의 방법으로 처리하고 싶을 때
- 컬렉션에 접근하는 로직을 커스터마이징 하고 싶을 때
- 컬렉션에 접근하는 로직과 비즈니스 로직을 분리하고 싶을 때

### 🤩 어떤 점이 좋을까?

- **컬렉션의 내부 구조가 캡슐화된다.**
    - 집합체 내에서 어떤 식으로 일이 처리되는지 모르는 상태에서 모든 항목을 대상으로 반복 작업을 수행할 수 있다.
- **다형성이 향상된다.**
    - 컬렉션 객체 안에 들어있는 모든 항목에 접근하는 방식이 통일되어 있어 다형적인 코드를 만들 수 있다.
- **책임이 명확히 분리된다.**
    - 컬렉션에 접근하는 작업을 Iterator 객체가 맡게 되므로, 집합체의 인터페이스와 구현이 간단해진다.

### ✅ 디자인 원칙 - 단일 역할 원칙

> ***어떤 클래스가 바뀌는 이유는 하나뿐이어야 한다.***
> 
- 어떤 클래스에서 맡고 있는 역할이 2개 이상이면, 변경 발생 시 2가지 이상의 부분에 영향을 미친다.
- 이러한 이유로 하나의 클래스는 하나의 역할만 수행해야한다.

# 컴포지트 패턴

<aside>
💡 **객체를 트리구조로 구성해서 부분-전체 계층구조를 구현하는 패턴**

</aside>

```java
// leaf와 복합 노드에서 동일하게 사용되는 인터페이스
public abstract class MenuComponent {
  public void add(MenuComponent menuComponent) {}
  public void remove(MenuComponent menuComponent) {}
  public MenuComponent getChild(int position) {}
  public void print() {}
}
```

```java
// MenuComponent 클래스를 상속받아 필요한 메소드만 오버라이딩한다.
public class MenuCategory extends MenuComponent { ... }
public class MenuItem extends MenuComponent { ... }
```

```java
// 객체 트리 구조를 생성한다.
public class MenuTestDrive {
  public static void main(String[] args) {
    var pancakeHouseMenu = new MenuCategory("팬케이크 하우스 메뉴", "아침 메뉴");
    var dinerMenu = new MenuCategory("객체마을 식당 메뉴", "점심 메뉴");
    var cafeMenu = new MenuCategory("카페 메뉴", "저녁 메뉴");
    var dessertMenu = new MenuCategory("디저트 메뉴", "디저트를 즐겨보세요");

    var allMenus = new MenuCategory("전체 메뉴", "전체 메뉴");
    allMenus.add(pancakeHouseMenu);
    allMenus.add(dinerMenu);
    allMenus.add(cafeMenu);

    //메뉴 항목을 추가하는 부분
    pancakeHouseMenu.add(new MenuItem("K&B 팬케이크 세트", "스크램블 에그와 토스트가 곁들여진 팬케이크", true, 2.99));
    pancakeHouseMenu.add(new MenuItem("레귤려 팬케이크 세트", "달걀 프라이와 소시지가 곁들여진 팬케이크", false, 2.99));
    pancakeHouseMenu.add(new MenuItem("블루베리 팬케이크 세트", "신선한 블루베리와 블루베리 시럽으로 만든 팬케이크", true, 3.49));
    pancakeHouseMenu.add(new MenuItem("와플", "취향에 따라 블루베리나 딸기를 얹을 수 있는 와플", true, 3.59));

    dinerMenu.add(new MenuItem("채식주의자용 BLT", "통밀 위에 콩고기 베이컨, 상추, 토마토를 얹은 메뉴", true, 2.99));
    dinerMenu.add(new MenuItem("BLT", "통밀 위에 베이컨, 상추, 토마토를 얹은 메뉴", false, 2.99));
    dinerMenu.add(new MenuItem("오늘의 스프", "감자 샐러드를 곁들인 오늘의 스프", false, 3.29));
    dinerMenu.add(new MenuItem("핫도그", "사워크라우트, 갖은 양념, 양파, 치즈가 곁들여진 핫도그", false, 3.05));
    dinerMenu.add(new MenuItem("파스타", "마리나라 소스 스파게티, 효모빵도 드립니다", true, 3.89));

    cafeMenu.add(new MenuItem("베지 버거와 에어 프라이", "통밀빵, 상추, 토마토, 감자 튀김이 첨가된 베지 버거", true, 3.99));
    cafeMenu.add(new MenuItem("오늘의 스프", "샐러드가 곁들여진 오늘의 스프", false, 3.69));
    cafeMenu.add(new MenuItem("부리토", "통 핀토콩과 살사, 구아카몰이 곁들여진 푸짐한 부리토", true, 4.29));

    dinerMenu.add(dessertMenu);
    dessertMenu.add(new MenuItem("애플 파이", "바삭바삭한 크러스트에 바닐라 아이스크림이 얹혀 있는 애플 파이", true, 1.59));

    var waitress = new Waitress(allMenus);
    waitress.printMenu();
  }
}
```

- allMenus - **1 Depth**
- pencakeHouseMenu, dinerMenu, cafeMenu - **2 Depth**
- dessertMenu - **3 Depth**

### 🧐 언제 사용할까?

- 객체의 트리 구조를 표현해야 할 때
- 단일 객체와 복합 객체를 동일하게 다루고 싶을 때

### 🤩 어떤 점이 좋을까?

- 객체의 구성과 개별 객체를 노드로 가지는 트리 형태의 객체 구조를 만들 수 있다.
- 메뉴와 항목을 같은 구조에 넣어 **부분-전체 계층 구조(part-whole hierarchy)**를 생성할 수 있다,
    - 부분-전체 계층 구조란? 부분들이 계층을 이루고 있지만, 모든 부분을 묶어서 전체로 다룰 수 있는 구조
- 간단한 코드 구조를 가지고 동일한 작업을 단일 객체, 복합 객체에게 모두 적용할 수 있다.
- 클라이언트에서는 해당 객체가 복합 객체인지 leaf인지 신경 쓰지 않아도 된다.
