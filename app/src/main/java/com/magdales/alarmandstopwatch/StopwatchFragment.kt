package com.magdales.alarmandstopwatch

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

/**
 * Created by Lai on 2/11/2018.
 */
class StopwatchFragment : Fragment() {

    var handler: Handler? = null
    var hour: TextView? = null
    var minute: TextView? = null
    var seconds: TextView? = null
    var milli_seconds: TextView? = null

    var MillisecondTime: Long = 0
    var StartTime: Long = 0
    var TimeBuff: Long = 0
    var UpdateTime = 0L

    var Seconds: Int = 0
    var Minutes: Int = 0
    var MilliSeconds: Int = 0

    var flag: Boolean = false

    var startButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater!!.inflate(R.layout.stopwatch_fragment, container, false)

        startButton = activity.findViewById(R.id.startButton)
        hour = activity.findViewById(R.id.hour)
        minute = activity.findViewById(R.id.minute)
        seconds = activity.findViewById(R.id.seconds)
        milli_seconds = activity.findViewById(R.id.milli_second)

        startButton?.setOnClickListener {
            if(flag){
                handler?.removeCallbacks(runnable)
                startButton?.setImageResource(R.drawable.ic_play)
                flag=false
            } else {
                startButton?.setImageResource(R.drawable.pause)
                StartTime = SystemClock.uptimeMillis()
                handler?.postDelayed(runnable, 0)
                flag = true
            }
        }

        handler = Handler()


        return view
    }

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 1000).toInt()


            if (Minutes.toString().length < 2) {
                minute?.text = "0" + Minutes.toString()
            } else {
                minute?.text = Minutes.toString()
            }
            if (Seconds.toString().length < 2) {
                seconds?.text = "0" + Seconds.toString()
            } else {
                seconds?.text = Seconds.toString()
            }

            milli_seconds?.text = MilliSeconds.toString()

            handler?.postDelayed(this, 0)
        }

    }
}