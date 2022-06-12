package com.example.klindyuk1919materialdesign230522.view_viewmodel.main_activity

import android.os.Bundle
import android.view.View
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.ActivityMainBinding
import com.example.klindyuk1919materialdesign230522.view_viewmodel.SettingsFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.picture.PictureOfTheDayFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.planets_fragments.SystemFragment
import com.google.android.material.badge.BadgeDrawable


class MainActivity : ThemesChanger() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.activity_container_view,
                PictureOfTheDayFragment.newInstance()
            ).commit()
        }
            initBottomNavigation()  //TODO при при пересоздании активити в смене темы - падает
        createBadge()

        //TODO Если закоментить initBottomNavigation()  то работает, а так при при пересоздании активити в смене темы - падает
        binding.set.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.activity_container_view, SettingsFragment.newInstance())
                .commit()
        }
    }

    private fun createBadge() {
        binding.bottomNavigationActivity.getOrCreateBadge(R.id.action_bottom_navigation_settings).apply {
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
                R.id.action_bottom_navigation_system -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_container_view, SystemFragment.newInstance())
                        .commit()
                    true
                }
                R.id.action_bottom_navigation_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_container_view, SettingsFragment.newInstance())
                        .addToBackStack("")
                        .commit()
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.bottomNavigationActivity.selectedItemId = R.id.action_bottom_navigation_pod
    }
}