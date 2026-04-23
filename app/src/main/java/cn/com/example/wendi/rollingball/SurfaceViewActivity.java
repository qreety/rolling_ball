package cn.com.example.wendi.rollingball;

/**
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: April 3, 2015
 * For Assignment 5, CS6301.001
 * This class is the SurfaceView activity
 * It maintains the game
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SurfaceViewActivity extends Activity{

    GameView gv;
    final int PAUSE = 0;
    final int CONTINUE = 0;
    final int RESTART = 1;
    final int END_GAME = 2;
    final int SOUND = 3;
    boolean sound;

    /**
     * onCreate method
     * starts the SurfaceView
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_surface_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        SharedPreferences sharedPref = SurfaceViewActivity.this.getSharedPreferences("Scores", MODE_PRIVATE);
        int highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0);

        gv = new GameView(SurfaceViewActivity.this, highScore, sound);
        setContentView(gv);
    }

    /**
     * save the high score when the game is paused
     */
    @Override
    public void onPause(){
        SharedPreferences sharedPref = SurfaceViewActivity.this.getSharedPreferences("Scores", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.current_score), gv.score);
        editor.commit();
        super.onPause();
    }

    /**
     * Pause the game whenever user touched the screen
     * during the game
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            gv.gRunning = false;
            Intent it = new Intent(SurfaceViewActivity.this, Pause.class);
            it.putExtra("Sound", sound);
            startActivityForResult(it, PAUSE);
        }
        return true;
    }

    /**
     * Get the result from Pause or GameOver activity
     * and response to their request
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PAUSE){
            switch (resultCode) {
                case CONTINUE:{
                    gv.gRunning = true;
                    break;}
                case RESTART:{
                    gv.restart();
                    break;}
                case END_GAME: {
                    finish();
                    break;
                }
                case SOUND:{
                    Intent intent = getIntent();
                    sound = intent.getBooleanExtra("Sound", true);
                }
            }
        }
    }

    /**
     * when comes back to surfaceview
     * resume the game
     */
    @Override
    public void onResume(){
        gv.gRunning = true;
        super.onResume();
    }

    /**
     * When the activity is killed
     * kill the sound and draw threads
     */
    @Override
    public void onStop(){
        gv.kill();
        super.onStop();
    }
}
