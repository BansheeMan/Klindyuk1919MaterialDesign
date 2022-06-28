package com.example.materialdesign.view_viewmodel.nasa_requests

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
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentPictureOfTheDayBinding
import com.example.materialdesign.utils.*
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
        viewModel.sendRequestPOD(YESTERDAY)
        findWiki()
        stateBottomSheetBehavior()
        switchChipGroup()
    }

    private fun switchChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { group, position ->
            val chip = group.findViewById<Chip>(position)
            when (chip?.tag) {
                "chip1" -> {
                    viewModel.sendRequestPOD(YESTERDAY)
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
            BottomSheetBehavior.STATE_HIDDEN //берет стартовую точку от app:behavior_peekHeight в XML
        bottomSheetBehavior.peekHeight *= 2
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
                    if (appState.pictureOfTheDayResponseData.mediaType == VIDEO) {
                        val url = appState.pictureOfTheDayResponseData.hdurl
                        if (url.isNullOrEmpty()) {
                            val videoUrl = appState.pictureOfTheDayResponseData.url
                            showAVideoUrl(videoUrl)
                        }
                    } else {
                        imageView.load(appState.pictureOfTheDayResponseData.url) {
                            placeholder(R.drawable.hello)
                            transformations(CircleCropTransformation())
                            crossfade(2000) //плавный переход, изменение непрозрачности
                            scale(Scale.FILL) //заполняет картинку на весь ImageView, решает проблему c crossfade()
                        }
                    }
                    hackBsl.title.text = appState.pictureOfTheDayResponseData.title
                    hackBsl.explanation.text = appState.pictureOfTheDayResponseData.explanation
                }
            }
        }
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        imageView.visibility = View.GONE
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = String.format(getString(R.string.video_today), videoUrl)
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
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
