package cn.com.example.wendi.rollingball;

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

/**
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: April 14, 2015
 * For Assignment 5, CS6301.001
 * This class is the activity
 * to show the final score when game is over
 */

public class FinalScore extends Activity {

    /**
     * onCreate method
     * set the buttons and shows the score
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPref = FinalScore.this.getSharedPreferences("Scores", MODE_PRIVATE);
        int highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0);
        int Score = sharedPref.getInt(getString(R.string.current_score), 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /**
         * Restart the game
         */
        Button button1 = (Button) findViewById(R.id.RE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                Intent it = new Intent();
                setResult(1,it);
                finish();
            }
        });

        /**
         * End the game and back to main menu
         */
        Button button3 = (Button) findViewById(R.id.END);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                Intent it = new Intent();
                setResult(2,it);
                finish();
            }
        });

        /**
         * Compare current score with saved high score
         * if current score is higher then replace saved high score
         */
        TextView tv1 = (TextView) findViewById(R.id.game_over);
        tv1.setText("Game Over!");
        tv1.setTextSize(36);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chalkduster.ttf");
        tv1.setTypeface(face);

        TextView tv3 = (TextView) findViewById(R.id.score);
        tv3.setText("Final Score: " + String.valueOf(Score));
        tv3.setTextSize(30);
        tv3.setTypeface(face);

        if(Score > highScore){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.saved_high_score), Score);
            editor.commit();

            TextView tv4 = (TextView) findViewById(R.id.game_over);
            tv4.setText("HighScore!");
            tv4.setTextSize(40);
            tv4.setTypeface(face);
        }
    }

    /**
     * if user press back button
     * goes back to main screen
     */
    @Override
    public void onBackPressed(){
        Intent it = new Intent();
        setResult(2,it);
        finish();
    }
}
