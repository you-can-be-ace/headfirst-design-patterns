# 14장 기타 패턴 | 다양한 패턴 빠르게 알아보기

교재 앞에서는 다루지 않았던 기타 패턴 간략하게 훑어보기 

## 브리지 패턴 
- 구현과 추상화를 독립적으로 분리하여 각기 변경하고자 할 때 사용하는 패턴
  - 추상화 부분도 바꾸고자 한다면 사용
- 장점
  - 구현과 인터페이스를 완전히 결합하지 않아 구현과 추상화 부분을 분리할 수 있다.
  - 추상화 부분과 실제 구현 부분을 독립적으로 확장할 수 있다.
  - 추상화 부분을 구현한 구상 클래스가 변경돼도 클라이언트에는 영향을 끼치지 않는다. 
- 단점
  - 클래스 수가 증가하여 구조가 복잡해질 수 있다.

## 빌더 패턴
- 제품을 여러 단계로 나누어 제품 생산 단계를 캡슐화하고자 할때 사용한다.
- 클라이언트마다 유연하게 옵션을 선택하고자 할때, 유연한 디자인이 필요할 때 사용하면 좋다.
  - 객체 마을 테마파크에서는 손님이 원하는 호텔, 입장권, 레스토랑, 특별 이벤트 등을 마음대로 선택해서 예약 가능하다.
```
builder.buildDay(date);
builder.addHotel(date, "grand hotel");
builder.addTickets("Patterns on Ice");
```
- 세가지 외에도 addReservation(), addSpecialEvent()등의 클라이언트가 원하는 메소드 호출이 가능하다.
- 복합 객체 구조를 구축하는 용도로 많이 쓰인다.
- 장점
  - 다양한 절차를 거쳐 객체를 만들 수 있다.
  - 제품의 내부 구조를 클라이언트로부터 보호 가능하다.
  - 클라이언트는 추상 인터페이스만 볼 수 있어 제품을 구현한 코드를 쉽게 수정 가능하다.
- 단점
  - 팩토리를 사용할 때보다 객체 만들 때 클라이언트에 관해 더 많이 알아야 함

## 책임 연쇄 패턴 
- 1개의 요청을 2개의 객체에서 처리해야 한다면 책임 연쇄 패턴을 사용하면 된다.
- 책임 연쇄 패턴에서는 주어진 요청을 검토하는 객체 사슬을 생성한다.
- 그 사슬에 속해 있는 각 객체는 자기가 받은 요청을 직접 처리하거나 사슬에 있는 다른 객체에게 넘긴다.
- 마우스 클릭, 키보드 이벤트 처리시 흔히 사용한다. 
- 장점
  - 요청을 보낸 쪽과 받는 쪽을 분리할 수 있다.
  - 사슬에 들어가는 객체를 바꾸거나 순서를 바꿈으로써 역할을 동적으로 추가하거나 제거 가능하다.
- 단점
  - 요청이 반드시 수행됨을 보장할 수 없다. -> 사슬을 끝까지 갔는데도 처리되지 않을 수 있다.
- 실행 시에 과정을 살펴보거나 디버깅하기 힘들다. 

## 플라이웨이트 패턴 
- 어떤 클래스의 인스턴스 하나로 여러 개의 '가상 인스턴스'를 제공하고 싶다면 플라이웨이트 패턴을 사용하면 된다.
- 장점
  - 실행 객체 인스턴스 수를 줄여 메모리 절약 가능 
  - 여러 '가상' 객체의 상태를 한곳에 모아 둘 수 있다.
  - 어떤 클래스의 인스턴스가 아주 많이 필요하지만 모두 똑같은 방식으로 제어해야 할 때 유용하게 사용됨
- 단점
  - 특정 인스턴스만 다른 인스턴스와 다르게 행동할 수 없다. 

## 인터프리터 패턴
- 어떤 언어의 인터프리터를 만들고자 할 때 사용됨
- 인터프리터 패턴을 구현하고자 한다면 문법을 어느 정도 알고 있어야 한다. -> 이해하는 데 큰 무리는 없을거다.
- 장점
  - 간단한 언어를 구현할 때 인터프리터 패턴이 유용하게 사용된다.
  - 효율보다는 단순하고 간단하게 문법을 만드는 것이 더 중요한 경우에 유용하다.
  - 스크립트 언어와 프로그래밍 언어에서 모두 사용 가능
- 단점
  - 문법 규칙의 수가 많아지면 복잡도가 증가한다.
  - 그럴 때는 파서나 컴파일러 생성기를 쓰는 편이 낫다.
     
## 중재자 패턴 
- 서로 관련된 객체 사이의 복잡한 통신과 제어를 한곳으로 집중하고 싶다면 사용한다.
- 중재자를 추가하기 전에는 모든 객체가 서로 밀접하게 연관돼야 했으나 중재자를 사용하면 서로 완전한 분리 가능하다.
- 서로 연관된 GUI 구성요소를 관리하는 용도로 많이 쓰인다. 
- 장점
  - 시스템과 객체를 분리함으로써 재사용성을 획기적으로 향상시킬 수 있다.
  - 제어 로직을 한 군데 모아놨으므로 관리하기가 수월하다.
  - 시스템에 들어있는 객체 사이에서 오가는 메시지를 확 줄이고 단순화할 수 있다.
- 단점
  - 디자인을 잘못하면 중재자 객체가 너무 복잡해질 수 있다.
  
## 메멘토 패턴 
- 객체를 이전의 상태로 복구해야 한다면 사용한다.
  - ex) 사용자의 '작업 취소' 요청
- 메멘토 객체를 써서 상태를 저장한다.
- 장점
  - 저장된 상태를 핵심 객체와는 다른 별도의 객체에 보관할 수 있어 안전하다.
  - 핵심 객체의 데이터를 계속해서 캡슐화된 상태로 유지할 수 있다.
  - 복구 기능을 구현하기 쉽다.
- 단점
  - 상태 저장&복구 시간이 오래 걸릴 수 있다는 단점이 있다. 

## 프로토타입 패턴 
- 어떤 클래스의 인스턴스를 만들 때 자원과 시간이 많이 들거나 복잡할 때 사용하면 된다.
- 클라이언트 코드에서 어떤 클래스의 인스턴스를 만드는지 전혀 모르는 상태에서도 새로운 인스턴스를 만들 수 있다는 점이 특징이다.
- 시스템에서 복잡한 클래스 계층 구조에 파묻혀 있는 다양한 형식의 객체 인스턴스를 생성해야 할 때 유용하다.
- 장점
  - 클라이언트는 새로운 인스턴스를 만드는 과정을 몰라도 된다.
  - 클라이언트는 구체적인 형식을 몰라도 객체를 생성할 수 있다.
  - 상황에 따라서 객체를 새로 생성하는 것보다 객체를 복사하는 것이 더 효율적일 수 있다.
- 단점
  - 때때로 객체의 복사본을 만드는 일이 매우 복잡할 수도 있다. 

## 비지터 패턴 
- 객체 구조를 변경하지 않고 새로운 연산을 추가해야 하는 경우라면 사용한다.
- 여러 가지 다른 종류의 객체에 대해 다양한 연산을 수행해야 하는 경우 사용한다. 
- 장점
  - 구조를 변경하지 않고 복합 객체에 새로운 기능을 추가할 수 있다.
  - 비교적 손쉽게 새로운 기능을 추가할 수 있다.
  - 비지터가 수행하는 기능과 관련된 코드를 한곳에 모아둘 수 있다.
- 단점
  - 비지터를 사용하면 복합 클래스의 캡슐화가 깨진다.
  - 컬렉션 내의 모든 항목에 접근하는 트래버서가 있으므로 복합 구조를 변경하기가 더 어려워진다.
  - 객체 구조가 자주 변경되거나 새로운 요소가 자주 추가된다면 적합하지 않다. 
