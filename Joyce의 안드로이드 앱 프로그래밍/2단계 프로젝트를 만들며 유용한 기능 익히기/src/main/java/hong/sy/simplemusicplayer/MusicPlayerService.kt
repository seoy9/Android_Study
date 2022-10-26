package hong.sy.simplemusicplayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast

class MusicPlayerService() : Service() {
    var mMediaPlayer: MediaPlayer? = null   // 미디어 플레이어 객체를 null로 초기화

    // 바인더를 반환해 서비스 함수를 쓸 수 있게 함
    var mBinder: MusicPlayerBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder: Binder() {
        fun getService(): MusicPlayerService {
            return this@MusicPlayerService
        }
    }
    // 바인더를 반환해 서비스 함수를 쓸 수 있게 함

    // 서비스가 생성될 때 딱 한 번만 실행
    override fun onCreate() {
        super.onCreate()

        // 포그라운드 서비스 시작
        startForegroundService()
    }

    // 바인드
    // 바인더 반환
    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    // 시작된 상태 & 백그라운드
    // startService()를 호출하면 실행되는 콜백 함수
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //return super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    // 서비스 종료
    // 서비스 중단 처리
    override fun onDestroy() {
        super.onDestroy()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
    }

    // 서비스가 실행되고 있다는 사실을 알림과 함께 알림
    fun startForegroundService() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // 알림 채널 생성
            val mChannel = NotificationChannel(
                "CHANNEL_ID",
                "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        // 알림 생성
        val notification: Notification = Notification.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_play)   // 알림 아이콘
            .setContentTitle("뮤직 플레이어 앱")   // 알림의 제목 설정
            .setContentText("앱이 실행 중입니다.")  // 알림의 내용 설정
            .build()

        startForeground(1, notification)    // 인수로 알림 ID와 알림 지정
    }

    // 재생 중인지 확인
    // 재생되고 있는지 확인
    fun isplaying() : Boolean {
        return (mMediaPlayer != null && mMediaPlayer?.isPlaying ?: false)
    }

    // 재생
    // 음악 재생
    fun play() {
        if(mMediaPlayer == null) {
            // 음악 파일의 리소스를 가져와 미디어 플레이어 객체를 할당
            mMediaPlayer = MediaPlayer.create(this, R.raw.chocolate)

            mMediaPlayer?.setVolume(1.0f, 1.0f)     // 볼륨을 지정
            mMediaPlayer?.isLooping = true                              // 반복재생 여부 지정
            mMediaPlayer?.start()                                       //음악을 재생
        } else {    // 음악 재생 중인 경우
            if (mMediaPlayer!!.isPlaying) {
                Toast.makeText(this, "이미 음악이 실행 중입니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 음악을 재생
                mMediaPlayer?.start()
            }
        }
    }

    // 일시정지
    fun pause() {
        mMediaPlayer?.let {
            if(it.isPlaying) {
                // 음악을 일시정지
                it.pause()
            }
        }

    }

    // 완전 정지
    // 재생중지
    fun stop() {
        mMediaPlayer?.let {
            if(it.isPlaying) {
                it.stop()       // 음악을 멈춤
                it.release()    // 미디어 플레이어에 할당된 자원을 해제
                mMediaPlayer = null
            }
        }
    }
}