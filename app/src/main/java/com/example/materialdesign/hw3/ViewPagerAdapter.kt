package com.example.materialdesign.hw3

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.materialdesign.R
import com.example.materialdesign.hw3.planets_fragments.EarthFragment
import com.example.materialdesign.hw3.planets_fragments.MarsFragment
import com.example.materialdesign.hw3.planets_fragments.SystemFragment
import com.example.materialdesign.utils.EARTH
import com.example.materialdesign.utils.MARS
import com.example.materialdesign.utils.SOLAR_SYSTEM

class ViewPagerAdapter(fm: FragmentManager, val resources: Resources) :
    FragmentStatePagerAdapter(fm) {

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

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            EARTH -> resources.getString(R.string.earth)
            SOLAR_SYSTEM -> resources.getString(R.string.solar_system)
            MARS -> resources.getString(R.string.mars)
            else -> resources.getString(R.string.earth)
        }
    }
}