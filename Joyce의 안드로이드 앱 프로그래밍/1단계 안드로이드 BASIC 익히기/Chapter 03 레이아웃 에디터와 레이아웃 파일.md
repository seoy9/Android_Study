# Chapter 03 레이아웃 에디터와 레이아웃 파일

## 3.0 들어가며

### 3.0.1 레이아웃

- 여러 UI 요소를 화면에 배치한 모습
- UI 요소 = 버튼, 텍스트, 이미지 등 ⇒ 통틀어 뷰
- 레이아웃 종류 = LinearLayout, RelativeLayout, FrameLayout, TableLayout 등
</br></br>

### 3.0.2 레이아웃 에디터

- 화면을 구성할 때 쓰는 도구
</br></br>

### 3.0.3 레이아웃 파일

- 화면 구성 요소를 담은 XML 파일
- XML 코드로 직접 작성해도 되고, 레이아웃 에디터를 사용해 작성해도 됨
</br></br></br>

## 3.1 레이아웃 에디터 알아보기

- 레이아웃 에디터 : 화면을 구성할 때 쓰는 도구
</br></br>

### 3.1.1 실습용 프로젝트 생성하기

(생략)
</br></br>

> **MainActivity.kt 에서 사용할 레이아웃 파일 지정하기**

onCreate( ) 함수 안에 있는 setContentView( ) 함수에 지정하면 됨
R.layout.activity_main 대신 R.layout.activity_new 를 인수로 넣으면 됨
> 
</br>

### 3.1.2 레이아웃 에디터 배치 살펴보기

- 팔레트
    - 레이아웃으로 드래그 앤 드롭할 수 있는 다양한 뷰와 레이아웃을 보여줌
    - 뷰라는 물감을 담는 쟁반 같은 것
- 컴포넌트 트리
    - 레이아웃에는 계층 구조 존재
    - 그 구조를 보기 좋게 보여줌
- 디자인 편집기
    - 레이아웃 디자인할 때 사용하는 도화지
    - 팔레트에서 드래그해 디자인 편집이 위에 드롭하면 됨
- 속성창
    - 선택한 뷰의 여러 속성을 제어할 수 있는 영역
- 뷰 모드
    - 레이아웃을 코드(Code), 분할(Split), 디자인(Design) 모드 중 하나로 표시
    - 분할 모드에서는 코드창과 디자인창을 동시에 보여줌
</br></br>

### 3.1.3 레이아웃 미리보기 모양 변경

- 레이아웃 에디터는 수정한 앱 화면을 레이아웃 미리보기에서 보여줌
- 디자인 및 청사진
    - 편집기에서 레이아웃을 어떻게 표시할지 선택
    - 사용자가 볼 화면인 렌더링된 레이아웃을 보고 싶다면 [Design] 선택
    - 뷰의 윤곽선만 표시하고 싶다면 [Blueprint] 선택
    - 두 뷰를 나란히 표시하고 싶다면 [Design + Blueprint] 선택
- 화면 방향 및 레이아웃 변형
    - 화면을 세로 방향으로 보고 싶다면 [Portrait] 선택
    - 화면을 가로 방향으로 보고 싶다면 [Landscape] 선택
- 데이/나이트 모드 선택
    - 안드로이드 파이(API 28)부터 데이/나이트 테마를 사용자가 설정할 수 있음
    - 나이트 모드를 선택하면 어두운 나이트 테마의 레이아웃을 미리 보여줌
- 기기 유형 및 크기
    - 스마트폰, 태블릿, 웨어러블 기기와 같은 기기 유형과 화면의 크기, 밀도와 같은 구성을 선택
    - 미리 구성된 여러 유형의 기기와 직접 생성한 AVD(Android Virtual Device) 중에서 선택 가능
    - 목록에서 [Add Device Definition]을 선택해 새로운 구성 추가 가능
- API 버전
    - API 버전에 따라 레이아웃 형식이 달라짐
    - 해당 버전에 맞는 레이아웃을 선택할 때 사용
- 앱 테마
    - 미리보기 화면에 적용할 UI 테마 지정
    - 앱 테마는 지원되는 레이아웃에서만 쓰여서 레이아웃을 디자인할 때는 자주 사용되지 않음
- 언어
    - UI 문자의 언어를 지정
    - 기본값은 en-us, 영어이지만 한국어와 같이 왼쪽에서 오른쪽으로 쓰는 언어이므로 그대로 사용해도 괜찮음
    - [Preview Right to Left]를 선택하면 아랍어와 같이 오른쪽에서 왼쪽으로 쓰는 언어가 어떻게 보이는지 확인 가능
</br></br>

## 3.2 레이아웃 파일 생성 방법

- [app] > [res] > [layout] 위에서 > 마우스 우클릭 > [New] → [layout Resource File]
- 레이아웃 파일은 모두 XML 이기 때문에, 확장자는 붙이지 않아도 됨
- 루트 엘리먼트 : 해당 레이아웃 파일에서 루트가 될 최상위 요소
→ 이후 추가되는 레이아웃이나 뷰는 루트 엘리먼트에 종속됨
</br></br>

## 3.3 레이아웃 파일 코딩을 위한 아주 얕은 XML 지식

- XML (Extensible Markup Language) : 많은 종류의 데이터를 저장하고 관리할 목적으로 만들어짐
- 안드로이드에서는 AndroidManifest.xml, 레이아웃 파일, 리소스 파일 등에서 XML 사용
→ 데이터를 구조화하여 쉽게 표현할 수 있기 때문
</br></br>

### 3.3.1 아주 얕은 XML 지식 3가지

1. xml 코드는 크게 태그, 요소, 값을 구성됨
안드로이드 스튜디오 테마 기준 : 파란색(태그), 보라색(요소), 초록색(값)
    
    ```xml
    <TextView android:text="안녕 세상아!" />
    
    시작 기호 / 태그 / 요소 (네임 스페이스 / 속성 / 값 입력 기호) / 값 / 닫는 기호
    ```
    

1. 태그의 시작 기호는 < 이고 닫는 기호는 />
태그를 여닫는 형태는 2가지
    
    
    | 형태 | 예제 |
    | --- | --- |
    | <태그명 내용 /> | <TextView android:layout_width=”wrap_content” … />
    | <태그명> … </태그명> | <androidx.constraintlayout.widget.ConstraintLayout tools:context=”.MainActivity> … </androidx.constraintlayout.widget.ConstraintLayout> |
</br>

2. activity_main.xml 파일의 첫 줄은 태그를 여닫는 기호가 특이함
<? 로 열고  ?>로 닫았음 → 이 태그는 문서의 메타 데이터를 알려주는 데 사용
XML 코드이고, XML 버전은 1.0, 인코딩 방식은 utf-8을 쓴다는 내용
코드 전체에서 딱 한 번 여기서만 씀
지우면 안드로이드가 문서 정보를 알 수 없으므로 지우면 안 됨
    
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    ```
    
</br>

### 3.3.2 activity_main.xml 파악하기

| 코드 | 설명 |
| --- | --- |
| ```<?xml version="1.0" encoding="utf-8"?>``` | 이 파일이 XML 문서임을 명시, version은 XML의 버전, encoding은 인코딩 방식 |
| ```<androidx.constraintlayout.widget.ConstraintLayout``` | 컨스트레인트 레이아웃을 모든 뷰들을 감싸는 루트 레이아웃으로 지정, 레이아웃의 종류에 따라 배치 방법 다름 |
| ```xmlns:android="http://schemas.android.com/apk/res/android"``` | xmls 는 네임스페이스를 선언, android라는 네임스페이스를 선언함, 추후 안드로이드가 기본으로 제공하는 속성을 사용할 때, 이 네임스페이스를 사용 |
| ```xmlns:app="http://schemas.android.com/apk/res-auto"``` | app이라는 네임스페이스를 선언 |
| ```xmlns:tools="http://schemas.android.com/tools"``` | tools라는 네임스페이스를 선언 |
| ```android:layout_width="match_parent"``` | 레이아웃의 너비를 화면에 꽉차게 |
| ```android:layout_height="match_parent"``` | 레이아웃의 높이를 화면에 꽉차게 |
| ```tools:context=".MainActivity"``` |  액티비티를 명시, 편의를 위한 것이므로 삭제해도 무방 |
| ```>``` | 이상으로 컨스트레인트 레이아웃 태그를 열었음 |
| ```<TextView``` | 텍스트뷰 태그 |
| ```android:layout_width="wrap_content"``` | 뷰의 너비 속성을 내용에 딱 맞게 조정 |
| ```android:layout_height="wrap_content"``` | 뷰의 높이 속성을 내용에 딱 맞게 조정 |
| ```android:text="Hello World!"``` | 뷰의 텍스트 지정 |
| ```app:layout_constraintBottom_toBottomOf="parent"``` | 뷰의 하단을 부모 레이아웃의 하단에 위치 |
| ```app:layout_constraintLeft_toLeftOf="parent"``` | 뷰의 좌측을 부모 레이아웃의 좌측에 위치 |
| ```app:layout_constraintRight_toRightOf="parent"``` | 뷰의 우측을 부모 레이아웃의 우측에 위치 |
| ```app:layout_constraintTop_toTopOf="parent"``` | 뷰의 상단을 부모 레이아웃의 상단에 위치
이로써 상단/하단, 좌측/우측 모두에서 팽팽하게 위치하므로 뷰는 화면의 중앙에 위치됨 |
| ```/>``` | 텍스트뷰 태그를 닫아줌 |
| ```</androidx.constraintlayout.widget.ConstraintLayout>``` | 컨스트레인트 레이아웃 태그를 닫아줌 |
</br>

> **안드로이드 스튜디오 자동 정렬 기능**

윈도우/리눅스 : Ctrl + Alt + L
맥OS : Command + Option + L
> 
</br>

- 안드로이드 스튜디오 단축키

| 구분 | 설명 | 윈도우/리눅스 | 맥OS |
| --- | --- | --- | --- |
| 일반 | 모두 저장 | Control + S | Command + S |
|  | 동기화 | Control + Alt + Y | Command + Option + Y |
| 검색 | 모든 항목 검색
(코드와 메뉴 포함) | Shift 키를 두 번 누름 | Shift 키를 두 번 누름 |
|  | 찾기 | Control + F | Command + F |
|  | 다음 항목 찾기 | F3 | Command + G |
|  | 이전 항목 찾기 | Shift + F3 | Command + Shift + G |
|  | 바꾸기 | Control + R | Command + R |
|  | 작업 찾기 | Control + Shift + A | Command + Shift + A |
|  | 기호 이름으로 검색 | Control + Alt + Shift + N | Command + Oprion + O |
|  | 클래스 찾기 | Control + N | Command + O |
|  | (클래스 대신에) 파일 찾기 | Control + Shift + N | Command + Shift + O |
|  | 경로에서 찾기 | Control + Shift + F | Command + Shift + F |
|  | 줄 이동 | Control + G | Command + L |
</br></br>

## 핵심 요약

- 레이아웃 에디터는 XML 코드를 직접 작성하는 대신 시각적인 요소로 UI를 디자인하는 도구
- Split 모드를 이용하면 XML 모드를 작성하고 바로 미리보기를 할 수 있어 편리
- 레이아웃 파일을 XML로 이루어져 있으며 태그, 요소, 값으로 이루어져 있음