package alexandre.ladriere.intervaltimer

import alexandre.ladriere.intervaltimer.utils.convertMinutesToSeconds
import alexandre.ladriere.intervaltimer.utils.getTimeFromStr
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


const val FORMAT = "%02d"
const val INTENT_SET_NUMBER = "sets_number"
const val INTENT_WORK_INTERVAL = "work_interval"
const val INTENT_REST_INTERVAL = "rest_interval"

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
        startB.setOnClickListener {
            val setNumber: Int = setNumberTV.text.toString().toInt()
            val workSeconds: Int = convertMinutesToSeconds(
                getTimeFromStr(workIntervalTV.text.toString()).first,
                getTimeFromStr(workIntervalTV.text.toString()).second
            )
            val restSeconds: Int = convertMinutesToSeconds(
                getTimeFromStr(restIntervalTV.text.toString()).first,
                getTimeFromStr(restIntervalTV.text.toString()).second
            )
            val intent = Intent(this, TimerActivity::class.java)
            intent.putExtra(INTENT_SET_NUMBER, setNumber)
            intent.putExtra(INTENT_WORK_INTERVAL, workSeconds)
            intent.putExtra(INTENT_REST_INTERVAL, restSeconds)
            this.startActivity(intent)
        }
        iniButtonListener()
    }

    private fun iniButtonListener() {
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

        setButtonLongClick(restIntervalPlusB, restIntervalTV)
        setButtonLongClick(workIntervalPlusB, workIntervalTV)
        setButtonLongClick(restIntervalMinusB, restIntervalTV, add = false)
        setButtonLongClick(workIntervalMinusB, workIntervalTV, add = false)
        setButtonLongClick(setNumberMinusB, setNumberTV, add = false, time = false)
        setButtonLongClick(setNumberPlusB, setNumberTV, add = true, time = false)
    }

    private fun plus10(textViewTime: TextView) {
        var currentTime = textViewTime.text.toString()
        var seconds = getTimeFromStr(currentTime).second
        var minutes = getTimeFromStr(currentTime).first
        if (seconds < 50) {
            seconds += 10
        } else if (seconds == 50) {
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
        if (seconds > 0) {
            seconds -= 10
        } else if (seconds == 0) {
            if (minutes != 0) {
                seconds = 50
                minutes -= 1
            } else {
                seconds = 0
                minutes = 0
            }
        }
        currentTime = String.format(FORMAT, minutes) + ":" + String.format(FORMAT, seconds)
        textViewTime.text = currentTime
    }

    private fun plusOrMinus1(textViewSet: TextView, add: Boolean = true) {
        var currentSetNumber = textViewSet.text.toString().toInt()
        if (add) {
            currentSetNumber += 1
        } else if (currentSetNumber != 0 && !add) {
            currentSetNumber -= 1
        } else if (currentSetNumber == 0 && !add) {
            currentSetNumber = 0
        }
        textViewSet.text = currentSetNumber.toString()
    }

    private fun setButtonLongClick(
        button: ImageButton,
        textView: TextView,
        add: Boolean = true,
        time: Boolean = true
    ) {
        button.setOnLongClickListener(object : OnLongClickListener {
            private val mHandler: Handler = Handler()
            private val incrementRunnable: Runnable = object : Runnable {
                override fun run() {
                    mHandler.removeCallbacks(this)
                    if (button.isPressed) {
                        if (time) {
                            if (add) {
                                plus10(textView)
                            } else {
                                minus10(textView)
                            }
                        } else {
                            plusOrMinus1(textView, add)
                        }
                        mHandler.postDelayed(this, 100)
                    }
                }
            }

            override fun onLongClick(view: View): Boolean {
                mHandler.postDelayed(incrementRunnable, 0)
                return true
            }
        })
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
