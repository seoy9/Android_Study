# Chapter 04 화면 제어하기_액티비티와 프래그먼트

## 4.0 들어가며

### 4.0.1 액티비티와 프래그먼트

- 액티비티 : 사용자에게 사용자 인터페이스를 제공하는 앱 구성요소
→ 화면을 통해 사용자와 소통하는 역할 담당
- 액티비티는 화면 전체를 자치
- 프래그먼트(파편, 조각) : 각 영역을 담당하는 개념
- 정의상 한 액티비티에서 여러 프래그먼트를 보여줄 수 있음
- 프래그먼트를 여러 액티비티에서 재활용 가능
- 대부분 앱은 여러 화면으로 이루어짐
- 클릭이나 드래그를 해서 화면을 이동
- 이때 각 화면을 액티비티나 프래그먼트라고 생각하면 됨
- 프로젝트 설계 방식에 따라 프래그먼트를 쓸 수도 있고, 두 개를 혼용해서 쓸 수도 있음
</br></br>

## 4.1 안드로이드 4대 구성요소

- 안드로이드는 4대 기본 구성요소로 이루어짐
- 4대 기본 구성요소 = 액티비티 + 서비스 + 브로드캐스트 리시버 + 콘텐트 프로바이더
- 각 구성요소는 서로 독립적인 기능을 가지고, 나름의 생명 주기를 가지고 있음
- 액티비티 (Activity) : 사용자에게 사용자 인터페이스를 제공
- 서비스 (Service) : 백그라운드 조작을 수행
- 브로드캐스크 리시버 (broadcast receiver) : 앱의 밖에서 일어난 이벤트를 앱에 전달
- 콘텐트 프로바이더 (content provider) : 데이터를 관리하고 다른 앱의 데이터를 사용할 수 있게 함
- 모든 앱에서 4가지 구성요소를 모두 써야 하는 건 아님
⇒ 사용자에게 화면을 보여주는 액티비티 꼭 하나는 있어야 함
</br></br></br>

## 4.2 액티비티

- 액티비티 : 화면의 기본 구성 단위
- 액티비티는 만든 UI를 보여주고, 터치, 드래그, 키보드 입력과 같은 여러 상호작용을 제공
- 액티비티를 작동시키려면 알아야 하는 것 : 액티비티의 생명 주기, 화면 전환 방법
</br></br>

### 4.2.1 액티비티 작동시키기

- 눈에 보이는 UI는 XML 파일로, 동작은 .kt 파일로 구성하고, 둘이 한 쌍을 이룸
- 액티비티를 작동시키려면 XML 파일에 액티비티의 UI 구성을 먼저 해야 함
- 액티비티에 코틀린 코드로 기능을 심고, 필요하다면 액티비티 간 전환도 제공해야 함
- 액티비티가 생성되고 종료되기까지 = 생명 주기
- 생명 주기를 알아야 때에 알맞는 동작을 심어넣을 수 있음
- 이때 XML에서 정의한 뷰 ID를 사용
</br></br>

### 4.2.2 액티비티의 생명 주기

- 액티비티는 사용자의 활동에 따라 새로운 상태에 들어감
→ 그 상태에 들어가면 시스템은 미리 정의된 콜백 함수를 실행

![C45B423A-351F-43DA-9DA9-B8CA280D1AE0.jpeg](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/675711be-652d-4aa3-9dba-c52a90d774f2/C45B423A-351F-43DA-9DA9-B8CA280D1AE0.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221018%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221018T061530Z&X-Amz-Expires=86400&X-Amz-Signature=980549e18295dba72b428a1f38f7dfd996edf1e015631cc08ac5125c916182ac&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22C45B423A-351F-43DA-9DA9-B8CA280D1AE0.jpeg%22&x-id=GetObject)

- 앱은 ① 생성된 상태, ② 시작된 상태, ③ 재개된 상태, ④ 일시정지된 상태, ⑤ 멈춰진 상태, ⑥ 종료된 상태 가 있음
- 각 상태에 진입하려면 그에 맞는 콜백 함수가 호출되어야 함
ex) onCreate( ) 함수가 호출되어야 ‘액티비티가 생성된 상태’가 됨
- 다른 상태와 달리 ‘종료된 상태’의 액티비티는 다시 상태 변환을 할 수 없음
- 액티비티는 생성부터 소멸까지 각각의 상태를 거치며 그때 수행할 수 있는 일이 있다는 정도만 기억
- 각 콜백 함수마다 할 수 있는 일이 따로 있음
- onCreate() 콜백 함수 호출 → 액티비티를 ‘생성된 상태’로 만들어줌
⇒ 액티비티에 필요한 초기 설정을 여기서
- 대표적인 초기화 설정 = 보여줄 레이아웃을 선택
→ setContentView( ) 함수로 사용자에게 보여줄 레이아웃을 지정 (레이아웃을 지정한 파일(리소스)의 ID를 인수로 줌)
- ‘생성된 상태’가 되려면 화면이 있어야 하고, 화면이 있으려면 사용할 레이아웃이 뭔지 알아야 함
⇒ ‘생성된 상태’ 이전에는 꼭 레이아웃을 onCreate( ) 함수에서 지정해줘야 함
- 액티비티가 시작될 때 실행되는 콜백 함수
    - onCreate( )
        - 시스템이 액티비티를 처음 시작할 때 실행
        - 레이아웃 지정, 클래스 범위 변수 초기화 등 기본적인 앱 시작 로직을 여기서 구현
    - onStart( )
        - 액티비티가 시작된 상태에 들어가기 직전에 실행
        - 액티비티가 사용자에게 보이지만 사용자와의 상호작용은 아직 준비하는 단계
        - UI 관련 로직을 초기화하는 코드 작성
    - onResume( )
        - 액티비티가 재개된 상태로 들어가기 직전에 실행
        - 액티비티와 사용자의 상호작용 가능
        - 전화가 온다거나, 사용자가 다른 액티비티로 이동하는 등 포커스를 잃는 경우가 아닌 이상 액티비티는 재개된 상태로 존재
- 다른 액티비티가 호출될 때 실행되는 콜백 함수
    - onPause( )
        - 사용자가 액티비티를 떠나는 경우 처음 실행되는 콜백 함수
        - 더는 이 액티비티에 포커스가 없다는 것
        - 액티비티가 보이지 않을 때 더 이상 실행할 필요가 없는 부분들을 비활성화해 주면 됨
        - 주의할 점 : onPause( )가 지속되는 시간이 굉장히 짧으므로 여기서 사용자의 정보를 데이터베이스에 저장하거나, 네트워크 호출을 하는 등 중요하거나 시간이 올래 걸리는 작업을 수행하면 안 됨 → 부하가 큰 작업은 onStop( ) 활용
    - onStop( )
        - 액티비티가 사용자에게 더 이상 표시 안 되는 중단된 상태에 들어가기 직전에 실행되는 콜백
        - 데이터베이스에 정보를 저장하는 일처럼 부하가 큰 작업 가능
    - onDestory( )
        - 액티비티가 완전히 소멸되기 직전에 호출되는 함수
</br></br>
### 4.2.3 액티비티 간의 화면 전환

- 앱을 사용할 때 수많은 화면 전환 일어남
- 화면 전환 = 액티비티(혹은 프래그먼트) 전환
→ 여기서 매우 중요한 개념 중 하나 : 인텐트 (Intent)
- Intent : (사전적 의미) 의지, 의향
→ 인텐트를 사용하여 무엇을 하고 싶은지 의지를 나타냄
- 인텐트 객체를 사용하면 액티비티뿐만 아니라 앞서 배운 안드로이드 4대 구성요소를 자유롭게 넘나들 수 있음
- 인텐트 객체가 사용자의 다양한 요청을 알맞은 구성요소에게 보내주는 것
</br></br>

(실습 생략)

- Intent 객체 생성
    
    ```kotlin
    Intent(this, SubActivity:clss.java)
    
    Intent(현재 객체, 이동할 목적지(클래스))
    ```
    
    - this : 현재 객체, 액티비티의 컨텍스트 가리킴
    - 컨텍스트
        - 애플리케이션(객체)의 현재 상태를 알 수 있도록 해 주는 인터페이스
        - 안드로이드 시스템에 의해 자동으로 구현됨
        - 액티비티 생명 주기와 연결됨
    - 자바의 클래스 참조 : **클래스 이름::class.java**
    
    ```kotlin
    startActivity(intent)
    ```
    
    - startActivity( ) 함수 : 방금 만든 인텐트 개겣를 인수로 받아 새로운 액티비티를 시작
</br></br></br>

## 4.3 프래그먼트 알아보기

- 프래그먼트는 액티비티 안에서 액티비티의 일정 부분을 차지
→ 한 액티비티에서 여러 프래그먼트를 보여줄 수도 있고, 같은 프래그먼트를 여러 액티비티에서 재사용을 할 수도 있음
</br></br>

### 4.3.1 프래그먼트의 유래

- 프래그먼트의 사전적 정의 : ‘파편, 조각’
- 한 화면의 UI를 분할하는 데 사용
- 한 화면에 2개 영역을 보여주려면 화면을 쪼개야 함 → 프래그먼트 필요
- 기억해두어야 할 점 : 2개 프래그먼트 모두 메인 액티비티 안에 종속됨
⇒ 메인 액티비티의 생명 주기가 종료되면 두 프래그먼트 역시 함께 종료
- 액티비티가 실행 중 → 두 프래그먼트는 각각 나름의 생명 주기를 갖고, 따로 사용자 입력 이벤트를 받음
- 프래그먼트는 액티비티와 하는 일은 비슷하지만, 조금 더 하위 개념, 액티비티 위에 뗐다 붙였다 할 수 있는 것
- 프래그먼트 특징
    - 꼭 액티비티 안에 종속되어야 함
    - 액티비티의 생명 주기에 영향을 받지만, 프래그먼트 고유의 생명 주기가 존재
    - 액티비티가 실행 중일 때 프래그먼트를 추가하거나 제거할 수 있음
- 프래그먼트트 분할된 화면을 독립적으로 활용하고 재사용하기에 좋음
</br></br>

### 4.3.2 프래그먼트의 생명 주기

| 액티비티 상태 | 프래그먼트 콜백 함수 |
| --- | --- |
| 생성된 상태 | onAttach( ) |
|  | onCreate( ) |
|  | onCreateView( ) |
|  | onActivityCreated( ) |
| 시작된 상태 | onStart( ) |
| 재개된 상태 | onResume( ) |
| 일시정지된 상태 | onPause( ) |
| 멈춘 상태 | onStop( ) |
| 종료된 상태 | onDestoryView( ) |
|  | onDestory( ) |
|  | onDetach( ) |
- 프래그먼트 생명 주기는 액티비티의 생명 주기에 영향을 받음
- 액티비티의 상태에 따라 프래그먼트가 호출할 수 있는 함수의 범위가 달라짐
- onAttach( )
    - 프래그먼트가 호스트 액티비티에 더해지고(attach) 나서 호출되는 함수
    - ‘이 프래그먼트가 액티비티에 추가되었어’ 라고 알려주는 것
- onCreate( )
    - 프래그먼트가 최초로 생성된 시점에 호출되는 함수
    - 프래그먼트를 초기화하는 코드 넣음
- onCreateView( )
    - 프래그먼트에서 굉장히 중요한 콜백 함수 중 하나
    - 프래그먼트에 그릴 뷰를 생성할 때 호출되는 함수
    - 이 함수에서 그릴 뷰를 반환해야 함
- onActivityCreated( )
    - 액티비티의 onCreate( ) 함수가 완료되고 나서 실행되는 함수
    - 액티비티 생성 후에 프래그먼트에서 해 주어야 할 작업이 있다면, 여기에 코드 작성
    - 액티비티와 프래그먼트의 뷰가 생성된 후이기 때문에, 뷰를 변경할 수 있음
- onStart( )
    - 사용자에게 프래그먼트가 보이기 시작할 때 실행
- onResume( )
    - 사용자와 상호작용 가능
    - 사용자가 프래그먼트를 떠나지 않는 이상 계속 재개된 상태에서 머무름
- onPause( )
    - 사용자가 프래그먼트를 떠날 때 처음 불러지는 콜백 함수
    - 불필요한 리소스들을 해제
- onStop( )
    - 프래그먼트가 사용자에게 더 이상 보이지 않을 때 호출되는 콜백 함수
- onDestoryView( )
    - onCreateView( ) 와 상응되는 함수
    - 뷰 리소스들을 해제
- onDestory( )
    - 프래그먼트가 마지막으로 완전히 삭제되기 전에 호출되는 함수
- onDetach( )
    - onAttach( ) 콜백 함수와 상응되는 것
    - 액티비티와의 연결을 끊기 전에 호출됨
</br></br>

### 4.3.3 프래그먼트의 화면 전환

- 프래그먼트는 액티비티 안에 자유롭게 추가되거나 제거될 수 있음

(실습 생략)

```kotlin
android:background="#F44336"
```

- android : 안드로이드 SDK에서 제공하는 기본 속성에 접근하겠다는 말
- background : 뷰의 배경을 지정하는 속성

```kotlin
// Fragment.kt

class Fragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater,     // 뷰를 생성하는 객체
		container: ViewGroup?,        // 생성할 뷰(자식 뷰)가 들어갈 부모 뷰
		savedInstanceState: Bundle?   // 이전 프래그먼트 객체에서 전달된 데이터(번들)
	): View? {
		return inflater.inflate(R.layout.fragment, container, flase);
	}
}
```

- onCreateView( ) : 프래그먼트의 레이아웃을 연결할 때 쓰는 콜백 함수
→ 어떤 레이아웃을 사용할지 정함

```kotlin
inflate(R.layout.fragment, container, flase)

inflate(레이아웃 리소스 참조값, 부모 뷰, attachToRoot)
```

- 인수로 받는 inflater 를 통해 프래그먼트의 레이아웃을 지정
- container : 부모 뷰, 자식 뷰가 들어갈 곳
- attachToRoot : 지금 즉시 부모 뷰에 뷰를 추가할 것인지 나중에 추가할 것인지
→ 나중 : false

```kotlin
// MainActivity.kt

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		settingButtons()
	}

	// 버튼이 클릭되었을 때 행동 정의
	fun settingButtons() {
		// 버튼 변수 초기화
		val redButton = findViewById<Button>(R.id.button_red_fragment)
		val blueButton = findViewById<Button>(R.id.button_blue_fragment)

		// 빨간 버튼 리스너
		redButton.setOnClickListener {
			// 프래그먼트 트랜잭션 클래스의 객체 생성
			val fragmentTransaction = supportFragmentManager.beginTransaction()
			// replace()로 기존의 프래그먼트를 새로운 프래그먼트로 교체
			fragmentTransaction.replace(R.id.fragmentFrame, RedFragment())
			// 트랜잭션 이후에는 반드시 commit() 함수 호출
			fragmentTransaction.commit()
		}

		// 파란 버튼 리스너
		blueButton.setOnClickListener {
			val fragmentTransaction = supportFragmentManager.beginTransaction()
			fragmentTransaction.replace(R.id.fragmentFrame, BlueFragment())
			fragmentTransaction.commit()
		}
	}
}
```

- settingButtons( ) : 뷰가 생성되었을 때 실행되어, 빨강과 파랑 버튼이 클릭되었을 때 행동의 정의
- 버튼 변수 초기화
    
    ```kotlin
    val redButton = findViewById<Button>(R.id.button_red_fragment)
    
    val redButton = ID로 뷰를 찾는 함수<뷰 유형>(리소스 ID)
    ```
    
- supportFragmentManager.beginTransaction() : 프래그먼트 트랜잭션(Fragment Transaction) 클래스의 객체 생성
    
    ```kotlin
    supportFragmentManager.beginTransaction()
    
    프래그먼트 관련 작업 수행.프래그먼트 트랜잭션 객체 생성
    ```
    
- 프래그먼트 트랜잭션을 사용하면 프래그먼트 추가, 삭제 혹은 기존 프래그먼트와 교체하고 백스택에 추가하는 등을 할 수 있음
</br></br>

> **백스택**

스택과 같이 태스크들이 쌓여있는 형태로 이해하면 됨  
이 경우에는 프래그먼트들이 쌓여있는 것
> 
</br>

- 트랜잭션에서 무엇을 할지 정의하는 곳
    
    ```kotlin
    fragmentTransaction.replace(R.id.fragmentFrame, RedFragment())
    
    replace(프래그먼트를 넣는 프레임 레이아웃 ID, 새로운 프래그먼트 객체)
    ```
    
    - replace( ) 로 기존의 프래그먼트를 새로운 프래그먼트로 교체
        - 첫 번째 인수 : 교체할 컨테이너의 ID 참조값
        → 액티비티 레이아웃 파일에서 생성했던 FrameLayout의 id를 **R.id.실제ID** 형식으로 넣으면 됨
        - 두 번째 인수 : 프래그먼트 객체

- 트랜잭션 이후에는 반드시 commit( ) 함수를 호출해야 함

- 앱을 켰을 때 처음 나오는 액티비티로 지정 → AndroidManifest.xml
    
    ```kotlin
    // 수정 전
    <activity
    	android:name=".MainColorActivity"
    	andorid:exported="true" />
    
    // 수정 후
    <activity android:name=".MainColorActivity"
    	andorid:exported="true">
    	<intent-filter>
    		...
    	</intent-filter>
    </activity>
    ```
    
</br>

> **import문 추가 방법 알아보기**

임포트 단축키
윈도우/리눅스 단축키 : Alt + enter
맥OS 단축키 : Option + enter
> 
</br></br>

## 핵심 요약

- 안드로이드 앱 4대 구성요소는 액티비티, 서비스, 브로드캐스트 리시버, 콘텐트 프로바이더
- 액티비티는 사용자에게 UI 화면을 제공하는 앱 구성요소
- 액티비티와 프래그먼트는 고유의 생명 주기가 존재하며, 생명 주기에 따라 해야 하는 것과 할 수 있는 활동이 나누어져 있음
- 인텐트는 사용자로 하여금 액티비티를 시작하거나, 서비스를 시작하는 등 다양한 활동을 할 수 있게끔 해 주는 메시징 객체
- 프래그먼트는 분할된 화면을 독립적으로 활용하고 재사용하기에 적절한, 액티비티에 종속된 UI 구성요소