package com.example.materialdesign.view_viewmodel.main_activity

import android.os.Bundle
import com.example.materialdesign.R
import com.example.materialdesign.databinding.ActivityMainBinding
import com.example.materialdesign.hw3.Les3VPFragment
import com.example.materialdesign.hw4.WikiFragment
import com.example.materialdesign.utils.BUNDLE_KEY_BN
import com.example.materialdesign.view_viewmodel.nasa_requests.LifsFragment
import com.example.materialdesign.view_viewmodel.nasa_requests.PictureOfTheDayFragment
import com.example.materialdesign.view_viewmodel.settings.SettingsFragment
import com.google.android.material.badge.BadgeDrawable


class MainActivity : ThemesChanger() {

    private var currentItem = R.id.action_bottom_navigation_pod
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState != null) {
            currentItem = savedInstanceState.getInt(BUNDLE_KEY_BN)
        }
        initBottomNavigation()
        createBadge()
    }

    private fun createBadge() {
        binding.bottomNavigationActivity.getOrCreateBadge(R.id.action_bottom_navigation_settings)
            .apply {
                number = 5
                maxCharacterCount = 6
                badgeGravity = BadgeDrawable.TOP_END
            }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationActivity.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_bottom_navigation_pod -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.activity_container_view,
                            PictureOfTheDayFragment.newInstance()
                        ).commit()
                    true
                }
                R.id.action_bottom_navigation_hw4 -> {                              //HW
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_container_view, WikiFragment.newInstance())
                        .commit()
                    currentItem = R.id.action_bottom_navigation_hw4
                    true
                }
                R.id.action_bottom_navigation_lifs -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_container_view, LifsFragment.newInstance())
                        .commit()
                    currentItem = R.id.action_bottom_navigation_lifs
                    true
                }
                R.id.action_bottom_navigation_system -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_container_view, Les3VPFragment.newInstance())
                        .commit()
                    currentItem = R.id.action_bottom_navigation_system
                    true
                }
                R.id.action_bottom_navigation_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_container_view, SettingsFragment.newInstance())
                        .commit()
                    currentItem = R.id.action_bottom_navigation_settings
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.bottomNavigationActivity.selectedItemId = currentItem
    }
}