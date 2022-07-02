package com.example.materialdesign.viewviewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialdesign.databinding.FragmentEmptyBinding


class EmptyFragment : Fragment() {
    private var _binding: FragmentEmptyBinding? = null
    private val binding: FragmentEmptyBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = EmptyFragment()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}