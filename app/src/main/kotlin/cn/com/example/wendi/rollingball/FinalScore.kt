package cn.com.example.wendi.rollingball

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class FinalScore : AppCompatActivity() {

    /**
     * onCreate method
     * set the buttons and shows the score
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_score)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0)
        val score = sharedPref.getInt(getString(R.string.current_score), 0)

        /**
         * Restart the game
         */
        val button1 = findViewById<Button>(R.id.RE)
        button1.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            setResult(1, intent)
            finish()
        }

        /**
         * End the game and back to main menu
         */
        val button3 = findViewById<Button>(R.id.END)
        button3.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            setResult(2, intent)
            finish()
        }

        /**
         * Compare current score with saved high score
         * if current score is higher then replace saved high score
         */
        val tv1 = findViewById<TextView>(R.id.game_over)
        tv1.text = "Game Over!"
        tv1.textSize = 36f
        val face = Typeface.createFromAsset(assets, "fonts/Chalkduster.ttf")
        tv1.typeface = face

        val tv3 = findViewById<TextView>(R.id.score)
        tv3.text = "Final Score: $score"
        tv3.textSize = 30f
        tv3.typeface = face

        if (score > highScore) {
            val editor = sharedPref.edit()
            editor.putInt(getString(R.string.saved_high_score), score)
            editor.apply()

            tv1.text = "HighScore!"
            tv1.textSize = 40f
            tv1.typeface = face
        }
    }
}
