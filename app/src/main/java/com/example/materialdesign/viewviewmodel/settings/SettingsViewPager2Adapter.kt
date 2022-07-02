package com.example.materialdesign.viewviewmodel.settings

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.materialdesign.utils.EMPTY
import com.example.materialdesign.utils.THEMES
import com.example.materialdesign.viewviewmodel.EmptyFragment
import com.example.materialdesign.viewviewmodel.ThemesFragment

class SettingsViewPager2Adapter(fa: Fragment) : FragmentStateAdapter(fa) {

    private val fragments = arrayOf(
        ThemesFragment.newInstance(),
        EmptyFragment.newInstance(),
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            THEMES -> ThemesFragment.newInstance()
            EMPTY -> EmptyFragment.newInstance()
            else -> {
                ThemesFragment.newInstance()
            }
        }
    }

}