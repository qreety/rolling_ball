package cn.com.example.wendi.rollingball

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.edit

class GameView : SurfaceView, SurfaceHolder.Callback, GameEngineListener {

    private lateinit var soundManager: SoundManager

    private lateinit var state: GameState
    private lateinit var renderer: GameRenderer
    private lateinit var objectManager: GameObjectManager
    private lateinit var gameEngine: GameEngine
    private lateinit var sensorController: SensorController

    private var holder: SurfaceHolder
    private var gameThread: Thread? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    var isGameRunning: Boolean
        get() = state.isGameRunning
        set(value) {
            state.isGameRunning = value
        }

    val score: Int
        get() = state.score

    val isGameActive: Boolean
        get() = state.run

    constructor(context: Context, highscore: Int, soundManager: SoundManager) : super(context) {
        this.soundManager = soundManager
        holder = getHolder()
        initialize(highscore)
    }

    private fun initialize(highscore: Int) {
        this.isFocusable = true
        this.isFocusableInTouchMode = true
        holder.addCallback(this)

        state = GameState()
        state.highScore = highscore

        renderer = GameRenderer(context)
        renderer.initialize()

        objectManager = GameObjectManager()

        sensorController = SensorController(context, state)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        state.run = true
        state.isGameRunning = true

        val ballBitmap = renderer.getBallBitmap()
        state.initializeDimensions(width, height, ballBitmap.width, ballBitmap.height)

        sensorController.initialize()

        gameEngine = GameEngine(holder, state, renderer, objectManager, soundManager, this)
        gameThread = Thread(gameEngine)
        gameThread?.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        state.isGameRunning = false
        state.run = false
        gameThread?.join(100)
        gameThread = null
        sensorController.unregister()
    }

    fun restart() {
        state.restart()
    }

    fun kill() {
        state.run = false
        state.isGameRunning = false
        gameThread?.join(100)
        gameThread = null
        sensorController.unregister()
    }

    override fun onGameOver() {
        // Post to main thread since this is called from the game engine thread
        mainHandler.post {
            val sharedPref = context.getSharedPreferences("Scores", Context.MODE_PRIVATE)
            val highScore = sharedPref.getInt(context.getString(R.string.saved_high_score), 0)
            val currentScore = state.score
            
            val isNewHighScore = currentScore > highScore
            if (isNewHighScore) {
                sharedPref.edit {
                    putInt(context.getString(R.string.saved_high_score), currentScore)
                }
            }
            
            // Show FinalScoreFragment instead of starting FinalScore Activity
            (context as? SurfaceViewActivity)?.showFinalScore(currentScore, highScore, isNewHighScore)
        }
    }

    override fun onStarCollected() {
        context.vibrateInGame(VibrationType.STAR)
    }

    override fun onBombHit() {
        context.vibrateInGame(VibrationType.BOMB)
    }
}
