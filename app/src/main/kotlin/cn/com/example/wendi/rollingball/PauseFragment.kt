package cn.com.example.wendi.rollingball

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class PauseFragment : DialogFragment() {

    companion object {
        const val REQUEST_KEY = "pause_request"
        const val RESULT_CODE = "result_code"
        
        const val CONTINUE = 0
        const val RESTART = 1
        const val END_GAME = 2

        fun newInstance(): PauseFragment {
            return PauseFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_pause, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity ?: return

        val soundButton = view.findViewById<ImageButton>(R.id.s_btn)
        updateSoundButton(soundButton, activity.isSoundEnabled())
        
        soundButton.setOnClickListener {
            activity.vibrateInGame(VibrationType.CLICK)
            val newSoundState = !activity.isSoundEnabled()
            activity.setSoundEnabled(newSoundState)
            updateSoundButton(soundButton, newSoundState)
        }

        val vibrationButton = view.findViewById<ImageButton>(R.id.v_btn)
        updateVibrationButton(vibrationButton, activity.isVibrationEnabled())
        
        vibrationButton.setOnClickListener {
            activity.vibrateInGame(VibrationType.CLICK)
            val newVibrationState = !activity.isVibrationEnabled()
            activity.setVibrationEnabled(newVibrationState)
            updateVibrationButton(vibrationButton, newVibrationState)
        }

        val button1 = view.findViewById<Button>(R.id.new_btn)
        button1.setOnClickListener {
            activity?.vibrateInGame(VibrationType.CLICK)
            sendResult(RESTART)
        }

        val button2 = view.findViewById<Button>(R.id.back_btn)
        button2.setOnClickListener {
            activity?.vibrateInGame(VibrationType.CLICK)
            sendResult(CONTINUE)
        }

        val button3 = view.findViewById<Button>(R.id.end_btn)
        button3.setOnClickListener {
            activity?.vibrateInGame(VibrationType.CLICK)
            sendResult(END_GAME)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(true)
            window?.setBackgroundDrawableResource(R.drawable.background)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        sendResult(CONTINUE)
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

    private fun sendResult(resultCode: Int) {
        val result = Bundle().apply {
            putInt(RESULT_CODE, resultCode)
        }
        setFragmentResult(REQUEST_KEY, result)
        dismiss()
    }
}
