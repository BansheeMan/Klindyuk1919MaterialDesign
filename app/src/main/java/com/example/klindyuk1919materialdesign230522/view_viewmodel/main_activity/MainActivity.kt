package com.example.klindyuk1919materialdesign230522.view_viewmodel.main_activity

import android.os.Bundle
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.ActivityMainBinding
import com.example.klindyuk1919materialdesign230522.utils.BUNDLE_KEY_BN
import com.example.klindyuk1919materialdesign230522.view_viewmodel.nasaRequestFragments.EpicFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.nasaRequestFragments.LifsFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.nasaRequestFragments.PictureOfTheDayFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.les3.Les3VPFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.settings.SettingsFragment
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
                R.id.action_bottom_navigation_epic -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_container_view, EpicFragment.newInstance())
                        .commit()
                    currentItem = R.id.action_bottom_navigation_epic
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