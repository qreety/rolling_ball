package cn.com.example.wendi.rollingball

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class FinalScoreFragment : DialogFragment() {

    companion object {
        const val REQUEST_KEY = "final_score_request"
        const val RESULT_CODE = "result_code"
        
        const val RESTART = 0
        const val END_GAME = 1

        private const val ARG_SCORE = "score"
        private const val ARG_IS_NEW_HIGH_SCORE = "is_new_high_score"

        fun newInstance(score: Int, highScore: Int, isNewHighScore: Boolean): FinalScoreFragment {
            return FinalScoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SCORE, score)
                    putBoolean(ARG_IS_NEW_HIGH_SCORE, isNewHighScore)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_final_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val score = arguments?.getInt(ARG_SCORE) ?: 0
        val isNewHighScore = arguments?.getBoolean(ARG_IS_NEW_HIGH_SCORE) ?: false

        val button1 = view.findViewById<Button>(R.id.RE)
        button1.setOnClickListener {
            activity?.vibrateInGame(VibrationType.CLICK)
            sendResult(RESTART)
        }

        val button3 = view.findViewById<Button>(R.id.END)
        button3.setOnClickListener {
            activity?.vibrateInGame(VibrationType.CLICK)
            sendResult(END_GAME)
        }

        val tv1 = view.findViewById<TextView>(R.id.game_over)
        
        if (isNewHighScore) {
            tv1.text = "HighScore!"
            tv1.textSize = 40f
        } else {
            tv1.text = "Game Over!"
            tv1.textSize = 36f
        }

        val tv3 = view.findViewById<TextView>(R.id.score)
        tv3.text = "Final Score: $score"
        tv3.textSize = 30f
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            window?.setBackgroundDrawableResource(R.drawable.background)
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
