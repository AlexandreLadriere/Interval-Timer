package alexandre.ladriere.intervaltimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

const val FORMAT = "%02d"

class MainActivity : AppCompatActivity() {

    private lateinit var setNumberTV: TextView
    private lateinit var setNumberMinusB: ImageButton
    private lateinit var setNumberPlusB: ImageButton

    private lateinit var workIntervalTV: TextView
    private lateinit var workIntervalMinusB: ImageButton
    private lateinit var workIntervalPlusB: ImageButton

    private lateinit var restIntervalTV: TextView
    private lateinit var restIntervalMinusB: ImageButton
    private lateinit var restIntervalPlusB: ImageButton

    private lateinit var startB: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNumberTV = a_main_text_view_sets_number
        setNumberMinusB = a_main_image_button_sets_minus
        setNumberPlusB = a_main_image_button_sets_plus
        workIntervalTV = a_main_text_view_work_interval_time
        workIntervalMinusB = a_main_image_button_work_interval_minus
        workIntervalPlusB = a_main_image_button_work_interval_plus
        restIntervalTV = a_main_text_view_rest_interval_time
        restIntervalMinusB = a_main_image_button_rest_interval_minus
        restIntervalPlusB = a_main_image_button_rest_interval_plus
        startB = a_main_button_start

        setNumberMinusB.setOnClickListener {
            plusOrMinus1(setNumberTV, false)
        }
        setNumberPlusB.setOnClickListener {
            plusOrMinus1(setNumberTV)
        }
        workIntervalPlusB.setOnClickListener {
            plus10(workIntervalTV)
        }
        workIntervalMinusB.setOnClickListener {
            minus10(workIntervalTV)
        }
        restIntervalPlusB.setOnClickListener {
            plus10(restIntervalTV)
        }
        restIntervalMinusB.setOnClickListener {
            minus10(restIntervalTV)
        }
    }

    private fun plus10(textViewTime: TextView) {
        var currentTime = textViewTime.text.toString()
        var seconds = getTimeFromStr(currentTime).second
        var minutes = getTimeFromStr(currentTime).first
        if(seconds < 50) {
            seconds += 10
        }
        else if(seconds == 50) {
            seconds = 0
            minutes += 1
        }
        currentTime = String.format(FORMAT, minutes) + ":" + String.format(FORMAT, seconds)
        textViewTime.text = currentTime
    }

    private fun minus10(textViewTime: TextView) {
        var currentTime = textViewTime.text.toString()
        var seconds = getTimeFromStr(currentTime).second
        var minutes = getTimeFromStr(currentTime).first
        if(seconds > 0) {
            seconds -= 10
        }
        else if(seconds == 0) {
            if(minutes != 0) {
                seconds = 50
                minutes -= 1
            }
            else {
                seconds = 0
                minutes = 0
            }
        }
        currentTime = String.format(FORMAT, minutes) + ":" + String.format(FORMAT, seconds)
        textViewTime.text = currentTime
    }

    private fun plusOrMinus1(textViewSet: TextView, add: Boolean = true) {
        var currentSetNumber = textViewSet.text.toString().toInt()
        if(add) {
            currentSetNumber += 1
        }
        else if(currentSetNumber != 0 && !add) {
            currentSetNumber -= 1
        }
        else if(currentSetNumber == 0 && !add) {
            currentSetNumber = 0
        }
        textViewSet.text = currentSetNumber.toString()
    }

    private fun getTimeFromStr(timeStr: String) : Pair<Int, Int> {
        val tmpList = timeStr.split(":")
        val minutes = tmpList[0].toInt()
        val seconds = tmpList[1].toInt()
        return Pair(minutes, seconds)
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
}
