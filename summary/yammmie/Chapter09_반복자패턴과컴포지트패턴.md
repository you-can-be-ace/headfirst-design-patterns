# 반복자 패턴과 컴포지트 패턴 Iterator Pattern & Composite Pattern

<br>

## 반복자 패턴 Iterator Pattern

- 컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법을 제공함
- 집합체에 있는 모든 항목에 일일이 접근 가능
    - 집합체 인터페이스와 구현이 간단해짐
    - 원래 자신이 할 일만 처리할 수 있게 됨

<br>

### 구조

<img width="735" alt="image" src="https://github.com/user-attachments/assets/5d819974-d3dc-404e-b168-6821d2df4649">


- Aggregate
    - 공통된 인터페이스
    - 클라이언트와 객체 컬렉션 구현을 분리해 클라이언트가 편하게 작업 처리 가능
- ConcreteAggregate
    - 객체 컬렉션이 들어있음
    - 들어있는 컬렉션을 `Iterator`로 리턴하는 메소드 구현
- `java.util.Iterator`
    - 모든 반복자가 구현해야 하는 인터페이스 제공
- ConcreteIterator
    - 반복 작업 중 현재 위치를 관리

<br>

### Before: 배열과 `ArrayList`의 반복문

```java
PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
ArrayList<MenuItem> breakfastItems = pancakeHouseMenu.getMenuItems();

DinerMenu dinerMenu = new DinerMenu();
MenuItem[] lunchItems = dinerMenu.getMenuItems();

for(int i = 0; i < breakfastItems.size(); i++) {
		MenuItem menuItem = breakfastItems.get(i);
		System.out.println(menuItem.getName() + " ");
		System.out.println(menuItem.getPrice() + " ");
		System.out.println(menuItem.getDescription() + " ");
}

for(int i = 0; i < lunchItems.length; i++) {
		MenuItem menuItem = lunchItems[i];
		System.out.println(menuItem.getName() + " ");
		System.out.println(menuItem.getPrice() + " ");
		System.out.println(menuItem.getDescription() + " ");
}
```

<br>

### 반복자 추가

```java
public Iterator<MenuItem> createIterator() {
		return menuItems.iterator();
}
```

```java
public interface Menu {
		public Iterator<MenuItem> **createIterator**();
}
```

```java
public class DinerMenuIterator implements Iterator<MenuItem> {
		Menu[] items;
		int position = 0;
		
		public MenuItem next() {
				MenuItem menuItem = items[position];
				position++;
				return menuItem;
		}
		
		public boolean hasNext() {
				if (position >= items.length || items[position] == null) {
						return false;
				}
		
				return true;
		}
		
		public void remove() {
				**throw new UnsupportedOperationException("메뉴 항목은 지우면 안됩니다.");**
		}
}
```

```java
public class Waitress {
		**Menu** pancakeHouseMenu;
		**Menu** dinerMenu;
		
		public Waitress(**Menu** pancakeHouseMenu, **Menu** dinerMenu) {
				this.pancakeHouseMenu = pancakeHouseMenu;
				this.dinerMenu = dinerMenu;
		}
		
		...
		
		private void printMenu(Iterator iterator) {
				while(iterator.hasNext()) {
						MenuItem menuItem = iterator.next();
						System.out.println(menuItem.getName() + ", ");
						System.out.println(menuItem.getPrice() + " -- ");
						System.out.println(menuItem.getDescription());
				}
		}
}
```

- `remove()`를 쓰지 않도록 하는 방법: `java.lang.UnsupportedOperationException`을 던질 것

<br><br>

## 단일 역할 원칙

> **📍 디자인 원칙

어떤 클래스가 바뀌는 이유는 하나뿐이어야 한다.**
> 
- 어떤 클래스의 역할이 2개 이상 있으면 바뀔 수 있는 부분이 2개 이상이 됨
- 컬렉션이 어떤 이유로 바뀌게 되면 그 클래스도 바뀌어야 함
- 반복자 관련 기능이 바뀌었을 때도 클래스가 바뀌어야 함

**⇒ 하나의 클래스는 하나의 역할만 맡아야 함** 

<br>

### 응집도 cohesion

- 한 클래스 또는 모듈이 특정 목적이나 역할을 얼마나 일관되게 지원하는지를 나타내는 척도
- 어떤 모듈이나 클래스의 응집도가 높다 → 서로 연관된 기능이 묶여있음
- 응집도가 낮다 → 서로 상관없는 기능들이 묶여있음
- 단일 역할 원칙과 응집도는 서로 밀접하게 연관되어 있음
    - 단일 역할 원칙을 잘 따르는 클래스는 2개 이상의 역할을 맡은 클래스에 비해 응집도가 높고, 관리하기 쉬움

<br><br>

## 컴포지트 패턴 Composite Pattern

- 객체를 트리구조로 구성해서 부분-전체 계층구조를 구현함
    - 부분-전체 계층구조: 부분들이 계층을 이루고 있지만 모든 부분을 묶어 전체로 다룰 수 있는 구조
- 컴포지트 패턴을 사용하면 클라이언트에서 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있음
    - 메뉴, 서브메뉴, 서브서브메뉴로 구성된 트리구조가 있을 때 각각이 모두 복합 객체가 될 수 있음

<br>

### 구조

<img width="663" alt="image" src="https://github.com/user-attachments/assets/1785219c-65f8-4be2-b769-0aa8473faeb9">


- 클라이언트는 `Component` 인터페이스를 사용해 복합 객체 내의 객체들을 조작함
- Component
    - 복합 객체 내에 들어있는 모든 객체의 인터페이스를 정의
    - 복합 노드와 잎에 관한 메소드까지 정의함
    - `add()`, `remove()`, `getChild()`와 같은 기본 행동 정의 가능
    - 복합 객체에게 별 쓸모없는 기능이라면 예외 던지는 방법으로 처리 가능
- Composite
    - 자식이 있는 구성 요소의 행동을 정의하고 자식 구성 요소를 저장하는 역할
    - `Leaf`와 관련된 기능도 구현해야 함

<br>

### Component, Composite, 트리?

- 복합 객체 Composite에는 구성 요소 Component가 들어있음
- 구성 요소는 복합 객체와 잎으로 나눌 수 있음 → 재귀 구조
- 복합 객체에는 자식들이 있고, 자식들은 복합 객체일 수도 잎일 수도 있음

<br>

### 예시

```java
public class MenuTestDrive {
		public static void main(String[] args) {
				MenuComponent pancakeHouseMenu = new Menu("팬케이크 하우스 메뉴", "아침 메뉴");
				MenuComponent dinerMenu = new Menu("객체마을 식당 메뉴", "점심 메뉴");
				MenuComponent cafeMenu = new Menu("카페 메뉴", "저녁 메뉴");
				MenuComponent dessertMenu = new Menu("디저트 메뉴", "디저트를 즐겨보세요");
				
				MenuComponent allMenus = new Menu("전체 메뉴", "전체 메뉴");
				
				allMenus.add(pancakeHouseMenu);
				allMenus.add(dinerMenu);
				allMenus.add(cafeMenu);
				
				// 메뉴 항목 추가
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
				
				Waitress waitress = new Waitress(allMenus);
				waitress.printMenu();
		}
}
```

<img width="763" alt="image" src="https://github.com/user-attachments/assets/87f964b5-b528-4e8e-89cd-047f4b2b9a14">


<br>

### 컴포지트 패턴에서의 단일 역할 원칙

- 컴포지트 패턴에서는 계층구조를 관리하는 일과 메뉴 관련 작업 처리 → 역할 2개
- **단일 역할 원칙을 깨는 대신 투명성을 확보하는 패턴**
- 투명성(transparency)
    - `Component` 인터페이스에 자식들을 관리하는 기능과 잎으로써의 기능을 전부 넣어 클라이언트가 복합 객체와 잎을 똑같은 방식으로 처리할 수 있도록 만들 수 있음
    - 어떤 원소가 복합 객체인지 잎인지 클라이언트에게는 투명하게 보임

<br><br>

## 핵심 정리

- 반복자 패턴을 사용하면 집합체를 대상으로 하는 반복 작업을 별도의 객체로 캡슐화 가능 + 반복 작업 하는 역할을 컬렉션에서 분리 가능
- 한 클래스에는 될 수 있으면 한 가지 역할만 부여하는 것이 좋음
- 컴포지트 패턴은 개별 객체와 복합 객체를 모두 담아 둘 수 있는 구조 제공
- 컴포지트 패턴을 사용하면 클라이언트가 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있음
- 컴포지트 패턴 사용 시 여러 장단점을 고려해야 함 → 투명성과 안정성 사이에서 적절한 균형 찾기
- `HashMap`은 반복자를 간접적으로 지원하는 클래스
    - 반복자를 가져오는 것은 `values` 컬렉션을 먼저 가져온 후에 가능하기 때문

🔗 Iterable과 Iterator: [https://www.notion.so/47-ded0bf83255548ad9d54ff911df1f270?pvs=4#a8b5346074034d2fbfc21eeef2477e4b](https://www.notion.so/ded0bf83255548ad9d54ff911df1f270?pvs=21)

🔗 외부 반복자와 내부 반복자: [https://www.notion.so/47-ded0bf83255548ad9d54ff911df1f270?pvs=4#dd93be8e20c7476894750109f11c8d2b](https://www.notion.so/ded0bf83255548ad9d54ff911df1f270?pvs=21)
