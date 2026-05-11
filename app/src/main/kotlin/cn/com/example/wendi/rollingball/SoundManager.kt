package cn.com.example.wendi.rollingball

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.core.content.edit

class SoundManager(private val context: Context) {

    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<SoundType, Int>()
    private var isSoundEnabled: Boolean = true
    private var isInitialized: Boolean = false

    companion object {
        private const val PREFS_NAME = "Scores"
    }

    enum class SoundType {
        STAR,
        BOMB
    }

    fun initialize() {
        if (isInitialized) return

        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        isSoundEnabled = sharedPref.getBoolean(context.getString(R.string.sound_enabled), true)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()

        soundMap[SoundType.STAR] = soundPool?.load(context, R.raw.star, 1) ?: 0
        soundMap[SoundType.BOMB] = soundPool?.load(context, R.raw.bomb, 1) ?: 0

        isInitialized = true
    }

    fun playSound(type: SoundType, volume: Float = 1.0f) {
        if (!isSoundEnabled || !isInitialized) return

        val soundId = soundMap[type] ?: return
        soundPool?.play(soundId, volume, volume, 1, 0, 1.0f)
    }

    fun setSoundEnabled(enabled: Boolean) {
        isSoundEnabled = enabled
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPref.edit {
            putBoolean(context.getString(R.string.sound_enabled), enabled)
        }
    }

    fun isSoundEnabled(): Boolean = isSoundEnabled

    fun release() {
        soundPool?.release()
        soundPool = null
        soundMap.clear()
        isInitialized = false
    }
}
