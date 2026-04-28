package cn.com.example.wendi.rollingball

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HowTo : AppCompatActivity() {

    /**
     * onCreate
     * shows the tutorial texts and back button
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val sharedPref = getSharedPreferences("Scores", Context.MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0).toLong()
        val tv = findViewById<TextView>(R.id.high_S)
        tv.text = highScore.toString()
        val face = Typeface.createFromAsset(assets, "fonts/Chalkduster.ttf")
        tv.typeface = face

        val tut = findViewById<TextView>(R.id.ht)
        tut.text = "Tilt your phone.\n" +
                "Eat stars to get score.\n" +
                "Avoid bombs, or you lose.\n" +
                "Have fun!"
        tut.typeface = face
        tut.textSize = 28f
        tut.setLineSpacing(5f, 1.5f)

        val btn = findViewById<Button>(R.id.back_btn)
        btn.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            finish()
        }
    }
}
