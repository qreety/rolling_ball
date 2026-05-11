package cn.com.example.wendi.rollingball

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    private val soundManager: SoundManager?
        get() = (activity as? SurfaceViewActivity)?.soundManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_pause, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val soundManager = soundManager ?: return

        val button = view.findViewById<Button>(R.id.s_btn)
        updateSoundButton(button, soundManager.isSoundEnabled())
        
        button.setOnClickListener {
            activity?.vibrateInGame(VibrationType.CLICK)
            val newSoundState = !soundManager.isSoundEnabled()
            soundManager.setSoundEnabled(newSoundState)
            updateSoundButton(button, newSoundState)
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

    private fun updateSoundButton(button: Button, isSoundEnabled: Boolean) {
        if (isSoundEnabled) {
            button.setBackgroundResource(R.drawable.s_on)
        } else {
            button.setBackgroundResource(R.drawable.s_off)
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
