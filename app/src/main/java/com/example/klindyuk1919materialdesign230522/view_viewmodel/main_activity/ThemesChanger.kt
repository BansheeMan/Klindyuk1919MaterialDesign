package com.example.klindyuk1919materialdesign230522.view_viewmodel.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.utils.*

open class ThemesChanger : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
    }

    fun getCurrentTheme(): Int {
        val shp = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return shp.getInt(KEY_CURRENT_THEME, -1)
    }

    fun setCurrentTheme(currentTheme: Int) {
        val shp = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = shp.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            THEME_BASE -> R.style.MyBaseTheme
            THEME_RED -> R.style.MyRedTheme
            THEME_ORANGE -> R.style.MyOrangeTheme
            THEME_GREEN -> R.style.MyGreenTheme
            THEME_PURPLE -> R.style.MyPurpleTheme
            else -> 0
        }
    }
}