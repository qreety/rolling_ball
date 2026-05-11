package cn.com.example.wendi.rollingball

import android.graphics.Bitmap

class Bomb(
    var X: Float,
    var Y: Float,
    var gTime: Int,
    var pic: Bitmap
) {
    fun checkBall(bX: Float, bY: Float): Boolean {
        return X + pic.width > bX && Y + pic.height > bY && X < bX + GameConstants.BALL_COLLISION_SIZE && Y < bY + GameConstants.BALL_COLLISION_SIZE
    }
}
