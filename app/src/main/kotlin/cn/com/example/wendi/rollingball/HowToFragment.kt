package cn.com.example.wendi.rollingball

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class HowToFragment : Fragment() {

    companion object {
        fun newInstance(): HowToFragment {
            return HowToFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_how_to, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity ?: return
        val sharedPref = activity.getSharedPreferences("Scores", Context.MODE_PRIVATE)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0).toLong()
        
        val tv = view.findViewById<TextView>(R.id.high_S)
        tv.text = highScore.toString()

        val tut = view.findViewById<TextView>(R.id.ht)
        tut.text = "Tilt your phone.\n" +
                "Eat stars to get score.\n" +
                "Avoid bombs, or you lose.\n" +
                "Have fun!"
        tut.textSize = 28f
        tut.setLineSpacing(5f, 1.5f)

        val btn = view.findViewById<Button>(R.id.back_btn)
        btn.setOnClickListener {
            activity.vibrateInGame(VibrationType.CLICK)
            parentFragmentManager.popBackStack()
        }
    }
}
