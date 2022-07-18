package com.example.materialdesign.viewviewmodel.nasarequests

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.text.set
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
        // viewModel.sendRequestPOD(TODAY)
        findWiki()
        stateBottomSheetBehavior()
        switchChipGroup()
        hw7()
        hw7Rainbow()
    }



    private fun hw7Rainbow() {
        val arrColor =
            arrayOf(R.color.r, R.color.o, R.color.y, R.color.g, R.color.b, R.color.db, R.color.p)
        val myText = getText(R.string.middle_text)
        val spannableString = SpannableString(myText)
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            spannableString.length,
            SpannedString.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableString.setSpan(
            ScaleXSpan(2f),
            0,
            myText.length,
            SpannedString.SPAN_INCLUSIVE_INCLUSIVE
        )

        var currentColor = 0
        for ((ind, elem) in myText.withIndex()) {
            val color = ContextCompat.getColor(requireContext(), arrColor[currentColor])
            spannableString.setSpan(ForegroundColorSpan(color), ind, ind+1, SpannedString.SPAN_INCLUSIVE_INCLUSIVE)
            if (currentColor == arrColor.size-1) currentColor = 0 else currentColor++
        }
        binding.hackBsl.explanation.text = spannableString
    }

    private fun hw7() {
        val findWord = "MATERIAL"
        val a = "MY $findWord DESIGN"
        val b = "$findWord MY DESIGN"
        val c = "MY DESIGN $findWord"

        val titleSpanText = c //a //b //c

        val arr = titleSpanText.split(" ")
        var newStr = ""
        var position = 0
        for (item in arr) {
            newStr += "$item "
            if (item == findWord) position = newStr.length
        }
        val spannableStringBuilder = SpannableStringBuilder(newStr)

        val color = ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
        spannableStringBuilder.setSpan(
            BackgroundColorSpan(color),
            position - 1 - findWord.length,
            position,
            SpannedString.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.insert(position, "Surprise ")
        spannableStringBuilder.setSpan(
            UnderlineSpan(),
            0,
            spannableStringBuilder.length,
            SpannedString.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannableStringBuilder.setSpan(
            ScaleXSpan(6f),
            0,
            arr[0].length,
            SpannedString.SPAN_INCLUSIVE_INCLUSIVE
        )

        val blurMaskFilter = BlurMaskFilter(1f, BlurMaskFilter.Blur.SOLID)
        spannableStringBuilder.setSpan(
            MaskFilterSpan(blurMaskFilter),
            arr[0].length + 1,
            arr[0].length + 1 + arr[1].length,
            SpannedString.SPAN_INCLUSIVE_INCLUSIVE
        )
        binding.hackBsl.title.text = spannableStringBuilder.trim()
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
            BottomSheetBehavior.STATE_EXPANDED //берет стартовую точку от app:behavior_peekHeight в XML
        //bottomSheetBehavior.peekHeight *= 2

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
        //##########################################################################################

        //##########################################################################################
        binding.hackBsl.explanation.text = getText(R.string.middle_text)
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
