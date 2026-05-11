package cn.com.example.wendi.rollingball

import android.view.SurfaceHolder

interface GameEngineListener {
    fun onGameOver()
    fun onStarCollected()
    fun onBombHit()
}

class GameEngine(
    private val holder: SurfaceHolder,
    private val state: GameState,
    private val renderer: GameRenderer,
    private val objectManager: GameObjectManager,
    private val soundManager: SoundManager,
    private val listener: GameEngineListener
) : Runnable {

    companion object {
        const val TIME_IN_FRAME = 50
    }

    override fun run() {
        state.bombIntervalAccumulator = GameConstants.INITIAL_BOMB_INTERVAL

        while (state.run) {
            if (state.isGameRunning) {
                val startTime = System.currentTimeMillis()
                var endTime = System.currentTimeMillis()
                var diffTime = (endTime - startTime).toInt()
                val sTime = (endTime - state.iniTime).toInt()

                val collisionResult = objectManager.update(
                    state,
                    sTime,
                    renderer.getStarBitmap(),
                    renderer.getBombBitmap()
                )

                if (collisionResult.starsCollected > 0) {
                    repeat(collisionResult.starsCollected) {
                        soundManager.playSound(SoundManager.SoundType.STAR)
                        state.score += 5
                        listener.onStarCollected()
                    }
                }

                if (collisionResult.bombHit) {
                    state.isGameRunning = false
                    state.run = false
                    listener.onBombHit()
                    soundManager.playSound(SoundManager.SoundType.BOMB)
                    listener.onGameOver()
                }

                synchronized(holder) {
                    val canvas = holder.lockCanvas()
                    if (canvas != null) {
                        renderer.draw(canvas, state)
                        holder.unlockCanvasAndPost(canvas)
                    }
                }

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

    fun kill() {
        state.run = false
    }
}
