package cn.com.example.wendi.rollingball

/**
 * Game configuration constants
 */
object GameConstants {
    // Game area boundaries
    const val GAME_AREA_LEFT = 42
    const val GAME_AREA_TOP = 132
    const val GAME_AREA_RIGHT_MARGIN = 36
    const val GAME_AREA_BOTTOM_MARGIN = 42

    // Ball collision box size
    const val BALL_COLLISION_SIZE = 30

    // Game board border
    const val BOARD_BORDER_LEFT = 32f
    const val BOARD_BORDER_TOP = 142f
    const val BOARD_BORDER_RIGHT_OFFSET = 32
    const val BOARD_BORDER_BOTTOM_OFFSET = 42

    // Score display
    const val SCORE_X = 50f
    const val SCORE_Y = 80f
    const val HIGH_SCORE_X_OFFSET = 200

    // Timing (milliseconds)
    const val STAR_SPAWN_INTERVAL = 2000
    const val OBJECT_LIFETIME = 5000
    const val INITIAL_BOMB_INTERVAL = 2000

    // Physics
    const val SENSOR_SENSITIVITY = 3f
}
