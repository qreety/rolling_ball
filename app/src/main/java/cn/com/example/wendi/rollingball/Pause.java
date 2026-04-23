package cn.com.example.wendi.rollingball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

/**
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: April 14, 2015
 * For Assignment 5, CS6301.001
 * This class is the activity
 * to show when user pauses the game
 */

public class Pause extends Activity {

    /**
     * onCreate method
     * set the buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /**
         * change the sound option
         */
        Intent intent = getIntent();
        boolean sound = intent.getBooleanExtra("Sound", true);
        final Button button = (Button) findViewById(R.id.s_btn);
        if (!sound) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg) {
                    Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    long [] pattern = {0,20};
                    vb.vibrate(pattern,-1);
                    button.setBackground(Pause.this.getResources().getDrawable(R.drawable.s_on));

                    Intent it = new Intent();
                    it.putExtra("Sound",true);
                    setResult(3,it);

                    finish();
                }
            });
        }
        else {
            button.setBackground(this.getResources().getDrawable(R.drawable.s_on));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg) {
                    Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    long [] pattern = {0,20};
                    vb.vibrate(pattern,-1);
                    button.setBackground(Pause.this.getResources().getDrawable(R.drawable.s_off));
                    Intent it = new Intent();
                    it.putExtra("Sound",false);
                    setResult(3,it);

                    finish();
                }
            });
        }

        /**
         * Start a new game
         */
    Button button1 = (Button) findViewById(R.id.new_btn);
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
         * Continue current game
         */
        Button button2 = (Button) findViewById(R.id.back_btn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                Intent it = new Intent();
                setResult(0,it);
                finish();
            }
        });

        /**
         * End current game and back to main menu
         */
        Button button3 = (Button) findViewById(R.id.end_btn);
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
    }

    /**
     * if user hits back button
     * continue the game
     */
    @Override
    public void onBackPressed(){
        Intent it = new Intent();
        setResult(0,it);
        finish();
    }
}
