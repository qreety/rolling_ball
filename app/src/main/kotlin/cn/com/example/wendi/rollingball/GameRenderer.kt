package cn.com.example.wendi.rollingball

import android.content.Context
import android.graphics.*

class GameRenderer(private val context: Context) {

    private lateinit var gBall: Bitmap
    private lateinit var gStar: Bitmap
    private lateinit var gBomb: Bitmap

    private val shapePaint = Paint()
    private val textPaint = Paint()

    fun initialize() {
        shapePaint.color = Color.BLACK
        shapePaint.strokeWidth = 10f
        shapePaint.style = Paint.Style.STROKE

        textPaint.textSize = 60f
        textPaint.color = Color.BLACK
        val tf = Typeface.createFromAsset(context.assets, "fonts/Chalkduster.ttf")
        textPaint.typeface = tf

        gBall = BitmapFactory.decodeResource(context.resources, R.drawable.ball)
        gStar = BitmapFactory.decodeResource(context.resources, R.drawable.s)
        gBomb = BitmapFactory.decodeResource(context.resources, R.drawable.bomb)
    }

    fun draw(canvas: Canvas?, state: GameState) {
        if (canvas == null) return

        canvas.drawColor(Color.rgb(235, 230, 203))

        canvas.drawRect(
            GameConstants.BOARD_BORDER_LEFT,
            GameConstants.BOARD_BORDER_TOP,
            (state.screenWidth - GameConstants.BOARD_BORDER_RIGHT_OFFSET).toFloat(),
            (state.screenHeight - GameConstants.BOARD_BORDER_BOTTOM_OFFSET).toFloat(),
            shapePaint
        )

        synchronized(state.starList) {
            for (s in state.starList) {
                canvas.drawBitmap(gStar, s.X, s.Y, shapePaint)
            }
        }

        synchronized(state.bombList) {
            for (b in state.bombList) {
                canvas.drawBitmap(gBomb, b.X, b.Y, shapePaint)
            }
        }

        canvas.drawBitmap(gBall, state.ballX, state.ballY, shapePaint)

        canvas.drawText(state.score.toString(), GameConstants.SCORE_X, GameConstants.SCORE_Y, textPaint)
        canvas.drawText(state.highScore.toString(), (state.screenWidth - GameConstants.HIGH_SCORE_X_OFFSET).toFloat(), GameConstants.SCORE_Y, textPaint)
    }

    fun getBallBitmap(): Bitmap = gBall

    fun getStarBitmap(): Bitmap = gStar

    fun getBombBitmap(): Bitmap = gBomb
}
