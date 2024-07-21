# 템플릿 메소드 패턴 Template Method Pattern

<br>

- 알고리즘의 골격을 정의
- 알고리즘의 일부 단계를 서브클래스에서 구현할 수 있으며, 알고리즘의 구조는 그대로 유지하면서 특정 단계를 서브클래스에서 재정의할 수 있음

<br><br>

## 기본 구현

<img width="753" alt="image" src="https://github.com/user-attachments/assets/c40e1630-0cb5-4a19-ae2e-24c9d811a12c">


```java
abstract class AbstractClass {
		final void templateMethod() {
				primitiveOperation1();
				primitiveOperation2();
				concreteOperation();
		}
		
		abstract void primitiveOperation1();
		
		abstract void primitiveOperation2();
		
		void concreteOperation() {
				// concreteOperation() 메소드 코드
		}
}
```

- `templateMethod()`
    - 서브클래스가 알고리즘의 각 단계를 마음대로 건드리지 못하도록 `final`로 선언
    - 메소드로 표현되는 각 단계를 순서대로 정의
- `primitiveOperation()`
    - 기본 단계로써, 구상 서브클래스에서 구현하도록 추상으로 선언
- `concreteOperation()`
    - 추상 클래스 내의 구상 메소드
    - `final`로 선언된 경우 서브클래스에서 오버라이드 불가

<br><br>

## 장점

- 추상 클래스에서 작업을 처리함 → 알고리즘 독점
- 서브클래스에서 코드 재사용 가능
- 알고리즘이 모여 있는 부분만 수정하면 됨
- 다른 서브클래스도 쉽게 추가할 수 있는 프레임워크 제공
- 추상 클래스에 알고리즘 지식이 집중되어 있으며 일부 구현만 서브클래스에 의존함

<br><br>

## 후크(hook)

- 추상 클래스에서 선언되지만 기본적인 내용만 구현되어 있거나 아무 코드도 들어있지 않은 메소드
- 후크를 사용하려면 서브클래스에서 후크를 오버라이드해야 함
- 서브클래스가 다양한 위치에서 알고리즘에 끼어들 수 있게 함
- 알고리즘의 특정 단계가 선택적으로 작용된다면 후크 사용
    - 알고리즘의 특정 단계를 제공해야만 한다면 추상 메소드 사용

<br>

### 용도

- 알고리즘에서 필수적이지 않은 부분을 서브클래스에서 구현하도록 하고 싶을 때
- 템플릿 메소드에서 앞으로 일어날 일이나 막 일어난 일에 서브클래스가 반응할 수 있도록 기회를 제공할 때
- 내부적으로 특정 목록을 재정렬한 후 서브클래스에서 특정 작업(화면상 표시되는 내용 다시 보여줌)을 수행할 때
- 서브클래스가 추상 클래스에서 진행되는 작업을 처리할지 말지 결정하게 할 때

<br>

### 예시

```java
public abstract class CaffeineBeverageWithHook {
		final void prepareRecipe() {
				boidWater();
				brew();
				pourInCup();
				if(customerWantsCondiments()) {
						addCondiments();
				}
		}
		
		// 생략
		
		// 후크
		boolean customerWantsCondiments() {
				return true;
		}
}
```

```java
public class CoffeeWithHook extends CaffeineBeverageWithHook {
		// 생략
		
		public boolean customerWantsCondiments() {
				String answer = getUserInput();
				
				if(answer.toLowerCase().startsWith("y")) {
						return true;
				} else {
						return false;
				}
		}
		
		private String getUserInput() {
				// 입력받는 내용
		}
}
```

- 별 내용 없이 `true`만 리턴할 뿐 다른 작업은 하지 않음
- 서브클래스에서 필요할 때 오버라이드할 수 있는 메소드

<br><br>

## 할리우드 원칙 Hollywood Principle

> **📍 디자인 원칙

먼저 연락하지 마세요. 저희가 연락 드리겠습니다.**
> 
- 의존성 부패 방지 가능
    - 의존성이 복잡하게 꼬여있는 상황
- 저수준 구성 요소가 시스템에 접속할 수는 있지만 언제, 어떻게 그 구성 요소를 사용할지는 고수준 구성 요소가 결정함
- 저수준 구성 요소는 절대 고수준 구성 요소를 직접 호출할 수 없음
- **저수준 구성 요소와 고수준 구성 요소 사이에 순환 의존성이 생기지 않도록 해야 함**
- 클라이언트는 고수준 구성 요소인 추상 클래스에 의존하여 전체 시스템의 의존성을 줄임
- 저수준 구성 요소인 서브클래스는 자질구레한 메소드 구현을 제공하는 용도로만 쓰이며, 호출 당하기 전까지는 추상 클래스를 직접 호출하지 않음
- 활용하는 패턴: 팩토리 메소드, 옵저버 등

<br><br>

## 템플릿 메소드 패턴과 전략 패턴

- 둘 다 알고리즘을 캡슐화하는 패턴
- 템플릿 메소드 패턴은 상속 사용 → 중복되는 코드가 전부 슈퍼클래스에 들어있어 서브클래스가 공유받으므로 코드 중복이 적음
- 전략 패턴은 객체 구성 사용 → 더 유연하고, 클라이언트에서 다른 전략 객체를 사용해 알고리즘 변경 가능

<br><br>

## 핵심 정리

- 템플릿 메소드 만들 때 알고리즘 단계를 너무 잘게 쪼개지 말 것
    - 추상 메소드가 많아지면 서브클래스에서 일일이 구현해야 함
    - 그렇다고 너무 큼직하게 나누면 유연성 떨어짐 고려
- 팩토리 메소드 패턴은 특화된 템플릿 메소드 패턴
