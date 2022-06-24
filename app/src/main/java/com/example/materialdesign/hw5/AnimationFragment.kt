package com.example.materialdesign.hw5

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentAnimationStartBinding


class AnimationFragment : Fragment() {

    private var _binding: FragmentAnimationStartBinding? = null
    private val binding: FragmentAnimationStartBinding
        get() = _binding!!

    var isOpen: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStartBTN()
    }

    private fun initStartBTN() {
        binding.swBtn.setOnClickListener {
            val trFade = Fade()
            trFade.duration = 1500
            TransitionManager.beginDelayedTransition(binding.frameBtn, trFade)
            binding.frameBtn.visibility = View.GONE

            Handler(Looper.getMainLooper()).postDelayed({
                binding.tvSw.animate().alpha(1f).duration = 3000
            }, 1500)

            Handler(Looper.getMainLooper()).postDelayed({
                binding.tvSw.animate().alpha(0f).duration = 2500
            }, 5000)

            Handler(Looper.getMainLooper()).postDelayed({
                val constraintSet = ConstraintSet()
                constraintSet.clone(binding.animationContainer)
                val trCB = ChangeBounds()
                trCB.duration = 18000
                TransitionManager.beginDelayedTransition(binding.animationContainer, trCB)
                constraintSet.clone(requireContext(), R.layout.fragment_animation_end)
                constraintSet.applyTo(binding.animationContainer)
            }, 4000)
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AnimationFragment()
    }
}
