package cn.com.example.wendi.rollingball

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.core.content.edit

/**
 * Manages game sound effects using SoundPool.
 * Handles sound loading, playback, and resource cleanup.
 * Persists sound settings to SharedPreferences.
 */
class SoundManager(private val context: Context) {

    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<SoundType, Int>()
    private var isSoundEnabled: Boolean = true
    private var isInitialized: Boolean = false

    companion object {
        private const val PREFS_NAME = "Scores"
    }

    /**
     * Types of sounds available in the game
     */
    enum class SoundType {
        STAR,  // Sound when collecting a star
        BOMB   // Sound when hitting a bomb
    }

    /**
     * Initialize the SoundManager and load sound resources.
     * Loads sound setting from SharedPreferences.
     * Must be called before playing any sounds.
     */
    fun initialize() {
        if (isInitialized) return

        // Load sound setting from SharedPreferences
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

        // Load sound resources
        soundMap[SoundType.STAR] = soundPool?.load(context, R.raw.star, 1) ?: 0
        soundMap[SoundType.BOMB] = soundPool?.load(context, R.raw.bomb, 1) ?: 0

        isInitialized = true
    }

    /**
     * Play a sound effect if sound is enabled.
     * @param type The type of sound to play
     * @param volume Volume level (0.0 to 1.0), default is 1.0
     */
    fun playSound(type: SoundType, volume: Float = 1.0f) {
        if (!isSoundEnabled || !isInitialized) return

        val soundId = soundMap[type] ?: return
        soundPool?.play(soundId, volume, volume, 1, 0, 1.0f)
    }

    /**
     * Enable or disable sound playback.
     * Persists the setting to SharedPreferences.
     * @param enabled true to enable sound, false to disable
     */
    fun setSoundEnabled(enabled: Boolean) {
        isSoundEnabled = enabled
        // Persist to SharedPreferences
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPref.edit {
            putBoolean(context.getString(R.string.sound_enabled), enabled)
        }
    }

    /**
     * Check if sound is currently enabled.
     * @return true if sound is enabled, false otherwise
     */
    fun isSoundEnabled(): Boolean = isSoundEnabled

    /**
     * Release SoundPool resources.
     * Should be called when the SoundManager is no longer needed.
     */
    fun release() {
        soundPool?.release()
        soundPool = null
        soundMap.clear()
        isInitialized = false
    }
}
