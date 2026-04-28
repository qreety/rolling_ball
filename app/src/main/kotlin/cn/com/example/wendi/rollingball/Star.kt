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

    /**
     * Check if the ball has hit the bomb
     * @param bX the ball's location
     * @param bY the ball's location
     * @return true if the ball hits the bomb
     */
    fun checkBall(bX: Float, bY: Float): Boolean {
        return X + W > bX && Y + H > bY && X < bX + 30 && Y < bY + 30
    }
}
