package cn.com.example.wendi.rollingball

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainMenu : AppCompatActivity() {

    /**
     * onCreate method
     * set up three buttons
     * get and show the highscore
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val button0 = findViewById<Button>(R.id.new_game)
        button0.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            val intent = Intent(this@MainMenu, SurfaceViewActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.ht_play)
        button1.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            val intent = Intent(this@MainMenu, HowTo::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.opt)
        button2.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            val intent = Intent(this@MainMenu, Option::class.java)
            startActivity(intent)
        }

        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0).toLong()
        val tv = findViewById<TextView>(R.id.high_S)
        tv.text = highScore.toString()
        val face = Typeface.createFromAsset(assets, "fonts/Chalkduster.ttf")
        tv.typeface = face
    }
}
