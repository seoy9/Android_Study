package hong.sy.qrcodereader

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import hong.sy.qrcodereader.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    // 바인딩 변수 생성
    private lateinit var binding : ActivityMainBinding
    // ListenableFuture형 변수 선언
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    // 태그 기능 코드, 권한 요청 후 결과 받을 때 필요
    private val PERMISSIONS_REQUEST_CODE = 1
    // 카메라 권한 지정
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)
    // onDetect() 함수 중복 호출 제어 변수
    private var isDetected = false

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

    override fun onResume() {
        super.onResume()
        isDetected = false
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

    // 미리보기와 이미지 분석 시작
    fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            // 미리보기 객체 가져오기
            val preview = getPreview()
            // IamgeAnalysis 객체 생성
            val imageAnalysis = getImageAnalysis()
            // 후면 카메라 선택
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // 미리보기 기능 선택
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
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