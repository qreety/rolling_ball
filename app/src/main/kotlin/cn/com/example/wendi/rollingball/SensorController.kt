package cn.com.example.wendi.rollingball

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorController(
    private val context: Context,
    private val state: GameState
) : SensorEventListener {

    private var gSensor: Sensor? = null
    private var sensorManager: SensorManager? = null

    fun initialize() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager?.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun unregister() {
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val gX = event.values[0]
        val gY = event.values[1]

        if (state.isGameRunning) {
            state.ballX += gY * GameConstants.SENSOR_SENSITIVITY
            state.ballY += gX * GameConstants.SENSOR_SENSITIVITY

            // Only clamp if dimensions have been initialized
            if (state.maxBallX > 0 && state.maxBallY > 0) {
                if (state.ballX < GameConstants.GAME_AREA_LEFT)
                    state.ballX = GameConstants.GAME_AREA_LEFT.toFloat()
                else if (state.ballX > state.maxBallX)
                    state.ballX = state.maxBallX.toFloat()

                if (state.ballY < GameConstants.GAME_AREA_TOP) {
                    state.ballY = GameConstants.GAME_AREA_TOP.toFloat()
                } else if (state.ballY > state.maxBallY) {
                    state.ballY = state.maxBallY.toFloat()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
