package cn.com.example.wendi.rollingball

import android.graphics.Bitmap

class Bomb(
    var X: Float,
    var Y: Float,
    var gTime: Int,
    var pic: Bitmap
) {
    /**
     * Check if the ball has hit the star
     * @param bX the ball's location
     * @param bY the ball's location
     * @return true if the ball hits the star
     */
    fun checkBall(bX: Float, bY: Float): Boolean {
        return X + pic.width > bX && Y + pic.width > bY && X < bX + 30 && Y < bY + 30
    }
}
