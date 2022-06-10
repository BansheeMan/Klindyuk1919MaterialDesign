package com.example.klindyuk1919materialdesign230522.view_viewmodel.les3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.klindyuk1919materialdesign230522.utils.EARTH
import com.example.klindyuk1919materialdesign230522.utils.MARS
import com.example.klindyuk1919materialdesign230522.utils.SOLAR_SYSTEM
import com.example.klindyuk1919materialdesign230522.view_viewmodel.EarthFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = arrayOf(
        SystemFragment.newInstance(),
        MarsFragment.newInstance(),
        EarthFragment.newInstance()
    )

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            EARTH -> EarthFragment.newInstance()
            SOLAR_SYSTEM -> SystemFragment.newInstance()
            MARS -> MarsFragment.newInstance()
            else -> {
                EarthFragment.newInstance()
            }
        }
    }


}