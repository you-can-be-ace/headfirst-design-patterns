# ⭐ 핵심 디자인 원칙
- OCP : 클래스는 확장에는 열려 있어야 하지만 변경에는 닫혀 있어야 함
    - 코드에서 확장해야 할 부분을 선택할 때는 세심한 주의가 필요함
    - 여러 디자인을 살펴보면 바뀌는 부분 중 가장 중요한 부분을 골라내는 안목이 높아짐

```
💡
Q. 모든 부분에서 OCP를 준수하려면 어떻게 해야 하나요?
A. 보통 그렇게 하는 것은 불가능함.
    디자인의 모든 부분을 깔끔하게 정돈할 만큼 여유가 있는 상황도 흔치 않음.
    OCP를 지키다 보면 새로운 단계의 추상화가 필요한 경우가 종종 있는데, 이로 인해 복잡해짐
    디자인한 것 중에서 가장 바뀔 가능성이 높은 부분을 중점적으로 OCP를 적용하는 것이 좋음
```

# 데코레이터 패턴의 정의

- 객체에 추가 요소를 동적으로 더함
- 데코레이터를 사용하면 서브 클래스를 만들 때보다 훨씬 유연하게 기능 확장 가능

## 구성 요소
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/3f2ea9a8-046e-45a5-840b-6991787eb528)


- 추상 구성 요소 : `Component`
- 구상 구성 요소 : 구성요소 구현체, `ConcreteComponent`
- 추상 데코레이터 : `Decorator`
    - 추상 구성 요소와 동일한 타입을 가지기 위해 추상 구성 요소 상속
    - 추상 구성 요소와 동일한 타입의 객체를 포함하고 있음
- 구상 데코레이터 : 데코레이터 구현체, `ConcreteDecoratorA/B`

⇒ 예제에서는 추상 클래스로 구현하지만 보통 인터페이스로 구현함

## 예제
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/da2ee65c-38e7-4c84-aa32-587cdd625855)


## 동작 방식
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/4ef08dba-3b74-43b5-8789-98778a9cc4dd)

## 장점

- 데코레이터는 자신이 장식하고 있는 객체에게 어떤 행동을 위임하는 일 말고도 추가 작업 수행 가능
- 실행 중에 필요한 데코레이터를 마음대로 적용 가능
- 코드 재사용을 위한 상속이 아닌 구상 요소와 동일한 타입으로 구현하기 위해 상속 진행

## 한계

- 데코레이터가 많아질 경우 클래스가 너무 많아져 코드 이해가 어려워질 수 있음

# 자바의 데코레이터 패턴
![image](https://github.com/you-can-be-ace/headfirst-design-patterns/assets/16659000/44f28ce7-b424-4f39-ae34-068d6609b0bc)

- [`java.io`](http://java.io) 패키지는 데코레이터 패턴을 바탕으로 만들어졌음
- 위 그림에서 `FileInputStream`은 추상 데코레이터 클래스 역할을 함
