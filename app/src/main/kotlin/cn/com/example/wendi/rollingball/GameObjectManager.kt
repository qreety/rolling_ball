package cn.com.example.wendi.rollingball

import android.graphics.Bitmap

class GameObjectManager {

    data class CollisionResult(
        val starsCollected: Int,
        val bombHit: Boolean
    )

    fun update(
        state: GameState,
        sTime: Int,
        starBitmap: Bitmap,
        bombBitmap: Bitmap
    ): CollisionResult {
        val starTime = GameConstants.STAR_SPAWN_INTERVAL
        val stayTime = GameConstants.OBJECT_LIFETIME
        val bombTime = 200000 / (state.score + 10)

        if (sTime >= starTime * state.counter) {
            val xy = randomPosition(state, starBitmap, bombBitmap)
            val sX = xy[0]
            val sY = xy[1]

            val s = Star(sX, sY, sTime, starBitmap)
            state.starList.add(s)
            state.counter++
        }

        if (sTime >= state.bombIntervalAccumulator && sTime - state.bombIntervalAccumulator > bombTime) {
            val xy = randomPosition(state, starBitmap, bombBitmap)
            val bX = xy[0]
            val bY = xy[1]

            val b = Bomb(bX, bY, sTime, bombBitmap)
            state.bombList.add(b)
            state.bombIntervalAccumulator += bombTime
        }

        var starsCollected = 0
        synchronized(state.starList) {
            var i = 0
            while (i < state.starList.size) {
                if (sTime - state.starList[i].gTime >= stayTime) {
                    state.starList.removeAt(i)
                    continue
                }

                if (state.starList[i].checkBall(state.ballX, state.ballY)) {
                    state.starList.removeAt(i)
                    starsCollected++
                    continue
                }
                i++
            }
        }

        var bombHit = false
        synchronized(state.bombList) {
            var i = 0
            while (i < state.bombList.size) {
                if (state.bombList[i].checkBall(state.ballX, state.ballY)) {
                    bombHit = true
                    state.bombList.removeAt(i)
                    break
                }
                if (sTime - state.bombList[i].gTime >= stayTime) {
                    state.bombList.removeAt(i)
                    continue
                }
                i++
            }
        }

        if (bombHit) {
            synchronized(state.starList) {
                state.starList.clear()
            }
            synchronized(state.bombList) {
                state.bombList.clear()
            }
        }

        return CollisionResult(starsCollected, bombHit)
    }

    private fun randomPosition(
        state: GameState,
        starBitmap: Bitmap,
        bombBitmap: Bitmap
    ): FloatArray {
        val xy = FloatArray(2)
        var sX = 0f
        var sY = 0f
        var oks = false
        var okb = false
        var os = true
        var ob = true
        var attempts = 0
        val maxAttempts = 100

        while (!(oks && okb) && attempts < maxAttempts) {
            sX = (Math.random() * (state.screenWidth - starBitmap.width - GameConstants.GAME_AREA_RIGHT_MARGIN - GameConstants.GAME_AREA_LEFT) + GameConstants.GAME_AREA_LEFT).toFloat()
            sY = (Math.random() * (state.screenHeight - starBitmap.height - GameConstants.GAME_AREA_BOTTOM_MARGIN - GameConstants.GAME_AREA_TOP) + GameConstants.GAME_AREA_TOP).toFloat()
            oks = os
            okb = ob

            synchronized(state.starList) {
                for (s in state.starList) {
                    os = (s.X + starBitmap.width) < sX || (sX + starBitmap.width) < s.X || (s.Y + starBitmap.height) < sY || (sY + starBitmap.height) < s.Y
                    oks = oks && os
                }
            }

            synchronized(state.bombList) {
                for (b in state.bombList) {
                    ob = (b.X + bombBitmap.width) < sX || (sX + bombBitmap.width) < b.X || (b.Y + bombBitmap.height) < sY || (sY + bombBitmap.height) < b.Y
                    okb = okb && ob
                }
            }
            attempts++
        }

        xy[0] = sX
        xy[1] = sY
        return xy
    }
}
