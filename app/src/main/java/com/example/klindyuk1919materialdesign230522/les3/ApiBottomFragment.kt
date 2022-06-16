package com.example.klindyuk1919materialdesign230522.les3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentApiBottomBinding
import com.example.klindyuk1919materialdesign230522.les3.planets_fragments.EarthFragment
import com.example.klindyuk1919materialdesign230522.les3.planets_fragments.MarsFragment
import com.example.klindyuk1919materialdesign230522.les3.planets_fragments.SystemFragment
import com.google.android.material.badge.BadgeDrawable


class ApiBottomFragment : Fragment() {
    private var _binding: FragmentApiBottomBinding? = null
    private val binding: FragmentApiBottomBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        createBadge()
    }

    private fun createBadge() {
        binding.bottomNavigation2.getOrCreateBadge(R.id.action_bottom_navigation_mars).apply {
            number = 100
            maxCharacterCount = 6
            badgeGravity = BadgeDrawable.TOP_END
            //binding.bottomNavigation.removeBadge(R.id.action_bottom_navigation_mars)
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation2.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_bottom_navigation_earth -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view2, EarthFragment.newInstance())
                        .commit()
                    true
                }
                R.id.action_bottom_navigation_system -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view2, SystemFragment.newInstance())
                        .commit()
                    false // выключает пункт меню
                }
                R.id.action_bottom_navigation_mars -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view2, MarsFragment.newInstance()).commit()
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.bottomNavigation2.selectedItemId = R.id.action_bottom_navigation_earth

        //binding.bottomNavigation.menu.removeItem(R.id.action_bottom_navigation_earth)
        //binding.bottomNavigation.menu.removeItem(R.id.action_bottom_navigation_system)
        binding.bottomNavigation2.inflateMenu(R.menu.menu_bottom_navigation_drawer)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApiBottomFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}