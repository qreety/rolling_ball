package cn.com.example.wendi.rollingball

import android.graphics.Bitmap

class Star(
    var X: Float,
    var Y: Float,
    var gTime: Int,
    var bt: Bitmap
) {
    private val H: Int = bt.height
    private val W: Int = bt.width

    fun checkBall(bX: Float, bY: Float): Boolean {
        return X + W > bX && Y + H > bY && X < bX + GameConstants.BALL_COLLISION_SIZE && Y < bY + GameConstants.BALL_COLLISION_SIZE
    }
}
