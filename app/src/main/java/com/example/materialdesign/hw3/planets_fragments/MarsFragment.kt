package com.example.materialdesign.hw3.planets_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialdesign.databinding.FragmentMarsBinding


class MarsFragment : Fragment() {
    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}