package com.example.materialdesign.view_viewmodel.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentSettingsBinding
import com.example.materialdesign.utils.EMPTY
import com.example.materialdesign.utils.THEMES
import com.google.android.material.tabs.TabLayoutMediator


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager2()
    }

    private fun initViewPager2() {
        binding.viewPager2Settings.adapter = SettingsViewPager2Adapter(this)
        TabLayoutMediator(
            binding.tabLayoutSettings, binding.viewPager2Settings
        ) { tab, position ->
            tab.text = when (position) {
                THEMES -> resources.getString(R.string.themes)
                EMPTY -> resources.getString(R.string.empty_fragment)
                else -> resources.getString(R.string.themes)
                //TODO Уточнить как TextView привязать и менять динамически (aналогично с BottomNavigation)
            }
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}