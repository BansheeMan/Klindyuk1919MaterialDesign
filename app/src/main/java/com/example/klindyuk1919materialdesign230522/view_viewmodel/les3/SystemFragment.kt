package com.example.klindyuk1919materialdesign230522.view_viewmodel.les3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.klindyuk1919materialdesign230522.databinding.FragmentSystemBinding


class SystemFragment : Fragment() {
    private var _binding: FragmentSystemBinding? = null
    private val binding: FragmentSystemBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SystemFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}