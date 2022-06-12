package com.example.klindyuk1919materialdesign230522.view_viewmodel.picture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.BottomNavigationDrawerLayoutBinding
import com.example.klindyuk1919materialdesign230522.view_viewmodel.les3.ApiBottomFragment
import com.example.klindyuk1919materialdesign230522.view_viewmodel.les3.Les3VPFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationDrawerLayoutBinding? = null
    private val binding: BottomNavigationDrawerLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationDrawerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickNavigationFragment()
    }

    private fun clickNavigationFragment() {
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.hw3_vp -> {
                    Log.d("@@@", "hw3_vp")
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack("").replace(R.id.fragment_container_view, Les3VPFragment.newInstance())
                        .commit()
                }
                R.id.api_bottom_fragment -> {
                    Log.d("@@@", "api_bottom_fragment")
                    requireActivity().supportFragmentManager.beginTransaction()
                        .addToBackStack("").replace(R.id.fragment_container_view, ApiBottomFragment.newInstance())
                        .commit()
                }
            }
            dismiss()
            true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BottomNavigationDrawerFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}