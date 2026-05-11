package cn.com.example.wendi.rollingball

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FinalScore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_score)

        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0)
        val score = sharedPref.getInt(getString(R.string.current_score), 0)

        val button1 = findViewById<Button>(R.id.RE)
        button1.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            val intent = Intent(this, SurfaceViewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        val button3 = findViewById<Button>(R.id.END)
        button3.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            val intent = Intent(this, MainMenu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

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
