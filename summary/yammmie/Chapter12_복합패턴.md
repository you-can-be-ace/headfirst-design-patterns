# 복합 패턴

- 반복적으로 생길 수 있는 일반적인 문제를 해결하는 용도로 2개 이상의 패턴을 결합해서 사용하는 것
- 패턴으로 이루어진 패턴
- 디자인 도구상자에서 가장 강력한 패턴

<br><br>

# MVC 패턴

- 복합 패턴의 왕

<br>

## 구조

<img width="702" alt="image" src="https://github.com/user-attachments/assets/a55adcbb-b888-4972-b63c-f17216c367d3">


<br>

### 뷰

- 모델을 표현하는 방법 제공
- 일반적으로 화면에 표시할 때 필요한 상태와 데이터는 모델에서 직접 가져옴

<br>

### 컨트롤러

- 사용자로부터 입력을 받으며 입력받은 내용이 모델에게 어떤 의미가 있는지 파악함

<br>

### 모델

- 모든 데이터, 상태와 애플리케이션 로직이 들어있음
- 뷰와 컨트롤러에서 모델의 상태를 조작하거나 가져올 때 필요한 인터페이스를 제공함
- 기본적으로 뷰와 컨트롤러에 별 관심없음

<br><br>

## 모델, 뷰, 컨트롤러의 관계

- 사용자는 뷰에만 접촉할 수 있음
    - 사용자가 뷰에서 어떤 행동을 하면 뷰는 무슨 일이 일어났는지 컨트롤러에게 알려주고, 컨트롤러가 상황에 맞게 작업을 처리함
- 컨트롤러가 모델에게 상태를 변경하라고 요청함
    - 사용자가 버튼을 클릭하면 컨트롤러는 그것이 무엇을 의미하는지 해석하고, 모델을 어떤 식으로 조작해야 하는지 결정함
- 컨트롤러가 뷰를 변경해 달라고 요청할 수 있음
    - 컨트롤러는 인터페이스에 있는 어떤 버튼이나 메뉴를 활성화 또는 비활성화할 수 있음
- 상태가 변경되면 모델이 뷰에게 그 사실을 알림
    - 모델에서 무언가 바뀌면 모델은 뷰에게 상태가 변경되었다고 알림
- 뷰가 모델에게 상태를 요청함
    - 뷰는 화면에 표시할 상태를 모델로부터 직접 가져옴
    - 컨트롤러가 뷰에게 무언가 바꾸라고 요청했을 때도 뷰는 모델에게 상태를 알려 달라고 요청할 수 있음

<br><br>

## 컨트롤러 코드를 뷰에 넣어도 되는가?

- 사용자 인터페이스도 관리해야 하고, 모델을 제어하는 로직도 처리해야 하므로 뷰 코드가 복잡해짐
- 뷰를 모델에 밀접하게 연관시켜야 하는데, 이러면 뷰를 다른 모델과 연결해서 재사용하기 어려워짐

**⇒ 컨트롤러는 제어 로직을 뷰로부터 분리해서 뷰와 모델의 결합을 끊어 주는 역할**

<br><br>

## MVC 패턴에 사용되는 패턴

<img width="744" alt="image" src="https://github.com/user-attachments/assets/6e94f307-2642-451f-8e6f-52527f672f71">


<br>

### 전략 패턴

- 뷰와 컨트롤러는 전략 패턴으로 구현됨
- 컨트롤러가 전략을 제공하고, 뷰 객체는 여러 전략을 써서 설정 가능
- 뷰는 애플리케이션의 겉모습에만 신경을 쓰고, 인터페이스의 행동을 결정하는 일은 모두 컨트롤러에게 맡김
- 전략 패턴을 사용하면 뷰를 모델로부터 분리하는 데 도움됨

<br>

### 컴포지트 패턴

- 디스플레이는 여러 단계로 겹쳐 있는 윈도우, 패널, 버튼, 텍스트 레이블 등으로 구성됨
- 각 디스플레이 항목은 복합 객체(윈도우)나 잎(버튼)이 될 수 있음
- 컨트롤러가 뷰에게 화면을 갱신해 달라고 요청하면 최상위 뷰 구성 요소에게만 화면 갱신하라고 하면 됨

<br>

### 옵저버 패턴

- 모델은 옵저버 패턴을 써서 상태가 변경되었을 때 그 모델과 연관된 객체들에게 연락함
- 옵저버 패턴을 사용하면 모델을 뷰와 컨트롤러로부터 완전히 독립시킬 수 있음
- 한 모델에서 서로 다른 뷰를 사용할 수도 있고, 여러 개의 뷰를 동시에 사용하는 것도 가능함

<br>

### MVC 패턴을 웹에 적용하는 접근법

- 웹 애플리케이션에는 클라이언트 측 애플리케이션과 서버 측 애플리케이션이 있음
- 모델, 뷰, 컨트롤러가 각각 어디 있는지에 따라 설계 방식이 달라짐
- **신 클라이언트(thin client) 접근법**
    - 대부분의 모델과 뷰, 컨트롤러가 모두 서버로 들어감
    - 브라우저는 뷰를 화면에 표시하고 컨트롤러로 입력을 받아오는 역할만 함
- **단일 페이지 애플리케이션(single page application) 접근법**
    - 대부분의 모델, 뷰, 컨트롤러가 모두 클라이언트에 들어감
- 일부 구성 요소를 클라이언트와 서버에서 공유하는 **하이브리드 모델**도 있음

<br><br>

## 핵심 정리

- 모델-뷰-컨트롤러는 옵저버, 전략, 컴포지트 패턴으로 이루어진 복합 패턴
- 모델은 옵저버 패턴을 사용해서 의존성을 없애면서도 옵저버들에게 자신의 상태가 변경되었음을 알릴 수 있음
- 컨트롤러는 뷰의 전략 객체로, 뷰는 컨트롤러를 바꿔서 또 다른 행동을 할 수 있음
- 뷰는 컴포지트 패턴을 사용해 사용자 인터페이스를 구현함
    - 보통 패널이나 프레임, 버튼과 같은 중첩된 구성 요소로 이루어짐
- MVC는 3가지 패턴으로 서로 느슨하게 결합되므로 깔끔하면서 유연한 구현 가능함
- 새로운 모델을 기존의 뷰와 컨트롤러에 연결해서 쓸 때는 어댑터 패턴을 활용하면 됨
