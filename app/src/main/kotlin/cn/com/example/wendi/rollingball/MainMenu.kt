package cn.com.example.wendi.rollingball

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainMenu : AppCompatActivity() {

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
            showFragment(HowToFragment.newInstance())
        }

        val button2 = findViewById<Button>(R.id.opt)
        button2.setOnClickListener {
            vibrateInGame(VibrationType.CLICK)
            showFragment(OptionFragment.newInstance())
        }

        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0).toLong()
        val tv = findViewById<TextView>(R.id.high_S)
        tv.text = highScore.toString()
        val face = Typeface.createFromAsset(assets, "fonts/Chalkduster.ttf")
        tv.typeface = face

        // Listen for fragment back stack changes to show/hide main menu
        supportFragmentManager.addOnBackStackChangedListener {
            updateMainMenuVisibility()
        }
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateMainMenuVisibility() {
        val mainMenuContent = findViewById<View>(R.id.main_menu_content)
        val hasFragments = supportFragmentManager.backStackEntryCount > 0
        mainMenuContent.visibility = if (hasFragments) View.GONE else View.VISIBLE
        
        // Refresh high score when main menu becomes visible
        if (!hasFragments) {
            refreshHighScore()
        }
    }

    private fun refreshHighScore() {
        val sharedPref = getSharedPreferences("Scores", MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0).toLong()
        val tv = findViewById<TextView>(R.id.high_S)
        tv.text = highScore.toString()
    }

    override fun onResume() {
        super.onResume()
        // Refresh high score when returning from OptionFragment or game
        refreshHighScore()
        updateMainMenuVisibility()
    }
}
