package com.example.materialdesign.hw5

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.example.materialdesign.databinding.FragmentAnimationTwoBinding

class AnimationFragmentTwo : Fragment() {

    private var _binding: FragmentAnimationTwoBinding? = null
    private val binding: FragmentAnimationTwoBinding
        get() = _binding!!

    private val duration = 1000L
    private var isOpenImageView: Boolean = false
    private var isOpenFab: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFab()
        initOptionMenu()
        imageViewZoom()

    }

    private fun imageViewZoom() {
        binding.imageView.setOnClickListener {
            isOpenImageView = !isOpenImageView
            val trCB = ChangeBounds()
            val trImage = ChangeImageTransform()
            trCB.duration = 3000
            trImage.duration = 3000
            val trSet = TransitionSet().apply {
                addTransition(trCB)    //ScaleType
                addTransition(trImage)  //изменение размеров в лэйауте (меняется height)
            }
            TransitionManager.beginDelayedTransition(binding.root, trSet)

            binding.imageView.scaleType = if (isOpenImageView) {
                ImageView.ScaleType.CENTER_CROP
            } else {
                ImageView.ScaleType.CENTER_INSIDE
            }

            val params = (binding.imageView.layoutParams as FrameLayout.LayoutParams)
            params.height = if (isOpenImageView) {
                FrameLayout.LayoutParams.MATCH_PARENT
            } else {
                FrameLayout.LayoutParams.WRAP_CONTENT
            }
            binding.imageView.layoutParams = params
        }
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            isOpenFab = !isOpenFab
            if (isOpenFab) {
                openFab()
            } else {
                closeFab()
            }
        }
    }

    private fun initOptionMenu() {
        binding.optionOneContainer.setOnClickListener { //когда вешаем слушатель isClickable = true
            Toast.makeText(requireContext(), "optionOneContainer", Toast.LENGTH_SHORT).show()

        }
        binding.optionTwoContainer.setOnClickListener {
            Toast.makeText(requireContext(), "optionTwoContainer", Toast.LENGTH_SHORT).show()

        }
        binding.optionOneContainer.isClickable = false
        binding.optionTwoContainer.isClickable = false
    }

    private fun closeFab() {
        binding.imageView.isClickable = true
        ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 135f, 0f)
            .setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -265f, 0f)
            .setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -380f, 0f)
            .setDuration(duration).start()

        binding.optionTwoContainer.animate().alpha(0f).setDuration(duration)
            .setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.optionTwoContainer.isClickable = false
                }
            })

        binding.optionOneContainer.animate().alpha(0f).setDuration(duration)
            .setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.optionOneContainer.isClickable = false
                    Log.d("@@@", "${binding.optionOneContainer.isClickable}")

                }
            })

        binding.transparentBackground.animate().alpha(0.0f).duration = duration
    }

    private fun openFab() {
        binding.imageView.isClickable = false
        ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 135f)
            .setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f, -265f)
            .setDuration(duration).start()
        ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f, -380f)
            .setDuration(duration).start()

        binding.optionTwoContainer.animate().alpha(1f).setDuration(duration)
            .setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.optionTwoContainer.isClickable = true
                }
            })

        binding.optionOneContainer.animate().alpha(1f).setDuration(duration)
            .setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.optionOneContainer.isClickable = true
                    Log.d("@@@", "${binding.optionOneContainer.isClickable}")

                }
            })

        binding.transparentBackground.animate().alpha(0.6f).duration = duration
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AnimationFragmentTwo()
    }
}
