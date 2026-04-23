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


public class Option extends Activity {

    /**
     * onCreate method
     * set the buttons
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        SharedPreferences sharedPref = Option.this.getSharedPreferences("Scores",MODE_WORLD_READABLE);
        long highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0);
        TextView tv = (TextView) findViewById(R.id.high_S);
        tv.setText(String.valueOf(highScore));
        Typeface face = Typeface.createFromAsset (  getAssets() , "fonts/Chalkduster.ttf" );
        tv.setTypeface(face);

        Button bk = (Button)findViewById(R.id.bck);
        bk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg){
                Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                Option.this.finish();
            }
        });

        /**
         * user can clear high score record here
         */
        Button cr = (Button) findViewById(R.id.del);
        cr.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View arg){
                Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                SharedPreferences sharedPref = Option.this.getSharedPreferences("Scores", MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.saved_high_score), 0);
                editor.commit();
                TextView tv = (TextView) findViewById(R.id.high_S);
                tv.setText("0");
                Typeface face = Typeface.createFromAsset (  getAssets() , "fonts/Chalkduster.ttf" );
                tv.setTypeface(face);
            }
        });
    }


}
