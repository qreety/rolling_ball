package cn.com.example.wendi.rollingball

import java.util.ArrayList
import java.util.Collections

class GameState {
    @Volatile
    var ballX = 60f
    @Volatile
    var ballY = 200f

    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var maxBallX: Int = 0
    var maxBallY: Int = 0

    @Volatile
    var score = 0
    var highScore: Int = 0

    @Volatile
    var isGameRunning = false
    @Volatile
    var run = true

    // Thread-safe lists for game objects
    val starList = Collections.synchronizedList(ArrayList<Star>())
    val bombList = Collections.synchronizedList(ArrayList<Bomb>())

    var iniTime = System.currentTimeMillis()
    var counter = 0
    var bombIntervalAccumulator: Int = 0

    fun restart() {
        isGameRunning = true
        ballX = 60f
        ballY = 200f
        score = 0
        synchronized(starList) {
            starList.clear()
        }
        synchronized(bombList) {
            bombList.clear()
        }
        iniTime = System.currentTimeMillis()
        counter = 0
        bombIntervalAccumulator = GameConstants.INITIAL_BOMB_INTERVAL
    }

    fun initializeDimensions(width: Int, height: Int, ballWidth: Int, ballHeight: Int) {
        screenWidth = width
        screenHeight = height
        this.maxBallX = width - ballWidth - GameConstants.GAME_AREA_RIGHT_MARGIN
        this.maxBallY = height - ballHeight - GameConstants.GAME_AREA_BOTTOM_MARGIN
    }
}
