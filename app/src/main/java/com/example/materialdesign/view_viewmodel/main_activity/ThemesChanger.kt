package com.example.materialdesign.view_viewmodel.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.materialdesign.R
import com.example.materialdesign.utils.*

open class ThemesChanger : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNightMode(getCurrentMode())
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

    private fun setNightMode(boolean: Boolean) {
        when (boolean) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getCurrentMode(): Boolean {
        val shp = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return shp.getBoolean(KEY_CURRENT_MODE, false)
    }

    fun setCurrentMode(currentMode: Boolean) {
        val shp = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = shp.edit()
        editor.putBoolean(KEY_CURRENT_MODE, currentMode)
        editor.apply()
    }
}