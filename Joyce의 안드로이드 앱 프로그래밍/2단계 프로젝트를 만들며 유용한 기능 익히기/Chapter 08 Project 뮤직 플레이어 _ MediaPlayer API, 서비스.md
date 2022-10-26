# Chapter 08 Project 뮤직 플레이어 : MediaPlayer API, 서비스

## Project 뮤직 플레이어

| 난이도 | ★★☆☆ |
| --- | --- |
| 이름 | 뮤직 플레이어 |
| 프로젝트명 | SimpleMusicPlayer |
| 개발 환경 | - minSdk : 26</br>- tartgetSdk : 31 |
| 미션 | MP3 파일을 직접 재생해보자 |
| 기능 | - MP3 파일 재생, 일시정지, 재생중지</br>- 서비스를 이용하여 앱이 종료되어도 재생하기 |
| 조작법 | - 재생 버튼을 눌러 음악을 재생</br>- 일시정지 버튼을 눌러 음악을 일시정지</br>- 재생중지 버튼을 눌러 음악을 완전히 멈춤</br>- 음악 재생 중 앱이 종료되어도 계속 재생하고, 다시 앱을 열었을 때 이어서 재생</br>- 음악을 재생 중이지 않을 때는 앱을 종료하면 완전히 종료 |
| 핵심 구성요소 | - 서비스</br>- 미디어 플레이어 |
</br></br>

## 8.0 들어가며

### 8.0.1 프로젝트 구상하기

- MediaPlayer API : 사운드 및 동영상을 재생
- 서비스 : 앱이 종료되더라도 MediaPlayer가 계속 재생할 수 있도록 해줌
→ 액티비티와 함께 안드로이드 4대 구성요소 중 하나, 안드로이드 개발자라면 꼭 숙지해야 할 개념
</br></br></br>

## 8.1 사전 지식 : 오디오 재생 MediaPlayer API

- MediaPlayer API : 안드로이드에서 다양한 유형의 미디어 재생을 지원
- MediaPlayer API로 오디오를 재생할 때 음원을 가져와 재생하는 방법은 다양함
    - Raw 리소스를 사용해 재생
    - URI를 사용해 재생
    - 등등
</br></br>

### 8.1.1 Raw 리소스를 사용해 재생하기

- Raw 리소스를 가져와 실행하는 방법 (뮤직 플레이어에 사용할 방법)
    1. [res] 폴더에 [raw] 폴더를 생성한 후, 사용할 mp3 파일들을 넣어줌
    2. 액티비티 안에 코드 작성하면, 음악 재생 가능
        
        ```kotlin
        val mPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.FILE_NAME)
        mPlayer?.start()
        ```
</br>

### 8.1.2 URI를 사용해 재생하기

- 기기 안에 들어있는 오디오 파일의 위치를 알려주는 URI를 사용해 재생
- URI : 통합 자원 식별자 ⇒ 텍스트, 이미지, 영상 등 자원의 주소를 표현하는 형식
- URL은 웹에서 자원의 위치를 알려주는 URI의 일종
- 액티비티 안에 코드 작성하면, 음악 재생 가능
    
    ```kotlin
    val myUri: Uri = ... // 여기서 URI 초기화
    val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
    	setAudioStreamType(AudioManager.STERAM_MUSIC)
    	setDataSource(applecationContext, myUri)
    	prepare()
    	start()
    }
    ```
    
</br>

> **URI**

Uniform Resource Identifier 약자
자료의 주소를 표현하는 형식
> 
</br>

### 8.1.3 MediaPlayer 클래스에서 지원하는 함수

- 파일 준비하기
    - 음원을 재생하려면 prepare( ), prepareAsync( ), setDataResource( ) 함수를 사용하여 파일을 로드시켜주어야 함
    - prepare( )와 prepareAsync( ) 함수의 큰 차이점
    : prepare( )는 메인 스레드에서 실행, prepareAsync( )는 백그라운드 스레드에서 실행
    - prepare( )는 메인 스레드에서 실행되므로, 영상이나 음원이 너무 크면 ANR(Application Not Responding, 앱 응답 없음, 일명 먹통)을 유발할 수 있음
    - prepareAsync( )는 메인 스레드를 막지 않고, 백그라운드 스레드를 이용하기 때문에 ANR 문제가 발생하지 않는 장점
    - onPreparedListener를 등록하여 음악 준비가 완료되는 시점을 알 수 있음
- 파일 재생하기
    - start( ) 함수로 재생
    - pause( ) 함수로 일시멈춤
    - pause( ) 후에 다시 start( )를 누르면 멈춘 부분부터 다시 재생
- 파일 멈추기
    - 현재 재생되는 미디어를 reset( ) 함수를 호출해 멈출 수 있음
    - MediaPlayer 객체도 초기화시킬 수 있음
    - 이 함수 이후에 새로운 미디어를 준비한 후 재생하면 됨
- 음악 길이 찾기
    - getDuration( ) 함수로 음악의 길이를 얻을 수 있음
    - 단위는 밀리초(ms)로 반환
- 특정 구간으로 이동하기
    - seekTo( ) 함수로 특정 위치로 이동
- 자원 해제하기
    - release( ) 함수를 통해 사용하던 메모리와 자원들을 해제시킬 수 있음
    - MediaPlayer를 더 이상 사용하지 않으면, 꼭 release( ) 호출
</br></br></br>

## 8.2 사전 지식 : 서비스와 생명 주기

- 서비스 : 백그라운드에서 꺼지지 않고 작업을 수행하는 안드로이드 4대 구성요소 중 하나
- 사용자가 서비스를 실행하면, 시스템에게 “이건 서비스라는 건데, 사용자와 인터랙션을 하지는 않지만 오랫동안 실행되어야 해. 누군가가 멈추라고 하기까지는 멈추지 말아줘” 라고 알려줌
- 서비스는 독립된 구성요소이기 때문에 독립된 생명 주기를 가짐
- 액티비티가 소멸되더라도 서비스는 독립된 상태로 실행이 되고 있어서 다시 액티비티를 생성하여 해당 서비스와 소통할 수도 있음
- 서비스를 앱에서 사용하려면 AndroidManifest.xml 에 직접 추가해줘야 함
- 서비스의 유형
    - 시작된 서비스
    - 바인드된 서비스
    - 시작되고 바인드된 서비스
</br></br>

### 8.2.1 시작된 서비스

- 서비스를 시작하려면 startService( ) 함수를 액티비티나 다른 서비스에서 실행해야 함
- startService( ) 를 호출하면 서비스가 시작되고, 서비스 내의 콜백 메서드인 onCreate( ) 와 onStartCommand( ) 가 차례로 호출이 되어 **시작된 상태**가 됨
- 한 번 시작된 상태가 된 서비스는 stopSelf( ) 함수로 알아서 중지하거나, 다른 구성요소가 stopService( ) 를 호출하여 서비스를 명백하게 종료시키기 전까지는 계속 실행 중인 상태로 존재
- 두 가지 방법 중 하나로 서비스를 종료하면 onDestory( ) 함수가 호출되어 서비스가 완전히 종료됨
- 서비스는 아무리 많은 구성요소에서 startService( ) 를 호출하더라도 서비스 객체는 하나만 생셩되며 onCreate( ) 함수 역시 서비스를 만들 때 처음에만 호출됨
- 서비스에서 딱 한 번만 수행되어야 하는 작업은 onCreate( ) 함수를 이용

- 시작된 서비스 생명 주기
    
    ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ecd7a282-e60e-4adc-9c93-77b2b988fca4/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221026%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221026T055344Z&X-Amz-Expires=86400&X-Amz-Signature=03bd5f4656272fff6295b6e9818964e81aac0922d4729151eddfe08ee85c8b20&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
</br>

### 8.2.2 바인드된 서비스

- 바인드 : ‘묶다’
→ 흩어진 요소를 연결되게끔 묶다는 뜻
- 바인드가 된 서비스는 다른 구성요소와 연결이 가능
예) 액티비티가 서비스와 바인딩이 되었다면 액티비티는 서비스에 정의된 함수를 사용할 수 있고, 서비스에 요청을 보내 응답을 받을 수 있음
- **다른 앱의 구성요소가 서비스에 접근할 수 있도록 만들 수 있음**
- 서버(서비스)와 클라이언트(다른 구성요소) 관계를 이룬다고 할 수 있음
- 바인드된 서비스는 기본적으로 다른 구성요소들에 바인드된 동안에만 실행됨
→ 계속 백그라운드에서 실행되는 건 아님
- 바인드된 서비스를 이용하는 예
    - 음악 앱
        - 서비스를 실행한 후, 음악을 재생하면 앱에서 나가더라도 계속 재생됨
        - 다시 앱을 실행하면 바로 액티비티가 서비스와 바인드가 되고 재생을 컨트롤할 수 있게 됨
- 액티비티에서 바인드된 서비스를 시작하는 코드
    
    ```kotlin
    class MainActivity : AppCompatActivity() {
    
    	val mServiceConnection = object : ServiceConnection {
    		override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
    			// 서비스 연결 성공
    		}
    
    		override fun onServiceDisconnected(name: ComponentName?) {
    			// 서비스 연결 실패
    		}
    	}
    
    	private fun bindService() {
    		val intent = Intent(this, AudioPlayerService::class.java)
    		bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    	}
    }
    ```
    
    - 액티비티에서 bindService( ) 함수를 호출할 때 인텐트 객체, 서비스 연결 관련 정보를 받을 수 있는 Service Connection 구형 객체와 Context.BIND_AUTO_CREATE 를 인수로 줌
    - 안드로이드 시스템에 의해 구성요소가 서비스와 연결되면 해당 구현 객체의 onServiceConnected( ) 함수가 호출되는데, 여기에서 서비스와 통신을 가능케 하는 IBinder 객체를 전달받음
    - BIND_AUTO_CREATE 는 bindService( ) 함수를 실행했을 때, 만약 해당 서비스가 없으면 서비스의 onCreate( ) 콜백 함수를 실행시켜 서비스를 생성하라는 의미
- 바인드된 서비스의 생명 주기
    
    ![Untitled](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/f43512b6-7495-4b3b-8511-21fc36459ac0/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221026%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221026T055416Z&X-Amz-Expires=86400&X-Amz-Signature=977e4720ea7c227ee8c11809a6dfd9f1ed11e52f4bd187dc9b3658b3258ea8d2&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
    
    - bindService( ) 함수를 액티비티와 같은 구성요소에서 호출
    - 서비스가 생성되어 있지 않다면 onCreate( ) 함수를 실행
    - onBind( )가 호출되었을 때는 서비스는 IBinder 인터페이스 구현 객체를 bindService( ) 를 호출한 구성요소에 전달
    - 해당 구성요소가 IBinder 를 받고 나서부터는 이것을 통해 서비스와 상호작용을 할 수 있게 됨
    - 모든 연결된 구성요소들이 unBindService( ) 를 호출하여 서비스와의 연결을 모두 끊게 되면 onUnbind( ) 가 실행되고, 이어서 onDestory( ) 가 호출되면서 완전히 서비스가 종료됨
- ‘시작된 서비스’ 와 ‘바인드된 서비스’ 의 차이점
    - **시작된 서비스**
        - 다른 구성요소와의 연결고리가 없음
        - 단순히 startService( ) 함수를 실행해 서비스를 시작
        - stopService( ) 로 서비스를 멈춤
        - 예) 크기가 큰 동영상을 다운받는 경우
        서비스를 시작하고, 다운로드가 종료되면, 서비스에서 stopSelf( ) 를 호출해 서비스를 종료시킴
    - **바인드된 서비스**
        - IBinder 라는 인터페이스를 매개로 다른 구성요소와 소통 가능
        - bindService( ) 는 서비스와 연결된 다른 구성요소들이 모두 unbindService( ) 를 호출하여 연결을 끊게 되면 서비스가 종료됨
        - 계속 살아있어야 하는 태스크에는 부적절
</br></br>

### 8.2.3 시작되고 바인드된 서비스

- 대부분 ‘시작된 서비스’ 와 ‘바인드된 서비스’ 를 함께 사용
- 백그라운드에서 계속 남아있는 동시에 다른 구성요소와 연결되어 소통이 가능하게끔 하는 경우
⇒ 이를 ‘시작되고 바인드된 서비스’ 라고 함
- 실행 방법은 간단
    - startService( ) 와 bindService( ) 함수 둘 다를 실행해 주면 됨
    - 즉, startService( ) 함수로 시작된 상태가 된 서비스는 특별한 요청이 없는 한 바인드된 구성요소와 모든 연결이 끊겨도 계속 살아있게 됨
</br></br>

### 8.2.4 포그라운드 서비스와 백그라운드 서비스

- 서비스를 만들게 되면 startService( ) 함수를 자주 사용하게 됨
- startService( ) 함수만으로 코드를 작성하면, ‘java.land.IllegalStateException: Not allowed to start service Intent’ 와 같은 런타임 에러 만남
- 에러의 내용 : 백그라운드 서비스 실행에 제한을 두는 것으로, 안드로이드 API 레벨 26( 안드로이드 O) 이상을 타깃 SDK로 설정하면 발생
- 서비스 =  포그라운드 서비스 + 백그라운드 서비스
- 포그라운드 서비스 : 상태 표시줄에 알림이 표시되며 사용자가 서비스가 실행되고 있음을 능동적으로 인지할 수 있는 서비스
- 백그라운드 서비스 : 사용자가 보이지 않는 곳에서 조용히 작업을 수행
- 많은 앱이 백그라운드 서비스를 사용하고 사용자가 해당 사실을 모른다면, 기기에 성능 저하가 나타남
- 그래서 안드로이드는 API 레벨 26(안드로이드 O) 부터는 포그라운드 서비스를 통해 상태 표시줄에서 서비스가 실행되고 있음을 사용자에게 알리고 다른 서비스보다 높은 우선순위를 가지게끔 함
- startForegroundService( ) 함수가 startService( ) 함수와 다른 점
    - startForegroundService( ) 함수를 호출하고 나서 서비스 생성 이후 5초 이내에 startForegound( ) 함수를 통해 알림을 보여주어야 한다는 점
        
        ```kotlin
        startForeground(id, notification)
        
        startForeground(식별자, 사용자에게 보여질 알림)
        ```
        
- 뮤직 플레이어는 타깃으로 하는 SDK 버전이 31이므로 startForegroundService( ) 를 사용
</br></br></br>

## 8.3 준비하기 : 프로젝트

- 액티비티 : Empty Activity
- 프로젝트 이름 : SimpleMusicPlayer
- 언어 : Kotlin
- Minimum SDK : API 26 : Android 8.0 (Oreo)
</br></br></br>

## 8.4 레이아웃 구성하기

- 버튼 [재생], [일시정지] [재생 중지] 3개 배치
</br></br>

### 8.4.1 레이아웃 팔레트를 사용해 버튼 생성하기

- 버튼 3개 추가 후, id값과 텍스트 지정
- 버튼의 수직 방향 정렬
    - 마우스로 드래그 하여 함께 선택 → 한 버튼 위에서 마우스 우클릭 → [Chains] → [Create Vertical Chain] 선택
- 버튼의 수평 방향 정렬
    - 드래그하여 모두 선택 → 한 버튼 위에서 마우스 우클릭 → [Center] → [Horizontally in Parent] 선택
    - 부모 레이아웃의 수평 방향의 중심에 위치 (가운데 정렬)
</br></br>

### 8.4.2 버튼 속 텍스트를 리소스 파일을 사용해 지정하기

- [app] → [res] → [values] → strings.xml 에서 버튼 3개에 필요한 문자열 코드 추가
- 세 버튼의 text 속성을 string 리소스로 바꿈
</br></br></br>

## 8.5 리소스 준비하기

- MP3 파일과 아이콘 준비
</br></br>

### 8.5.1 MP3 파일 준비하기

- [app] → [res] 폴더에서 우클릭 → [New] → [Android Resource Directory] 선택
- 디렉터리 이름 raw → 리소스 타입 raw → [OK]
- mp3 파일을 raw 폴더에 넣기
</br></br>

### 8.5.2 아이콘 준비하기

- 상태 표시줄에 알림 아이콘으로 쓸 재생 이미지 임포트
- 백터 이미지는 안드로이드 스튜디오에서 기본적으로 제공하는 클립아트 사용
- [app] → [res] → [drawable] 폴더에서 마우스 우클릭 → [New] → [Vector Asset] 선택
- [Clip Art] 옆 아이콘을 클릭해, 클립아트 선택
- 아이콘 이름을 ic_play 로 수정 → [Next] → [Finish]
- 이제 이 벡터 이미지를 프로젝트에서 사용 가능
</br></br></br>

## 8.6 서비스 클래스 구현하기

- 서비스 유형 3가지 ([8.2절](https://www.notion.so/Chapter-08-Project-MediaPlayer-API-de2298e7223c4376a8698eaa5e4ec043) ‘사전 지식 : 서비스와 생명 주기’ 참조)
    - **시작된 서비스**
    - **바인드된 서비스**
    - **시작되고 바인드된 서비스**
- 뮤직 플레이어 앱에 서비스의 세 번째 유형인 ‘시작되고 바인드된 서비스’를 구현
- 왜냐하면 뮤직 플레이어는 앱을 끄더라도 음악을 재생하더 ㄴ상태라면 백그라운드에서 계속 재생이 되어야 하고, 다시 앱으로 돌아왔을 때 재생되고 있는 정보를 보여줄 수 있어야 하기 때문
- 새로운 서비스 클래스 생성
    - [app] → [java] → [com.example.simplemusicplayer] 폴더 위에서 마우스 우클릭 → [New] → [Kotlin File/Class] 선택
    - 기본 선택된 [Class] 그대로 → 파일의 이름 MusicPlayerService 입력 → enter 로 클래스 생성
- 전체 골격 생성
    
    ```kotlin
    // MusicPlayerService.kt
    
    class MusicPlayerService : Service() {
    
    	override fun onCreate() {   // 서비스가 생성될 때 딱 한 번만 실행
    		super.onCreate()
    		startForegroundService()   // 포그라우드 서비스 시작
    	}
    
    	// 바인드
    	override fun onBind(intent: Intent?): IBinder? {
    		TODO("Not yet implemented")
    	}
    
    	// 시작된 상태 & 백그라운드
    	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    		return super.onStartCommand(intent, flags, startId)
    	}
    
    	// 서비스 종료
    	override fun onDestory() {
    		super.onDestory()
    	}
    
    	fun startForegroundService() {}   // 서비스가 실행되고 있다는 사실을 알림과 함께 알림
    	fun isPlaying() {}   // 재생 중인지 확인
    	fun play() {}   // 재생
    	fun pause() {}   // 일시정지
    	fun stop() {}   // 완전 정지
    }
    ```
    
    - onCreate( )
        - 서비스가 생성될 때 딱 한 번만 실행되는 함수
        - startService( ) 로 서비스를 생성하든, bindService( ) 로 생성하든 onCreate( )는 처음에 딱 한 번만 실행됨
        - 여기서 상태 표시 줄에 앱이 실행되고 있다는 알림을 생성하는 startForegoundService( ) 를 실행
    - onBind( )
        - 액티비티와 같은 구성요소에서 bindService( ) 함수를 호출할 때 실행되는 함수
        - 서비스와 구성요소를 이어주는 매개체 역할을 하는 IBinder를 반환
        - 바인드가 필요 없는 서비스 (ex. 시작된 서비스)라면 null 반환
        - 뮤직 플레이어는 시작되고 바인드된 서비스이기 때문에 꼭 구현해 주어야 함
    - onStartCommand( )
        - startService( ) 나 startForegroundService( )를 호출할 때 실행되는 함수
        - 이 함수가 실행되면 서비스는 시작된 상태가 되고 백그라운드에서 쭉 존재할 수 있게 됨
    - onDestory( )
        - 서비스 생명 주기의 마지막 단계
        - onCreate( )에서 상태 표시줄에 보여줬던 알림을 해제
    - startForegroundService( )
        - 알림 채널을 만들고, startForeground( ) 함수를 실행
        - 안드로이드 O (API level 26) 버전부터는 반드시 startService( )가 아닌 startForeground( )를 실행하여 사용자로 하여금 서비스가 실행되고 있다는 사실을 알림과 함께 알려야 함
- MusicPlayerService 클래스의 기능 구현
    
    ```kotlin
    // MusicPlayerService.kt
    
    class MusicPlayerService : Service() {
    	var mMediaPlayer: MediaPlayer? = null   // 미디어 플레이어 객체를 null로 초기화
    	
    	// 바인더를 반환해 서비스 함수를 쓸 수 있게 함
    	var mBinder: MusicPlayerBinder = MusicPlayerBinder()
    
    	inner class MusicPlayerBinder: Binder() {
    		fun getService(): MusicPlayerService {
    			return this@MusicPlayerService
    		}
    	}
    	// 바인더를 반환해 서비스 함수를 쓸 수 있게 함
    
    	override fun onCreate() {
    		super.onCreate()
    		startForegroundService()
    	}
    
    	// 바인더 반환
    	override fun onBind(intent: Intent?): IBinder? {
    		return mBinder
    	}
    
    	// startService()를 호출하면 실행되는 콜백 함수
    	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    		return START_STICKY
    	}
    ```
    
    - onBind( )
        - bindService( )를 호출한 구성요소에 IBinder 인터페이스를 구현한 객체를 반환값으로 넘겨주어야 함
        - 서비스 안에 내부 클래스 MusicPlayerBinder 생성
        - mBinder 객체를 반환값으로 전달
    - MusicPlayerBinder 클래스
        - Binder 클래스를 상속받고 있으며 Binder 클래스가 IBinder 인터페이스를 구현해주므로 onBind에서 MusicPlayerBinder 클래스 객체를 반환해줘도 됨
        - getService( ) 함수를 가지고 있으며 반환값으로 현재 서비스를 반환
        - 서비스와 연결하려는 액티비티와 같은 구성요소에 현재 서비스를 반환함으로써, 연결된 구성요소가 서비스의 함수를 사용할 수 있게 됨
    - onStartCommand( )
        - startService( )를 호출하면 실행되는 콜백 함수
        - 이 함수는 반드시 정수값(START_STICKY, START_NOT_STICKY, START_REDELIVER_INTENT)을 반환해야 함
        - 이 값은 시스템이 서비스를 종료할 때 서비스를 어떻게 유지할지를 설명
            - START_STICKY
                - 시스템이 서비스를 중단하면 서비스를 다시 실행하고 onStartCommand( ) 함수를 호출
            - START_NOT_STICKY
                - 시스템이 서비스를 중단시키면 서비스를 재생성하지 않음
            - START_REDELIVER_INTENT
                - 시스템이 서비스를 중단하면 서비스를 다시 실행하고 onStartCommand( ) 함수를 호출
                - 서비스가 종료되기 전 마지막으로 전달된 인텐트를 재전달
                - 반드시 명령을 실행해야 하는 경우 쓰임
- 알림 채널, 알림, 서비스 종료 기능 구현
    
    ```kotlin
    // MusicPlayerService.kt
    
    fun startForegroundService() {
    	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    		val mChannel = NotificationChannel(   // 알림 채널 생성
    			"CHANNEL_ID",
    			"CHANNEL_NAME",
    			NotificationManager.IMPORTANCE_DEFAULT
    		)
    		notificationManager.createNotificationChannel(mChannel)
    	}
    
    	// 알림 생성
    	val notification: Notification = Notification.Builder(this, "CHANNEL_ID")
    		.setSmallIcon(R.drawable.ic_play)       // 알림 아이콘
    		.setContentTitle("뮤직 플레이어 앱")     // 알림의 제목 설정
    		.setContentText("앱이 실행 중입니다.")   // 알림의 내용 설정
    		.build()
    
    	startForeground(1, notification)   // 인수로 알림 ID와 알림 지정
    }
    
    // 서비스 중단 처리
    	override fun onDestory() {
    		super.onDestory()
    		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    			stopForeground(true)
    		}
    	}
    
    // 재생되고 있는지 확인
    fun isPlaying() : Boolean {
    	return (mMediaPlayer != null && mMediaPlayer?.isPlaying ?: false)
    }
    
    ```
    
    - startForegroundService( )
        - 알림 채널을 생성
            - 알림 채널은 알림을 용도나 중요도에 따라 구분하여 사용자가 앱의 알림을 관리할 수 있게 함
            - 안드로이드 O부터는 반드시 알림 채널을 사용하여 사용자에게 알림을 보여줘야 함
        - 알림 생성
            - 만든 알림 채널을 “CHANNEL_ID”와 같이 인수로 넣어줌
            - 알림을 만든 후에는 startForeground(1, notification)으로 서비스를 포그라운드 서비스로 만듬
            - 첫 번째 인수로는 0이 아닌 정수값을 넘겨 알림의 식별자로 사용
            - 두 번째 인수로는 보여줄 알림 넘겨줌
    - onDestory( )
        - 서비스가 중단될 때 stopForeground(true) 함수를 사용하여 포그라운드 서비스를 멈춤
- play( ), pause( ), stop( ) 함수 구현
    
    ```kotlin
    // MusicPlayerService.kt
    
    // 음악 재생
    fun play() {
    	if(mMediaPlayer == null) {
    		// 음악 파일의 리소스를 가져와 미디어 플레이어 객체를 할당
    		mMediaPlayer = MediaPlayer.create(this, R.raw.chocolate)
    
    		mMediaPlayer?.setVolume(1.0f, 1.0f);   // 볼륨을 지정
    		mMediaPlayer?.isLooping = true         // 반복재생 여부를 지정
    		mMediaPlayer?.start()                  // 음악을 재생
    	} else {   // 음악 재생 중인 경우
    		if(mMediaPlayer!!.isPlaying) {
    			Toast.makeText(this, "이미 음악이 실행 중입니다.", Toast.LENGTH_SHORT).show()
    		} else {
    			mMediaPlayer?.start()   // 음악을 재생
    		}
    	}
    }
    
    // 일시정지
    fun pause() {
    	mMediaPlayer?.let {
    		if(it.isPlaying) {
    			it.pause()   // 음악을 일시정지
    		}
    	}
    }
    
    // 재생중지
    fun stop() {
    	mMediaPlayer?.let {
    		if(it.isPlaying) {
    			it.stop()   // 음악을 멈춤
    			it.release()   // 미디어 플레이어에 할당된 자원을 해제
    			mMediaPlayer = nul
    		}
    	}
    }
    ```
    
    - play( )
        - 음악을 실행하는 로직을 작성
        - mMediaPlayer가 null로 아직 설정이 되어있지 않다면 음악을 로딩하고 재생
        - mMediaPlayer가 이미 있고 음악 재생 중 상태면 음악이 실행 중이라는 토스트를 띄우고, 일시정지 상태면 다시 음악을 실행
        - 토스트 : 짧은 메시지 형식으로 사용자에게 정보를 보여주는 팝업, Toast.makeText( ) 함수로 토스트 객체를 만들어주고, show( ) 함수로 보여주면 됨
    - pause( )
        - 음악이 실행 중이면 음악을 일시정지
    - stop( )
        - 음악을 완전히 멈춰주고 release( ) 함수를 호출해 미디어 플레이어에 할당된 자원을 해제
- service도 구성요소이기 때문에 꼭 AndroidMenifest.xml 파일에  <service> 태그를 추가해줘야 함
    - 안드로이드 API 레벨 28을 대상으로 하는 앱은 포그라운드 서비스를 위한 권한 요청을 해야 함
    - [app] → [manifests] → AndroidManifest.xml
        
        ```kotlin
        <manifest ...생략... >
        <uses-permission android:name="android.permisson.FOREGROUND_SERVICE" />
        
        	<application ...생략...>
        	...생략...
        
        	// 추가
        	<service android:name="com.example.simplemusicplayer.MusicPlayerService" />
        	
        	...생략...
        	</application>
        </manifest>
        ```
        
        - TargetSDK는 29이므로 포그라운드 서비스 권한 요청이 꼭 필요
        - 서비스 태그의 name 속성을 패키지 이름으로 설정
</br></br></br>

## 8.7 버튼 초기화하기

- 세 버튼들을 초기화해주고 onClickListener를 등록해주어 클릭 시에 play( ), pause( ), stop( ) 함수가 실행되도록 설정
- [apa] → [java] → [com.example.simplemusicplayer] → MainActivity.kt
    
    ```kotlin
    // MainActivity.kt
    
    class MainActivity : AppCompatActivity(), View.OnClickListener {
    	lateinit var btn_play: Button
    	lateinit var btn_pause: Button
    	lateinit var btn_stop: Button
    
    	override fun onCreate(savedInstanceState: Bundle?) {
    		super.onCreate(savedInstanceState)
    		setContentView(R.layout.activity_main)
    
    		btn_play = findViewById(R.id.btn_play)
    		btn_pause = findViewById(R.id.btn_pause)
    		btn_stop = findViewById(R.id.btn_stop)
    
    		// 리스너 등록
    		btn_play.setOnClickListener(this)
    		btn_pause.setOnClickListener(this)
    		btn_stop.setOnClickListener(this)
    }
    
    	// 콜백 함수
    	override fun onClick(v: View?) {
    		when(v?.id) {
    			R.id.btn_play -> {
    				play()
    			}
    	
    			R.id.btn_pause -> {
    				pause()
    			}
    	
    			R.id.btn_stop -> {
    				stop()
    			}
    		}
    	}
    
    	override fun onResume() {
    		super.onResume()
    	}
    
    	override fun onPause() {
    		super.onPause()
    	}
    
    	private fun play() {
    	}
    
    	private fun pause() {
    	}
    
    	private fun stop() {
    	}
    }
    ```
    
    - View.onClickListener( ) 인터페이스
        - 이 인터페이스를 구현함으로써 사용자가 뷰를 클릭했을 때 어떤 행동을 할지 정해줄 수 있음
    - .setOnClickListener( )
        - [재생], [일시정지], [재생중지] 버튼에 구현한 onClickListener( )를 등록
    - onClick( )
        - 클릭했을 때 실행되는 콜백 함수
        - View.OnClickListener( )는 onClick( ) 함수만 오버라이드해 주면 됨
        - 뷰가 클릭되면 onClick( ) 함수가 실행이 되고 뷰의 ID에 따라 when문으로 분기가 되어 그에 맞는 함수가 실행됨
</br></br></br>

## 8.8 액티비티와 서비스 연결하기

- 서비스를 다 만들었으므로 메인 액티비티를 구현
- [app] → [java] → [com.example.simplemusicplayer] → MainActivity.kt
    
    ```kotlin
    // MainActivity.kt
    
    class MainActivity : AppCompatActivity(), View.OnClickListener {
    	lateinit var btn_play: Button
    	lateinit var btn_pause: Button
    	lateinit var btn_stop: Button
    
    	// 서비스 변수
    	var mService: MusicPlayerService? = null
    
    	// 서비스와 구성요소 연결 상태 모니터링
    	val mServiceConnection = object : ServiceConnection {
    		override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
    			
    			// MusicPlayerBinder로 형변환
    			mService = (service as MusicPlayerService.MusicPlayerBinder).getService()
    		}
    
    		override fun onServiceDisconnected(name: ComponentName?) {
    			// 만약 서비스가 끊기면, mService를 null로 만듬
    			mService = null
    		}
    	}
    
    	override fun onCreate(savedInstanceState: Bundle?) {
    		super.onCreate(savedInstanceState)
    		setContentView(R.layout.activity_main)
    
    		btn_play = findViewById(R.id.btn_play)
    		btn_pause = findViewById(R.id.btn_pause)
    		btn_stop = findViewById(R.id.btn_stop)
    
    		// 리스너 등록
    		btn_play.setOnClickListener(this)
    		btn_pause.setOnClickListener(this)
    		btn_stop.setOnClickListener(this)
    }
    
    	// 콜백 함수
    	override fun onClick(v: View?) {
    		when(v?.id) {
    			R.id.btn_play -> {
    				play()
    			}
    	
    			R.id.btn_pause -> {
    				pause()
    			}
    	
    			R.id.btn_stop -> {
    				stop()
    			}
    		}
    	}
    
    	override fun onResume() {
    		super.onResume()
    
    		// 서비스 실행
    		if(mService == null) {
    			// 안드로이드 O 이상이면 startForegroundService를 사용해야 함
    			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    				startForegroundService(Intent(this, MusicPlayerService::class.java))
    			} else {
    				startService(Intent(applicationContext, MusicPlayerService::class.java))
    			}
    
    			// 액티비티를 서비스와 바인드시킴
    			val intent = Intent(this, MusicPlayerService::class.java)
    			// 서비스와 바인드
    			bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    		}
    	}
    
    	override fun onPause() {
    		super.onPause()
    
    		// 사용자가 액티비티를 떠났을 때 처리
    		if(mService != null) {
    			// mService가 재생되고 있지 않다면
    			if(!mService!!.isPlaying()) {
    				// 서비스를 중단
    				mService!!.stopSelf()
    			}
    			// 서비스로부터 연결을 끊음
    			unbindService(mServiceConnection)
    			mService = null
    		}
    	}
    
    	private fun play() {
    	}
    
    	private fun pause() {
    	}
    
    	private fun stop() {
    	}
    }
    ```
    
    - mService
        - 뮤직 플레이어 서비스를 담을 수 있는 변수 생성
    - mServiceConnection
        - 서비스와 구성요소의 연결 상태를 모니터링
        - bindService( ) 함수를 호출할 때 두 번째 인수로 들어가게 됨
    - onResume( )
        - 액티비티가 사용자에게 보일 때마다 실행되는 콜백 함수
        - 여기서 바로 서비스를 시작
        - mService가 null이면 ‘아직 서비스가 액티비티와 연결되지 않았다’라는 말이므로 버전에 따라 startService( ) 함수나 startForegroundService( ) 함수를 호출
        - 이 두 함수는 아무리 많이 불러도 이미 해당 서비스가 만들어진 상태면 서비스를 새로 만들지 않음
    - intent
        - 이 액티비티를 서비스와 바인드시킴
        - bindService( ) 함수의 첫 번째 인수 intent는 어떤 서비스와 바인드할지를 알려줌
        - 다음 인수인 mServiceConnection은 구현한 대로 연결되면 onServiceConnected( )를, 연결이 끊기면 onServiceDisconnected( )를 실행
        - 마지막으로 Context.BIND_AUTO_CREATE는 바인드할 시점에 서비스가 실행되지 않은 상태라면 서비스를 생성하라는 표시
    - onPause( )
        - 사용자가 액티비티를 떠나게 되면 실행
        - if문을 사용하여 만약 mService가 null이 아닌 상태일 때(바인딩된 상태일 때)
        - 음악이 실행되고 있지 않은 상태라면 서비스를 종료하고 바인딩을 해제
        - 음악이 실행 중이면 바인딩만 해제하여 다시 앱으로 돌아왔을 때 해당 서비스에 바인딩할 수 있게끔 함
</br></br></br>

## 8.9 테스트하기

- 안드로이드 스튜디오 메뉴 [Run] → [Run ‘app’] 선택하여 앱을 실행
- 잘 실행되면 다음과 같이 테스트
    1. 음악 [재생] 버튼 누름
    상단의 상태 표시줄에서 풀레이 표시의 알림 뜸
    2. 앱을 떠나서 앱 자체를 종료
    종료하더라도 계속 음악이 재생됨
    3. 상태 표시줄에서 역시 계속 떠있는 것 확인
    4. 다시 앱에 들어가서 음악을 일시정지
    음악이 멈춰야 함
    5. 앱을 종료
    알림표시줄에서 알림이 사라지고, 서비스 역시 완전히 종료되어야 함
</br></br></br>

## 학습 마무리

- 서비스
    - 앱의 생명 주기와 무관하게 백그라운드에서 작업할 수 있게 해줌
    - 다른 구성요소들이 서비스 기능에 접근할 수 있게 해줌
    - 다른 앱의 구성요소에서 서비스에 접근하는 것도 가능
- MediaPlayer API
    - 오디오나 비디오와 같은 파일을 재생할 수 있게 해줌
    - URI를 사용하는 방법 / MP3 파일을 탐색해 추가하는 방법 (업그레이드해 사용해보기)
</br></br></br>

## 핵심 요약

- MediaPlayer API를 이용하면 오디오나 비디오와 같은 다양한 일반 미디어 유형을 재생할 수 있음
- 서비스를 이용하면 앱이 꺼져도 계속 태스크를 실행할 수 있음
- 서비스의 유형 : 시작된 서비스, 바인드된 서비스, 시작되고 바인드된 서비스
- 서비스를 시작하는 함수 : startService( ), startForegroundService( )
안드로이드 O (API 레벨 26) 버전 이상부터는 사용자에게 알림을 보여주는 startForegroundService( ) 이용