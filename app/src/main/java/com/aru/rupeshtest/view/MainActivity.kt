package com.aru.rupeshtest.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.os.CountDownTimer
import android.view.View
import java.util.concurrent.TimeUnit
import com.aru.rupeshtest.R
import com.aru.rupeshtest.util.NotificationUtil

class MainActivity : AppCompatActivity() {
    var isStart_Stop:Boolean =true
    var countDownTimer: CountDownTimer? = null
    var hms:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start_timer.setOnClickListener {
            if(isStart_Stop){
                isStart_Stop =false
                btn_start_timer.text = resources.getString(R.string.btn_title_stop)
                btn_start_timer.setBackgroundColor(Color.RED)
                tv_timeup.visibility = View.GONE
                startTimer()
            }else{
                isStart_Stop =true
                btn_start_timer.text = resources.getString(R.string.btn_title_start)
                btn_start_timer.setBackgroundColor(Color.GREEN)
                stopTimer()
                NotificationUtil.CancelNotification(this@MainActivity)
            }

        }

    }

    private fun stopTimer() {
        /*
        * cancel Count down timer remember countdown time must not be null
        * */
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
            tv_mm.text =resources.getString(R.string.txt_mm)

        }
    }

    private fun startTimer() {
        /*convert minuts to miliseconds*/
        val strMinute = 2 * 60 * 1000
        countDownTimer = object : CountDownTimer(strMinute.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var millis = millisUntilFinished
                hms = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millis)
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)
                    )

                )
                /*set notification for Timer
                * */
                NotificationUtil.setNotification(hms!!,this@MainActivity)
                tv_mm.text =hms

            }
            override fun onFinish() {
                tv_mm.text =resources.getString(R.string.txt_mm)
                tv_timeup.visibility = View.VISIBLE
                tv_timeup.text = resources.getString(R.string.txt_timeup)
                countDownTimer = null
                isStart_Stop =true
                btn_start_timer.text = resources.getString(R.string.btn_title_start)
                btn_start_timer.setBackgroundColor(Color.GREEN)
                NotificationUtil.CancelNotification(this@MainActivity)
            }
        }.start()
    }

}

