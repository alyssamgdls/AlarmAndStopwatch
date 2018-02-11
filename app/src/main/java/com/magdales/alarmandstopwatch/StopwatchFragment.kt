package com.magdales.alarmandstopwatch

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.os.Bundle
import android.widget.ArrayAdapter
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

/**
 * Created by Lai on 2/11/2018.
 */
class StopwatchFragment : Fragment() {

    internal lateinit var textView: TextView

    internal lateinit var startButton: Button
    internal lateinit var pauseButton: Button
    internal lateinit var resetButton: Button
    internal lateinit var saveLapButton: Button

    internal var MillisecondTime: Long = 0
    internal var StartTime: Long = 0
    internal var TimeBuff: Long = 0
    internal var UpdateTime = 0L

    internal var Seconds: Int = 0
    internal var Minutes: Int = 0
    internal var MilliSeconds: Int = 0

    internal lateinit var listView: ListView
    internal lateinit var handler: Handler

    internal var ListElements = arrayOf<String>()
    internal lateinit var ListElementsArrayList: MutableList<String>
    internal lateinit var adapter: ArrayAdapter<String>

    var runnable: Runnable = object : Runnable {

        override fun run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime
            UpdateTime = TimeBuff + MillisecondTime
            Seconds = (UpdateTime / 1000).toInt()
            Minutes = Seconds / 60
            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 1000).toInt()
            MilliSeconds = MilliSeconds / 10

            textView.text = ("" + java.lang.String.format("%02d", Minutes) + ":"
                    + java.lang.String.format("%02d", Seconds) + "."
                    + MilliSeconds)

            handler.postDelayed(this, 0)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater!!.inflate(R.layout.stopwatch_fragment, container, false)

        textView = view.findViewById(R.id.stopwatch) as TextView
        startButton = view.findViewById(R.id.start_button) as Button
        pauseButton = view.findViewById(R.id.pause_button) as Button
        resetButton = view.findViewById(R.id.reset_button) as Button
        saveLapButton = view.findViewById(R.id.saveLap_button) as Button
        listView = view.findViewById(R.id.listview) as ListView

        handler = Handler()

        ListElementsArrayList = ArrayList(Arrays.asList(*ListElements))
        adapter = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        )
        listView.adapter = adapter

        startButton.setOnClickListener {
            StartTime = SystemClock.uptimeMillis()
            handler.postDelayed(runnable, 0)

            resetButton.isEnabled = false
        }

        pauseButton.setOnClickListener {
            TimeBuff += MillisecondTime

            handler.removeCallbacks(runnable)

            resetButton.isEnabled = true
        }

        resetButton.setOnClickListener {
            MillisecondTime = 0L
            StartTime = 0L
            TimeBuff = 0L
            UpdateTime = 0L
            Seconds = 0
            Minutes = 0
            MilliSeconds = 0

            textView.text = "00:00.00"

            ListElementsArrayList.clear()

            adapter.notifyDataSetChanged()
        }

        saveLapButton.setOnClickListener {
            ListElementsArrayList.add(textView.text.toString())

            adapter.notifyDataSetChanged()
        }

        return view
    }
}