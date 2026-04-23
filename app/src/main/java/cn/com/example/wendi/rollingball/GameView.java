package cn.com.example.wendi.rollingball;

/**
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: April 6, 2015
 * For Assignment 5, CS6301.001
 * This class is the Game View
 * It draws the game
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback, SensorEventListener {

    Context mContext;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> hashMap;      //Sound pool hash map
    public static final int TIME_IN_FRAME = 50;     //Refresh time
    Paint gPaint, tPaint;                           //Paint the background and texts
    boolean gRunning = false, run = true;           //thread and ball status
    SurfaceHolder holder;
    Canvas gCanvas;
    private Bitmap gBall, gStar, gBomb;
    Sensor gSensor = null;
    private float ballX = 60, ballY = 200;
    private int scnWidth, scnHeight, ballWidth, ballHeight;
    private int bsum;                               //Calculate bombs' interval time
    private List<Star> starList = new ArrayList<>();
    private List<Bomb> bombList = new ArrayList<>();
    public int score = 0;
    long iniTime = System.currentTimeMillis();      //When the game starts
    int counter = 0;
    int hs;                                         //HighScore
    boolean sound;

    /**
     * Constructor
     * @param mContext
     */
    public GameView(Context mContext){
        super(mContext);
        this.mContext = mContext;
    }

    /**
     * Constructor
     * @param mContext the activity's context
     * @param Highscore saved high score
     * @param Sound sound option
     */
    public GameView(Context mContext, int Highscore, boolean Sound) {
        super(mContext);
        this.mContext = mContext;
        hs = Highscore;
        sound = Sound;
        ini();
    }

    /**
     * initialize variables, canvas and soundpool
     */
    private void ini(){
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        holder = getHolder();
        holder.addCallback(this);
        gCanvas = new Canvas();
        gPaint = new Paint();
        tPaint = new Paint();
        gPaint.setColor(Color.BLACK);
        gPaint.setStrokeWidth(10);
        gPaint.setStyle(Paint.Style.STROKE);
        tPaint.setTextSize(60);
        tPaint.setColor(Color.BLACK);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Chalkduster.ttf");
        tPaint.setTypeface(tf);
        gBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.ball);
        gStar = BitmapFactory.decodeResource(this.getResources(), R.drawable.s);
        gBomb = BitmapFactory.decodeResource(this.getResources(), R.drawable.bomb);

        SensorManager gSensorMgr = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        gSensor = gSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gSensorMgr.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_FASTEST);

        initSoundPool();
    }

    /**
     * initialize soundpool
     */
    private void initSoundPool() {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        hashMap = new HashMap<>();
        hashMap.put(1, soundPool.load(mContext, R.raw.star, 1));
        hashMap.put(2, soundPool.load(mContext, R.raw.bomb, 2));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * when surfaceview is created
     * start the thread to draw the game
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gRunning = true;

            Thread t = new Thread(this);
            t.start();

        scnWidth = this.getWidth();
        scnHeight = this.getHeight();
        ballWidth = scnWidth - gBall.getWidth()- 36;
        ballHeight = scnHeight - gBall.getHeight() - 42;
    }

    /**
     * when the view is closed
     * stop the thread
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gRunning = false;
        Thread.currentThread().interrupt();
    }

    /**
     * Reset all the parameter
     * to restart the game
     */
    public void restart(){
        gRunning = true;
        ballX = 0;
        ballY = 0;
        score = 0;
        starList.clear();
        bombList.clear();
        iniTime = System.currentTimeMillis();
        counter = 0;
        bsum = 2000;
    }

    /**
     * calculate and draw the game
     */
    @Override
    public void run() {
        bsum = 2000;

        while (run) {
            if( gRunning ){
                int starTime = 2000;
                int stayTime = 5000;
                int bombTime = 200000 / (score + 10);

            long startTime = System.currentTimeMillis();
            long endTime = System.currentTimeMillis();
            int diffTime = (int) (endTime - startTime);
            int sTime = (int) (endTime - iniTime);

                /**
                 * Calculate and generate new stars
                 */
                if (sTime >= starTime * counter)
                {
                    float sX = randomX()[0];
                    float sY = randomX()[1];

                    Star s = new Star(sX, sY, sTime, gStar);
                    starList.add(s);
                    counter++;
                }

                /**
                 * Calculate and generate new bombs
                 */
                if (sTime >= bsum && sTime - bsum > bombTime)
                {
                    float bX = randomX()[0];
                    float bY = randomX()[1];

                    Bomb b = new Bomb(bX, bY, sTime, gBomb);
                    bombList.add(b);
                    bsum += bombTime;
                }

                /**
                 * lock and draw on the canvas
                 */
            synchronized (holder) {
                gCanvas = holder.lockCanvas();

                gCanvas.drawColor(Color.rgb(235,230,203));
                gCanvas.drawRect(32, 142, scnWidth - 32, scnHeight - 42, gPaint);

                for (Star s : starList) {
                    gCanvas.drawBitmap(gStar, s.X, s.Y, gPaint);
                }

                for (Bomb b : bombList) {
                    gCanvas.drawBitmap(gBomb, b.X, b.Y, gPaint);
                }

                gCanvas.drawBitmap(gBall, ballX, ballY, gPaint);
                gCanvas.drawText(Integer.toString(score), 50, 80, tPaint);
                gCanvas.drawText(Integer.toString(hs), scnWidth - 200, 80, tPaint);
                holder.unlockCanvasAndPost(gCanvas);

                holder.lockCanvas(new Rect(0,0,0,0));
                holder.unlockCanvasAndPost(gCanvas);
            }

                /**
                 * Calculate if the stars are hitted by the ball
                 * or if the stars should be removed
                 */
                for(int i = 0; i < starList.size(); i++)
                {
                    if(sTime - starList.get(i).gTime >= stayTime)
                        starList.remove(i);

                    if(starList.get(i).checkBall(ballX,ballY)) {
                        starList.remove(i);
                        if (sound)
                            soundPool.play(hashMap.get(1), 20, 20, 1, 0, 1.0f);
                        score +=5;
                    }
                }

                /**
                 * Calculate if the bombs are hitted by the ball
                 * or if the bomb should be remove
                 */
                for(int i = 0; i < bombList.size(); i++) {
                    if (bombList.get(i).checkBall(ballX, ballY)) {
                        gRunning = false;
                        Vibrator vb = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
                        long [] pattern = {0,1000};
                        vb.vibrate(pattern,-1);
                        if (sound)
                            soundPool.play(hashMap.get(2), 20, 20, 1, 0, 1.0f);
                        Intent newIntent = new Intent(mContext, FinalScore.class);
                        ((Activity)mContext).startActivityForResult(newIntent,0);

                    }
                    if(sTime - bombList.get(i).gTime >= stayTime)
                        bombList.remove(i);
                }

                /**
                 * refresh the canvas every 50 ms
                 */
            while (diffTime <= TIME_IN_FRAME) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                Thread.yield();
            }}
            else {
                Thread.yield();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    /**
     * kill both soundpool and drawing thread
     */
    public void kill(){
        run = false;
        soundPool.release();
    }

    /**
     * Generate X value for new stars and bombs
     * make sure no overlapping with current stars and bombs
     * @return X value
     */
    private float[] randomX(){
        float[] xy = new float[2];
        float sX = 0, sY = 0;
        boolean oks = false;
        boolean okb = false;
        boolean os = true;
        boolean ob = true;

        while (!(oks && okb))
        {
            sX = (float) (Math.random()*(scnWidth - gStar.getWidth() - 36 - 42) + 42);
            sY = (float) (Math.random()*(scnHeight - gStar.getHeight() - 42 - 132) + 132);
            oks = os;
            okb = ob;

            for(Star s: starList)
            {
                if((s.X + gStar.getWidth()) < sX | (sX + gStar.getWidth()) < s.X | (s.Y + gStar.getHeight()) < sY | (sY + gStar.getHeight()) < s.Y)
                    os = true;
                else
                    os = false;
                oks = oks && os;
            }

            for (Bomb b:bombList)
            {
                if ((b.X + gBomb.getWidth()) < sX | (sX + gBomb.getWidth()) < b.X | (b.Y + gBomb.getHeight()) < sY | (sY + gBomb.getHeight()) < b.Y)
                    ob = true;
                else
                    ob = false;
                okb = okb && ob;
            }
        }

            xy[0] = sX;
            xy[1] = sY;
            return xy;
    }

    /**
     * Override the method
     * let the ball react to the the change of gravity sensor value
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float gX = event.values[SensorManager.DATA_X];
        float gY = event.values[SensorManager.DATA_Y];
        float gZ = event.values[SensorManager.DATA_Z];

        if(gRunning) {
            ballX += gY * 3;
            ballY += gX * 3;
        }
            if (ballX < 42)
                ballX = 42;
            else if (ballX > ballWidth)
                ballX = ballWidth;

            if (ballY < 132) {
                ballY = 132;
            } else if (ballY > ballHeight) {
                ballY = ballHeight;
            }
        }
    }