package com.magdales.alarmandstopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var tablayout: TabLayout
    lateinit var viewpager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        viewpager = findViewById(R.id.viewpager) as ViewPager
        setupViewPager(viewpager)
        tablayout = findViewById(R.id.tablayout) as TabLayout
        tablayout.setupWithViewPager(viewpager)
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AlarmFragment(),"Alarm")
        adapter.addFragment(StopwatchFragment(), "Stopwatch")
        viewpager.adapter = adapter
    }
}
