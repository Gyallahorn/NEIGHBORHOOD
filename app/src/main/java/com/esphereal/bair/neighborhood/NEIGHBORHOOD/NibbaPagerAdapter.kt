package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class NibbaPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                NibbaNewsFragment()
            }
           /* 1 ->
                ololo()*/
            else -> {
                  SecondFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "FirsTab"
            2 -> "SecondTab"
            else  -> {return "ThirdTab"}
        }
    }
}