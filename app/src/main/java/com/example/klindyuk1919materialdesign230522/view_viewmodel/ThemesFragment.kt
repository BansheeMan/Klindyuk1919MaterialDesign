package com.example.klindyuk1919materialdesign230522.view_viewmodel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentThemesBinding
import com.example.klindyuk1919materialdesign230522.utils.*
import com.example.klindyuk1919materialdesign230522.view_viewmodel.main_activity.MainActivity


class ThemesFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentThemesBinding? = null
    private val binding: FragmentThemesBinding
        get() = _binding!!

    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity =
            requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rbBase.setOnClickListener(this)
        binding.rbRed.setOnClickListener(this)
        binding.rbOrange.setOnClickListener(this)
        binding.rbGreen.setOnClickListener(this)
        binding.rbPurple.setOnClickListener(this)
        with(binding) {
            when (parentActivity.getCurrentTheme()) {
                THEME_BASE -> radioButtons.check(R.id.rb_base)
                THEME_RED -> radioButtons.check(R.id.rb_red)
                THEME_ORANGE -> radioButtons.check(R.id.rb_orange)
                THEME_GREEN -> radioButtons.check(R.id.rb_green)
                THEME_PURPLE -> radioButtons.check(R.id.rb_purple)
                else -> radioButtons.check(R.id.rb_base)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rb_base -> {
                parentActivity.setCurrentTheme(THEME_BASE)
                parentActivity.recreate()
            }
            R.id.rb_red -> {
                parentActivity.setCurrentTheme(THEME_RED)
                parentActivity.recreate()
            }
            R.id.rb_orange -> {
                parentActivity.setCurrentTheme(THEME_ORANGE)
                parentActivity.recreate()
            }
            R.id.rb_green -> {
                parentActivity.setCurrentTheme(THEME_GREEN)
                parentActivity.recreate()
            }
            R.id.rb_purple -> {
                parentActivity.setCurrentTheme(THEME_PURPLE)
                parentActivity.recreate()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThemesFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}