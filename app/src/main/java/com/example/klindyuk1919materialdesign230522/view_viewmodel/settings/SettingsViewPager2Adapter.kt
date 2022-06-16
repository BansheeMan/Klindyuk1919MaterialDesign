package com.example.klindyuk1919materialdesign230522.view_viewmodel.settings

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.klindyuk1919materialdesign230522.utils.EMPTY
import com.example.klindyuk1919materialdesign230522.utils.THEMES
import com.example.klindyuk1919materialdesign230522.view_viewmodel.EmptyFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.ThemesFragment

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