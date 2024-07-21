# headfirst-design-patterns
[OREILLY] 헤드 퍼스트 디자인 패턴
<br/><br/>

🚨 Rule
---
- 스터디는 **매 주 일요일 09:00**에 진행한다.
- 코드 담당자는 **목요일까지(D-3)** 실습 코드를 작성하여 PR을 생성한다.
- 코드에서 논의하고 싶은 내용은 **PR Comment**로, 그 외 논의하고 싶은 내용은 **Issue**로 등록한다.
<br/><br/>


⚙️ Convention
---
- example
  - `/src/main/java/com.ace.example` 패키지 하위에 `chapterXX` 패키지 생성
- summary
  - 본인 이름 패키지 하위에 정리한 자료 업로드 (확장자 자유롭게)
<br/><br/>


💻 Local Setting Guide
---
- 로컬 환경 세팅 및 개발 편의를 위해 Springboot(Java 21 + Gradle)로 구성하였다.
- Java 21 버전 관련 오류가 발생한다면, 아래 두 군데를 확인해보면 된다.
  - `Preferences > Build, Excution, Deployment > Build Tools > Gradle > Gradle JVM`이 JDK 21로 설정되어 있는지 확인
  - `Project Structure > Project Settings > Project`
    - `SDK`가 JDK 21로 설정되어 있는지 확인
    - `Language Level`이 21로 설정되어 있는지 확인 (21이 목록에서 안뜨는 경우 IntelliJ Update)
<br/><br/>

✍🏻 Planning
---
|Part|제목|코드담당자|발표자|스터디일시|
|---|---|---|---|---|
|01|디자인 패턴의 세계로 떠나기 (디자인 패턴 소개와 전략 패턴)|혜령|혜령|2024.06.01 (토) 08:30|
|02|객체들에게 연락 돌리기 (옵저버 패턴)|금비|미애|2024.06.09 (일) 09:00|
|03|객체 꾸미기 (데코레이터 패턴)|미애|금비|2024.06.16 (일) 09:00|
|04|객체지향 빵 굽기 (팩토리 패턴)|보람|혜령|2024.06.23 (일) 09:00|
|05|하나뿐인 특별한 객체 만들기 (싱글턴 패턴)|금비|금비|2024.06.30 (일) 09:00|
|06|호출 캡슐화하기 (커멘드 패턴)|미애|미애|2024.07.07 (일) 09:00|
|07|적응시키기 (어댑트 패턴과 퍼사드 패턴)|혜령|보람|2024.07.14 (일) 09:00|
|08|알고리즘 캡슐화하기 (템플릿 메소드 패턴)|보람|미애|2024.07.21 (일) 09:00|
|09|컬렉션 잘 관리하기 (반복자 패턴과 컴포지트 패턴)|혜령||2024.07.28 (일) 09:00|
|10|객체의 상태 바꾸기 (상태 패턴)|금비||2024.08.04 (일) 09:00|
|11|객체 접근 제어하기 (프록시 패턴)|미애||2024.08.11 (일) 09:00|
|12|패턴을 모아 패턴 만들기 (복합 패턴)|보람||2024.08.18 (일) 09:00|
|13|패턴과 행복하게 살아가기 (실전 디자인 패턴)|||2024.08.25 (일) 09:00|
|14|다양한 패턴 빠르게 알아보기 (기타 패턴)|||2024.09.01 (일) 09:00|
