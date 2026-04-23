package cn.com.example.wendi.rollingball;

/**
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: April 3, 2015
 * For Assignment 5, CS6301.001
 * This class is the Main Menu activity
 * It contains the main menu for the game
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {

    private Vibrator vb;

    /**
     * onCreate method
     * set up three buttons
     * get and show the highscore
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Button button0 = (Button) findViewById(R.id.new_game);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                Intent intent = new Intent(MainMenu.this, SurfaceViewActivity.class);
                startActivity(intent);
            }
        });


        Button button1 = (Button) findViewById(R.id.ht_play);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                Intent intent = new Intent(MainMenu.this, HowTo.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.opt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                Intent intent = new Intent(MainMenu.this, Option.class);
                startActivity(intent);
            }
        });


        SharedPreferences sharedPref = MainMenu.this.getSharedPreferences("Scores", MODE_PRIVATE);
        long highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0);
        TextView tv = (TextView) findViewById(R.id.high_S);
        tv.setText(String.valueOf(highScore));
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chalkduster.ttf");
        tv.setTypeface(face);
    }

}
