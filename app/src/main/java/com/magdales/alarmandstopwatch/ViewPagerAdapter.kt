package com.magdales.alarmandstopwatch

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Lai on 2/11/2018.
 */
class ViewPagerAdapter : FragmentPagerAdapter {

    private val mFragmentList : MutableList<Fragment> = mutableListOf()
    private val mFragmentTitleList: MutableList<String> = mutableListOf()

    constructor(manager: FragmentManager) : super(manager) {

    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList.get(position)
    }
}
