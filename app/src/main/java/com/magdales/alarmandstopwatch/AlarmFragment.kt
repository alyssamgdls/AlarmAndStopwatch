package com.magdales.alarmandstopwatch

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.support.v4.app.Fragment
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*



/**
 * Created by Lai on 2/11/2018.
 */
class AlarmFragment : Fragment() {

    lateinit var manager: AlarmManager
    lateinit var clock: TimePicker
    lateinit var update_text: TextView
    lateinit var btnSet: Button
    lateinit var btnStop: Button
    var hour: Int = 0
    var min: Int = 0
    lateinit var pI: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater!!.inflate(R.layout.alarm_fragment, container, false)

        manager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        clock = view.findViewById(R.id.clock) as TimePicker
        update_text = view.findViewById(R.id.update_text) as TextView
        btnSet = view.findViewById(R.id.set_button) as Button
        btnStop = view.findViewById(R.id.stop_button) as Button

        var calendar: Calendar = Calendar.getInstance()
        var myIntent: Intent = Intent(activity, AlarmReceiver::class.java)

        btnSet.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("NewApi")
            override fun onClick(v: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(Calendar.HOUR_OF_DAY, clock.hour)
                    calendar.set(Calendar.MINUTE, clock.minute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    hour = clock.hour
                    min = clock.minute
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, clock.currentHour)
                    calendar.set(Calendar.MINUTE, clock.currentMinute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    hour = clock.currentHour
                    min = clock.currentMinute
                }

                var hr_str: String = hour.toString()
                var min_str: String = min.toString()
                if (hour > 12) {
                    hr_str = (hour - 12).toString()
                }
                if (min < 10) {
                    min_str = "0$min"
                }
                set_alarm_text("Alarm set to: $hr_str: $min_str")
                myIntent.putExtra("extra", "on")
                pI = PendingIntent.getBroadcast(activity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                manager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pI)
            }
        })
        btnStop.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                set_alarm_text("Alarm off")
                pI = PendingIntent.getBroadcast(activity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                manager.cancel(pI)
                myIntent.putExtra("extra", "off")
                activity.sendBroadcast(myIntent)
            }

        })

        return view
    }

    private fun set_alarm_text(s: String) {
        update_text.setText(s)
    }
}