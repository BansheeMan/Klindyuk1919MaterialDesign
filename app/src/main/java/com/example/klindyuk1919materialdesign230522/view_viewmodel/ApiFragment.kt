package com.example.klindyuk1919materialdesign230522.view_viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentApiBinding
import com.example.klindyuk1919materialdesign230522.view_viewmodel.picture.PictureOfTheDayFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.planets_fragments.EarthFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.planets_fragments.MarsFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.planets_fragments.SystemFragment
import com.google.android.material.badge.BadgeDrawable


class ApiFragment : Fragment() {
    private var _binding: FragmentApiBinding? = null
    private val binding: FragmentApiBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        createBadge()
    }

    private fun createBadge() {
        binding.bottomNavigation.getOrCreateBadge(R.id.action_bottom_navigation_settings).apply {
            number = 5
            maxCharacterCount = 6
            badgeGravity = BadgeDrawable.TOP_END
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_bottom_navigation_pod -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, PictureOfTheDayFragment.newInstance()).commit()
                    true
                }
                R.id.action_bottom_navigation_system -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, SystemFragment.newInstance())
                        .commit()
                    true
                }
                R.id.action_bottom_navigation_settings -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, SettingsFragment.newInstance()).commit()
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.bottomNavigation.selectedItemId = R.id.action_bottom_navigation_pod
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApiFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}