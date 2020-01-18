package alexandre.ladriere.intervaltimer

import alexandre.ladriere.intervaltimer.utils.convertMinutesToSeconds
import alexandre.ladriere.intervaltimer.utils.getTimeFromStr
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_timer.*


class TimerActivity : AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var timeTV: TextView
    private lateinit var stepTV: TextView
    private lateinit var stepCountTV: TextView
    private var setNumberIni: Int = 0
    private var workSecondsIni: Int = 0
    private var restSecondsIni: Int = 0
    private var setNumber: Int = 0
    private var workSeconds: Int = 0
    private var restSeconds: Int = 0
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        constraintLayout = a_timer_constraint_layout_main
        timeTV = a_timer_text_view_time
        stepTV = a_timer_text_view_step
        stepCountTV = a_timer_text_view_step_count
        getValues()
        iniWorkout()
    }

    private fun iniWorkout() {
        constraintLayout.setBackgroundResource(R.drawable.blue_gradient)
        stepCountTV.text = resources.getString(R.string.upper_set) + " " + setNumber.toString()
        stepTV.text = resources.getString(R.string.upper_get_ready)
        timeTV.text = resources.getString(R.string.ini_time)
        startTimer(6)
    }

    private fun getValues() {
        setNumberIni = intent.getIntExtra(INTENT_SET_NUMBER, 0)
        setNumber = setNumberIni
        workSecondsIni = intent.getIntExtra(INTENT_WORK_INTERVAL, 0)
        workSeconds = workSecondsIni
        restSecondsIni = intent.getIntExtra(INTENT_REST_INTERVAL, 0)
        restSeconds = restSecondsIni
    }

    private fun decreaseTV(textViewTime: TextView) {
        var currentTime = textViewTime.text.toString()
        var seconds = getTimeFromStr(currentTime).second
        var minutes = getTimeFromStr(currentTime).first
        if(seconds == 0 && minutes != 0) {
            seconds = 59
            minutes -= 1
        }
        else if(seconds == 0 && minutes == 0) {
            seconds = 0
            minutes = 0
        }
        else {
            seconds -= 1
        }
        currentTime = String.format(FORMAT, minutes) + ":" + String.format(FORMAT, seconds)
        textViewTime.text = currentTime
    }

    private fun startTimer(sec: Int) {
        timer = object : CountDownTimer((sec * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                decreaseTV(timeTV)
            }
            override fun onFinish() {}
        }
        (timer as CountDownTimer).start()
    }

    private fun cancelTimer() {
        timer?.cancel()
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
