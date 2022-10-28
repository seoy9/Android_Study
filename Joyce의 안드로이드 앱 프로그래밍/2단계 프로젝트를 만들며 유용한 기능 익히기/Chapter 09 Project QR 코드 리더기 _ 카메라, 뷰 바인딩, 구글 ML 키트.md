# Chapter 09 Project QR 코드 리더기 : 카메라, 뷰 바인딩, 구글 ML 키트

## Project QR 코드 리더기

| 난이도 | ★★★☆ |
| --- | --- |
| 이름 | QR 코드 리더기 만들기 |
| 프로젝트명 | QRCodeReader |
| 개발 환경 | - minSdk : 26</br></br>- targetSdk : 31 |
| 미션 | 구글 ML 키트 라이브러리와 CameraX를 이용해 QR 코드 리더기를 만들어보기 |
| 기능 | 카메라를 이용해 QR 코드 인식하기 |
| 조작법 | 인식하려는 QR 코드를 카메라로 찍기 |
| 핵심 구성요소 | - CameraX 라이브러리</br></br>- 뷰 바인딩</br></br>- 구글 ML 키트 라이브러리 |
</br></br>

## 9.1 사전 지식 : 뷰 바인딩

- 뷰를 액티비티에서 사용할 때 findViewById( ) 함수 사용  
→ 뷰 바인딩으로 더 간단하게 뷰를 사용 가능
</br></br>

### 9.1.1 뷰 바인딩의 탄생

- xml에서 작성한 뷰를 사용하는 데 findViewById( ) 함수를 이용
    
    ```kotlin
    class MainActivity : AppCompatActivity() {
    	... 생략 ...
    	lateinit var mButton : Button
    
    	override fun onCreate(savedInstanceState: Bundle?) {
    		super.onCreate(savedInstanceState)
    		setContentView(R.layout.activity_main)
    
    		mButton = findViewById(R.id.my_button)
    		... 생략 ...
    	}
    }
    ```
    
    - mButton이라는 변수 선언
    - onCreate( ) 함수 안에서 findViewById(R.id.my_button) 함수를 호출해 찾고자 하는 뷰 할당
    - 이렇게 뷰를 변수에 할당해주고 나서야 액티비티에 생성한 버튼을 사용할 수 있음
- findViewById( )와 같이 꼭 필요하고 간단하지만 반복적인 코드 = 보일러 플레이트 (Boiler Plate)
- 보일러 플레이트 코드를 없애려고 안드로이드 생태계는 많은 노력을 함
    - 바인딩이 제공되지 않았을 때 사용되던 버터나이프(ButterKnife) 라이브러리부터 코틀린 안드로이드 확장(Kotlin Android Extensions) 플로그인까지
    - 2017년에 발표된 코틀린 안드로이드 확장 플러그인은 보일러 플레이트 없이 바로 뷰를 사용할 수 있게끔 해주어 많은 개발자에게 사랑을 받기도 함
    - 하지만 같은 id를 여러 레이아웃에 사용하면 엉뚱한 레이아웃이 임포트되는 문제, 코틀린에서만 사용 가능한 점, 완전히 널 안전(Null Safe)하지 않은 문제 있었음
    - 그래서 해당 플로그인은 코틀린 1.4.20 저번부터 디프리케이티드됨
    - 대안 : 안드로이드 잿팩(Jetpack)에서 제공하는 뷰 바인딩
</br></br>

### 9.1.2 뷰 바인딩 사용 방법

- 뷰 바인딩
    - 안드로이드가 권장하는 방법
    - 뷰 컴포넌트를 이용해 편리하게 뷰와 소통하는 기능을 제공
- 작동 방식
    - 뷰 바인딩이 활성화
    - 우리가 작성하는 모든 xml 파일은 자동으로 각각 바인딩 클래스가 생성됨
        - 예) activity_main.xml 파일을 생성하면 자동으로 ActivityMainBinding 클래스가 생성됨
    - activity_main.xml이 뷰를 활용할 때 ActivityMainBinding 객체를 이용하면 됨
    - 귀찮은 일은 모두 자동으로 만들어진 바인딩 클래스가 대신해 줌

1. 뷰 바인딩은 모듈 기반으로 적용됨
    - [app] → [Gradle Scripts] → build.gradle(Module : ViewBindingSample.app) 에 코드 추가, 설정 적용
    
    ```kotlin
    android {
    	... 생략 ...
    	buildFeatures {
    		viewBinding true   // 이 코드를 추가
    	}
    }
    ```
    
2. 오른쪽 상단 상태 표시줄에 뜨는 [Sync now]를 눌러 새로운 설정 반영
3. 액티비티 레이아웃에 [Binding], [No Binding] 버튼 두 개 추가
    - [app] → [res] → [layout] → activity_main.xml
</br></br>

### 9.1.3 뷰 바인딩 파일 자세히 보기

- 뷰 바인딩은 각 XML 레이아웃 파일의 루트 뷰 및 ID가 있는 모든 뷰의 참조를 포함한 클래스를 자동으로 생성
- 클래스의 이름은 XML 파일의 이름을 카멜 표기법으로 변환하고 끝에 ‘Binding’을 추가하여 생성

1. 뷰 바인딩 설정을 추가했으므로 프로젝트 빌드 진행
    - IDE 상단의 망치 모양 버튼을 클릭하거나, 메뉴에서 [Build] → [Make Project] 선택
    - 일정 시간이 지나면, 빌드 완료
</br></br>

> **Run과  Build의 차이**

Build : 빌드란, 실행 가능한 형태의 파일 (apk, jar, aar)을 만드는 과정
Run : 앱이 빌드되고 난 후 기기(에뮬레이터와 실제 단말)에 배포(실행)하는 과정
> 
</br>

2. activity_main.xml의 뷰 바인딩 파일인 [ActivityMainBinding.java](http://ActivityMainBinding.java)를 직접 확인
1. Project 모드 선택
2. [app] → [build] → [generated] → [data_building_base_class_source_out] 클릭
3. ActivityMainBinding.java 클릭
2. 뷰 바인딩은 xml 파일에서 ID가 있는 뷰와 루트 뷰 참조를 자동으로 생성
    - ConstraintLayout을 rootView라는 변수로
    - @+id/binding_button이라는 ID 값을 가진 버튼 뷰를 bindingButton이라는 이름의 변수로 선언함
    - ID값을 지정하지 않은 버튼 뷰는 변수가 생성되지 않음
</br></br>

### 9.1.4 액티비티 파일에서 뷰 바인딩 사용하기

- ID는 뷰 바인딩을 사용하는 데 필수
- 모든 바인딩 클래스는 루트 뷰(root view)를 반환하는 getRoot( ) 함수 가지고 있음
</br></br>

> **루트 뷰란?**

루트 뷰를 최상위 뷰라고도 함
뷰들은 계층 구조를 가지고 레이아우승ㄹ 구성
루트 뷰는 단 하나 존재
모든 뷰들을 포함하는 뷰 = 루트 뷰
루트 뷰는 트리 구조를 생각해보았을 때 루트 노드라고 생각하면 됨
> 
</br>

1. [app] → [java] → [com.example.viewbindingsample] → MainActivity.kt 파일 열기
2. onCreate( ) 함수 작성
    
    ```kotlin
    class MainActivity : AppCompatActivity() {
    	private lateinit var binding : ActivityMainBinding
    
    	override fun onCreate(savedInstanceState : Bundle?) {
    		super.onCreate(savedInstanceState)
    
    		binding = ActivityMainBinding.inflate(layoutInflater)   // 바인딩 클래스의 객체 생성
    
    		val view = binding.root   // 바인딩 객체의 root 뷰 참조
    		setContentView(view)      // 생성한 뷰 설정
    	}
    }
    ```
    
    - 바인딩 클래스에 포함된 inflate( ) 함수를 실행해 바인딩 클래스의 객체를 생성
    - 모든 바인딩 클래스에는 getRoot( ) 함수 있음
    새로운 변수 view를 만들고 바인딩 객체의 root 뷰 참조
    - setContentView 함수에 생성한 변수 view를 넣어주어 화면에 보여줄 수 있게 함

- 이렇게 하면 뷰 바인딩을 사용할 모든 준비 끝
- 예를 들어 activity_main.xml 파일에서 ID가 binding_button인 버튼을 사용하는 코드
    
    ```kotlin
    binding.bindingButton.setOnClickListener {
    	// do something
    }
    ```
    
- 바인딩 클래스에서 자동으로 Button형 멤버 변수로 선언되었기 때문에 액티비티에서 사용만 하면 됨
- 뷰 바인딩 덕분에 어떤 뷰든지 ID값만 알면 binding.[ID_NAME] 형식으로 해당 뷰를 참조 가능
- 한 가지 유념해야 할 것은 바인딩 클래스에서는 첫 문자를 소문자로 표기하고 그 다음부터는 대문자로 표기하는 카멜 표기법으로 변수명을 생성
예) xml 파일에서 ID값을 “tv_name”으로 주면 tvName으로, “btn_login”으로 주면 btnLogin이 됨
</br></br></br>

## 9.2 사전 지식 : 안드로이드 젯팩

- 안드로이드 젯팩 : 구글 안드로이드팀에서 공식 발표한 라이브러리 모음
- 안드로이드 젯팩에 포함된 여러 라이브러리를 프로젝트 특성에 맞게 적용하면 안드로이드에서 권장하는 방식으로 퀄리티 높은 앱을 만들 수 있음
- 안드로이드 젯팩을 사용하면 얻는 이점
    - 안정성 : 모든 안드로이드 버전 및 다양한 기기에서 일관되게 작동하는 라이브러리이므로 복잡성을 낮추고 안정성을 높일 수 있음
    - 간편성 : 백그라운드 작업, 생명 주기 관리 등을 대신해주어 개발자가 앱을 만들 때 보일러 플레이트 코드를 줄이고 앱의 로직에만 집중할 수 있음
- 안드로이드 젯팩의 주요 라이브러리
    
    
    | 라이브러리 | 설명 |
    | --- | --- |
    | AppSearch | 전체 텍스트 검색으로 사용자를 위한 맞춤 인앱 검색 기능을 만드는 데 사용 |
    | CameraX | 앱에 카메라 기능을 제공 |
    | Compose | 선언형 UI 중의 하나로 더 적은 코드를 사용하여 효율적인 UI 개발을 제공 |
    | Data Binding | 뷰가 선언적 형식을 사용하여 데이터와 결합이 되도록 함 |
    | LiveData | 데이터 변경을 실시간으로 뷰에 반영 |
    | WorkManager | 백그라운드 작업, 비동기 작업 예약 등을 제공 |
    | Navigation | 액티비티, 프래그먼트 간 화면 이동을 용이하게 제공 |
    | Test | 테스트 관련 여러 유틸리티를 제공 |
    | ViewBinding | 뷰 컴포넌트를 이용해 편리하게 뷰와 소통하는 기능을 제공 |
- 이 외에도 굉장히 많은 라이브러리가 제공됨
- 참고로 안드로이드 젯팩 라이브러리는 모두 [androidx.이름]과 같은 패키지명으로 구성됨
- [공식 페이지](https://developer.android.com/jetpack)에서 들어가 최근 안드로이드 개발에서 가장 인기 있거나, 프로젝트에 도입할 만한 안드로이드 젯팩 라이브러리가 있는지 확인 가능
→ 훨씬 모던하고 안정적인 앱 개발 가능
- QR 리더기 프로젝트에는 사용하기 편한 카메라 API를 제공해주는 CameraX와 뷰와 상호작용을 도와주는 ViewBinding 라이브러리 사용
</br></br></br>

## 9.3 사전 지식 : CameraX 라이브러리

- 구글 ML 키트 : 구글 머신 러닝 기술을 안드로이드, iOS와 같은 모바일 기기에서 사용할 수 있게 해주는 라이브러리
- 사용하기 쉬운 API 제공, 모바일 환경에 최적화된 환경을 제공
→ 모바일 기기에서 바코드 스캐닝, 얼굴 인식, 텍스트 인식과 같은 기능을 사용하고 싶을 때 직접 개발하기보다는 구글 ML 키트와 같은 라이브러리를 적극 활용하는 것이 좋음
- 대표적인 API
    - 바코드 스캐닝 API : 바코드를 스캔하고 해석하는 API 제공
    - 얼굴 인식 API : 얼굴을 인식하거나 얼굴의 요소들을 인식
    - 텍스트 인식 API : 이미지로부터 텍스트를 인식하거나 추출
    - 포즈 인식 API : 사람 몸의 자세를 실시간으로 인식
    - 언어 감지 API : 주어진 텍스트가 쓰여진 언어가 무엇인지를 알려줌
- 이 외에도 굉장히 많은 API 제공, 궁금하면 [구글 ML 키트 홈페이지](https://developers.google.com/ml-kit) 방문
</br></br></br>

## 9.5 준비하기 : 프로젝트, 뷰 바인딩, 라이브러리

### 9.5.1 프로젝트 생성

- 액티비티 : Empty Activity
- 프로젝트 이름 : QRCodeReader
- 언어 : Kotlin
- Minimum SDK : API 26 : Android 8.0 (Oreo)
</br></br>

### 9.5.2 뷰 바인딩 설정과 라이브러리 추가

1. 뷰 바인딩 설정
    - [Gradle Scripts] 아래 모듈 수준의 build.gradle 파일 android 태그 안에 코드 추가해 뷰 바인딩 설정
    
    ```kotlin
    android {
    	compileSdk 31
    
    	defaultConfig {
    		applicationId "com.example.qrcodereader"
    		minSdk 26
    		targetSdk 31
    		versionCode 1
    		versionName "1.0"
    
    		testInstrumrntationRummer "androidx.test.runner.AndroidJUnitRunner"
    	}
    
    	... 생략 ...
    	buildFeatures {
    		viewBinding true   // 이 코드를 추가
    	}
    }
    ```
    
2. Gradle 파일을 수정하면 Sync 하라는 표시줄이 뜸 → [Sync now] 클릭

- 이것으로 프로젝트에서 뷰 바인딩을 쓸 준비 모두 마침

3. 사용할 라이브러리 추가
    - 첫 번째, 카메라를 쉽게 활용할 수 있는 CameraX 라이브러리
    - 두 번째, 머신러닝을 직접 구현하지 않고도 원하는 기능을 손쉽게 사용할 수 있는 구글 ML 키트 라이브러리
    - [Gradle Scripts]에서 모듈 수준의 build.gradle 파일 dependencies 태그 안에 4개의 라이브러리들을 임포트
    
    ```kotlin
    dependencies {
    	... 생략 ...
    
    	def camerax_version = "1.1.0-alpha04"
    	// CameraX의 코어 라이브러리
    	implementation "androidx.camera:camera-camera2:$camerax_version"
    	// CameraX의 생명 주기 관련 라이브러리
    	implementation "androidx.camera:camera-lifecycle:$camerax_version"
    	// CameraX의 뷰 관련 라이브러리
    	implementation "androidx.camera:camera-view:1.0.0-alpha24"
    
    	// ML kit 라이브러리
    	implementation "com.google.mlkit:barcode-scanning:16.1.1"
    }
    ```
    
2. [Sync Now] 클릭

- 이렇게 뷰 바인딩 설정과 프로젝트에 필요한 라이브러리 모두 추가
</br></br></br>

## 9.6 레이아웃 구성하기

- 화면 1 : QR 코드를 찍는 리더기 페이지 → MainActivity.kt에 구현
- 화면 2 : QR 코드를 정상 인식했을 때 결과 페이지 → 새로운 액티비티를 생성해 구현

1. [app] → [java] → [com.example.qrcodereader] 우클릭 → [New] → [Activity] → [Empty Activity] 클릭
2. 결과 페이지 이름으로 ResultActivity 적고, [Finish] 클릭 (액티비티 생성)
</br></br>

### 9.6.1 MainActivity 레이아웃 작성하기

1. [app] → [res] → [layout] → activity_main.xml 에서 기존 코드 모두 지우기
2. 화면에 꽉 차게 PreviewView 배치
    - MainActivity는 QR 코드 리더기 화면으로서 카메라 미리보기 기능만 있으면 됨
    
    ```kotlin
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
    	xmlns:android="http://schemas.android.com/apk/res/android"
    	android:layout_width="match_parent"
    	android:layout_height="match_parent">
    
    	<androidx.camera.view.PreviewView
    		android:id="@+id/barcode_preview"
    		android:layout_width="match_parent"
    		android:layout_height="match_parent" />
    
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```
    
</br>

### 9.6.2 ResultActivity 레이아웃 작성하기

- 텍스트뷰 2개, 버튼 1개

1. [app] → [res] → [layout] → activity_result.xml 에서 기존 코드 모두 지우기
2. 레이아웃 코드 작성
    
    ```kotlin
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout
    	xmlns:android="http://schemas.android.com/apk/res/android"
    	xmlns:app="http://schemas.android.com/apk/res-auto"
    	xmlns:tools="http://schemas.android.com/tools"
    	android:layout_width="match_parent"
    	android:layout_height="match_parent"
    	tools:context=".ResultActivity">
    
    	<!-- 결괏값 텍스트뷰 -->
    	<TextView
    		android:id="@+id/tv_title"
    		android:layout_width="wrap_content"
    		android:layout_height="wrap_content"
    		android:layout_marginTop="168dp"
    		android:text="결괏값 : "
    		android:textSize="24sp"
    		android:textStyle="bold"
    		app:layout_constraintEnd_toEndOf="parent"
    		app:layout_constraintStart_toStartOf="parent"
    		app:layout_constraintTop_toTopOf="parent" />
    
    	<!-- QR 코드 인식 결과 자리 -->
    	<TextView
    		android:id="@+id/tv_content"
    		android:layout_width="wrap_content"
    		android:layout_height="wrap_content"
    		android:layout_marginTop="48dp"
    		android:text="결괏값이 들어갈 자리"
    		android:textSize="18sp"
    		app:layout_constraintEnd_toEndOf="parent"
    		app:layout_constraintStart_toStartOf="parent"
    		app:layout_constraintTop_toBottomOf="@+id/tv_title" />
    
    	<!-- [돌아가기] 버튼 -->
    	<Button
    		android:id="@+id/btn_go_back"
    		android:layout_width="wrap_content"
    		android:layout_height="wrap_content"
    		android:layout_marginBottom="68dp"
    		android:text="돌아가기"
    		app:layout_constraintBottom_toBottomOf="parent"
    		app:layout_constraintEnd_toEndOf="parent"
    		app:layout_constraintStart_toStartOf="parent" />
    
    </androidx.constraintlayout.widget.ConstraintLayout>
    ```
    
    - 결괏값이라는 단어를 보여줄 텍스트뷰 추가
    - QR 코드 인식 결괏값이 들어갈 텍스트뷰 추가
    - 메인 액티비티 화면으로 [돌아가기] 버튼 추가
</br></br></br>

## 9.7 카메라 미리보기 화면 구현하기

- QR 코드 리더기 페이지는 총 2가지 기능으로 구성됨
    1. CameraX를 이용한 미리보기 기능
    2. CameraX와 구글 ML 키트를 이용한 QR 코드 인식 기능
</br></br>

### 9.7.1 미리보기 구현하기

- CameraX를 이용한 미리보기 기능을 MainActivity에서 구현

1. [app] → [java] → [com.example.qrcodereader] → MainActivity.kt 에서 코드 작성
    
    ```kotlin
    class MainActivity : AppCompatActivity() {
    	private lateinit var binding : ActivityMainBinding   // 바인딩 변수 생성
    
    	override fun onCreate(savedInstanceState: Bundle?) {
    		super.onCreate(savedInstanceState)
    
    		// 뷰 바인딩 설정
    		binding = ActivityMainBinding.inflate(layoutInflater)
    		val view = binding.root
    		setContentView(view
    	}
    
    	// 미리보기와 이미지 분석 시작
    	fun startCamera() {
    	}
    
    	// 미리 보기 객체 반환
    	fun getPreview() : Preview {
    	}
    }
    ```
    
    - binding 변수 생성
    - 뷰 바인딩 설정
        - binding.root는 바인딩 클래스라면 항상 자동으로 생성되는 루트 뷰를 반환
    - startCamera( ) : 카메라를 시작하는 함수
    - getPreview( ) : 미리보기 기능을 설정하고 설정이 완료된 객체를 반환하는 함수
2. getPreview( ) 함수 작성
    
    ```kotlin
    // 미리보기 객체를 반환
    fun getPreview(): Preview {
    	// Preview 객체 생성
    	val preview : Preview = Preview.Builder().build()
    	preview.setSurfaceProvider(binding.barcodePreview.getSurfaceProvider())
    
    	return preview
    }
    ```
    
    - Preview 객체 생성
    - setSurfaceProvider( ) 함수
        - Preview 객체에 SurfaceProvider를 설정
        - SurfaceProvider : Preview에 Surface를 제공해 주는 인터페이스
        - Surface : 화면에 보여지는 픽셀들이 모여 있는 객체
        - 인수로는 activity_main.xml에서 ID 값이 android:id=”@+id/barcode_preview”인 Preview 뷰의 SurfaceProvider를 줌
3. startCamera( ) 함수 작성
    
    ```kotlin
    class MainActivity : AppCompatActivity() {
    	private lateinit var binding : ActivityMainBinding   // 바인딩 변수 생성
    
    	// ListenableFuture형 변수 선언
    	private lateinit var cameraProviderFuture : ListaenableFuture<ProcessCameraProvider>
    
    	override fun onCreate(savedInstanceState: Bundle?) {
    		super.onCreate(savedInstanceState)
    
    		// 뷰 바인딩 설정
    		binding = ActivityMainBinding.inflate(layoutInflater)
    		val view = binding.root
    		setContentView(view)
    
    		// 카메라 시작
    		startCamera()
    	}
    
    	// 미리보기와 이미지 분석 시작
    	fun startCamera() {
    		cameraProviderFuture = ProcessCameraProvider.getInstance(this)
    		cameraProviderFuture.addListner(Runnable {
    			val cameraProvider = cameraProviderFuture.get()
    			// 미리보기 객체 가져오기
    			val preview = getPreview()
    			// 후면 카메라 선택
    			val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    
    			// 미리보기 기능 선택
    			cameraProvider.bindToLifecycle(this, cameraSelector, preview)
    		}, ContextCompat.getMainExecutor(this))
    	}
    
    	// 미리 보기 객체 반환
    	fun getPreview() : Preview {
    	}
    }
    ```
    
    - ListenableFuture형 변수 선언
        - ListenableFuture에 태스크가 제대로 끝났을 때 동작을 지정해 줄 수 있음
        - Future : 안드로이드의 병렬 프로그래밍에서 태스크가 제대로 끝났는지 확인할 때 씀
    - ProcessCameraProvider.getInstance(this)
        - cameraProviderFuture에 객체의 참조값 할당
    - cameraProviderFuture.addListner(Runnable
        - cameraProviderFuture 태스크가 끝나면 실행됨
    - cameraProviderFuture.get( )
        - 카메라의 생명 주기를 액티비티나 프래그먼트와 같은 생명 주기에 바인드해 주는 ProcessCameraProvider 객체를 가져옴
    - getPreview( )
        - 미리보기 객체를 가져옴
    - CameraSelector.DEFAULT_BACK_CAMERA
        - 후면 카메라(DEFAULT_BACK_CAMERA)를 선택
        - 전면 카메라 : DEFAULT_FRONT_CAMERA
    - cameraProvider.bindToLifecycle(this, cameraSelector, preview)
        - 미리보기, 이미지 분석, 이미지 캡쳐 중 무엇을 쓸지 지정
        - 하나 이상 선택할 수도 있음
        - 미리보기(preview)만 넣음
    - startCamera( )
        - onCreate( )에 startCamera( ) 함수 넣기
4. 미리보기가 잘 되나 실행
    - [Run] → [Run ‘app’] 선택
    - 까맣게 나옴 → 사용자로부터 카메라 사용 권한을 승인받지 않았기 때문
</br></br>

### 9.7.2 카메라 권한 승인하기

1. [app] → [manifests] → AndroidManifest.xml 에서 태그 2개 추가
    
    ```kotlin
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
    	xmlns:tools="http://schemas.android.com/tools"
    	package="hong.sy.qrcodereader">
    	
    	<!-- 카메라 사용 명시 -->
    	<uses-feature android:name="android:hardware.camera" />
    	<!-- 카메라 권한 추가 -->
    	<uses-permission android:name="android.permission.CAMERA" />
    ```
    
    - <uses-feature android:name="android:hardware.camera" />
        - 카메라 사용 명시
        - <uses-feature>를 사용해서 하드웨어 및 소프트웨어의 기능 요구 사항을 명시 가능
        - 구글 플레이에 앱을 올리면 카메라가 있는 기기에서만 다운로드 가능
    - <uses-permission android:name="android.permission.CAMERA" />
        - <uses-permission>로 앱을 실행할 때 사용자가 승인해야 하는 권한 명시
        - 카메라 권한 추가
2. MainActivity.kt 에서 권한을 확인하는 코드 추가
    
    ```kotlin
    // 태그 기능 코드, 권한 요청 후 결과 받을 때 필요
    private val PERMISSONS_REQUEST_CODE = 1
    // 카메라 권한 지정
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)
    
    override fun onCreate(savedInstanceState: Bundle?) {
    	super.onCreate(savedInstanceState)
    
    	// 뷰 바인딩 설정
    	binding = ActivityMainBinding.inflate(layoutInflater)
    	val view = binding.root
    	setContentView(view)
    
    	if(!hasPermissions(this)) {
    		// 카메라 권한을 요청
    		requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
    	} else {
    		// 만약 권한이 있다면 카메라를 시작
    		startCamera()
    	}
    }
    
    // 권한 유무 확인
    fun hasPermissions(context: Context) = PERMISSONS_REQUIRED.all {
    	ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
    
    // 권한 요청 콜백 함수
    override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    	super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    
    	// requestPermissions의 인수로 ㄴ허은 PERMISSONS_REQUEST_CODE와 맞는지 확인
    	if(requestCode == PERMESSIONS_REQUEST_CODE) {
    		if(PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
    			Toast.makeText(this@MainActivity, "권한 요청이 승인되었습니다.", Toast.LENGTH_LONG).show()
    			startCamera()
    		} else {
    			Toast.makeText(this@MainActivity, "권한 요청이 거부되었습니다.", Toast.LENGTH_LONG).show()
    			finish()
    		}
    	}
    }
    ```
    
    - PERMISSONS_REQUEST_CODE
        - 태그 기능을 하는 코드
        - 일종의 이름표를 붙여놓는 것
        - 나중에 권한을 요청한 후 결과를 onRequestPermissionsResult 에서 받을 때 필요
        - 0과 같거나 큰 양수이기만 하면 어떤 수든 상관없음
    - PERMISSIONS_REQUIRED
        - 카메라 권한 지정
    - override fun onRequestPermissionResult
        - 권한이 있는지 없는지 확인
        - all
            - PERMISSIONS_REQUIRED 배열의 원소가 모두 조건문을 만족하면 true를 반환
            - 그렇지 못하면 false 반환
        - if(requestCode == PERMESSIONS_REQUEST_CODE) { }
            - 권한이 수락되면 startCamera( ) 호출
            - 권한이 거부되는 경우 액티비티를 종료하는 finish( ) 호출
    - if(!hasPermissions(this)) { } else { }
        - onCreate( ) 에서 만들어둔 함수 사용
        - 처음 액티비티가 실행될 때 권한을 체크
            - 권한이 있으면 startCamera( )
            - 권한이 없으면 권한 요청
3. [Run] → [Run ‘app’] 선택하여 앱 실행
    - 권한 허용 팝업이 뜨면 → [허용] 을 눌러야 → 카메라가 실행됨
</br></br>

### 9.7.3 MainActivity.kt 전체 코드

```kotlin
class MainActivity : AppCompatActivity() {
	// 바인딩 변수 생성
	private lateinit var binding : ActivityMainBinding
	// ListenableFuture형 변수 선언
	private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
	// 태그 기능 코드, 권한 요청 후 결과 받을 때 필요
	private val PERMISSIONS_REQUEST_CODE = 1
	// 카메라 권한 지정
	private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

	override fun onCreate(savedInstanceState: Bundle?) {
		 super.onCreate(savedInstanceState)

		// 뷰 바인딩 설정
		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		if(!hasPermissions(this)) {
		// 카메라 권한을 요청
			requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
		} else {
			// 만약 권한이 있다면 카메라를 시작
			startCamera()
		}
	}

	// 권한 유무 확인
	fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
		ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
	}

	// 권한 요청 콜백 함수
	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		// requestPermissions의 인수로 넣은 PERMISSONS_REQUEST_CODE와 맞는지 확인
		if(requestCode == PERMISSIONS_REQUEST_CODE) {
			if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
				Toast.makeText(this@MainActivity, "권한 요청이 승인되었습니다.", Toast.LENGTH_LONG).show()
				startCamera()
			} else {
				Toast.makeText(this@MainActivity, "권한 요청이 거부되었습니다.", Toast.LENGTH_LONG).show()
				finish()
			}
		}
	}

	// 미리보기와 이미지 분석 시작
	fun startCamera() {
		cameraProviderFuture = ProcessCameraProvider.getInstance(this)
		cameraProviderFuture.addListener(Runnable {
			val cameraProvider = cameraProviderFuture.get()
			// 미리보기 객체 가져오기
			val preview = getPreview()
			// 후면 카메라 선택
			val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

			// 미리보기 기능 선택
			cameraProvider.bindToLifecycle(this, cameraSelector, preview)
		}, ContextCompat.getMainExecutor(this))
	}

	// 미리 보기 객체 반환
	fun getPreview() : Preview {
		// Preview 객체 생성
		val preview : Preview = Preview.Builder().build()
		preview.setSurfaceProvider(binding.barcodePreview.getSurfaceProvider())

		return preview
	}
}
```
</br></br>

## 9.8 QR 코드 인식 기능 구현하기

- QR 코드 인식 후, QR 코드의 데이터를 팝업 메시지로 출력
    1. CameraX의 Analyzer 클래스 만들기
    2. onDetect( ) 메서드가 있는 인터페이스 만들기
    3. MainActivity.kt에서 Analyzer와 카메라 연동하기
</br></br>

### 9.8.1 CameraX의 Analyzer 클래스 만들기

- CameraX의 두 번째 사용 사례 : 이미지 분석
- ImageAnalysis.Anaylzer 인터페이스 구현한 Analyzer 클래스 만들어야 함
- 실제 이 클래스에서 분석이 이루어짐
- 분석에는 구글 ML 키트 API 사용

1. QRCodeAnalyzer 클래스 생성
    - [app] → [java] → [com.example.qrcodereader] 우클릭 → [New] → [Kotlin Class/File] 선택
    - 클래스 이름으로 QRCodeAnalyzer 입력 → [enter]
2. QRCodeAnalyzer 클래스의 기본 골격
    - ImageAnalysis.Analyzer 인터페이스는 analyze( ) 함수 하나만 오버라이드하면 됨
    
    ```kotlin
    class QRCodeAnalyzer : ImageAnalysis.Analyzer {
    	// 바코드 스캐닝 객체 생성
    	private val scanner = BarcodeScanning.getClient()
    
    	@SuppressLint("UnsafeOptInUsageError")
    	override fun analyze(image: ImageProxy) {
    	}
    }
    ```
    
    - BarcodeScanning.getClient( )
        - 바코드 스캐닝 할 수 있는 객체를 scanner 변수에 할당
        - 빨간 줄이 뜨면, ML 키트 라이브러리가 제대로 임포트 되지 않은 것
        - [[9.5.2]](https://www.notion.so/Chapter-09-Project-QR-ML-ce13448b379d4918aaf28e4ec4288ddb)로 돌아가서 설정 확인
3. analyze( ) 함수 작성
    
    ```kotlin
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
    	val mediaImage = imageProxy.image
    	
    	if(mediaImage != null) {
    		val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
    		scanner.process(image)
    			.addOnSuccessListener { qrCodes ->
    				// 리스너가 들어갈 자리
    			}
    			.addOnFailureListener {
    				it.printStackTrace()
    			}
    			.addOnCompleteListener {
    				imageProxy.close()
    			}
    	}
    }
    ```
    
    - InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        - 이미지가 찍힐 당시 카메라의 회전 각도를 고려하여 입력 이미지를 생성
    - scanner.process(image)
        - 이를 통해 이미지를 분석
        - SuccessListner, FailureListener, CompleteListner를 각각 달아주어 결과를 확인 가능
        - 현재 addOnFailureListener{ } : 실패 시 에러를 로그에 프린트
        - addOnCompleteListner{ } : 스캐너가 이미지 분석을 완료했을 때 이미지 프록시를 닫는 작업
        - addOnSuccessListner{ } : QR 코드 인식이 성공했을 때의 작업 (메인 액티비티에게 “QR 코드 인식 성공, 데이터는 이것” 하고 말해주는 것 → 인터페이스가 함)
</br></br>

### 9.8.2 onDetect() 메서드가 있는 인터페이스 만들기

- MainActivity에서 QRCodeAnalyzer를 이용해 QR 코드 속에 있는 데이터를 가져오려고 함
- 현재 일어나고 있는 문제 상황
    1. 메인 액티비티에서 QRCodeAnalyzer에 QR 코드 이미지 보냄
    2. QRCodeAnalyzer에서 QR 코드 데이터 인식 완료
    3. 하지만 QRCodeAnalyzer에서 메인 액티비티로 인식한 데이터를 보낼 방법 없음
- 아직 QR 코드 데이터는 QRCodeAnalyzer 클래스 속에만 존재하기 때문에 MainActivity는 알 수가 없음
⇒ MainActivity 클래스와 QRCodeAnalyzer 클래스 사이에 소통 창구가 없음
- 이것을 인터페이스가 해결 가능
    - onDetect( ) 함수가 있는 onDetectListener 인터페이스 생성
    - MainActivity에서 이 onDetectListener 인터페이스를 구현하고, 해당 인터페이스 객체를 QRCodeAnalyzer에 전달
    - QRCodeAnalyzer는 QR 코드를 인식
    - 전달받은 인터페이스의 onDetect( )를 호출
    - 미리 MainActivity에서 구현한 onDetect( ) 함수가 실행됨
    - MainActivity에서는 onDetect( )가 실행됨으로써 데이터가 감지되었다는 것을 알 수 있음

1. [app] → [java] → [com.example.qrcodereader] 우클릭 → [New] → [Kotlin Class/File] 선택
2. Interface 선택 → onDetectListener로 이름 설정 → [enter]
3. 코드 작성
    
    ```kotlin
    interface OnDetectListener {
    	fun onDetect(msg : String)
    }
    ```
    
    - onDetect( ) : QRCodeAnalyzer에서 QR 코드가 인식되었을 때 호출할 함수로서, 데이터 내용을 인수로 받음
4. 인터페이스를 [app] → [java] → [com.example.qrcodereader] → QRCodeAnalyzer.kt의 QRCodeAnalyzer 클래스에 적용
    
    ```kotlin
    class QRCodeAnalyzer(val onDetectListener: OnDetectListener) : ImageAnalysis.Analyzer {
    	// 바코드 스캐닝 객체 생성
    	private val scanner = BarcodeScanning.getClient()
    
    	@SuppressLint("UnsafeOptInUsageError")
    	override fun analyze(image: ImageProxy) {
    		val mediaImage = imageProxy.image
    		
    		if(mediaImage != null) {
    			val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
    			scanner.process(image)
    				.addOnSuccessListener { qrCodes ->
    					for(qrCode in qrCodes) {
    						onDetectListener.onDetect(qrCode.rawValue ?: "")
    					}
    				}
    				.addOnFailureListener {
    					it.printStackTrace()
    				}
    				.addOnCompleteListener {
    					imageProxy.close()
    				}
    		}
    	}
    }
    ```
    
    - class QRCodeAnalyzer(val onDetectListener: OnDetectListener)
        - MainActivity에서 QR 코드 데이터를 인식하려면 QRCodeAnalyzer 객체를 생성할 때 onDetectListener를 구현하여 주 생성자의 인수로 넘겨주어야 함
        - QRCodeAnalyzer에서는 이 리스너를 통하여 MainActivity와 소통할 수 있게 됨
    - for(qrCode in qrCodes) { }
        - QRCode가 성공적으로 찍히게 되면 onDetectListener.onDetect(qrCode.rawValue?: “ “)를 실행
        - rawValue가 존재하면 그 값을 보내고 값이 null이면 빈 문자열을 보냄
        - qrCodes와 같은 배열이 넘어오는 이유는 한 화면에 다수의 QR 코드가 찍히게 되면 모든 QR 코드를 분석해 각각 배열로 보내기 때문
</br></br>

### 9.8.3 MainActivity.kt에서 Analyzer와 카메라 연동하기

1. [app] → [java] → [com.example.qrcodereader] → MainActivity.kt 에 코드 추가
    - getImageAnalysis( ) 함수 추가
    - startCamera( ) 함수 수정
    
    ```kotlin
    class MainActivity : AppCompatActivity() {
    	... 생략 ...
    
    	fun getImageAnalysis() : ImageAnalysis {
    		val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    		val imageAnalysis = ImageAnalysis.Builder().build()
    
    		imageAnalysis.setAnalyzer(cameraExecutor,
    			QRCodeAnalyzer(object : OnDetectListener {
    				override fun onDetect(msg: String) {
    					Toast.makeText(this@MainActivity, "${msg}", Toast.LENGTH_SHORT).show()
    				}
    			}))
    
    		return imageAnalysis
    	}
    
    	... 생략 ...
    
    	// 미리보기와 이미지 분석 시작
    	fun startCamera() {
    		cameraProviderFuture = ProcessCameraProvider.getInstance(this)
    		cameraProviderFuture.addListener(Runnable {
    			val cameraProvider = cameraProviderFuture.get(
    			val preview = getPreview()
    			// IamgeAnalysis 객체 생성
    			val imageAnalysis = getImageAnalysis()
    			val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    
    			cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
    		}, ContextCompat.getMainExecutor(this))
    	}
    
    	... 생략 ...
    }
    ```
    
    - imageAnalysis.setAnalyzer( )
        - QRCodeAnalyzer 객체를 생성해 setAnalyzer( ) 함수의 인수로 넣어줌
        - QRCodeAnalyzer는 onDetectListener 인터페이스를 구현해야 함
        - object를 통해 인터페이스 객체를 만들어주고, onDetect( ) 함수를 오버라이드
        - onDetect( ) 함수가 QRCodeAnalyzer에서 불렸을 때 행동을 여기서 정의
            - 메시지 내용을 토스트(팝업) 형식으로 보여줌
    - getImageAnalysis()
        - ImageAnalysis 클래스의 객체 생성
    - cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        - 생성한 객체 imageAnalysis 객체를 기존에 있었던 인수에 추가로 넣음
        - 이렇게 함으로써 CameraX 라이브러리의 미리보기 기능뿐 아니라 이미지 분석 기능까지 사용 간으
2. [Run] → [Run ‘app’] 선택 → 실행
</br></br></br>

## 9.9 결과 화면 구현하기

- QR 코드의 데이터가 팝업 메시지로 뜨면 일정시간이 지나 화면에서 사라짐
- 결과 페이지에서 결과를 확인하도록 만들어 시간이 지나도 메시지가 남도록 구현

1. 인식이 되면 ResultActivity를 실행할 수 있도록 MainActivity를 수정
    
    ```kotlin
    class MainActivity : AppCompatActivity() {
    	... 생략 ...
    	// onDetect() 함수 중복 호출 제어 변수
    	private var isDetected = false
    
    	... 생략 ...
    
    	override fun onResume() {
    		super.onResume()
    		isDetected = false
    	}
    
    	... 생략 ...
    
    	fun getImageAnalysis() : ImageAnalysis {
    		val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    		val imageAnalysis = ImageAnalysis.Builder().build()
    
    		// Analyzer를 설정
    		imageAnalysis.setAnalyzer(cameraExecutor,
    			QRCodeAnalyzer(object : OnDetectListener {
    				override fun onDetect(msg: String) {
    					if(!isDetected) {
    						// 데이터가 감지되었으므로 true로 변경
    						isDetected = true
    						val intent = Intent(this@MainActivity, ResultActivity::class.java)
    						intent.putExtra("msg", msg)
    						startActivity(intent)
    					}
    				}
    		}))
    
    		return imageAnalysis
    	}
    
    	... 생략 ...
    }
    ```
    
    - private var isDetected = false
        - 이미지 분석이 실시간으로 계속해서 이루어지므로 onDetect( ) 함수가 여러 번 호출될 수 있음
        - 이를 막는 데 사용할 변수 isDetected 생성
    - override fun onResume( ) { }
        - 다시 사용자의 포커스가 MainActivity로 돌아온다면 다시 QR 코드를 인식하라 수 있도록 onResume( ) 함수를 오버라이드
        - isDetected를 false로 다시 되돌려줌
        - 안드로이드 생명 주기가 헷갈리면 [[4.2.2]](https://www.notion.so/Chapter-04-_-9743032c80984b5787e793c71bb61d1a)
    - if(!isDetected)
        - 한 번도 QR 코드가 인식된 적이 없는지 검사
        - 중복 실행을 막는 코드
    - isDetected = true … startActivity(intent)
        - 다음 액티비티로 이동할 수 있도록 인텐트 생성
        - 인텐트 : 내가 어디로, 어떤 정보를 가지고 갈지 정할 수 있음
        - intent.putExtra(”msg”, msg) 를 통해 다음 액티비티로 데이터를 키-쌍의 형태로 넘길 수 있음
            - 첫 번째 인수 “msg”는 키
            - 두 번째 인수 msg는 override fun onDetect(msg: String)에서 넘어온 문자열
2. ResultActivity에서 인텐트로 넘긴 데이터를 화면에 보여주고 [돌아가기] 버튼을 활성화
    
    ```kotlin
    class ResultActivity : AppCompatActivity() {
    	lateinit var binding : ActivityResultBinding
    
    	override fun onCreate(savedInstanceState: Bundle?) {
    		super.onCreate(savedInstanceState)
    
    		binding = ActivityResultBinding.inflate(layoutInflater)
    		val view = binding.root
    		setContentView(view)
    
    		val result = intent.getStringExtra("msg") ?: "데이터가 존재하지 않습니다."
    
    		// UI 초기화
    		setUI(result)
    	}
    
    	private fun setUI(result: String) {
    		// 넘어온 QR 코드 속 데이터를 텍스트뷰에 설정
    		binding.tvContent.text = result
    		binding.btnGoBack.setOnClickListener {
    			// [돌아가기] 버튼을 눌러줬을 때 ResultActivity를 종료			
    			finish()
    		}
    	}
    }
    ```
    
    - lateinit var binding : ActivityResultBinding … setcontentView(view)
        - binding 변수 선언
        - 뷰 바인딩 설정
    - val result = intent.getStringExtra("msg") ?: "데이터가 존재하지 않습니다."
        - MainActivity에서 보낸 데이터 받음
        - 값이 없으면 “데이터가 존재하지 않습니다.”를 result 변수에 넣음
    - private fun setUI(result: String) { }
        - 화면을 초기화하는 작업
        - 텍스트뷰에 데이터를 넣고, [돌아가기] 버튼을 눌렀을 때 ResultActivity가 종료되는 로직 넣음
        - 함수를 만들고 나서 onCreate( )에서 setUI(result) 추가하는 것 잊지 말기
</br></br></br>

## 9.10 테스트하기

1. QR 코드가 정상적으로 인ㅅ기이 되고 결과 페이지로 잘 넘어가는지 확인
    - 메뉴 → [Run] → [Run ‘app’] 선택 → 실행
2. QR 코드 찍어보기
    - 인식이 완료되면 결과 페이지로 넘어가고 [돌아가기] 버튼을 누르면 이전 페이지로 넘어가는지 확인
</br></br>

> **‘Duplicate class com.google.android.gms.internal.mlkit’**

컴파일 이후 빌드 과정에서 이와 같은 에러가 발생하면, 모듈 수준의 build.gradle가 예제 코드와 같은지 확인
임포트 과정에서 엉뚱한 라이브러리를 추가했을 수도 있음
> 
</br></br>

## 핵심 요약

- 구글 ML 키트 : 구글 머신 러닝 기술을 제공하는 라이브러리
    - 바코드 스캐닝, 얼굴 인식, 텍스트 인식, 포즈 인식, 언어 감지 API 등을 제공
- 안드로이드 젯팩 라이브러리 모음 중에 하나인 CameraX 라이브러리를 이용하면 카메라를 이용한 앱을 훨씬 쉽게 만들 수 있음
- 뷰 바인딩을 사용하면 뷰를 참조할 때 findViewById( )와 같은 보일러 플레이트 코드를 줄일 수 있음
- \<uses-feature>를 사용해서 하드웨어 및 소프트웨어의 기능 요구사항을 명시 가능
- \<uses-permission>를 사용해서 앱을 실행할 때 사용자가 승인해야 하는 권한을 명시 가능