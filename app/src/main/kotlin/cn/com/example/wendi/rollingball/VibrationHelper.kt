package cn.com.example.wendi.rollingball

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.edit

enum class VibrationType {
    CLICK,
    STAR,
    BOMB
}

private const val PREFS_NAME = "Scores"

fun Context.isVibrationEnabled(): Boolean {
    val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(getString(R.string.vibration_enabled), true)
}

fun Context.setVibrationEnabled(enabled: Boolean) {
    val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    sharedPref.edit {
        putBoolean(getString(R.string.vibration_enabled), enabled)
    }
}

fun Context.vibrateInGame(type: VibrationType) {
    if (!isVibrationEnabled()) {
        return
    }

    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    when (type) {
        VibrationType.CLICK ->
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
        VibrationType.STAR -> vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
        VibrationType.BOMB -> vibrator.vibrate(
            VibrationEffect.createOneShot(
                1000,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }
}
