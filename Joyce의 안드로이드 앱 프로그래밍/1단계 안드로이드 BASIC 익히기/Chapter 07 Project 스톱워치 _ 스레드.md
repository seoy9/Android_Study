# Chapter 07 Project 스톱워치 : 스레드

## Project 스톱워치

| 난이도 | ★☆☆☆ |
| --- | --- |
| 이름 | 스톱워치 |
| 프로젝트명 | StopWatch |
| 개발 환경 | - minSdk : 26</br>- targetSdk : 31 |
| 미션 | 나만의 스톱워치를 만들어보자 |
| 기능 | 시작, 일시정지, 초기화 |
| 조작법 | - [시작] 버튼을 누르면 스톱워치가 시작</br>- [일시정지] 버튼을 누르면 스톱워치가 일시정지</br>- [초기화] 버튼을 누르면 스톱워치가 초기화 |
| 핵심 구성요소 | - 컨스트레인트 레이아웃 (체인, 베이스라인)</br>- 스레드 |
</br></br>

## 7.1 사전 지식 : 메인 스레드와 백그라운드 스레드

- 메인 스레드 : 앱이 처음 시작될 때 시스템이 생성하는 하나의 스레드
- 메인 스레드의 역할
    1. 액티비티의 모든 생명 주기 관련 콜백 실행 담당
    2. 버튼, 에디드텍스트와 같은 UI 위젯을 사용한 사용자 이벤트와 UI 드로잉 이벤트 담당
    
    ⇒ ‘UI 스레드’ 라고도 부름
    
- 작업량이 큰 연산이나, 네트워크 통신, 데이터베이스 쿼리 등은 처리에 긴 시간이 걸림
- 모든 작업을 메인 스레드의 큐에 넣고 작업하면, 한 작업의 처리가 완료될 때까지 다른 작업을 처리하지 못함
⇒ 사용자 입장에서는 마치 앱이 먹통이 된 것처럼 보이게 됨
→ 몇 초 이상 메인 스레드가 멈추면 “앱이 응답하지 ㅇ낳습니다” 라는 메시지를 받게 됨
- 백그라운드 스레드를 활용하면 이러한 먹통 현상을 피할 수 있음
(백그라운드 스레드를 ‘워커 스레드’ 라고도 부름)
- 메인 스레드에서 너무 많은 일을 처리하지 않도록 백그라운드 스레드를 만들어 일을 들어주는 것
- 백그라운드 스레드에서 복잡한 연산이나 네트워크 작업, 데이터베이스 작업 등을 해 주면 됨
- 주의할 점 : **절대로 UI 관련 작업을 백그라운드 스레드에서 하면 안 됨**
    - 예) 백그라운드 스레드 2개에서 ‘오렌지 그리기’, ‘사과 그리기’ 작업, 어떤 게 그려지나?
    → 각 백그라운드 스레드가 언제 처리를 끝내고 UI에 접근할지 순서를 할 수 없기 때문에, UI는 메인 스레드에서만 수정할 수 있게 함
- 백그라운드 스레드에서 UI 자원을 사용하려면, 메인 스레드에 UI 자원 사용 메시지를 전달하는 방법을 이용해야 함
백그라운드 스레드 → (UI 자원 사용 메시지 전송) → UI 스레드 → (자원 변경 명령) → UI 자원
- UI 스레드에서 UI 작업을 할 때 활용하는 것 : Handler 클래스, AsyncTask 클래스, runOnUiThread() 메서드 등
(스톱워치에서는 사용이 간편한 runOnUiThread() 메서드 활용)
</br></br>

### 7.1.1 runOnUiThread() 메서드

- runOnUiThread( ) : UI 스레드(메인 스레드)에서 코드를 실행시킬 때 쓰는 액티비티 클래스의 메서드

```kotlin
// Activity.java

public final void runOnUiThread(Runnable action) {
	if (Thread.currentThread() != mUiThread) {
		mHandler.post(action);
	} else {
		action.run()'
	}
}
```

- if 문을 살펴보면, 만약 현재 스레드가 UI 스레드가 아니면 핸드러를 이용해 UI 스레드의 이벤트 큐에 action을 전달(post)
- 만약 UI 스레드이면, action.run( )을 수행
- 즉, 어떤 스레드에 있든지 runOnUiThread( ) 메서드는 UI 스레드에서 Runnable 객체를 실행
- 다음과 같은 UI 관련 코드를 runOnUiThread( )로 감싸주어 사용하면 됨
    
    ```kotlin
    runOnUiThread(object : Runnable {
    	override fun run() {
    		doSomethingWithUI() // 여기에 원하는 로직을 구현
    	}
    })
    ```
    
- 코틀린의 SAM 변환([2.8.3](https://www.notion.so/Chapter-02-034aee59dfeb474488f929520d4bca25) 참고)을 사용하면 더 간단히 표현 가능
    
    ```kotlin
    runOnUiThread {
    	doSomethingWithUI() // 여기에 원하는 로직 구현
    }
    ```
</br></br>

## 7.2 준비하기 : 프로젝트, SDK 버전

### 7.2.1 프로젝트 생성

- 액티비티 : Empty Activity
- 프로젝트 이름 : StopWatch
- 언어 : Kotlin
- Minimum SDK : API 26 : Android 8.0 (Oreo)
</br></br>

### 7.2.2 SDK 버전 설정

- Minumum SDK 버전 : API 26 : Android 8.0 (Oreo)
- 앱이 돌아가는 최소 사양을 API 26으로 명시해준 것
- 프로젝트에서 설정해주는 SDK 버전
    - Minimum SDK 버전
    - 타깃 SDK 버전
    - 컴파일 SDK 버전
- 현재 프로젝트의 모든 SDK 버전 : [Gradle Scripts] → build.gradle(Module: app) 파일에서 확인 가능
    - compileSdk
        - Gradle에 어떤 안드로이드 SDK 버전으로 앱을 컴파일할 것인지를 명시
        - 가장 최신 API 버전으로 지정을 하면, 개발 시점 기준으로 모든 API를 사용할 수 있음
        - 컴파일 SDK 버전이므로 런타임에는 영향을 미치지 않음
        - 디프리케이티드 API (Deprecated API) 체크나, 기존 코드의 컴파일 체크 그리고 새로운 API에 대비하려면 항상 최신 SDK 버전으로 지정하는 것이 좋음
    - minSdk
        - 앱을 사용할 수 있는 최소한의 API 레벨 명시
        - 안드로이드는 앱을 설치하려는 기기의 API 레벨이 minSdk 버전보다 낮으면 설치되지 않도록 함
    - targetSdk
        - 앱이 기기에서 동작할 때 사용하는 SDK 버전
        - 기기 버전이 API 28이고 targetSdk 버전이 API 24이면, 앱은 API 24를 기본으로 동작
        - 새로운 안드로이드 API 버전이 출시될 때마다 보안과 성능이 좋아지고 사용자 환경이 전반적으로 개선되므로 구글에서는 앱 개발 당시의 최신 API를 targetSdk로 지정할 것을 권장
    
</br>

> **디프리케이티드 API (Deprecated API)**

아직까지는 사용은 할 수 있지만, 조만간 사라질 수 있다고 예고된 API
> 
</br></br>

## 7.3 레이아웃 구성하기

### 7.3.1 기본 레이아웃 설정

- 레이아웃으로 사용자에게 보일 화면을 구성
</br></br>

### 7.3.2 colors.xml과 strings.xml 설정

- 스톱워치에서 4가지 색상과 3가지 문자열을 사용
- [app] → [res] → [values] → colors.xml 에 파랑, 빨강, 노랑 흰색 추가
</br></br>

> **색상 선택기 사용하기**

색상을 코드로만 지정할 수 있는 것이 아님
색상 선택기를 이용하는 방법도 있음
색상을 지정하는 코드 왼쪽에서 코드가 나타내는 색을 미리 보여줌
이 사각형을 클릭하면 색상 선택기가 뜸
여기서 선택한 색상대로 자동으로 코드가 바뀜
실제로 코딩할 때 꽤 유용하게 쓰이니 기억하기
> 
</br>

- [app] → [res] → [values] → strings.xml 에 ‘시작’, ‘일시정지’, ‘초기화’ 문자열 추가
</br></br>

### 7.3.3 버튼 추가

- 빈 화면에 [초기화] 버튼과 [시작] 버튼 만들기
</br></br>

### 7.3.4 텍스트뷰 추가 : 체인 사용해보기

- 흐르는 시간을 표현해줄 텍스트뷰 만들기
- 수직 방향 제약 추가하기 (baseline 사용)
    - 텍스트뷰 위에서 마우스 우클릭 → show baseline 선택 → 연결
</br></br>

> **왜 텍스트뷰 정렬에 베이스라인을 사용해야 할까요?**

베이스라인 없이 정렬을 맞추면
1. 모든 텍스트뷰들의 bottom, 즉 아래쪽이 정확히 일직선으로 정렬됨
2. 텍스트 크기가 다른 텍스트뷰 안의 텍스트 위치가 다른 뷰와 달라짐
→ 텍스트뷰 수직 위치는 정렬되었지만, 텍스트는 그렇지 못함

이 문제를 해결하려면 → 베이스라인 필요
베이스라인을 이용하면 텍스트뷰 안의 텍스트를 일직선으로 정렬 가능
> 
</br>

- 수평 방향 제약 추가하기 (Chain 사용)
    - 모든 뷰 선택 → 마우스 우클릭 → [Chains] → [Create Horizontal Chain] 선택
    - 텍스트뷰 위에서 마우스 우클릭 → [Chains] → [Horizontal Chain Style] → [packed] 선택
    - 체인이 제공하는 모드
        - packed
            
            ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/734dea0b-f4a3-4168-8305-f14e5f0f034e/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221024%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221024T022131Z&X-Amz-Expires=86400&X-Amz-Signature=e5cb80526660aa4983dd7431fd83a97114e03c3a2363edeb90bd88f3349362b1&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
            
        - spread inside
            
            ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/a53992c2-de2d-409d-b3cc-4a85868c417f/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221024%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221024T022142Z&X-Amz-Expires=86400&X-Amz-Signature=d3c299ce319a15cb494e130b2fc079c61f7bfcd4eb55816b220fb7cb644a7f78&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
            
        - spread
            
            ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/695f67ef-21ac-4d6a-a0e4-5bcc418ab4f2/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221024%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221024T022152Z&X-Amz-Expires=86400&X-Amz-Signature=2762c336bfc0bac4b80cf64759893cf9d14a8d95e984898d2c06e9c45a9b6c65&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
            
</br></br>

## 7.4 버튼에 이벤트 연결하기

- MainActivity.kt의 전반적인 코드의 틀을 잡고, 버튼에 이벤트를 연결
- 클릭 이벤트를 처리할 리스너 인터페이스를 구현
    1. 클릭 이벤트를 처리하는 View.OnClickListener 인터페이스 구현
    2. 스톱워치가 현재 실행되고 있는지를 확인하는 데 사용하는 isRunning 변수를 false로 초기화해서 생성
    3. findViewById() 함수로 xml 레이아웃 파일에서 정의한 뷰들을 액티비티에서 사용할 수 있게 가져옴
    4. btn_start 와 btn_refresh 에 구현한 리스너 등록
    setOnClickListener( ) 메서드를 이용해서 onClickListener를 추가해주어야 클릭 가능
    5. 클릭 이벤트가 발생했을 때 어떤 기능을 수행할지 구현
    따라서 View.OnClickListner 인터페이스는 반드시 onClick( ) 함수를 오버라이드해야 함
    클릭 이벤트가 발생했을 때, 뷰 ID가 R.id.start 이고, 스톱워치가 동작 중이라면 일시정지하고, 정지 상태라면 시작
    뷰 ID가 R.id.btn_refresh 이면 초기화 메서드가 실행
    6. start( ), pause( ), refresh( ) 는 각각 시작, 일시정지 초기화 함수
</br></br></br>

## 7.5 스톱워치 시작 기능 구현하기

- [시작] 버튼을 누르면 스톱워치가 시작되고, [시작] 버튼 텍스트가 ‘일시정지’로 바뀌는 기능 구현
    1. 일시정지와 초기화를 하려면, 이를 관리할 변수가 있어야 함 → 시간을 관리할 변수 생성
    2. [시작] 버튼을 클릭하면, 텍스트를 ‘일시정지’로, 색상을 빨강으로 변경
    3. 스톱워치가 시작되었으므로 isRunning 값을 true로 변경
    4. 코틀린에서 제공하는 timer(period = [주기]) { } 함수는 일정한 주기로 반복하는 동작을 수행할 때 유용하게 쓰임
    { } 안에 쓰인 코드들은 모두 백그라운드 스레드에서 실행됨
    주기를 나타내는 period 변수를 10으로 지정했으므로, 10밀리초마다 실행됨
    5. 0.01초마다 time에 1을 더합 (period 값, 주기가 10밀리초이기 때문, 참고로 1000밀리초는 1초)
    6. time 변수를 활용해 밀리초, 초, 분 계산
    7. 분, 초, 밀리초 텍스트뷰를 0.01초마다 갱신
    시간이 한 자리일 때 전체 텍스트 길이를 두 자리로 유지하려고 if문 추가
    텍스트 길이가 짧아졌다 길어졌다하면 보기 불편
- 분, 초, 밀리초를 출력하는 텍스트뷰가 UI 스레드에서 실행되도록 start( ) 함수 수정
    1. runOnUiThread 를 사용해서 텍스트뷰를 수정하는 부분에 감쌈
    그러면 UI 작업이 백그라운드 스레드가 아닌 UI 스레드(메인 스레드)에서 일어남
    2. isRunning 이 true 일 경우에만 UI가 업데이트되게 해 줌
    왜냐하면 사용자가 타이머를 정지하는 시점과 UI 스레드에서 코드가 실행되는 시점이 다를 수 있기 때문
</br></br></br>

## 7.6 일시정지 기능 구현하기

- pause( ) 함수 구현
    1. [일시정지] 버튼을 누르면, 다시 텍스트를 “시작”으로, 배경색을 파란색으로 변경
    2. 일시정지 상태이므로 isRunning 을 false 로 바꿈
    3. 현재 실행되는 타이머를 cancel( ) 함수를 호출해 멈춤
    (cancel( ) 함수는 백그라운드 스레드에 있는 큐를 깔끔하게 비워줌)
</br></br></br>

## 7.7 초기화 기능 구현하기

- [초기화] 버튼을 누르면 타이머가 실행 중이든, 일시정지 상태이든 시간이 00:00.00으로 초기화되어야 함
- refresh( ) 함수 구현
    1. 백그라운드 스레드에서 실행 중인 타이머를 멈춤
    2. 버튼에 시작 문구를 노출하고 버튼색을 파란색으로 변경
    3. isRunning 을 false 로 변경
    4. 0.01초에 1씩 늘어났던 time을 0으로 초기화
    분, 초, 밀리초 텍스트뷰 모두 “00”으로 초기화
</br></br></br>

## 7.8 테스트하기

- 안드로이드 스튜디오 메뉴에서 [Run] → [Run ‘app’]을 선택하여 에뮬레이터나 본인의 기기에서 앱을 실행
- 테스트
    1. [시작] 버튼 누름
    스톱워치가 시작되고, [일시정지] 버튼 보임
    2. [일시정지] 버튼 누름
    스톱워치가 멈추고, 다시 [시작] 버튼 보임
    [시작] 버튼 누르면, 초가 이어져 동작
    3. [초기화] 버튼 누름
    스톱워치가 초기화
</br></br></br>

## 학습 마무리

- 스톱워치 UI를 구성하며 버튼을 부모 컨스트레인트 레이아웃의 아래로 정렬시킴
- 가운데 있는 텍스트뷰를 부모 레이아웃과 [초기화] 버튼 사이에 위치하도록 제약 추가
→ 기준이 되는 위치를 먼저 정해 놓음
- 왼쪽 텍스트뷰와 오른쪽 텍스트뷰는 가운데 있는 텍스트뷰의 베이스라인에 맞도록 정렬
</br></br></br>

## 핵심 요약

- 서로 크기가 다른 텍스트뷰 안의 텍스트의 정렬을 맞추는 데 베이스라인 이용
- 서로 크기가 다른 뷰들의 여백을 수직 혹은 수평 방향으로 일정하게 분배하는 데 체인을 사용
- UI 변경은 UI 스레드(메인 스레드)에서만 가능
- 백그라운드 스레드에서는 네트워크 작업, 데이터베이스 작업, 계산량이 많은 작업을 하면 됨