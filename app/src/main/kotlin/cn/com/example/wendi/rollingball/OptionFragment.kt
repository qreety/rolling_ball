package cn.com.example.wendi.rollingball

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val face = Typeface.createFromAsset(activity.assets, "fonts/Chalkduster.ttf")
        tv.typeface = face

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
            tv.typeface = face
        }
    }
}
