package com.example.klindyuk1919materialdesign230522.view_viewmodel.les3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentLes3VPBinding


class Les3VPFragment : Fragment() {
    private var _binding: FragmentLes3VPBinding? = null
    private val binding: FragmentLes3VPBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLes3VPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager,
            resources
        ) //TODO Законно ли это?
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_system)
        binding.tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_mars)
    }

    companion object {
        @JvmStatic
        fun newInstance() = Les3VPFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}