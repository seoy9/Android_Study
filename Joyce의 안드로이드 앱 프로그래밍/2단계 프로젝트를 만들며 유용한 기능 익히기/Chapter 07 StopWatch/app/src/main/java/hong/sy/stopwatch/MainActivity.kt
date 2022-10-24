package hong.sy.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener /* 클릭 이벤트 처리 인터페이스 */ {

    var isRunning = false   // 실행 여부 확인용 변수
    var timer : Timer? = null   // timer 변수 추구ㅏ
    var time = 0    // time 변수 추가

    private lateinit var btn_start: Button
    private lateinit var btn_refresh: Button
    private lateinit var tv_millisecond: TextView
    private lateinit var tv_second: TextView
    private lateinit var tv_minute: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰 가져오기
        btn_start = findViewById(R.id.btn_start)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_millisecond = findViewById(R.id.tv_millisecond)
        tv_second = findViewById(R.id.tv_second)
        tv_minute = findViewById(R.id.tv_minute)

        // 버튼별 OnClickListener 등록
        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)
    }

    // 클릭 이벤트 처리
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start -> {
                if(isRunning) {
                    pause()
                } else {
                    start()
                }
            }
            R.id.btn_refresh -> {
                refresh()
            }
        }
    }

    private fun start() {
        // 스톱워치 측정을 시작하는 로직

        // 텍스트뷰 변경
        btn_start.text = "일시정지"
        btn_start.setBackgroundColor(getColor(R.color.red))

        // 실행 상태 변경
        isRunning = true

        // 스톱워치를 시작하는 로직
        timer = timer(period = 10) {
            time++  // 10밀리초 단위 타이머

            // 시간 계산
            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

            runOnUiThread {     // UI 스레드 생성
                if(isRunning) {     // UI 업데이트 조건 설정
                    // 밀리초
                    tv_millisecond.text = if(milli_second < 10) ".0${milli_second}" else ".${milli_second}"

                    // 초
                    tv_second.text = if(second < 10) ":0${second}" else ":${second}"

                    // 분
                    tv_minute.text = "${minute}"
                }
            }
        }
    }

    private fun pause() {
        // 스톱워치 측정을 일시정지하는 로직

        // 텍스트 속성 변경
        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))

        // 멈춤 상태로 전환
        isRunning = false

        // 타이머 멈추기
        timer?.cancel()
    }

    private fun refresh() {
        // 초기화하는 로직

        // 백그라운드 타이머 멈추기
        timer?.cancel()

        // 텍스트뷰 변경
        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.blue))

        // 멈춤 상태로 변경
        isRunning = false

        // 타이머 초기화
        time = 0
        tv_millisecond.text = ".00"
        tv_second.text = ":00"
        tv_minute.text = "00"
    }
}
