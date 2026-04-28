package cn.com.example.wendi.rollingball

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class SurfaceViewActivity : AppCompatActivity() {

    private lateinit var gv: GameView
    lateinit var soundManager: SoundManager
        private set

    /**
     * onCreate method
     * starts the SurfaceView
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_surface_view)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0)

        // Initialize SoundManager as Activity-scoped source of truth
        soundManager = SoundManager(this).apply {
            initialize()
        }

        gv = GameView(this, highScore, soundManager)
        setContentView(gv)

        // Listen for pause fragment results
        supportFragmentManager.setFragmentResultListener(
            PauseFragment.REQUEST_KEY,
            this
        ) { _, result ->
            when (result.getInt(PauseFragment.RESULT_CODE)) {
                PauseFragment.CONTINUE -> {
                    gv.gRunning = true
                }
                PauseFragment.RESTART -> {
                    gv.restart()
                }
                PauseFragment.END_GAME -> {
                    finish()
                }
            }
        }
    }

    /**
     * save the high score when the game is paused
     */
    override fun onPause() {
        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        sharedPref.edit {
            putInt(getString(R.string.current_score), gv.score)
        }
        super.onPause()
    }

    /**
     * Pause the game whenever user touched the screen
     * during the game
     * @param event
     * @return
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            gv.gRunning = false
            val pauseFragment = PauseFragment.newInstance()
            pauseFragment.show(supportFragmentManager, "pause_dialog")
        }
        return true
    }

    /**
     * when comes back to surfaceview
     * resume the game
     */
    override fun onResume() {
        gv.gRunning = true
        super.onResume()
    }

    /**
     * When the activity is killed
     * kill the sound and draw threads
     */
    override fun onStop() {
        gv.kill()
        soundManager.release()
        super.onStop()
    }
}
