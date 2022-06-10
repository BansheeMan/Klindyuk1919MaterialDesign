package com.example.klindyuk1919materialdesign230522.view_viewmodel.main_activity

import android.os.Bundle
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.ActivityMainBinding
import com.example.klindyuk1919materialdesign230522.view_viewmodel.picture.PictureOfTheDayFragment


class MainActivity : ThemesChanger() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                PictureOfTheDayFragment.newInstance()
            ).commit()
        }
    }
}