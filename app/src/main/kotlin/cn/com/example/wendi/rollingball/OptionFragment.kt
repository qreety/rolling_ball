package cn.com.example.wendi.rollingball

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment

class OptionFragment : Fragment() {

    companion object {
        fun newInstance(): OptionFragment {
            return OptionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity ?: return
        val sharedPref = activity.getSharedPreferences("Scores", Context.MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0).toLong()
        
        val tv = view.findViewById<TextView>(R.id.high_S)
        tv.text = highScore.toString()

        // Sound toggle button
        val soundButton = view.findViewById<ImageButton>(R.id.sound_btn)
        updateSoundButton(soundButton, activity.isSoundEnabled())
        soundButton.setOnClickListener {
            activity.vibrateInGame(VibrationType.CLICK)
            val newSoundState = !activity.isSoundEnabled()
            activity.setSoundEnabled(newSoundState)
            updateSoundButton(soundButton, newSoundState)
        }

        // Vibration toggle button
        val vibrationButton = view.findViewById<ImageButton>(R.id.vibration_btn)
        updateVibrationButton(vibrationButton, activity.isVibrationEnabled())
        vibrationButton.setOnClickListener {
            activity.vibrateInGame(VibrationType.CLICK)
            val newVibrationState = !activity.isVibrationEnabled()
            activity.setVibrationEnabled(newVibrationState)
            updateVibrationButton(vibrationButton, newVibrationState)
        }

        val bk = view.findViewById<Button>(R.id.bck)
        bk.setOnClickListener {
            activity.vibrateInGame(VibrationType.CLICK)
            parentFragmentManager.popBackStack()
        }

        val cr = view.findViewById<Button>(R.id.del)
        cr.setOnClickListener {
            activity.vibrateInGame(VibrationType.CLICK)
            sharedPref.edit {
                putInt(getString(R.string.saved_high_score), 0)
            }
            tv.text = "0"
        }
    }

    private fun updateSoundButton(button: ImageButton, isSoundEnabled: Boolean) {
        if (isSoundEnabled) {
            button.setImageResource(R.drawable.icon_sound_on)
        } else {
            button.setImageResource(R.drawable.icon_sound_off)
        }
    }

    private fun updateVibrationButton(button: ImageButton, isVibrationEnabled: Boolean) {
        if (isVibrationEnabled) {
            button.setImageResource(R.drawable.icon_vibration_on)
        } else {
            button.setImageResource(R.drawable.icon_vibration_off)
        }
    }
}
