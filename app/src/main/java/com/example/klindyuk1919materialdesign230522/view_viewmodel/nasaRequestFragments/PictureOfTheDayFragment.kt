package com.example.klindyuk1919materialdesign230522.view_viewmodel.nasaRequestFragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.klindyuk1919materialdesign230522.R
import com.example.klindyuk1919materialdesign230522.databinding.FragmentPictureOfTheDayBinding
import com.example.klindyuk1919materialdesign230522.utils.*
import com.example.klindyuk1919materialdesign230522.view_viewmodel.NasaRequestViewModel
import com.example.klindyuk1919materialdesign230522.view_viewmodel.NasaRequestAppState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding: FragmentPictureOfTheDayBinding
        get() = _binding!!

    private val viewModel: NasaRequestViewModel by lazy {
        ViewModelProvider(this).get(NasaRequestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.sendRequestPOD(TODAY)
        findWiki()
        stateBottomSheetBehavior()
        switchChipGroup()
    }

    private fun switchChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { group, position ->
            val chip = group.findViewById<Chip>(position)
            when (chip?.tag) {
                "chip1" -> {
                    viewModel.sendRequestPOD(TODAY)
                }
                "chip2" -> {
                    viewModel.sendRequestPOD(YESTERDAY)
                }
                "chip3" -> {
                    viewModel.sendRequestPOD(TWO_DAYS_AGO)
                }
            }
        }
    }

    private fun stateBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.hackBsl.bottomSheetContainer)
        bottomSheetBehavior.state =
            BottomSheetBehavior.STATE_COLLAPSED //берет стартовую точку от app:behavior_peekHeight в XML
    }

    private fun findWiki() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_DOMAIN${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun renderData(appState: NasaRequestAppState) {
        when (appState) {
            is NasaRequestAppState.Error -> {
                with(binding) {
                    val trouble = appState.error.message
                    successView.visibility = View.GONE
                    error.errorView.visibility = View.VISIBLE
                    error.errorView.showSnackBar(trouble, R.string.exit) {
                        requireActivity().finish()
                    }
                }
            }
            is NasaRequestAppState.Loading -> {
                with(binding) {
                    imageView.scaleType = ImageView.ScaleType.CENTER
                    imageView.load(R.drawable.progress_animation)
                }
            }
            is NasaRequestAppState.SuccessPOD -> {
                with(binding) {
                    error.errorView.visibility = View.GONE
                    successView.visibility = View.VISIBLE
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageView.load(appState.pictureOfTheDayResponseData.url) {
                        placeholder(R.drawable.hello)
                        transformations(CircleCropTransformation())
                        crossfade(2000) //плавный переход, изменение непрозрачности
                        scale(Scale.FILL) //заполняет картинку на весь ImageView, решает проблему c crossfade()
                    }
                    hackBsl.title.text = appState.pictureOfTheDayResponseData.title
                    hackBsl.explanation.text = appState.pictureOfTheDayResponseData.explanation
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayFragment()
    }
}
