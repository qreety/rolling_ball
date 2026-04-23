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


public class HowTo extends Activity {

    /**
     * onCreate
     * shows the tutorial texts and back button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        SharedPreferences sharedPref = HowTo.this.getSharedPreferences("Scores",MODE_WORLD_READABLE);
        long highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0);
        TextView tv = (TextView) findViewById(R.id.high_S);
        tv.setText(String.valueOf(highScore));
        Typeface face = Typeface.createFromAsset (  getAssets() , "fonts/Chalkduster.ttf" );
        tv.setTypeface(face);

        TextView tut = (TextView) findViewById(R.id.ht);
        tut.setText("Tilt your phone.\n" +
                "Eat stars to get score.\n" +
                "Avoid bombs, or you lose.\n" +
                "Have fun!");
        tut.setTypeface(face);
        tut.setTextSize(28);
        tut.setLineSpacing(5,(float) 1.5);

        Button btn = (Button) findViewById(R.id.back_btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg){
                Vibrator vb = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {0,20};
                vb.vibrate(pattern,-1);
                finish();
            }
        });
    }
}
