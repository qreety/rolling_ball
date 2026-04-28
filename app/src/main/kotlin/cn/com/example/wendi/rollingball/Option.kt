package cn.com.example.wendi.rollingball

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class Option : AppCompatActivity() {

    /**
     * onCreate method
     * set the buttons
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val sharedPref = getSharedPreferences("Scores", Context.MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0).toLong()
        val tv = findViewById<TextView>(R.id.high_S)
        tv.text = highScore.toString()
        val face = Typeface.createFromAsset(assets, "fonts/Chalkduster.ttf")
        tv.typeface = face

        val bk = findViewById<Button>(R.id.bck)
        bk.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            finish()
        }

        /**
         * user can clear high score record here
         */
        val cr = findViewById<Button>(R.id.del)
        cr.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            sharedPref.edit {
                putInt(getString(R.string.saved_high_score), 0)
            }
            tv.text = "0"
            tv.typeface = face
        }
    }
}
