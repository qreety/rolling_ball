package cn.com.example.wendi.rollingball

import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class SurfaceViewActivity : AppCompatActivity() {

    private lateinit var gv: GameView
    lateinit var soundManager: SoundManager
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_surface_view)

        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0)

        soundManager = SoundManager(this).apply {
            initialize()
        }

        gv = GameView(this, highScore, soundManager)
        setContentView(gv)

        supportFragmentManager.setFragmentResultListener(
            PauseFragment.REQUEST_KEY,
            this
        ) { _, result ->
            when (result.getInt(PauseFragment.RESULT_CODE)) {
                PauseFragment.CONTINUE -> {
                    gv.isGameRunning = true
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

    override fun onPause() {
        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        sharedPref.edit {
            putInt(getString(R.string.current_score), gv.score)
        }
        super.onPause()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            gv.isGameRunning = false
            val pauseFragment = PauseFragment.newInstance()
            pauseFragment.show(supportFragmentManager, "pause_dialog")
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        // Only resume if the game is still active (thread running)
        // If the game ended (bomb hit), don't resume a dead game
        if (gv.isGameActive) {
            gv.isGameRunning = true
        }
    }

    override fun onStop() {
        gv.kill()
        soundManager.release()
        super.onStop()
    }
}
