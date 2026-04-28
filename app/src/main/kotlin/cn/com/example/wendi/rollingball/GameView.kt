package cn.com.example.wendi.rollingball

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.ArrayList
import java.util.HashMap

class GameView : SurfaceView, Runnable, SurfaceHolder.Callback, SensorEventListener {

    private var context: Context
    private lateinit var soundManager: SoundManager
    private var gPaint: Paint                           //Paint the background and texts
    private var tPaint: Paint
    @Volatile
    var gRunning = false
    @Volatile
    private var run = true           //thread and ball status
    private var holder: SurfaceHolder
    private var gCanvas: Canvas? = null
    private lateinit var gBall: Bitmap
    private lateinit var gStar: Bitmap
    private lateinit var gBomb: Bitmap
    private var gSensor: Sensor? = null
    private var ballX = 60f
    private var ballY = 200f
    private var scnWidth: Int = 0
    private var scnHeight: Int = 0
    private var ballWidth: Int = 0
    private var ballHeight: Int = 0
    private var bsum: Int = 0                               //Calculate bombs' interval time
    private val starList = ArrayList<Star>()
    private val bombList = ArrayList<Bomb>()
    var score = 0
    private var iniTime = System.currentTimeMillis()      //When the game starts
    private var counter = 0
    private var hs: Int = 0                                         //HighScore

    companion object {
        const val TIME_IN_FRAME = 50     //Refresh time
    }

    /**
     * Constructor
     * @param context
     */
    constructor(context: Context) : super(context) {
        this.context = context
        holder = getHolder()
        gPaint = Paint()
        tPaint = Paint()
    }

    /**
     * Constructor
     * @param context the activity's context
     * @param highscore saved high score
     * @param soundManager the SoundManager instance (Activity-scoped)
     */
    constructor(context: Context, highscore: Int, soundManager: SoundManager) : super(context) {
        this.context = context
        this.soundManager = soundManager
        hs = highscore
        holder = getHolder()
        gPaint = Paint()
        tPaint = Paint()
        ini()
    }

    /**
     * initialize variables, canvas and soundpool
     */
    private fun ini() {
        this.isFocusable = true
        this.isFocusableInTouchMode = true
        holder = getHolder()
        holder.addCallback(this)
        gCanvas = Canvas()
        gPaint = Paint()
        tPaint = Paint()
        gPaint.color = Color.BLACK
        gPaint.strokeWidth = 10f
        gPaint.style = Paint.Style.STROKE
        tPaint.textSize = 60f
        tPaint.color = Color.BLACK
        val tf = Typeface.createFromAsset(context.assets, "fonts/Chalkduster.ttf")
        tPaint.typeface = tf
        gBall = BitmapFactory.decodeResource(this.resources, R.drawable.ball)
        gStar = BitmapFactory.decodeResource(this.resources, R.drawable.s)
        gBomb = BitmapFactory.decodeResource(this.resources, R.drawable.bomb)

        val gSensorMgr = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gSensor = gSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gSensorMgr.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    /**
     * when surfaceview is created
     * start the thread to draw the game
     * @param holder
     */
    override fun surfaceCreated(holder: SurfaceHolder) {
        gRunning = true

        val t = Thread(this)
        t.start()

        scnWidth = this.width
        scnHeight = this.height
        ballWidth = scnWidth - gBall.width - 36
        ballHeight = scnHeight - gBall.height - 42
    }

    /**
     * when the view is closed
     * stop the thread
     * @param holder
     */
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gRunning = false
        Thread.currentThread().interrupt()
    }

    /**
     * Reset all the parameter
     * to restart the game
     */
    fun restart() {
        gRunning = true
        ballX = 0f
        ballY = 0f
        score = 0
        starList.clear()
        bombList.clear()
        iniTime = System.currentTimeMillis()
        counter = 0
        bsum = 2000
    }



    /**
     * calculate and draw the game
     */
    override fun run() {
        bsum = 2000

        while (run) {
            if (gRunning) {
                val starTime = 2000
                val stayTime = 5000
                val bombTime = 200000 / (score + 10)

                val startTime = System.currentTimeMillis()
                var endTime = System.currentTimeMillis()
                var diffTime = (endTime - startTime).toInt()
                val sTime = (endTime - iniTime).toInt()

                /**
                 * Calculate and generate new stars
                 */
                if (sTime >= starTime * counter) {
                    val xy = randomX()
                    val sX = xy[0]
                    val sY = xy[1]

                    val s = Star(sX, sY, sTime, gStar)
                    starList.add(s)
                    counter++
                }

                /**
                 * Calculate and generate new bombs
                 */
                if (sTime >= bsum && sTime - bsum > bombTime) {
                    val xy = randomX()
                    val bX = xy[0]
                    val bY = xy[1]

                    val b = Bomb(bX, bY, sTime, gBomb)
                    bombList.add(b)
                    bsum += bombTime
                }

                /**
                 * lock and draw on the canvas
                 */
                synchronized(holder) {
                    gCanvas = holder.lockCanvas()

                    gCanvas?.drawColor(Color.rgb(235, 230, 203))
                    gCanvas?.drawRect(
                        32f,
                        142f,
                        (scnWidth - 32).toFloat(),
                        (scnHeight - 42).toFloat(),
                        gPaint
                    )

                    for (s in starList) {
                        gCanvas?.drawBitmap(gStar, s.X, s.Y, gPaint)
                    }

                    for (b in bombList) {
                        gCanvas?.drawBitmap(gBomb, b.X, b.Y, gPaint)
                    }

                    gCanvas?.drawBitmap(gBall, ballX, ballY, gPaint)
                    gCanvas?.drawText(score.toString(), 50f, 80f, tPaint)
                    gCanvas?.drawText(hs.toString(), (scnWidth - 200).toFloat(), 80f, tPaint)
                    holder.unlockCanvasAndPost(gCanvas)

                    holder.lockCanvas(Rect(0, 0, 0, 0))
                    holder.unlockCanvasAndPost(gCanvas)
                }

                /**
                 * Calculate if the stars are hit by the ball
                 * or if the stars should be removed
                 */
                var i = 0
                while (i < starList.size) {
                    if (sTime - starList[i].gTime >= stayTime) {
                        starList.removeAt(i)
                        continue
                    }

                    if (starList[i].checkBall(ballX, ballY)) {
                        starList.removeAt(i)
                        soundManager.playSound(SoundManager.SoundType.STAR)
                        score += 5
                        context.vibrateInGame(VibrationType.STAR)
                        continue
                    }
                    i++
                }

                /**
                 * Calculate if the bombs are hit by the ball
                 * or if the bomb should be remove
                 */
                i = 0
                while (i < bombList.size) {
                    if (bombList[i].checkBall(ballX, ballY)) {
                        gRunning = false
                        context.vibrateInGame(VibrationType.BOMB)
                        soundManager.playSound(SoundManager.SoundType.BOMB)
                        val newIntent = Intent(context, FinalScore::class.java)
                        context.startActivity(newIntent)
                    }
                    if (sTime - bombList[i].gTime >= stayTime) {
                        bombList.removeAt(i)
                        continue
                    }
                    i++
                }

                /**
                 * refresh the canvas every 50 ms
                 */
                while (diffTime <= TIME_IN_FRAME) {
                    endTime = System.currentTimeMillis()
                    diffTime = (endTime - startTime).toInt()
                    Thread.yield()
                }
            } else {
                Thread.yield()
            }
        }
    }

    override fun onAccuracyChanged(arg0: Sensor?, arg1: Int) {
        // TODO Auto-generated method stub
    }

    /**
     * kill the drawing thread
     */
    fun kill() {
        run = false
    }

    /**
     * Generate X value for new stars and bombs
     * make sure no overlapping with current stars and bombs
     * @return X value
     */
    private fun randomX(): FloatArray {
        val xy = FloatArray(2)
        var sX = 0f
        var sY = 0f
        var oks = false
        var okb = false
        var os = true
        var ob = true

        while (!(oks && okb)) {
            sX = (Math.random() * (scnWidth - gStar.width - 36 - 42) + 42).toFloat()
            sY = (Math.random() * (scnHeight - gStar.height - 42 - 132) + 132).toFloat()
            oks = os
            okb = ob

            for (s in starList) {
                if ((s.X + gStar.width) < sX || (sX + gStar.width) < s.X || (s.Y + gStar.height) < sY || (sY + gStar.height) < s.Y)
                    os = true
                else
                    os = false
                oks = oks && os
            }

            for (b in bombList) {
                ob = (b.X + gBomb.width) < sX || (sX + gBomb.width) < b.X || (b.Y + gBomb.height) < sY || (sY + gBomb.height) < b.Y
                okb = okb && ob
            }
        }

        xy[0] = sX
        xy[1] = sY
        return xy
    }

    /**
     * Override the method
     * let the ball react to the the change of gravity sensor value
     * @param event
     */
    override fun onSensorChanged(event: SensorEvent) {
        val gX = event.values[0]
        val gY = event.values[1]
        val gZ = event.values[2]

        if (gRunning) {
            ballX += gY * 3
            ballY += gX * 3
        }
        if (ballX < 42)
            ballX = 42f
        else if (ballX > ballWidth)
            ballX = ballWidth.toFloat()

        if (ballY < 132) {
            ballY = 132f
        } else if (ballY > ballHeight) {
            ballY = ballHeight.toFloat()
        }
    }
}
